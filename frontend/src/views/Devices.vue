<template>
  <div>
    <div class="header">
      <h2>Devices</h2>
      <button @click="addDevice" class="btn-add">Add Device</button>
    </div>

    <div v-if="loading">
      <p>loading devices...</p>
    </div>

    <div v-if="!loading && devices.length === 0">
      <p>no devices available</p>
    </div>

    <div v-if="!loading && devices.length > 0">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Location</th>
            <th>Status</th>
            <th>Active</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="device in devices" :key="device.id">
            <td>{{ device.id }}</td>
            <td>{{ device.name }}</td>
            <td>{{ device.type }}</td>
            <td>{{ device.location }}</td>
            <td>{{ device.status }}</td>
            <td>{{ device.active ? 'Yes' : 'No' }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { getDevices } from '../services/deviceService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'Devices',
  data() {
    return {
      devices: [],
      loading: false
    }
  },
  mounted() {
    this.fetchDevices()
  },
  methods: {
    async fetchDevices() {
      this.loading = true
      try {
        this.devices = await getDevices()
      } catch (error) {
        sendMessage('failed to load devices')
      } finally {
        this.loading = false
      }
    },
    addDevice(){
      this.$router.push('/devices/add')
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

</style>
