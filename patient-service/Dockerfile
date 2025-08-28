# Estágio 1: Builder - Constrói a aplicação
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src


RUN mvn clean package -DskipTests
# -----------------------------------------------------------

# Estágio 2: Runner - Executa a aplicação
FROM openjdk:21-jdk AS runner

WORKDIR /app

# Copia apenas o .jar gerado no estágio anterior
COPY --from=builder /app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

# Define o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

