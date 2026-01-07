<template>
  <div>
    <h2>Threshold Details</h2>

    <div v-if="loading">
      <p>loading threshold...</p>
    </div>

    <div v-if="!loading && threshold" class="detail-page">
      
      <div class="detail-section">
        <h3>
          General Information
        </h3>
        <p>
          <strong>ID:</strong>
           {{ threshold.id }}
          </p>
        <p>
          <strong>Device ID:</strong>
           {{ threshold.deviceId }}
          </p>
        <p>
          <strong>User ID:</strong>
           {{ threshold.userId }}
          </p>
        <p>
          <strong>Active:</strong>
           {{ threshold.active ? 'Yes' : 'No' }}
          </p>
        <p>
          <strong>Created At:</strong>
           {{ new Date(threshold.createdAt).toLocaleString() }}
          </p>
      </div>

      <div class="detail-section">
        <h3>
          Temperature Limits (C)
        </h3>
        <p>
          <strong>Min:</strong>
            {{ threshold.minTemperature || 'N/A' }}
        </p>
        <p>
          <strong>Max:</strong>
            {{ threshold.maxTemperature || 'N/A' }}
        </p>
      </div>

      <div class="detail-section">
        <h3>Humidity Limits (%)</h3>
        <p>
          <strong>Min:</strong>
            {{ threshold.minHumidity || 'N/A' }}
        </p>
        <p>
          <strong>Max:</strong>
            {{ threshold.maxHumidity || 'N/A' }}
        </p>
      </div>

      <div class="detail-section">
        <h3>Luminosity Limits (lux)</h3>
        <p>
          <strong>Min:</strong>
            {{ threshold.minLuminosity || 'N/A' }}
        </p>
        <p>
          <strong>Max:</strong>
            {{ threshold.maxLuminosity || 'N/A' }}
        </p>
      </div>

      <div class="detail-section">
        <h3>Power Limits (W)</h3>
        <p>
          <strong>Min:</strong>
            {{ threshold.minPower || 'N/A' }}
        </p>
        <p>
          <strong>Max:</strong>
            {{ threshold.maxPower || 'N/A' }}
        </p>
      </div>

      <div class="detail-section">
        <h3>Voltage Limits (V)</h3>
        <p>
          <strong>Min:</strong>
            {{ threshold.minVoltage || 'N/A' }}
        </p>
        <p>
          <strong>Max:</strong>
            {{ threshold.maxVoltage || 'N/A' }}
        </p>
      </div>

      <div class="detail-section">
        <h3>Current Limits (A)</h3>
        <p>
          <strong>Min:</strong>
            {{ threshold.minCurrent || 'N/A' }}
        </p>
        <p>
          <strong>Max:</strong>
            {{ threshold.maxCurrent || 'N/A' }}
        </p>
      </div>

      <div class="detail-actions">
        <button @click="editThreshold" class="btn-edit">Edit</button>
        <button @click="goBack" class="btn-back">Back to List</button>
      </div>

    </div>

    <div v-if="!loading && !threshold">
      <p>threshold not found</p>
    </div>
  </div>
</template>

<script>
import { getThresholdByDeviceId } from '../services/thresholdService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'ThresholdDetail',
  data() {
    return {
      threshold: null,
      loading: false
    }
  },
  mounted() {
    this.loadThreshold()
  },
  methods: {
    async loadThreshold() {
      this.loading = true
      try {
        const deviceId = this.$route.params.deviceId
        this.threshold = await getThresholdByDeviceId(deviceId)
      } catch (err) {
        sendMessage('failed to load threshold')
      } finally {
        this.loading = false
      }
    },
    editThreshold() {
      this.$router.push(`/thresholds/${this.$route.params.deviceId}/edit`)
    },
    goBack() {
      this.$router.push('/thresholds')
    }
  }
}
</script>

<style scoped>


.detail-page {
  max-width: 800px;
}

.detail-section {
  margin-bottom: 30px;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 5px;
}

.detail-section h3 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #333;
}

.detail-section p {
  margin: 8px 0;
}

.detail-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}


.btn-edit {
  padding: 10px 20px;
  background-color: #f39c12;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-edit:hover {
  opacity: 0.9;
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
