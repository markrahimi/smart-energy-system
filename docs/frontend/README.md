# Smart Energy System - Frontend Implementation

## Overview

This document explains how we developed the frontend for our Smart Energy Management system using Vue.js 3. We built a complete web application for managing users, devices, sensor data, notifications, and energy thresholds.

## Technology

- Vue.js 3 with Options API pattern
- JavaScript (ES6+)
- Vue Router 4 for navigation
- Native fetch() API for HTTP requests
- SCSS for styling
- Vite as build tool

## Development Process

### Starting Point

In our meeting before Christmas break, we asked the professor how to start the frontend. He suggested we use assignment-3 as the foundation since it already had a basic Vue.js structure. We copied those files to our frontend folder and used them as the starting point.

### API-First Development Approach

backend had already developed and documented all REST APIs using Swagger. We accessed the Swagger UI at `http://localhost:8080/swagger-ui.html` and studied all available endpoints.

For each entity (User, Device, Notification, SensorData, Threshold), we:

1. Reviewed the API endpoints in Swagger
2. Tested the endpoints using Postman to understand request/response formats
3. Created service files with fetch() calls
4. Built Vue components that use these services

This API-first approach meant we always knew exactly what data structure to expect and could develop the frontend independently while the backend was being tested.

### order

1. **Dashboard** - Started simple with statistics display to test API connectivity
2. **Users Management** - Complete CRUD (list, add, edit, detail views)
3. **Devices Management** - Similar to users, complete CRUD operations
4. **Notifications** - Read-only list and detail views
5. **Sensor Data** - List view with device filtering capability
6. **Thresholds Management** - Complex CRUD with validation for min/max ranges
7. **Login Page** - Added at the end as entry point

### Why We Built Login Last

Originally we planned to implement proper authentication with passwords. However, as we approached the deadline, we realized authentication would require:

- Password hashing in backend
- JWT tokens or sessions
- Login state management
- Protected routes

The professor advised us to focus on demonstrating Vue.js features rather than security implementation. He suggested a simple user selection interface for now which we can upgrade to real authentication later

## structure

```
frontend/
-- src/
---- views/                    (17 page components)
------ Login.vue              (User selection interface)
------ Dashboard.vue          (Statistics overview)
------ Users.vue              (User list)
------ AddUser.vue            (Create user form)
------ EditUser.vue           (Update user form)
------ UserDetail.vue         (User details with related data)
------ Devices.vue            (Device list)
------ AddDevice.vue          (Create device form)
------ EditDevice.vue         (Update device form)
------ DeviceDetail.vue       (Device details)
------ Notifications.vue      (Notification list)
------ NotificationDetail.vue (Notification details)
------ SensorData.vue         (Sensor readings list)
------ Thresholds.vue         (Threshold list)
------ AddThreshold.vue       (Create threshold form)
------ EditThreshold.vue      (Update threshold form)
------ ThresholdDetail.vue    (Threshold details)
---- components/
------ NavBar.vue             (Global navigation)
------ ErrorOverlay.vue       (Error message display)
---- services/
------ userService.js         (User API calls)
------ deviceService.js       (Device API calls)
------ notificationService.js (Notification API calls)
------ sensorDataService.js   (Sensor data API calls)
------ thresholdService.js    (Threshold API calls)
------ errorService.js        (Error messaging)
---- router/
------ index.js               (Route definitions)
---- config.js                (API URL configuration)
---- App.vue                  (Root component)
---- main.js                  (Application entry point)
-- public/
-- package.json
-- vite.config.js
```

### Why We Separated SCSS Files

Initially all our styles were in `<style scoped>` blocks inside each component. As the project grew, we noticed several problems:

1. **Code duplication**
2. **Similar components had slightly different styles**
3. **Hard to maintain**
4. **Large component files**

We solved this by:
we created separate SCSS files for each major page/component and Defining common variables (colors, spacing, fonts) in a shared file.

This made the codebase much easier. When we needed to change the primary color, we changed one variable and all pages updated automatically.

### Service Layer Architecture

All API communication goes through service files. Views never directly call fetch(). This separation provides several benefits: multiple components can use the same API call without duplicating code amd If an endpoint URL changes, we update one service function

Example from userService.js:

```
import { HOST } from "../config.js";

export async function getUsers() {
  const res = await fetch(`${HOST}/users`);
  if (!res.ok) {
    throw new Error("Failed to fetch users");
  }
  return await res.json();
}

export async function createUser(userData) {
  const res = await fetch(`${HOST}/users`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  if (!res.ok) {
    throw new Error("Failed to create user");
  }
  return await res.json();
}
```

Every service file follows this pattern - simple, focused functions that handle one API operation.

### Configuration Management

We created config.js to centralize the API base URL:

```
export const HOST = "http://localhost:8080/api";
```

All service files import this constant. This means we can change the backend URL in one place.

## Pages

### Login Page (Login.vue)

Entry point of the application. Shows a list of all users from the database. Each user card displays their name and email with a Login button.

**Why this approach**
we quick develop without complex authentication and professor approved this for demonstration purposes

