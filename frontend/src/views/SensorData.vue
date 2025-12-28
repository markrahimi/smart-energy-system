<template>
  <div>
    <h2>Sensor Data</h2>

    <div v-if="loading">
      <p>loading sensor data...</p>
    </div>

    <div v-if="!loading && sensorData.length===0">
      <p>no sensor data available</p>
    </div>

    <div v-if="!loading && sensorData.length>0">
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
          <tr v-for="data in sensorData" :key="data.id">
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
import {getSensorData} from '../services/sensorDataService.js'
import {sendMessage} from '../services/errorService.js'

export default {
  name: 'SensorData',
  data(){
    return{
      sensorData:[],
      loading:false
    }
  },
  mounted(){
    this.fetchSensorData()
  },
  methods:{
    async fetchSensorData(){
      this.loading=true
      try{
        this.sensorData=await getSensorData()
      }catch(error){
        sendMessage('failed to load sensor data')
      }finally{
        this.loading=false
      }
    }
  }
}
</script>