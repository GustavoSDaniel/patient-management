package com.gustavosdaniel.stack;

import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ecs.Protocol;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.msk.CfnCluster;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.route53.CfnHealthCheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocalStack extends Stack {

    private final Vpc vpc;
    private final Cluster ecsCluster;

    public LocalStack(final App scope, final String id, final StackProps stackProps) {
        super(scope, id, stackProps);

        // Cria a rede virtual (VPC)
        this.vpc = createVpc();

        // Cria as instâncias de banco de dados
        DatabaseInstance authServiceDb = createDatabase("AuthServiceDB", "auth-service-db");
        DatabaseInstance patientServiceDb =
                createDatabase("PatientServiceDB", "patient-service-db");

        // Cria "Health Checks" que monitoram se os bancos de dados estão acessíveis.
        CfnHealthCheck authDbHealthCheck =
                createDbHealthCheck(authServiceDb, "AuthServiceDNHealthCheck");
        CfnHealthCheck patientDbHealthCheck =
                createDbHealthCheck(patientServiceDb, "PatientServiceDNHealthCheck");

        // Cria um cluster de Kafka (MSK) para a comunicação assíncrona entre os serviços.
        CfnCluster mksCluster = createMskCluster();

        // Cria o Cluster ECS, que é o orquestrador responsável por rodar e gerenciar os contêineres.
        this.ecsCluster = createEcsCluster();

        // Cria os serviços conteinerizados (microserviços) usando Fargate.
        FargateService authService =
                createFargateService("AuthService",
                        "auth-service",
                        List.of(9002),
                        authServiceDb,
                        Map.of("JWT_SECRET", "'wJk/dG8xH+aVbNpL6fRzV2yZ4sA7eFjQ5gU9hC3iKlE='"));

        authService.getNode().addDependency(authDbHealthCheck);
        authService.getNode().addDependency(authServiceDb);

        FargateService billingService =
                createFargateService("BillingService",
                        "billing-service",
                        // Porta HTTP e porta gRPC
                        List.of(8081, 9092),
                        null,
                        null);

        FargateService analytics = createFargateService("AnalyticsService",
                "analytics-service",
                List.of(8095),
                null,
                null);
        // Serviço de analytics depende do cluster Kafka para funcionar.
        analytics.getNode().addDependency(mksCluster);

        FargateService patientService = createFargateService("PatientService",
                "patient-service",
                List.of(8080),
                patientServiceDb,
                Map.of(
                        "BILLING_SERVICE_ADDRESS",
                        "host.docker.internal",
                        "BILLING_SERVICE_GRPC_PORT",
                        "8081"
                ));
        // Define as dependências do serviço de pacientes.
        patientService.getNode().addDependency(patientServiceDb);
        patientService.getNode().addDependency(billingService);
        patientService.getNode().addDependency(patientDbHealthCheck);

        // Cria o serviço do API Gateway
        createApiGatewayService();

    }

    private Vpc createVpc() {
        return Vpc.Builder.create(this, "PatientManagementVPC")
                .vpcName("PatientManagementVPC")
                .maxAzs(2) // Usa no máximo 2 Zonas de Disponibilidade para alta disponibilidade.
                .build();
    }

    private DatabaseInstance createDatabase(String id, String dbName){
        return DatabaseInstance.Builder.
                create(this, id)
                .engine(DatabaseInstanceEngine.postgres(
                        PostgresInstanceEngineProps.builder().version(PostgresEngineVersion.VER_16)
                                .build()))
                .vpc(vpc)
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO)) //MAQUINA MAIS FRACA
                .allocatedStorage(28) // 28GB de memoria
                .credentials(Credentials.fromGeneratedSecret("admin_user"))
                .databaseName(dbName)
                .removalPolicy(RemovalPolicy.DESTROY) // Apaga o banco de dados se a stack for destruída.
                .build();
    }


    // MONITORA A "SAUDE DO BANCO DE DADOS"
    private CfnHealthCheck createDbHealthCheck(DatabaseInstance db, String id) {
        return CfnHealthCheck.Builder
                .create(this, id)
                .healthCheckConfig(CfnHealthCheck.HealthCheckConfigProperty.builder()
                        .type("TCP")
                        .port(Token.asNumber(db.getDbInstanceEndpointPort())) // Porta do banco
                        .ipAddress(db.getDbInstanceEndpointAddress()) // Endereço do banco
                        .requestInterval(30) // Verifica a cada 30 segundos
                        .failureThreshold(3) // Considera falha após 3 tentativas sem sucesso
                        .build())
                .build();
    }

    // MSK (Managed Streaming for Kafka)
    private CfnCluster createMskCluster(){
        return CfnCluster.Builder.create(this, "MskCluster")
                .clusterName("kafka-cluster")
                .kafkaVersion("2.8.0")
                .numberOfBrokerNodes(3) // 3 "nós" ou servidores para o Kafka, para redundância
                .brokerNodeGroupInfo(CfnCluster.BrokerNodeGroupInfoProperty.builder()
                        .instanceType("kafka.m5.large") /// Tipo de máquina para os nós
                        .clientSubnets(vpc.getPrivateSubnets().stream()
                                .map(ISubnet::getSubnetId)
                                .collect(Collectors.toList()))
                        .brokerAzDistribution("DEFAULT").build())
                .build();
    }

    // Elastic Container Service
    private Cluster createEcsCluster(){
        return Cluster.Builder.create(this, "PatientManagementCluster")
                .vpc(vpc)
                .defaultCloudMapNamespace(CloudMapNamespaceOptions.builder()
                        .name("patient-management-local")
                        .build())
                .build();
    }

    // rodar contêineres sem precisar gerenciar servidores
    private FargateService createFargateService(
            String id,
            String imageName,
            List<Integer> ports,
            DatabaseInstance db,
            Map<String, String> additionalEnvVars) {

        // Quanto de CPU e memória ele pode usar
        FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder.create(this, id + "task")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();

        // Constrói as opções do contêiner
        ContainerDefinitionOptions.Builder containerOptions = ContainerDefinitionOptions.builder()
                .image(ContainerImage.fromRegistry(imageName)) // Imagem Docker
                // Mapeia as portas.
                .portMappings(ports.stream()
                        .map(port -> PortMapping.builder()
                                .containerPort(port)
                                .hostPort(port)
                                .protocol(Protocol.TCP)
                                .build())
                        .toList())
                .logging(LogDriver.awsLogs(AwsLogDriverProps.builder() // Configura os logs para o CloudWatch
                        .logGroup(LogGroup.Builder.create(this, id + "LogGroup")
                                .logGroupName("/ecs/" + imageName)
                                .removalPolicy(RemovalPolicy.DESTROY)
                                .retention(RetentionDays.ONE_DAY) // Guarda os logs por 1 dia.
                                .build())
                        .streamPrefix(imageName)
                        .build()));

        // Monta o mapa de variáveis de ambiente.
        Map<String, String> envVars = new HashMap<>();
        envVars.put("SPRING_KAFKA_BOOTSTRAP_SERVERS",
                "localhost.localstack.cloud:4510, localhost.localstack.cloud:4511, localhost.localstack.cloud:4512");

        // Adiciona variáveis customizadas, se houver
        if(additionalEnvVars != null) {
            envVars.putAll(additionalEnvVars);
        }

        // Se um banco de dados foi passado, adiciona as variáveis de conexão.
        if(db != null) {
            envVars.put("SPRING_DATASOURCE_URL", "jdbc:postgresql://%s:%s/%s-db".formatted(
                    db.getDbInstanceEndpointAddress(), db.getDbInstanceEndpointPort(), imageName
            ));
            envVars.put("SPRING_DATASOURCE_USERNAME", "adm_user");
            envVars.put("SPRING_DATASOURCE_PASSWORD", db.getSecret().secretValueFromJson("password").toString()); // Pega a senha de forma segura do AWS Secrets Manager
            envVars.put("SPRING_JPA_HIBERNATE_DDL_AUTO", "update");
            envVars.put("SPRING_SQL_INIT_MODE", "always");
            envVars.put("SPRING_DATASOURCE_HIKARI_INITIALIZATION_FAIL_TIMEOUT", "60000");
        }

        // Adiciona as variáveis de ambiente ao contêiner e o contêiner à definição da tarefa.
        containerOptions.environment(envVars);
        taskDefinition.addContainer(imageName + "Container", containerOptions.build());

        // Cria e retorna o Serviço Fargate, que vai garantir que o contêiner esteja sempre rodando.
        return FargateService.Builder.create(this, id)
                .cluster(ecsCluster)
                .taskDefinition(taskDefinition)
                .assignPublicIp(false)
                .serviceName(imageName)
                .build();
    }


    /**
     * Cria o serviço específico para o API Gateway.
     * A diferença é que ele usa `ApplicationLoadBalancedFargateService`, que automaticamente
     * cria e configura um Load Balancer para distribuir o tráfego para o gateway.
     */
    private void createApiGatewayService(){

        FargateTaskDefinition taskDefinition =
                FargateTaskDefinition.Builder.create(this, "APIGatewayTaskDefinition")
                        .cpu(256)
                        .memoryLimitMiB(512)
                        .build();

        ContainerDefinitionOptions containerOptions = ContainerDefinitionOptions.builder()
                .image(ContainerImage.fromRegistry("api-gateway"))
                .environment(Map.of("SPRING_PROFILES_ACTIVE", "prod",
                        "AUTH_SERVICE_URL", "http://host.docker.internal:9002")) // Corrigido http// para http://
                .portMappings(List.of(8098).stream()
                        .map(port -> PortMapping.builder()
                                .containerPort(port)
                                .hostPort(port)
                                .protocol(Protocol.TCP)
                                .build())
                        .toList())
                .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                        .logGroup(LogGroup.Builder.create(this,  "ApiGatewayLogGroup")
                                .logGroupName("/ecs/api-gateway")
                                .removalPolicy(RemovalPolicy.DESTROY)
                                .retention(RetentionDays.ONE_DAY)
                                .build())
                        .streamPrefix("api-gateway")
                        .build()))
                .build();

        taskDefinition.addContainer("APIGatewayContainer", containerOptions);

        ApplicationLoadBalancedFargateService apiGateway=
                ApplicationLoadBalancedFargateService.Builder
                        .create(this, "APIGatewayService")
                        .cluster(this.ecsCluster)
                        .serviceName("api-gateway")
                        .taskDefinition(taskDefinition)
                        .desiredCount(1) // Garante que sempre haverá 1 instância do gateway rodando.
                        .healthCheckGracePeriod(Duration.seconds(60))
                        .build();

    }

    public static void main(String[] args) {

        //  Cria a aplicação CDK
        App app = new App(AppProps.builder().outdir("infrastructure/cdk.out").build());

        StackProps stackProps = StackProps.builder()
                .synthesizer(new BootstraplessSynthesizer())
                .build();

        // Instancia a nossa stack
        new LocalStack(app, "localstack", stackProps);

        // Gera o template do CloudFormation no diretório cdk.out.
        app.synth();

        System.out.println("App synthesizing in progress...");
    }
}