FROM openjdk:17 AS builder
ENV TZ=Asia/Seoul
EXPOSE 8000
COPY build/libs/cadang-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]