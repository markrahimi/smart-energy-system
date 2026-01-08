# Smart Energy System - Android Application

Android mobile application for Smart Energy Management system built with Kotlin and Jetpack Compose.

## Technology

- Kotlin
- Jetpack Compose
- MVVM Architecture
- Retrofit + Moshi
- Material Design 3

## Requirements

- Android Studio (latest version)
- JDK
- Android device or emulator

## Ruild and Run

### open Project

Open Android Studio and select "Open" then choose the `application` folder.

### Wait for Gradle Sync

Android Studio will automatically sync Gradle. Wait for it to finish (may take a few minutes first time).

### Configure Backend URL

Open `app/src/main/java/com/cps2/energy/data/api/RetrofitClient.kt`

Update the `BASE_URL`:

- For Emulator: `http://10.0.2.2:8080/api/`
- For Physical Device: `http://YOUR_COMPUTER_IP:8080/api/`

Make sure backend is running and listening on `0.0.0.0`

### Run

Click the green Run button or press `Shift + F10`

Select your emulator or connected device.

### 5. Build APK

To build an APK file:

```bash
./gradlew assembleDebug
```

APK will be at: `app/build/outputs/apk/debug/app-debug.apk`

## Screens

- User List
- User Detail (with devices and notifications)
- Device List
- Device Detail
- Notification List (with NEW badges)
- Notification Detail
- Sensor Data List

## Documentation

For complete implementation details, architecture, and development process, see:

**[Full Documentation](../docs/application/README.md)**
