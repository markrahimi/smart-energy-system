<template>
  <div>
    <h2>Device Detail</h2>

    <div v-if="loading">
      <p>loading...</p>
    </div>

    <div v-if="!loading && device" class="detail-container">

      <div class="detail-row">
        <span class="label">ID:</span>
        <span class="value">{{device.id}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Name:</span>
        <span class="value">{{device.name}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Type:</span>
        <span class="value">{{device.type}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Status:</span>
        <span class="value">{{device.status}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Location:</span>
        <span class="value">{{device.location || 'N/A'}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Active:</span>
        <span class="value">{{device.active ? 'Yes' : 'No'}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Created At:</span>
        <span class="value">{{device.createdAt}}</span>
      </div>

      <div class="detail-row">
        <span class="label">Owner:</span>
        <span class="value">{{device.user ? device.user.fullName : 'N/A'}}</span>
      </div>

      <div class="actions">
        <button @click="goBack" class="btn-back">Back to Devices</button>
      </div>

    </div>
  </div>
</template>



<script>
import { getDeviceById } from '../services/deviceService.js';
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'DeviceDetail',
  data() {
    return {
      device: null,
      loading: false
    }
  },
  mounted() {
    this.loadDevice()
  },
  methods: {
    async loadDevice() {
      this.loading = true;

      try {
        const deviceId = this.$route.params.id
        this.device = await getDeviceById(deviceId)
      } 
      catch(err) {
        sendMessage('failed to load device')
      } 
      finally {
        this.loading = false
      }
    },
    goBack() {
      this.$router.push('/devices')
    }
  }
}
</script>

<style scoped>
.detail-container{
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
