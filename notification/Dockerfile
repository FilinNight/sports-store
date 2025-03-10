FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-jdk
WORKDIR /notification_app
COPY --from=build /notification_app/target/*.jar app.jar
EXPOSE 8090
CMD ["java", "-jar", "app.jar"]