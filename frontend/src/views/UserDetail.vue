<template>
  <div>
    <div v-if="loading">
      <p>loading user...</p>
    </div>

    <div v-if="!loading && !user">
      <p>user not found</p>
    </div>

    <div v-if="!loading && user">
      <h2>User Details</h2>

      <div class="user-info">
        <div class="info-row">
          <span class="label">ID:</span>
          <span>{{user.id}}</span>
        </div>
        <div class="info-row">
          <span class="label">Username:</span>
          <span>{{user.username}}</span>
        </div>
        <div class="info-row">
          <span class="label">Email:</span>
          <span>{{user.email}}</span>
        </div>
        <div class="info-row">
          <span class="label">Full Name:</span>
          <span>{{user.fullName}}</span>
        </div>
        <div class="info-row">
          <span class="label">Role:</span>
          <span>{{user.role}}</span>
        </div>
      </div>

      <h3 v-if="user.devices && user.devices.length > 0">Devices</h3>
      <div v-if="user.devices && user.devices.length > 0">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Type</th>
              <th>Location</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="device in user.devices" :key="device.id">
              <td>{{device.id}}</td>
              <td>{{device.name}}</td>
              <td>{{device.type}}</td>
              <td>{{device.location}}</td>
              <td>{{device.status}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <p v-if="!user.devices || user.devices.length === 0">No devices found for this user</p>

      <h3 v-if="user.notifications && user.notifications.length > 0">Notifications</h3>
      <div v-if="user.notifications && user.notifications.length > 0">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Message</th>
              <th>Type</th>
              <th>Read</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="notification in user.notifications" :key="notification.id">
              <td>{{notification.id}}</td>
              <td>{{notification.title}}</td>
              <td>{{notification.message}}</td>
              <td>{{notification.type}}</td>
              <td>{{notification.isRead ? 'Yes' : 'No'}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <p v-if="!user.notifications || user.notifications.length === 0">No notifications for this user</p>
    </div>
  </div>
</template>

<script>
import { getUserById } from '../services/userService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'UserDetail',
  data() {
    return {
      user: null,
      loading: false
    }
  },
  mounted() {
    this.loadUser()
  },
  methods: {
    async loadUser() {
      this.loading = true
      try {
        const userId = this.$route.params.id
        this.user = await getUserById(userId)
      } catch(err) {
        sendMessage('failed to load user')
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
  margin-bottom: 20px;
}

h3 {
  margin-top: 30px;
  margin-bottom: 15px;
  color: #555;
}

.user-info {
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.info-row {
  display: flex;
  padding: 8px 0;
  border-bottom: 1px solid #ddd;
}

.info-row:last-child {
  border-bottom: none;
}

.label {
  font-weight: bold;
  width: 150px;
  color: #666;
}
</style>
