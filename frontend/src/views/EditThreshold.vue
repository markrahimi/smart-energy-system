<template>
  <div>
    <h2>Edit Threshold</h2>

    <div v-if="loading">
      <p>loading...</p>
    </div>

    <form v-if="!loading" @submit.prevent="handleSubmit">

      <div class="form-group">
        <label>
          Device ID
        </label>
        <input type="text" v-model="form.deviceId" disabled />
      </div>

      <div class="form-group">
        <label>
          User ID
        </label>
        <input type="text" v-model="form.userId" disabled />
      </div>

      <div class="form-group">
        <label>
          Min Temperature (C)
        </label>
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
          Min Humidity (%):
        </label>
        <input type="number" step="0.1" v-model.number="form.minHumidity" />
      </div>

      <div class="form-group">
        <label>
          Max Humidity (%)
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
        <label>
          Max Luminosity (lux):
        </label>
        <input type="number" step="0.1" v-model.number="form.maxLuminosity" />
      </div>

      <div class="form-group">
        <label>
          Min Power (W):
        </label>
        <input type="number" step="0.1" v-model.number="form.minPower" />
      </div>

      <div class="form-group">
        <label>
          Max Power (W):
        </label>
        <input type="number" step="0.1" v-model.number="form.maxPower" />
      </div>

      <div class="form-group">
        <label>
          Min Voltage (V):
        </label>
        <input type="number" step="0.1" v-model.number="form.minVoltage" />
      </div>

      <div class="form-group">
        <label>
          Max Voltage (V):
        </label>
        <input type="number" step="0.1" v-model.number="form.maxVoltage" />
      </div>

      <div class="form-group">
        <label>
          Min Current (A):
        </label>
        <input type="number" step="0.1" v-model.number="form.minCurrent" />
      </div>

      <div class="form-group">
        <label>
          Max Current (A):
        </label>
        <input type="number" step="0.1" v-model.number="form.maxCurrent" />
      </div>

      <div class="form-actions">
        <button type="submit" :disabled="submitting">
          {{ submitting ? 'Loading...' : 'Update Threshold' }}
        </button>

        <button type="button" @click="goBack">Cancel</button>
      </div>

    </form>
  </div>
</template>

<script>
  
import { getThresholdByDeviceId, updateThreshold } from '../services/thresholdService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'EditThreshold',
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
      loading: false,
      submitting: false
    }
  },
  mounted() {
    this.loadThreshold()
  },
  methods: {
    async loadThreshold() {
      this.loading = true
      try {
        
        const deviceId = this.$route.params.deviceId;

        const threshold = await getThresholdByDeviceId(deviceId);

        this.form = {
          deviceId: threshold.deviceId,
          userId: threshold.userId,
          minTemperature: threshold.minTemperature,
          maxTemperature: threshold.maxTemperature,
          minHumidity: threshold.minHumidity,
          maxHumidity: threshold.maxHumidity,
          minLuminosity: threshold.minLuminosity,
          maxLuminosity: threshold.maxLuminosity,
          minPower: threshold.minPower,
          maxPower: threshold.maxPower,
          minVoltage: threshold.minVoltage,
          maxVoltage: threshold.maxVoltage,
          minCurrent: threshold.minCurrent,
          maxCurrent: threshold.maxCurrent
        }
      } catch (err) {
        sendMessage('failed to load')
      } finally {
        this.loading = false
      }
    },
    async handleSubmit() {
      if (!this.validateForm()) {
        sendMessage('min values must be less than max values')
        return
      }

      this.submitting = true

      try {
        await updateThreshold(this.form.deviceId, this.form)
        sendMessage('threshold updated successfully')
        this.$router.push('/thresholds')
      } catch (err) {
        sendMessage('failed to update threshold')
      } finally {
        this.submitting = false
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
