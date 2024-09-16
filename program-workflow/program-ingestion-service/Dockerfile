# Use the maven image to build the project
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml /app/pom.xml
COPY src /app/src
RUN mvn clean package -DskipTests=true

# Use the openjdk image to run the project
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/program-ingestion-service-0.0.1-SNAPSHOT.jar /app/app.jar
RUN mkdir -p /app/src/main/resources/xml/
COPY src/main/resources/xml/ /app/src/main/resources/xml/
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
