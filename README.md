# Smart Energy Saving System

**Programmer:**
Mahdiyeh ANJOMSHOAE
Mohammadali RAHIMI

**Project:**
Smart Energy Saving System – S1 M1 CPS2

---

## 1. Below are some of the conversations we had at the beginning of the project, but after that we will write you detailed details of the project.

### embedded part:

embedded part validated by M. Lefrançois

We will use basic sensors such as:
-Temperature sensor (DHT22) to monitor room temperature and humidity.
-luminosity sensor (temt6000) to detect ambient light for smart lighting.

we will simulate energy consumption using either:

- the potentiometer
- the distance sensor of the bluefuit sense
- some existing power profiles of whitegood found on the web, that we will upload to the micro-sd card, and will "play" on the device

### Web part:

Online dashboard to monitor and analyze electricity consumption (charts, reports, suggestions).Users:

- Homeowners / Energy managers.
  Functionalities:
- Monitor and analyze electricity consumption with charts and reports.
- View suggestions to reduce energy usage.
- Cannot directly control devices (only monitoring).
  Database Data:
- User profiles (login, household info).
- Device IDs and types.
- Energy consumption logs (timestamp, current, voltage, power).
- Suggestions and thresholds.

### Mobile part:

Mobile app for remote control of devices and receiving energy-saving data.Users: Homeowners / Tenants.
Functionalities:

- Remote control of connected devices (turn on/off).
- Receive energy-saving notifications.
- View simplified consumption data.
- Cannot see advanced analytics (web only).

Database Data:

- User profiles and authentication tokens.
- Device status (on/off).
- Notification history.
- Real-time consumption values.

---

## 2.Detail of our Project:

## Embedded Part:

We will use:

- Board: ESP32
- MicroPython
- Potentiometer
- DHT22
- temt6000
- bluefuit sense

## Backend Part:

We want the backend to be able to cover all our REST APIs in full JSON. To complete this section, as the professor explained and we learned in class, we will use Keycloak for Authentication.
And our control and central core will be the backend of the application.

We have fully agreed to develop the application in VSCode, if you need it, you can use the gradlew tools to build and run the backend.

Our backend is the first part we will implement, although we will seek help from Notion to complete and control the software engineering.

We will use:

- Java 21
- Spring Boot 4.0.1
- Gradle 8.14
- H2 Database
- Eclipse Mosquitto
  -Spring Data JPA - Database Access (ORM)

## Frontend Part:

We will use:

- VueJS

## Application Part:

We will use:

- Kotlin

## 3. Project Folder Structure:

```
smart-energy-system/
├── backend                     # Spring Boot 4.0.1 + Java 21
├── frontend                    # VueJS
├── application                 # Kotlin
├── embedded                    # ESP32
├── docs                        # All documents
```

## 4.Data Loop

This section will be added soon.

#5.Data Loop

This section will be added soon.
