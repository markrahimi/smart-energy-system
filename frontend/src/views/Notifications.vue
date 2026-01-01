<template>
  <div>

    <h2>Notifications</h2>

    <div v-if="loading">
      <p>loading notifications...</p>
    </div>

    <div v-if="!loading && notifications.length === 0">
      <p>no notifications found</p>
    </div>

    <div v-if="!loading && notifications.length > 0">
      
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Message</th>
            <th>Type</th>
            <th>Priority</th>
            <th>Read</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="notif in notifications" :key="notif.id">
            <td>{{ notif.id }}</td>
            <td>{{ notif.title }}</td>
            <td>{{ notif.message }}</td>
            <td>{{ notif.type }}</td>
            <td>{{ notif.priority }}</td>
            <td>{{ notif.isRead ? 'Yes' : 'No' }}</td>
            <td>
              <button @click="viewNotification(notif.id)" class="btn-view">View</button>
            </td>
          </tr>
        </tbody>
      </table>

    </div>
    
  </div>
</template>



<script>

import { getNotifications } from '../services/notificationService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'Notifications',
  data() {
    return {
      notifications: [],
      loading: false
    }
  },
  mounted() {
    this.loadNotifications()
  },
  methods: {
    async loadNotifications() {
      this.loading = true;

      try {
        this.notifications = await getNotifications()
      } 
      catch (err) {
        sendMessage('failed to load notifications')
      } 
      finally {
        this.loading = false
      }
    },
    viewNotification(id) {
      this.$router.push(`/notifications/${id}`)
    }
  }
}
</script>


<style scoped>

.btn-view {
  padding: 5px 15px;
  background-color: #2ecc71;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-view:hover {
  opacity: 0.9;
}
</style>
