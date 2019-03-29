FROM openjdk:8-jdk-alpine
ADD batch-sample.jar app.jar
ENV POSTGRES_ENV_IP postgres
ENV POSTGRES_ENV_PORT 5432
ENV POSTGRES_ENV_DB helm_sample
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
