<template>
  <div>
    <h2>Notification Detail</h2>

    <div v-if="loading">
      <p>loading...</p>
    </div>

    <div v-if="!loading && notification" class="detail-container">
      
      <div class="detail-row">
        <span class="label">ID:</span>
        <span class="value">{{notification.id}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Title:</span>
        <span class="value">{{notification.title}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Message:</span>
        <span class="value">{{notification.message}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Type:</span>
        <span class="value">{{notification.type}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Priority:</span>
        <span class="value">{{notification.priority}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Status:</span>
        <span class="value">{{notification.isRead ? 'Read' : 'Unread'}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Created At:</span>
        <span class="value">{{notification.createdAt}}</span>
      </div>

      <div class="actions">
        <button @click="goBack" class="btn-back">Back to Notifications</button>
      </div>
    </div>
  </div>
</template>

<script>
import { getNotificationById } from '../services/notificationService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'NotificationDetail',
  data() {
    return {
      notification: null,
      loading: false
    }
  },
  mounted() {
    this.loadNotification()
  },
  methods: {
    async loadNotification() {
      this.loading = true
      try {
        const notifId = this.$route.params.id
        this.notification = await getNotificationById(notifId)
      } catch(err) {
        sendMessage('failed to load notification')
      } finally {
        this.loading = false
      }
    },
    goBack() {
      this.$router.push('/notifications')
    }
  }
}
</script>

<style scoped>
.detail-container {
  max-width: 600px;
  margin: 20px 0;
}

.detail-row {
  display: flex;
  padding: 12px;
  border-bottom: 1px solid #eee;
}

.label {
  font-weight: bold;
  width: 150px;
  color: #555;
}

.value {
  flex: 1;
  color: #333;
}

.actions {
  margin-top: 20px;
}

.btn-back {
  padding: 10px 20px;
  background-color: #95a5a6;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-back:hover {
  opacity: 0.9;
}
</style>
