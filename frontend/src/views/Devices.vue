<template>
  <div>
    <h2>Devices</h2>

    <div v-if="loading">
      <p>loading devices...</p>
    </div>

    <div v-if="!loading && devices.length===0">
      <p>no devices available</p>
    </div>

    <div v-if="!loading && devices.length>0">
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
            <td>{{device.id}}</td>
            <td>{{device.name}}</td>
            <td>{{device.type}}</td>
            <td>{{device.location}}</td>
            <td>{{device.status}}</td>
            <td>{{device.active ? 'Yes':'No'}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import {getDevices} from '../services/deviceService.js'
import {sendMessage} from '../services/errorService.js'

export default {
  name: 'Devices',
  data(){
    return{
      devices:[],
      loading:false
    }
  },
  mounted(){
    this.fetchDevices()
  },
  methods:{
    async fetchDevices(){
      this.loading=true
      try{
        this.devices=await getDevices()
      }catch(error){
        sendMessage('failed to load devices')
      }finally{
        this.loading=false
      }
    }
  }
}
</script>

<style scoped>
h2{
  color:#333;
  margin-bottom:20px;
}

table{
  width:100%;
  border-collapse:collapse;
  margin-top:10px;
}

th,td{
  padding:12px;
  text-align:left;
  border-bottom:1px solid #ddd;
}

th{
  background-color:#f4f4f4;
  font-weight:bold;
}

tr:hover{
  background-color:#f9f9f9;
}
</style>
