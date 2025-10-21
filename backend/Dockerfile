# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable and fix line endings
RUN chmod +x ./mvnw && \
    sed -i 's/\r$//' ./mvnw

# Copy source code
COPY src src

# Build the application using Maven directly
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD java -Dserver.port=${PORT:-8080} -jar target/library-system-backend-0.0.1-SNAPSHOT.jar
