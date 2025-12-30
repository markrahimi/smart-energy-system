<template>
  <div>
    <h2>Add Device</h2>

    <form @submit.prevent="handleSubmit">

      <div class="form-group">
        <label>Device Name </label>
        <input type="text" v-model="form.name" required />
      </div>

      <div class="form-group">
        <label>Type:</label>
        <select v-model="form.type" required>
          <option value="">Select type</option>
          <option value="SOLAR_PANEL">Solar Panel</option>
          <option value="WIND_TURBINE">Wind Turbine</option>
          <option value="BATTERY">Battery</option>
          <option value="SENSOR">Sensor</option>
        </select>
      </div>

      <div class="form-group">
        <label>Location:</label>
        <input type="text" v-model="form.location"/>
      </div>

      <div class="form-group">
        <label>Status:</label>
        <select v-model="form.status" required>
          <option value="OFF">Off</option>
          <option value="ACTIVE">Active</option>
          <option value="MAINTENANCE">maintenance</option>
        </select>
      </div>

      <div class="form-group">
        <label>User ID</label>
        <input type="number" v-model.number="form.userId" required />
      </div>

      <div class="form-actions">

        <button type="submit" :disabled="loading">
          {{ loading ? 'Loading...' : 'Create Device' }}
        </button>

        <button type="button" @click="goBack">Cancel</button>

      </div>

    </form>
  </div>
</template>

<script>
import {createDevice} from '../services/deviceService.js'
import {sendMessage} from '../services/errorService.js'

export default {
  name: 'AddDevice',
  data(){
    return{
      form:{
        name:'',
        type:'',
        location:'',
        status:'ACTIVE',
        userId:null
      },
      loading:false
    }
  },
  methods:{
    async handleSubmit(){
      this.loading=true

      try{
        await createDevice(this.form)
        sendMessage('device created successfully')
        this.$router.push('/devices')
      }
      catch(err){
        sendMessage('failed to create device')
      }
      finally{
        this.loading=false
      }
    },
    goBack(){
      this.$router.push('/devices')
    }
  }
}
</script>
