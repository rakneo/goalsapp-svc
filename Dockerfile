FROM openjdk:11-jre-slim
MAINTAINER rakshithvikramraj@gmail.com
COPY target/GoalsApp-0.0.1-SNAPSHOT.jar GoalsApp-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/GoalsApp-0.0.1-SNAPSHOT.jar"]