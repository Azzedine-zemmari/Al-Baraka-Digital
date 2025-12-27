# Step 1: Use Eclipse Temurin JDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set working directory inside the container
WORKDIR /app

# Step 3: Copy the jar from target/ (THIS IS THE FIX)
COPY target/securise-0.0.1-SNAPSHOT.jar myapp.jar

# Step 4: Expose the port your app runs on
EXPOSE 8080

# Step 5: Run the jar file
ENTRYPOINT ["java", "-jar", "myapp.jar"]
