<template>
  <div class="container">
    <nav class="navbar">
      <ul class="nav-menu">
        <li><router-link to="/">Dashboard</router-link></li>
        <li><router-link to="/users">Users</router-link></li>
        <li><router-link to="/devices">Devices</router-link></li>
        <li><router-link to="/notifications">Notifications</router-link></li>
        <li><router-link to="/sensors">Sensor Data</router-link></li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'NavBar'
}
</script>

<style scoped>
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
</style>
