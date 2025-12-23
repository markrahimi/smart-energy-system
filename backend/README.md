# Smart Energy System - Backend

## Project Setup

### Initial Configuration

This Spring Boot application was generated from [Spring Initializr](https://start.spring.io/) with the following configuration:

**Build Configuration:**

- **Project**: Gradle - Kotlin
- **Language**: Java
- **Spring Boot Version**: 4.0.1
- **Java Version**: 21

### Dependencies

The following dependencies were selected for this project:

#### Core Dependencies

1. **Spring Web**

   - Build web applications including RESTful APIs using Spring MVC
   - Uses Apache Tomcat as the default embedded container
   - Essential for creating REST endpoints

2. **Spring Data JPA**

   - Persist data in SQL stores using Java Persistence API
   - Uses Spring Data and Hibernate
   - Simplifies database operations with repository pattern

3. **H2 Database**

   - Fast in-memory database for development and testing
   - Supports JDBC API and R2DBC access
   - Small footprint (2mb)
   - Provides browser-based console for database management
   - Good for development and testing environments

4. **MySQL Driver**

   - MySQL JDBC driver for production database
   - Required for connecting to MySQL database in production

5. **Spring Security**
   - Highly customizable authentication and access-control framework
   - Essential for securing API endpoints
   - Handles user authentication and authorization

### Additional Recommended Dependencies

Consider adding these dependencies based on your project needs:

- **Lombok**: Reduces boilerplate code (getters, setters, constructors)
- **Spring Boot DevTools**: Automatic restart and live reload during development
- **Validation**: Bean validation with Hibernate validator
- **Spring Boot Actuator**: Production-ready monitoring and management endpoints

## Getting Started

### Prerequisites

- Java 21 JDK installed
- Gradle (wrapper included in project)

### Running the Application

```bash
# On Unix/macOS
./gradlew bootRun

# On Windows
gradlew.bat bootRun
```

### Building the Application

```bash
# On Unix/macOS
./gradlew build

# On Windows
gradlew.bat build
```

### Accessing H2 Console (Development)

When running in development mode with H2 database:

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (leave empty)

## Configuration

Database and other configurations can be found in:

- [src/main/resources/application.properties](src/main/resources/application.properties)

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/smarts/energy/
│   │   │   └── EnergyApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/smarts/energy/
│           └── EnergyApplicationTests.java
├── build.gradle.kts
└── settings.gradle.kts
```
