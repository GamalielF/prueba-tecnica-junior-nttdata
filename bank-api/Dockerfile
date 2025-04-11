FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/bank-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jardocker run -p 8080:8080 bank-api