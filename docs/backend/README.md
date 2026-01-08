# Smart Energy System - Backend

## Overview

This document explains how we built the backend system for our Smart Energy Management application. We started by learning Spring Boot from tutorials, then faced some challenges, went back to check the professor's resources, and finally develop a working system.

## Getting Started

### Initial Setup

We started our project using Spring Initializr at https://start.spring.io/. Here are the choices we made:

- Project Type: Gradle with Kotlin DSL
- Language: Java
- Spring Boot Version: 3.5.6
- Java Version: 21
- Packaging: Jar

### Dependencies We Added

At the beginning, we added these dependencies:

1. Spring Web - for creating REST APIs
2. Spring Data JPA - for database operations
3. H2 Database - for development and testing
4. Spring Security - for authentication (we configured it to allow all requests for simplicity)
5. Springdoc OpenAPI - for API documentation with Swagger

Later we also added:

- Validation - for checking input data
- Lombok - to reduce boilerplate code

### Why We Changed From MySQL to H2

Originally we planned to use MySQL database. However, we switched to H2 in-memory database because:

- It is easier to set up and test
- No need to install MySQL separately
- Good for development and demo purposes
- Data resets on each restart which is fine for our project scope

## Architecture Design

We followed Clean Architecture principles that we learned from the course materials. This means we separated our code into different layers. Each layer has a specific job and does not depend on the details of other layers.

### Layer Structure

Our backend has four main layers:

1. Domain Layer - the core business logic
2. Application Layer - services that use the domain
3. Persistence Layer - database access
4. Web Layer - REST API controllers

This was exactly what the professor had said in his tutorial link and was followed in the project that was also Lab 2. It was best for us to review Lab 2 again.

## Domain Layer:

The domain layer contains our main business entities. These are simple Java classes that represent the core concepts of our system.

### Entities We Have

**User**
This represents a person using the system.

We decided to use UUID instead of auto-increment numbers because UUIDs are better when you have multiple systems or databases.

Fields:

- id
- username
- password
- email
- fullName
- role
- createdAt

**Device**
This represents an IoT device like ESP32.

Fields:

- id
- name
- type
- status
- location
- active
- userId
- createdAt

**SensorData**
This stores readings from sensors.

Fields:

- id
- deviceId
- temperature
- humidity
- luminosity
- powerConsumption
- voltage
- current
- timestamp

We added the power-related fields later when we realized we needed to track energy usage.

**Notification**
This represents alerts sent to users.

Fields:

- id
- userId
- title
- message
- type
- priority
- isRead
- thresholdId
- createdAt

**Threshold**
This represents the limits users set for their sensors.

Fields:

- id
- deviceId
- userId
- minTemperature
- maxTemperature
- minHumidity
- maxHumidity
- minLuminosity
- maxLuminosity
- minPower
- maxPower
- minVoltage
- maxVoltage
- minCurrent
- maxCurrent
- isActive
- createdAt

We added this entity later when we realized users need to set custom alert limits.

## Application layer

This layer contains our service classes. Services handle the business logic and coordinate between the domain and persistence layers.

### Services We Implemented

**UserService**
Handles all user-related operations:

- Get all users or find by ID
- Find user by username
- Create new user
- Update user information

**DeviceService**
Manages IoT devices:

- Get all devices or filter by user
- Find device by ID
- Create new device
- Update device information
- Delete device

**SensorDataService**
Handles sensor readings:

- Get all sensor data with filters
- Get data for specific device
- Get latest reading for a device
- Save new sensor data from ESP32

**NotificationService**
Manages user notifications:

- Get all notifications or filter by user
- Get notification by ID (automatically marks it as read)
- Count unread notifications
- Create new notification
- Mark all notifications as read for a user

We added the auto-mark-as-read feature because when a user opens a notification, it should be marked as read automatically. This required removing the read-only transaction annotation.

**ThresholdService**
Manages alert thresholds:

- Get all thresholds or filter by user
- Get threshold for specific device
- Create or update threshold
- Delete threshold

This service checks sensor data and creates notifications when values go outside the defined limits.

## Persistence Layer

This layer handles database operations. We use Spring Data JPA which makes database access very simple.

### Entity Classes

For each domain entity, we have a corresponding JPA entity class. These classes have annotations like @Entity, @Table, @Id, etc. They also have methods to convert between domain objects and database entities.

For example:

- User domain -> UserEntity for database
- Device domain -> DeviceEntity for database
- And so on for other entities

### Repository Interfaces

We created repository interfaces that extend JpaRepository. Spring automatically implements these interfaces for us. We just need to declare the methods we want.

Examples of custom query methods we added:

- findByUsername in UserRepository
- findByUserId in DeviceRepository
- findByDeviceId in SensorDataRepository
- findByUserIdAndIsRead in NotificationRepository

## Web Layer

This layer contains our REST API controllers. These handle HTTP requests and return responses.

### Controllers We Have

**UserController** - /api/users
Endpoints:

- GET /api/users - get all users
- GET /api/users/{id} - get user by ID
- GET /api/users/username/{username} - get user by username
- POST /api/users - create new user
- PUT /api/users/{id} - update user

