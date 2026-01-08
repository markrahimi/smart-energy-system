# Smart Energy System - Backend

A Spring Boot REST API for smart home energy monitoring and management.

## Overview

This backend provides RESTful APIs for managing users , IOT devices , sensor data, notifications, and energy consumption thresholds. It is built using Spring Boot with Clean Architecture principles.

## Technology

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- H2 Database (in-memory)
- Gradle with Kotlin DSL
- Springdoc OpenAPI (Swagger)

## start

### Prerequisites

- Java 21 JDK
- Gradle (wrapper included)

### Run Locally

```bash
./gradlew bootRun
```

The application starts at http://localhost:8080

### Run with Docker

Build and run using Docker Compose:

```bash
docker-compose up backend
```

Or build manually:

```bash
docker build -t smart-energy-backend .
docker run -p 8080:8080 smart-energy-backend
```

### Access API Documentation

- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:mem:testdb
  - Username: sa
  - Password: (empty)

For complete API documentation, see [Complete Documentation](../docs/backend/README.md)

## Architecture

The backend follows Clean Architecture with four layers:

1. Domain Layer - Business entities and logic
2. Application Layer - Service classes
3. Persistence Layer - Database access with JPA
4. Web Layer - REST controllers

## Project Structure

```
backend/
-- src/
---- main/
------ java/com/cps2/energy/
-------- domain/           (User, Device, SensorData, etc.)
-------- application/      (Services)
-------- persistence/      (Entities, Repositories)
-------- web/              (Controllers, Representations)
-------- config/           (Security, CORS)
------ resources/
-------- application.properties
-------- data.sql          (Sample data)
---- test/
------ java/com/cps2/energy/
-------- SmartEnergyBackendApplicationTests.java
-------- ArchUnitTest.java
-------- web/UserControllerIntegrationTest.java
```

## Testing

### Run All Tests

```bash
./gradlew test
```

### Run Specific Test

```bash
./gradlew test --tests SmartEnergyBackendApplicationTests
```

We have three types of tests:

- Basic application context test
- Architecture layer test using ArchUnit
- Integration test for User API endpoints

## Building

### Create JAR file

```bash
./gradlew build

```

The JAR file will be in `build/libs/`

### Run JAR file

```bash
java -jar build/libs/smart-energy-backend-0.0.1-SNAPSHOT.jar
```

## Network Access

To access the backend from other devices on the same WiFi:

1. The backend is configured to listen on all interfaces (0.0.0.0)
2. Find your computer's IP address
3. Access from other devices using http://YOUR_IP:8080

for Example: http://172.20.10.3:8080

This allows the mobile app and frontend to connect from different devices.

## Docker Support

The project includes Docker support:

- Dockerfile for building the backend image
- docker-compose.yml for running the full stack (backend, frontend, Keycloak)

To run only the backend with Docker:

```bash
docker-compose up backend
```

To run the full stack:

```bash
docker-compose up
```

## Database

We use H2 in-memory database for development. The database is recreated on each restart and populated with sample data from data.sql.

Sample data includes:

- users(admin and regular user )
- devices
- Sensor data readings
- Notifications
- Thresholds

## Documentation

For detailed implementation documentation, architecture decisions, API endpoints, and challenges we faced, see:

[Complete Backend Documentation](../docs/backend/README.md)
