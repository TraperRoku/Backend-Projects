FROM openjdk:21-jdk-slim

ADD target/springboot-mongo-docker.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]