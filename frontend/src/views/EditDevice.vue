<template>
  <div>
    <h2>Edit Device</h2>

    <div v-if="loading">
      <p>loading device...</p>
    </div>

    <form v-if="!loading && device" @submit.prevent="handleSubmit">

      <div class="form-group">
        <label>Name:</label>
        <input type="text" v-model="device.name" required />
      </div>

      <div class="form-group">
        <label>Type:</label>
        <select v-model="device.type" required>
          <option value="">Select type</option>
          <option value="SOLAR_PANEL">Solar Panel</option>
          <option value="WIND_TURBINE">Wind Turbine</option>
          <option value="BATTERY">Battery</option>
          <option value="INVERTER">Inverter</option>
          <option value="CHARGE_CONTROLLER">Charge Controller</option>
        </select>
      </div>

      <div class="form-group">
        <label>Location:</label>
        <input type="text" v-model="device.location" required />
      </div>

      <div class="form-group">
        <label>Status:</label>
        <select v-model="device.status" required>
          <option value="">Select status</option>
          <option value="ONLINE">Online</option>
          <option value="OFFLINE">Offline</option>
          <option value="MAINTENANCE">Maintenance</option>
        </select>
      </div>

      <div class="form-actions">

        <button type="submit" :disabled="submitting">
          {{ submitting ? 'Updating...' : 'Update Device' }}
        </button>

        <button type="button" @click="cancel">Cancel</button>

      </div>
    </form>

  </div>
</template>



<script>
import { getDeviceById, updateDevice } from '../services/deviceService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'EditDevice',
  data() {
    return {
      device: null,
      loading: false,
      submitting: false
    }
  },
  mounted() {
    this.loadDevice()
  },
  methods: {
    async loadDevice() {
      this.loading = true
      try {
        const deviceId = this.$route.params.id
        this.device = await getDeviceById(deviceId)
      } catch(err) {
        sendMessage('failed to load device')
      } finally {
        this.loading = false
      }
    },
    async handleSubmit() {
      this.submitting = true
      try {
        const deviceId = this.$route.params.id
        await updateDevice(deviceId, {
          name: this.device.name,
          type: this.device.type,
          location: this.device.location,
          status: this.device.status
        })
        sendMessage('device updated successfully')
        this.$router.push('/devices')
      } 
      catch(err) {
        sendMessage('failed to update device')
      } finally {
        this.submitting = false
      }
    },
    cancel() {
      this.$router.push('/devices')
    }
  }
}
</script>
