# Smart Energy System - Frontend

A Vue.js 3 web application for smart home energy monitoring and management.

## Overview

This frontend provides a complete user interface for managing users, IOT devices, sensor data, notifications, and energy thresholds. It is built using Vue.js 3 with Options API pattern and connects to our Spring Boot backend.

## Technology

- Vue.js 3
- JavaScript (ES6+)
- Vue Router 4
- Native fetch() API
- SCSS
- Vite

## Details

### Install Dependencies

On first time setup:

```
npm install
```

If you don't install new dependencies, you won't need to run it again. Dependencies are installed in
the `node_modules` directory. If you delete this directory, you can always run again the install command.

## Start the development server

```

npm run dev

```

Normally you will only need this for the project. The server should auto reload your app each time you make
a change. However, sometimes after a compile error, it fails to detect new changes, you may need to restart it.

## Build the application for deployment

```

npm run build

```

This creates optimized production files in the dist directory.

## Features

### Pages

- Login - User selection interface
- Dashboard - system statistics overview
- Users - List, Add, Edit, Detail pages
- Devices - List, Add, edit, Detail pages
- Notifications - List and Detail pages
- Sensor Data - List with device filter
- Thresholds - List, add, edit, Detail, Delete pages

### all professor requirements are implemented:

- List rendering with v-for
- Conditional display with v-if
- Modular component architecture
- AJAX communication with fetch()
- User input with v-model
- Input validation
- Error message handling
- Natural and easy-to-use interface

## Project Structure

```
frontend/
-- src/
---- views/           (17 page components: Login, Dashboard, Users, Devices, Notifications, SensorData, Thresholds)
---- components/      (NavBar, ErrorOverlay)
---- services/        (API communication: userService, deviceService, notificationService, sensorDataService, thresholdService, errorService)
---- router/          (Navigation config)
---- config.js        (API URL configuration)
---- App.vue
---- main.js
```

## API Connection

The frontend connects to the backend at http://localhost:8080/api

Make sure the backend is running before starting the frontend.

## Notes

- All forms use v-model for two-way data binding
- All API calls use native fetch() (no axios)
- Error messages appear in ErrorOverlay component
- NavBar component provides navigation on all pages
- Routes are defined in router/index.js
- API URL is centralized in config.js for easy configuration
- Custom validation logic for complex forms (Thresholds)
- Delete operations include confirmation dialogs
- Format helpers for displaying range data (min/max thresholds)

## Documentation

For complete implementation documentation, features explanation, and code examples, see:

[Complete Frontend Documentation](../docs/frontend/README.md)

This frontend works with:

- Spring Boot backend on port 8080
