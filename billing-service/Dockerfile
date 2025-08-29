# Estágio 1: Build da aplicação com Maven
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml para baixar as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código-fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn clean package -DskipTests

# Estágio 2: Imagem final de execução
FROM openjdk:21-jdk

WORKDIR /app

# Copia o JAR correto, gerado pelo build do billing-service
COPY --from=builder /app/target/billing-service-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta do servidor gRPC e a porta HTTP
EXPOSE 9092
EXPOSE 8081

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]