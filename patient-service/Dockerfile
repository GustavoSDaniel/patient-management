# ============================
# Etapa de build
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .
RUN mvn clean package spring-boot:repackage -DskipTests

# ============================
# Etapa de runtime
# ============================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o JAR certo (fat jar gerado pelo spring-boot:repackage)
COPY --from=builder /app/target/patient-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