**DeviceController** - /api/devices
Endpoints:

- GET /api/devices - get all devices (can filter by userId)
- GET /api/devices/{id} - get device by ID
- POST /api/devices - create new device
- PUT /api/devices/{id} - update device
- DELETE /api/devices/{id} - delete device

**SensorDataController** - /api/sensor-data
Endpoints:

- GET /api/sensor-data - get all sensor data (can filter by deviceId)
- GET /api/sensor-data/{id} - get sensor data by ID
- GET /api/sensor-data/latest?deviceId={id} - get latest reading for device
- POST /api/sensor-data - save new sensor reading from ESP32

**NotificationController** - /api/notifications
Endpoints:

- GET /api/notifications - get all notifications (can filter by userId)
- GET /api/notifications/{id} - get notification by ID (auto marks as read)
- GET /api/notifications/unread-count?userId={id} - count unread notifications
- POST /api/notifications - create new notification
- POST /api/notifications/mark-all-read?userId={id} - mark all as read

**ThresholdController** - /api/thresholds
Endpoints:

- GET /api/thresholds - get all thresholds (can filter by userId)
- GET /api/thresholds/device/{deviceId} - get threshold for device
- POST /api/thresholds - create new threshold
- PUT /api/thresholds/device/{deviceId} - update threshold
- DELETE /api/thresholds/device/{deviceId} - delete threshold

### Representation Classes

For each controller, we have representation classes. These define what data we send and receive in the API.

Types of representations:

- Basic representation - for GET responses
- ToCreate representation - for POST requests
- ToUpdate representation - for PUT requests

This separation helps us control exactly what fields are required for each operation.

## Security Configuration

We use Spring Security but configured it to allow all requests without authentication. This was done for simplicity during development.

The SecurityConfig class:

- Allows all API endpoints (permitAll)
- Configures CORS to accept requests from any origin
- Disables CSRF protection (not needed for stateless API)

For local network access, we configured CORS to accept requests from any origin using setAllowedOriginPatterns("\*"). This lets the mobile app and frontend connect to the backend from different devices on the same WiFi.

We also set server.address=0.0.0.0 in application.properties so the backend listens on all network interfaces, not just localhost.

## Database Configuration

We use H2 in-memory database with these settings in application.properties:

- Database URL: jdbc:h2:mem:testdb
- Hibernate DDL: create (recreates tables on each restart)
- SQL logging: enabled (so we can see what queries run)
- Deferred initialization: true (loads data.sql after creating tables)

### Initial Data

We created a data.sql file that runs on startup. It inserts sample data:

- Two users (one admin, one regular user)
- One device for each user
- Some sensor data readings
- Some notifications
- Thresholds for the devices

This makes testing easier because we always have data to work with.

## Swagger Documentation

We added Springdoc OpenAPI to automatically generate API documentation. After starting the application, we can access:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

This is very useful for testing endpoints and understanding the structure of the API. We used this in another project(Django and php), so we decided to use Swagger.

## Project File Structure

The complete structure looks like this:

backend/
-- src/
---- main/
------ java/
-------- com/cps2/energy/
---------- SmartEnergyBackendApplication.java (main class)
---------- domain/
------------ User.java
------------ Device.java
------------ SensorData.java
------------ Notification.java
------------ Threshold.java
---------- application/
------------ UserService.java
------------ DeviceService.java
------------ SensorDataService.java
------------ NotificationService.java
------------ ThresholdService.java
---------- persistence/
------------ UserEntity.java
------------ UserRepository.java
------------ DeviceEntity.java
------------ DeviceRepository.java
------------ SensorDataEntity.java
------------ SensorDataRepository.java
------------ NotificationEntity.java
------------ NotificationRepository.java
------------ ThresholdEntity.java
------------ ThresholdRepository.java
---------- web/
------------ UserController.java
------------ UserRepresentation.java
------------ UserToCreateRepresentation.java
------------ UserToUpdateRepresentation.java
------------ DeviceController.java
------------ DeviceRepresentation.java
------------ DeviceToCreateRepresentation.java
------------ DeviceToUpdateRepresentation.java
------------ SensorDataController.java
------------ SensorDataRepresentation.java
------------ SensorDataToCreateRepresentation.java
------------ NotificationController.java
------------ NotificationRepresentation.java
------------ NotificationToCreateRepresentation.java
------------ ThresholdController.java
------------ ThresholdRepresentation.java
------------ ThresholdToCreateRepresentation.java
---------- config/
------------ SecurityConfig.java
------ resources/
-------- application.properties
-------- data.sql
---- test/
------ java/
-------- com/cps2/energy/
---------- SmartEnergyBackendApplicationTests.java
---------- ArchUnitTest.java
---------- web/
------------ UserControllerIntegrationTest.java
-- build.gradle.kts
-- settings.gradle.kts

## How To Run

### Development Mode

To run the backend during development:

./gradlew bootRun

The application starts on http://localhost:8080

### Building

To build a JAR file:

./gradlew build

