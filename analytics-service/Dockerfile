
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app


COPY pom.xml .
COPY analytics-service/pom.xml ./analytics-service/


RUN mvn -f analytics-service/pom.xml dependency:go-offline

# Copia o código-fonte apenas do analytics-service
COPY analytics-service/src ./analytics-service/src


RUN mvn -f analytics-service/pom.xml clean install


FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho para a aplicação
WORKDIR /app

# Cria um usuário e grupo 'appuser' para rodar a aplicação com menos privilégios
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copia o arquivo JAR gerado no estágio 'builder' para a imagem final
COPY --from=builder /app/analytics-service/target/analytics-service-*.jar /app/application.jar

# Muda a propriedade do arquivo JAR para o novo usuário
RUN chown appuser:appuser /app/application.jar

# Muda para o usuário não-root
USER appuser

# Expõe a porta que o serviço vai usar
EXPOSE 8091

# Comando para iniciar a aplicação quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "/app/application.jar"]