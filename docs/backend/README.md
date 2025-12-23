# Smart Energy System - Backend

## Project Setup

### Initial Configuration

We first downloaded the program with dependencies from the [Spring Initializr](https://start.spring.io/). I have included the initial dependencies and a screenshot of the version details to indicate where we started.

![spring-initializer](/docs/images/backend-1-spring-initializer.png)

**Build Configuration:**

- **Project**: Gradle - Kotlin
- **Language**: Java
- **Spring Boot Version**: 4.0.1
- **Java Version**: 21

#### Core Dependencies

1. **Spring Web**

2. **Spring Data JPA**

3. **H2 Database**

4. **MySQL Driver**

5. **Spring Security**

### Additional Recommended Dependencies

Consider adding these dependencies based on your project needs:

- **Lombok**
- **Spring Boot DevTools**
- **Validation**
- **Spring Boot Actuator**

## Run Backend(without Docker)

### Prerequisites

- Java 21 JDK installed
- Gradle (wrapper included in project)

### Running the Application

```bash

./gradlew bootRun

```

### Building the Application

```bash

./gradlew build

gradlew.bat build
```

## Data Structure Design

Before starting the backend, we tried to design the data structure. For this, we used the diagram class for design. Of course, I must say that we may change these items in the future based on the needs, which is a sign of the agility of our work. We used a few entities that I prefer to explain in this section instead of additional explanations.
See the diagram below, which I designed on the diagrams.net [diagrams.net](https://diagrams.net/) site.

![Class Diagram](/docs/images/EsmartEnergySystem.drawio.png)

So let's explain each entity.

1- **User:** (User Management) which includes the fields required by a user. For simplicity, we considered each user to be a homeowner, of course, we will specify this by placing the "Role" field.

2- **Device:** (Device Management): The purpose of this entity is to: Add a new device, view the list of user devices, turn on/off the device, edit device information (name, location).

3- **EnergyReading:** (Energy Consumption Monitoring): This entity aims to enable us to:

- Receive energy consumption data from ESP32
- Display voltage, current, instantaneous power
- Calculate energy consumption (kWh)
- Consumption graph over time (daily, weekly, monthly)

4-**SensorData:** Sensor Monitoring: The purpose of this entity is to display temperature, humidity, ambient light, and a graph of temperature changes over time, and to also provide alerts in the event of abnormal temperature/humidity. This is definitely for notification purposes.

5- **Notification:** As I said, the purpose of this entity is: sending savings suggestions, high consumption warnings, device status change notifications, and marking notifications as read.

This is just a general reference. We will try to provide better technical documentation when completing each section. This section is only to explain the purpose and different parts of each section.

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
