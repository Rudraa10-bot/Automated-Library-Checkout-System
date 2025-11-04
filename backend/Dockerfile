# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven config and download deps first (better caching)
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -DskipTests package

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar

# Render provides $PORT; wire it to Spring Boot
ENV PORT=8080
EXPOSE 8080

CMD ["sh","-c","java -Dserver.port=${PORT} -jar app.jar"]
