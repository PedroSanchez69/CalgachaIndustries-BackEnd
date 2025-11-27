# Multi-stage Dockerfile
# Build stage: use Maven with JDK 17 to build the project
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace

COPY . .

# Make the wrapper executable and build the project (skip tests for faster builds)
RUN if [ -f ./mvnw ]; then chmod +x ./mvnw && ./mvnw -B -DskipTests package; else mvn -B -DskipTests package; fi

# Runtime stage: small JRE image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=builder /workspace/target/*.jar app.jar

# Render (and many PaaS providers) supply a port via the PORT env var.
# Default to 8080 when PORT is not provided (useful for local testing).
ENV PORT=8080
EXPOSE 8080

# Use a shell form so we can expand the PORT environment variable at runtime.
ENTRYPOINT ["sh", "-c", "exec java -jar /app/app.jar --server.port=${PORT:-8080}"]
