FROM openjdk:11-jre-slim
MAINTAINER rakshithvikramraj@gmail.com
COPY target/ServerApp-0.0.1-SNAPSHOT.jar ServerApp-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/ServerApp-0.0.1-SNAPSHOT.jar"]