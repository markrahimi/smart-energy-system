<template>
  <div>
    <div class="header">
      
      <h2>Thresholds</h2>
      <button @click="addThreshold" class="btn-add">Add Threshold</button>
    </div>

    <div v-if="loading">
      <p>loading...</p>
    </div>

    <div v-if="!loading && thresholds.length === 0">
      <p>no thresholds found</p>
    </div>

    <div v-if="!loading && thresholds.length > 0">
      <table>
        <thead>
          <tr>
            <th>Device ID</th>
            <th>User ID</th>
            <th>Temperature (C)</th>
            <th>Humidity (%)</th>
            <th>Power (W)</th>
            <th>Active</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="threshold in thresholds" :key="threshold.id">
            <td>{{ threshold.deviceId }}</td>
            <td>{{ threshold.userId }}</td>
            <td>{{ formatRange(threshold.minTemperature, threshold.maxTemperature) }}</td>
            <td>{{ formatRange(threshold.minHumidity, threshold.maxHumidity) }}</td>
            <td>{{ formatRange(threshold.minPower, threshold.maxPower) }}</td>
            <td>{{ threshold.active ? 'Yes' : 'No' }}</td>
            <td>
              <button @click="viewThreshold(threshold.deviceId)" class="btn-view">View</button>
              <button @click="editThreshold(threshold.deviceId)" class="btn-edit">Edit</button>
              <button @click="confirmDelete(threshold.deviceId)" class="btn-delete">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { getThresholds, deleteThreshold } from '../services/thresholdService.js'
import { sendMessage } from '../services/errorService.js'


export default {
  name: 'Thresholds',
  data() {
    return {
      thresholds: [],
      loading: false
    }
  },
  mounted() {
    this.loadThresholds()
  },
  methods: {
    async loadThresholds() {
      this.loading = true
      try {
        this.thresholds = await getThresholds()
      } catch (err) {
        sendMessage('failed to load thresholds')
      } finally {
        this.loading = false
      }
    },
    formatRange(min, max) {
      if (min === null && max === null) {
        return 'N/A'
      }
      if (min === null) {
        return `<= ${max}`
      }
      if (max === null) {
        return `>= ${min}`
      }
      return `${min} - ${max}`
    },
    addThreshold() {
      this.$router.push('/thresholds/add')
    },
    viewThreshold(deviceId) {
      this.$router.push(`/thresholds/${deviceId}`)
    },
    editThreshold(deviceId) {
      this.$router.push(`/thresholds/${deviceId}/edit`)
    },
    async confirmDelete(deviceId) {
      if (confirm('are you sure you want to delete this threshold?')) {
        try {
          await deleteThreshold(deviceId)
          sendMessage('threshold deleted successfully')
          this.loadThresholds()
        } catch (err) {
          sendMessage('failed to delete threshold')
        }
      }
    }
  }
}
</script>

<style scoped>

.header{
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.btn-add{
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-add:hover{
  opacity: 0.9;
}

.btn-view{
  padding: 5px 15px;
  background-color: #2ecc71;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-view:hover{
  opacity: 0.9;
}

.btn-edit{
  padding: 5px 15px;
  background-color: #f39c12;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 5px;
}

.btn-edit:hover{
  opacity: 0.9;
}

.btn-delete{
  padding: 5px 15px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 5px;
}
</style>