The JAR file will be in build/libs/ directory.

### For Network Access

To make the backend accessible from other devices on the same WiFi:

1. The server.address is already set to 0.0.0.0
2. Find your computer's IP address on the network
3. Start the backend normally
4. Access from other devices using http://YOUR_IP:8080

For example, if your IP is 172.20.10.3, use http://172.20.10.3:8080

## Challenges We Faced

### Challenge 1: Understanding Clean Architecture

At first, we did not understand why we needed so many layers. It seemed complicated. After reading the course materials again, we understood that each layer has a purpose. The domain layer should not know about the database or HTTP. This makes the code more flexible.

### Challenge 2: UUID vs Auto-increment IDs

We started with Long IDs (1, 2, 3...) but changed to UUID. This was challenging because:

- UUID is a string, not a number
- Had to update all entity classes
- Had to update all repositories
- Had to update data.sql with UUID format
- Had to update the mobile app to use String IDs

But UUID is better for distributed systems and more secure.

### Challenge 3: Auto-marking Notifications as Read

We wanted notifications to be marked as read when a user views them. The problem was that the getNotificationById method had @Transactional(readOnly = true), which prevented updates.

The solution was to remove the readOnly flag and update the notification in the same method that retrieves it.

### Challenge 4: CORS Configuration

When we tried to access the API from the frontend, we got CORS errors. We had to add CORS configuration in SecurityConfig to allow requests from different origins.

For local network access, we changed from specific origins to accepting all origins using setAllowedOriginPatterns("\*").

### Challenge 5: Threshold Implementation

Initially we did not have thresholds. Users could not set custom alert limits. We added this feature later, which required:

- Creating new domain class
- Creating new entity and repository
- Creating new service and controller
- Updating the database schema
- Adding frontend pages

This showed us the importance of planning features from the beginning.

## Testing

We implemented tests following the examples from the professor's tutorials. We have three types of tests in our project.

### Test Files We Have

**SmartEnergyBackendApplicationTests.java**
This is the basic test that comes with Spring Boot. It checks if the application context loads correctly. This is important to make sure all beans and configurations are working.

**ArchUnitTest.java**
We created this test to check our architecture layers. We learned about ArchUnit from the professor's resources. This test makes sure:

- Domain layer does not depend on other layers
- Web layer only depends on application layer
- Persistence layer is separate from web layer

it automatically checks if we break the Clean Architecture rules. We took the pattern from Lab-2 examples.

**UserControllerIntegrationTest.java**
This is an integration test for the User API. It tests the actual HTTP endpoints using MockMvc. We followed the professor's tutorial on writing integration tests. This test checks:

- GET /api/users returns list of users
- GET /api/users/{id} returns specific user
- POST /api/users creates new user
- PUT /api/users/{id} updates user

We used @SpringBootTest annotation and loaded the full application context. This way we test the real behavior including database operations.

### Why We Did Not Write More Tests

We focused on these three tests because:

- Time limitations in the project
- We wanted to understand testing properly first
- The ArchUnit test covers the whole architecture
- One integration test shows the pattern for others

In a real project, we would add more integration tests for all controllers and also unit tests for services.

### How To Run Tests

To run all tests:

./gradlew test

To run only one test:

./gradlew test --tests SmartEnergyBackendApplicationTests

### Other Testing Methods

We can also test the API manually in several ways:

1. Using Swagger UI at http://localhost:8080/swagger-ui.html
2. Using curl commands from terminal
3. Using the frontend web application
4. Using the mobile Android application

We also have H2 Console enabled at http://localhost:8080/h2-console for checking the database directly. The JDBC URL is jdbc:h2:mem:testdb with username "sa" and empty password.

## Integration with Other Components

### Frontend (Vue.js)

The frontend makes HTTP requests to our API endpoints. All endpoints are under /api/ prefix. We configured CORS to allow requests from localhost:5173 where the frontend runs.

### Mobile App (Android)

The mobile app uses Retrofit library to call our API. For the emulator, it uses 10.0.2.2:8080. For physical devices on the same WiFi, it uses the computer's IP address like 172.20.10.3:8080.

### Embedded System (ESP32)

The ESP32 sends sensor data to POST /api/sensor-data endpoint. It sends JSON with deviceId, temperature, humidity, luminosity, powerConsumption, voltage, current, and timestamp.

## What We Develop

by the end, we successfully implemented:

1. A working REST API with 5 main resources
2. Clean architecture with proper layer separation
3. Database persistence with JPA and H2
4. Sample data loading on startup
5. API documentation with Swagger
6. Security configuration (currently open for development)
7. CORS configuration for frontend access
8. Network configuration for local WiFi access
9. Threshold-based notifications
10. Auto-marking notifications as read

## Future

Things we could add or improve:

1. Real authentication with JWT tokens
2. Password encryption with BCrypt
3. Input validation on all endpoints
4. Better error handling and messages
5. Pagination for large data sets
6. Filtering and sorting options
7. Unit tests for services
8. Integration tests for controllers
9. Scheduled tasks for checking thresholds
10. WebSocket support for real-time updates
