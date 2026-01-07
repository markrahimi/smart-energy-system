<template>
  <div>
    <h2>Add Threshold</h2>

    <form @submit.prevent="handleSubmit">

      
      <div class="form-group">
        <label>Device:</label>
        <select v-model="form.deviceId" required>
          <option value="">Select device</option>
          <option v-for="device in devices" :key="device.id" :value="device.id">
            {{ device.name }} ({{ device.type }})
          </option>
        </select>
      </div>


      <div class="form-group">
        <label>User:</label>
        <select v-model="form.userId" required>
          <option value="">Select user</option>
          <option v-for="user in users" :key="user.id" :value="user.id">
            {{ user.fullName }} ({{ user.email }})
          </option>
        </select>
      </div>


      <div class="form-group">
        <label>Min Temperature (C):</label>
        <input type="number" step="0.1" v-model.number="form.minTemperature" />
      </div>

      <div class="form-group">
        <label>
          Max Temperature (C)
        </label>
        <input type="number" step="0.1" v-model.number="form.maxTemperature" />
      </div>

      <div class="form-group">
        <label>
          Min Humidity (%)
        </label>
        <input type="number" step="0.1" v-model.number="form.minHumidity" />
      </div>

      <div class="form-group">
        <label>
          Max Humidity (%):
        </label>
        <input type="number" step="0.1" v-model.number="form.maxHumidity" />
      </div>

      <div class="form-group">
        <label>
          Min Luminosity (lux):

        </label>
        <input type="number" step="0.1" v-model.number="form.minLuminosity" />
      </div>


      <div class="form-group">
        <label>Max Luminosity (lux):</label>
        <input type="number" step="0.1" v-model.number="form.maxLuminosity"/>
      </div>

      <div class="form-group">
        <label>Min Power (W):</label>
        <input type="number" step="0.1" v-model.number="form.minPower" />
      </div>

      <div class="form-group">
        <label>Max Power (W):</label>
        <input type="number" step="0.1" v-model.number="form.maxPower"/>
      </div>

      <div class="form-group">
        <label>Min Voltage (V):</label>
        <input type="number" step="0.1" v-model.number="form.minVoltage" />
      </div>

      <div class="form-group">
        <label>Max Voltage (V):</label>
        <input type="number" step="0.1" v-model.number="form.maxVoltage" />
      </div>

      <div class="form-group">
        <label>Min Current (A):</label>
        <input type="number" step="0.1" v-model.number="form.minCurrent" />
      </div>

      <div class="form-group">
        <label>Max Current (A):</label>
        <input type="number" step="0.1" v-model.number="form.maxCurrent" />
      </div>

      <div class="form-actions">
        <button type="submit" :disabled="loading">
          {{ loading ? 'Loading...' : 'Create Threshold' }}
        </button>

        <button type="button" @click="goBack">Cancel</button>

      </div>

    </form>
  </div>
</template>

<script>
import { createThreshold } from '../services/thresholdService.js'
import { getDevices } from '../services/deviceService.js'
import { getUsers } from '../services/userService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'AddThreshold',
  data() {
    return {
      form: {
        deviceId: '',
        userId: '',
        minTemperature: null,
        maxTemperature: null,
        minHumidity: null,
        maxHumidity: null,
        minLuminosity: null,
        maxLuminosity: null,
        minPower: null,
        maxPower: null,
        minVoltage: null,
        maxVoltage: null,
        minCurrent: null,
        maxCurrent: null
      },
      devices: [],
      users: [],
      loading: false
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    async loadData() {
      try {
        this.devices = await getDevices()
        this.users = await getUsers()
      } catch (err) {
        sendMessage('failed to load devices and users')
      }
    },
    async handleSubmit() {
      if (!this.validateForm()) {
        sendMessage('min values must be less than max values')
        return
      }

      this.loading = true

      try {
        await createThreshold(this.form)
        sendMessage('threshold created successfully')
        this.$router.push('/thresholds')
      } catch (err) {
        sendMessage('failed to create threshold')
      } finally {
        this.loading = false
      }
    },
    validateForm() {
      if (this.form.minTemperature !== null && this.form.maxTemperature !== null
          && this.form.minTemperature >= this.form.maxTemperature) {
        return false
      }
      if (this.form.minHumidity !== null && this.form.maxHumidity !== null
          && this.form.minHumidity >= this.form.maxHumidity) {
        return false
      }
      if (this.form.minLuminosity !== null && this.form.maxLuminosity !== null
          && this.form.minLuminosity >= this.form.maxLuminosity) {
        return false
      }
      if (this.form.minPower !== null && this.form.maxPower !== null
          && this.form.minPower >= this.form.maxPower) {
        return false
      }
      if (this.form.minVoltage !== null && this.form.maxVoltage !== null
          && this.form.minVoltage >= this.form.maxVoltage) {
        return false
      }
      if (this.form.minCurrent !== null && this.form.maxCurrent !== null
          && this.form.minCurrent >= this.form.maxCurrent) {
        return false
      }
      return true
    },
    goBack() {
      this.$router.push('/thresholds')
    }
  }
}
</script>