**Future:**
Real authentication with username/password, JWT tokens, and protected routes.

### Dashboard (Dashboard.vue)

Overview page showing system statistics:

- Total number of users
- Total number of devices
- Total sensor data readings
- Total notifications

Uses Promise.all to fetch all data in parallel for faster loading.

### Users Management

**Users.vue** - Table showing all users with columns for name, email, role. Actions: View details, Edit, Add new user.

**AddUser.vue** - Form with fields:

- Username (required, text)
- Email (required, email validation)
- Full Name (required, text)
- Password (required, password)
- Role (required, dropdown: USER/ADMIN)

**EditUser.vue** - Same form as AddUser but pre-filled with existing user data. Fetches user by ID on page load.

**UserDetail.vue** - Shows complete user information plus lists of their devices and notifications. Demonstrates fetching related data from multiple endpoints.

### Devices Management

ike the users section with its own fields.

**Devices.vue**

**AddDevice.vue**

**EditDevice.vue**

**DeviceDetail.vue**

### Notifications

**Notifications.vue**

**NotificationDetail.vue** -when viewed, the backend automatically marks it as read.

### Sensor Data (SensorData.vue)

**Filter feature:** Dropdown to filter by device. Uses computed properties to:

1. Extract unique devices from the data
2. Filter readings based on selected device
3. Update display instantly when selection changes

This demonstrates Vue's reactivity system - when selectedDeviceId changes, filteredSensorData automatically recalculates.

### Thresholds

**Thresholds.vue**

**AddThreshold.vue**

**Custom validation:** Ensures min < max for each sensor type when both values are provided. Uses v-model.number to handle numeric inputs properly.

**EditThreshold.vue**

**ThresholdDetail.vue**

**Delete functionality:**

### Modular Component Architecture

The application is organized into modules:

**Views** - One component per page, focused on a single responsibility

**Components** - Reusable UI elements (NavBar, ErrorOverlay)

**Services** - API communication logic separated from UI

**Router** - All navigation routes in one place

## Navigation

```vue
<router-link to="/">Dashboard</router-link>
<router-link to="/users">Users</router-link>
<router-link to="/devices">Devices</router-link>
<router-link to="/notifications">Notifications</router-link>
<router-link to="/sensor-data">Sensor Data</router-link>
<router-link to="/thresholds">Thresholds</router-link>
```

Router-link creates links that navigate without page reload - the URL changes and the view updates instantly.

We also use programmatic navigation after form submissions:

```
await createUser(this.user);
this.$router.push("/users"); // Navigate to user list
```

Routes are defined in router/index.js with dynamic parameters:

```
{
  path: '/users/:id',
  component: UserDetail
}
```

Access parameters in components with `this.$route.params.id`.

## Styling Approach

We use SCSS for styling with a consistent design system

## Challenges and Solutions

### Challenge : Learning Vue Router

We hadn't used Vue Router before. We spent time reading documentation and watching tutorials to understand.

Studied the official Vue Router documentation and examined examples from assignment-3.

### Challenge: Pre-filling Edit Forms

edit pages needed to load existing data and populate forms. We learned to use the mounted() lifecycle hook:

```

mounted() {
  this.loadUser()
},
methods: {
  async loadUser() {
    const id = this.$route.params.id
    this.user = await getUserById(id)
  }
}

```

The `$route.params.id` extracts the ID from the URL (e.g., /users/123/edit).

### Challenge : fetching related

UserDetail.vue shows a user plus their devices and notifications. This required multiple API calls and filtering:

```
this.user = await getUserById(id);
const allDevices = await getDevices();
const allNotifications = await getNotifications();

this.userDevices = allDevices.filter((d) => d.userId === id);
this.userNotifications = allNotifications.filter((n) => n.userId === id);
```

### Challenge 4: Computed Properties for Filtering

SensorData filter was complex - we needed to extract unique devices and filter data reactively. Computed properties solved this elegantly by automatically recalculating when dependencies change.

### Challenge 6: Delete Confirmation

We needed to confirm before deleting thresholds. Browser's native confirm() dialog works well:

```
async confirmDelete(deviceId) {
  if (confirm('Are you sure you want to delete this threshold?')) {
    try {
      await deleteThreshold(deviceId)
      sendMessage('Threshold deleted successfully')
      this.loadThresholds()  // Refresh list
    } catch (error) {
      sendMessage('Failed to delete threshold')
    }
  }
}
```

## We Accomplished:

1. Complete web application with 17 pages
2. Full CRUD for Users (Create, Read, Update, Detail)
3. Full CRUD for Devices (Create, Read, Update, Detail)
4. Full CRUD for Thresholds (Create, Read, Update, Delete, Detail)
5. Read operations for Notifications and Sensor Data
6. Login page with user selection
7. Dashboard with system statistics
8. Device filtering in Sensor Data
9. Custom validation for complex forms
10. Delete confirmation dialogs
11. Centralized configuration management
12. Format helpers for displaying range data
13. Error handling and user feedback
14. UI design
15. Proper separation of concerns (views, services, routing)
