<template>
  <div>
    <h2>Sensor Data</h2>

    <div class="filter-section" v-if="!loading">
      
      <label>
        Filter by device:
      </label>

      <select v-model="selectedDeviceId" >
        <option value="">All Devices</option>
        <option v-for="device in uniqueDevices" :key="device.id" :value="device.id">
          {{device.name}}
        </option>
      </select>
      
    </div>

    <div v-if="loading">
      <p>loading sensor data...</p>
    </div>

    <div v-if="!loading && filteredSensorData.length === 0">
      <p>no sensor data available</p>
    </div>

    <div v-if="!loading && filteredSensorData.length > 0">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Device Name</th>
            <th>Temperature</th>
            <th>Humidity</th>
            <th>Luminosity</th>
            <th>Distance</th>
            <th>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="data in filteredSensorData" :key="data.id">
            <td>{{ data.id }}</td>
            <td>{{ data.device.name }}</td>
            <td>{{ data.temperature }}</td>
            <td>{{ data.humidity }}</td>
            <td>{{ data.luminosity }}</td>
            <td>{{ data.distance || 'N/A' }}</td>
            <td>{{ data.timestamp }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { getSensorData } from '../services/sensorDataService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'SensorData',
  data() {
    return {
      sensorData: [],
      loading: false,
      selectedDeviceId: ''
    }
  },
  computed: {
    uniqueDevices() {
      const devices = this.sensorData.map(data => data.device)
      const unique = []
      const ids = []
      devices.forEach(device => {
        if(!ids.includes(device.id)) {
          ids.push(device.id)
          unique.push(device)
        }
      })
      return unique
    },
    filteredSensorData() {
      if(!this.selectedDeviceId) {
        return this.sensorData
      }
      return this.sensorData.filter(data => data.device.id == this.selectedDeviceId)
    }
  },
  mounted() {
    this.fetchSensorData()
  },
  methods: {
    async fetchSensorData() {
      this.loading = true
      try {
        this.sensorData = await getSensorData()
      } catch (error) {
        sendMessage('failed to load sensor data')
      } finally {
        this.loading = false
      }
    }
  }
}
</script>



<style scoped>

h2 {
  margin-bottom: 20px;
}

.filter-section {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.filter-section label {
  margin-right: 10px;
  font-weight: bold;
}

.filter-section select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}
</style>
