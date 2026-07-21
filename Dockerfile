# ---------- Etapa 1: Compilación ----------
FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

# ---------- Etapa 2: Ejecución ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar inventario.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","inventario.jar"]