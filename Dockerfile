# Step 1 — Use a base image with Java 17 installed
FROM eclipse-temurin:17-jre

# Step 2 — Set working directory inside container
WORKDIR /app

# Step 3 — Copy your jar file into the container
COPY target/Blood_bank-0.0.1-SNAPSHOT.jar app.jar

# Step 4 — Tell Docker which port your app uses
EXPOSE 8080

# Step 5 — Command to run when container starts
ENTRYPOINT ["java", "-jar", "app.jar"]