FROM openjdk:17-jdk-slim

ADD build/libs/jackson-pollock-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8070
ENTRYPOINT ["java","-jar","/app.jar"]

