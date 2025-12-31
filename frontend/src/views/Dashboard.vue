<template>
  <div>
    <h2>Dashboard</h2>
    <p class="detail-page">Welcome to Smart Energy System</p>

    <div v-if="loading">
      <p>loading...</p>
    </div>

    <div v-if="!loading" class="stats">
      <div class="stat-card">
        <h3>{{ userCount }}</h3>
        <p>Total Users</p>
      </div>

      <div class="stat-card">
        <h3>{{ deviceCount }}</h3>
        <p>Total Devices</p>
      </div>

      <div class="stat-card">
        <h3>{{ sensorCount }}</h3>
        <p>Sensor Data</p>
      </div>

      <div class="stat-card">
        <h3>{{ notificationCount }}</h3>
        <p>Notifications</p>
      </div>
    </div>

  </div>
</template>

<script>
import { getUsers } from '../services/userService.js'
import { getDevices } from '../services/deviceService.js'
import { getSensorData } from '../services/sensorDataService.js'
import { getNotifications } from '../services/notificationService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'Dashboard',
  data() {
    return {
      userCount: 0,
      deviceCount: 0,
      sensorCount: 0,
      notificationCount: 0,
      loading: false
    }
  },
  mounted() {
    this.loadStats()
  },
  methods: {
    async loadStats() {
      this.loading = true
      try {
        const users = await getUsers()
        this.userCount = users.length

        const devices = await getDevices()
        this.deviceCount = devices.length

        const sensors = await getSensorData()
        this.sensorCount = sensors.length

        const notifs = await getNotifications()
        this.notificationCount = notifs.length
      } catch(err) {
        sendMessage('failed to load dashboard')
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
  h2 {
    color: #333;
  }
  .detail-page{
    margin-bottom: 20px;
  }
  .stats{
    display:flex;
    gap:20px;
    margin-bottom:30px;
  }

  .stat-card{
    flex:1;
    padding:20px;
    background:#f5f5f5;
    border-radius:5px;
    text-align:center;
  }

  .stat-card h3{
    font-size:32px;
    margin:0 0 10px 0;
    color:#3498db;
  }

  .stat-card p{
    margin:0;
    color:#555;
  }
</style>
