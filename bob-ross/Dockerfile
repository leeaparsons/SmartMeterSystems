FROM openjdk:17-jdk-slim

ADD build/libs/bob-ross-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8069
ENTRYPOINT ["java","-jar","/app.jar"]

