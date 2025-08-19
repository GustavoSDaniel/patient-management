# Estágio de build (multi-stage build)
# Usando uma imagem otimizada para compilação Maven e Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o pom.xml e baixa as dependências para o cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn clean package -DskipTests

# Estágio de execução
# Usando uma imagem leve e otimizada para apenas executar o JAR
FROM openjdk:21-jdk AS runner

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR do estágio de build para o estágio de execução
COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar ./app.jar

# Define as portas que o contêiner irá expor
# Exponha as portas que estão na sua configuração do Spring
EXPOSE 8082
EXPOSE 9092

# Comando para iniciar a aplicação, configurando a porta HTTP/REST
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]
