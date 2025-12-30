<template>
  <div>
    <h2>Add User</h2>

    <form @submit.prevent="handleSubmit">

      <div class="form-group">
        <label>Username:</label>
        <input type="text" v-model="form.username" required />
      </div>

      <div class="form-group">
        <label>Email:</label>
        <input type="email" v-model="form.email" required/>
      </div>

      <div class="form-group">
        <label>Full Name:</label>
        <input type="text" v-model="form.fullName" required />
      </div>

      <div class="form-group">
        <label>Password:</label>
        <input type="password" v-model="form.password" required/>
      </div>

      <div class="form-group">
        <label>Role:</label>
        <select v-model="form.role" required>
          <option value="">Select role</option>
          <option value="USER">User</option>
          <option value="ADMIN">Admin</option>
        </select>
      </div>

      <div class="form-actions">

        <button type="submit" :disabled="loading">
          {{ loading ? 'Creating...' : 'Create User' }}
        </button>

        <button type="button" @click="goBack">Cancel</button>

      </div>

    </form>
  </div>
</template>

<script>
import {createUser} from '../services/userService.js'
import {sendMessage} from '../services/errorService.js'

export default {
  name: 'AddUser',
  data(){
    return{
      form:{
        username:'',
        email:'',
        fullName:'',
        password:'',
        role:''
      },
      loading:false
    }
  },
  methods:{
    async handleSubmit(){
      this.loading=true
      
      try{
        await createUser(this.form)
        sendMessage('user created successfully')
        this.$router.push('/users')
      }
      catch(err){
        sendMessage('failed to create user')
      }
      finally{
        this.loading=false
      }
    },
    goBack(){
      this.$router.push('/users')
    }
  }
}
</script>

