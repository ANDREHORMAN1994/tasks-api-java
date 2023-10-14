# Estágio 1: Build
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Estágio 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/todolist-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
