FROM openjdk:11-jdk-slim

MAINTAINER Emanuel Almirante, emanuelalmirante@gmail.com

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080:8080