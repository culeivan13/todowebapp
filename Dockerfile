FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY /target/*.jar /app/todowebapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/todowebapp.jar"]