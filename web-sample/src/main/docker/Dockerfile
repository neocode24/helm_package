FROM openjdk:8-jdk-alpine
ADD web-sample.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
