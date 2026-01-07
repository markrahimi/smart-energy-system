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
            <th>Device ID</th>
            <th>Temperature (کاC)</th>
            <th>Humidity (%)</th>
            <th>Luminosity (lux)</th>
            <th>Power (W)</th>
            <th>Voltage (V)</th>
            <th>Current (A)</th>
            <th>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="data in filteredSensorData" :key="data.id">
            <td>{{ data.id }}</td>
            <td>{{ data.deviceId }}</td>
            <td>{{ data.temperature || 'N/A' }}</td>
            <td>{{ data.humidity || 'N/A' }}</td>
            <td>{{ data.luminosity || 'N/A' }}</td>
            <td>{{ data.powerConsumption || 'N/A' }}</td>
            <td>{{ data.voltage || 'N/A' }}</td>
            <td>{{ data.current || 'N/A' }}</td>
            <td>{{ new Date(data.timestamp).toLocaleString() }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { getSensorData } from '../services/sensorDataService.js'
import { getDevices } from '../services/deviceService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'SensorData',
  data() {
    return {
      sensorData: [],
      devices: [],
      loading: false,
      selectedDeviceId: ''
    }
  },
  computed: {
    uniqueDevices() {
      const deviceIds = [...new Set(this.sensorData.map(data => data.deviceId))]
      return deviceIds.map(id => {
        const device = this.devices.find(d => d.id === id)
        return { id, name: device ? device.name : id }
      })
    },
    filteredSensorData() {
      if(!this.selectedDeviceId) {
        return this.sensorData
      }
      return this.sensorData.filter(data => data.deviceId == this.selectedDeviceId)
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        this.devices = await getDevices()
        this.sensorData = await getSensorData()
      } catch (error) {
        sendMessage('failed to load data')
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
