# Use Eclipse Temurin OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR file from target directory
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application runs on
EXPOSE 8082

# Set JVM options for better container performance
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
