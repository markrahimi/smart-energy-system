<template>
  <error-overlay />

  <div class="app">
    <div class="container">
      <nav class="navbar">
        <ul class="nav-menu">
          <li><router-link to="/">Dashboard</router-link></li>
          <li><router-link to="/users">Users</router-link></li>
          <li><router-link to="/devices">Devices</router-link></li>
          <li><router-link to="/notifications">Notifications</router-link></li>
        </ul>
      </nav>
    </div>

    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import ErrorOverlay from './ErrorOverlay.vue'

export default {
  components: {
    ErrorOverlay
  }
}
</script>

<style scoped>
.app {
  min-height: 100vh;
}

.navbar {
  background-color: #2c3e50;
  padding: 15px 20px;
  color: white;
}

.navbar h1 {
  margin: 0 0 10px 0;
  font-size: 24px;
}

.nav-menu {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  gap: 20px;
}

.nav-menu li a {
  color: white;
  text-decoration: none;
  padding: 5px 10px;
  border-radius: 4px;
}

.nav-menu li a:hover {
  background-color: #34495e;
}

.nav-menu li a.router-link-active {
  background-color: #3498db;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  text-align: center;
  color: #333;
}
</style>
