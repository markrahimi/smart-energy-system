# Smart Energy System - Android Application

This is Android mobile application for the Smart Energy Management system

## Technology

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit with Moshi
- **Navigation**: Jetpack Navigation Component
- **State Management**: StateFlow

## Dependencies

We configured these dependencies in `build.gradle.kts`:

**Jetpack Compose** - For building UI with declarative approach

- `compose-bom` - Manages Compose library versions
- `material3` - Material Design 3 components
- `ui`, `ui-tooling` - Core UI libraries

**Retrofit** - For making HTTP requests

**Moshi** - For JSON parsing

**Navigation Component**

**Coroutines** - For asynchronous operations

**ViewModel** - For managing UI state

**KSP** (Kotlin Symbol Processing) - For code generation

- Used by Moshi to generate JSON adapters automatically

## Development Process

### API Integration

We used the same backend APIs as the web frontend. The backend provided Swagger documentation which helped us understand the data structures. We used Retrofit for HTTP requests and Moshi for JSON parsing.

### Development Order

1. **User Management** - List and detail screens
2. **Device Management** - View devices and their owners
3. **Notifications** - List and detail with read/unread status
4. **Sensor Data** - View sensor readings
5. **Navigation** - Connected all screens together

## Screens Implemented

We created the following screens:

1. **UserListScreen** - Shows all users in a scrollable list
2. **UserDetailScreen** - Shows user info with their devices and notifications
3. **DeviceListScreen** - Shows all devices
4. **DeviceDetailScreen** - Shows device details
5. **NotificationListScreen** - Shows notifications for a user with NEW badges
6. **NotificationDetailScreen** - Shows full notification details
7. **SensorDataListScreen** - Shows sensor readings

## Code Structure

The code is organized into layers:

**Data Layer:**

- `models/` - Data classes (User, Device, Notification, SensorData)
- `api/ApiService.kt` - Retrofit interface defining API endpoints
- `api/RetrofitClient.kt` - Network configuration

**UI Layer:**

- `screens/` - Composable screen functions
- `navigation/AppNavigation.kt` - Navigation setup with routes

**ViewModel Layer:**

- `viewmodels/` - ViewModels for each screen (handles data and state)

**Entry Point:**

- `MainActivity.kt` - App entry point

```
application/
-- app/
---- src/
------ main/
-------- java/com/cps2/energy/
---------- data/
------------ models/
-------------- User.kt
-------------- Device.kt
-------------- Notification.kt
-------------- SensorData.kt
------------ api/
-------------- ApiService.kt          (API endpoints)
-------------- RetrofitClient.kt      (Network config)
---------- ui/
------------ screens/
-------------- UserListScreen.kt
-------------- UserDetailScreen.kt
-------------- DeviceListScreen.kt
-------------- DeviceDetailScreen.kt
-------------- NotificationListScreen.kt
-------------- NotificationDetailScreen.kt
-------------- SensorDataListScreen.kt
---------- viewmodels/
------------ UserListViewModel.kt
------------ UserDetailViewModel.kt
------------ DeviceListViewModel.kt
------------ NotificationListViewModel.kt
---------- navigation/
------------ AppNavigation.kt
---------- MainActivity.kt
-------- AndroidManifest.xml
---- build.gradle.kts
```

## Features

### User Management

- View list of all users
- View user details
- See user's devices and notifications

### Device Management

- View list of all devices
- View device details
- See device owner

### Notifications

- View list of notifications
- View notification details
- Priority indicators (HIGH, MEDIUM, LOW)
- Read/unread status
- NEW badge for unread notifications

### Sensor Data

- View recent sensor readings
- Temperature, humidity, luminosity
- Power, voltage, current values

## Jetpack Compose UI

All UI built with Jetpack Compose using declarative approach:

```kotlin
@Composable
fun UserListScreen(
    onUserClick: (String) -> Unit
) {
    val users by viewModel.users.collectAsState()

    LazyColumn {
        items(users) { user ->
            UserCard(user = user, onClick = { onUserClick(user.id) })
        }
    }
}
```

## MVVM Architecture

Each screen has a ViewModel that:

- Fetches data from API
- Manages UI state with StateFlow
- Handles business logic

Example:

```kotlin
class UserListViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        loadUsers()
    }
}
```

## Network Configuration

The app connects to backend API. In `RetrofitClient.kt`:

```kotlin
private const val BASE_URL = "http://172.20.10.3:8080/api/"
```

- **Android Emulator**: Use `http://10.0.2.2:8080/api/`
- **Physical Device**: Use your computer's IP (e.g., `http://172.20.10.3:8080/api/`)

Backend must listen on 0.0.0.0 to accept connections from devices on same WiFi.

## Building and Running

### Prerequisites

- Android Studio (latest version)

### Run on Emulator

Open this project in Android Studio and Wait for Gradle sync after Click Run or Shift+F10

### Run on Physical Device

connect via USB and click Run and select device

### Build APK

```bash
./gradlew assembleDebug
```

APK location: `app/build/outputs/apk/debug/app-debug.apk`

## Challenges

### Challenge: Network Access

App worked on emulator but couldn't connect from physical device.

### Challenge: State Management

Managing UI state and loading/error states was complex.

Solution: Used StateFlow in ViewModels for reactive state updates.

## Tools We Didn't Use

We planned to use Android Studio's **Compose Preview** and **Layout Inspector** tools for debugging UI. However, we ran out of time at the end and focused on core functionality. These tools would have helped visualize composables and debug layouts, but we completed the app by testing directly on emulator.
