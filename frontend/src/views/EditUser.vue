<template>
  <div>
    <h2>Edit User</h2>

    <div v-if="loading">
      <p>loading user...</p>
    </div>

    <form v-if="!loading && user" @submit.prevent="handleSubmit">

      <div class="form-group">
        <label>Username:</label>
        <input type="text" v-model="user.username" required />
      </div>

      <div class="form-group">
        <label>Email:</label>
        <input type="email" v-model="user.email" required />
      </div>

      <div class="form-group">
        <label>Full Name:</label>
        <input type="text" v-model="user.fullName" required />
      </div>

      <div class="form-group">
        <label>Role:</label>
        <select v-model="user.role" required>
          <option value="">Select role</option>
          <option value="USER">User</option>
          <option value="ADMIN">Admin</option>
        </select>
      </div>

      <div class="form-actions">

        <button type="submit" :disabled="submitting">
          {{ submitting ? 'Updating...' : 'Update User' }}
        </button>

        <button type="button" @click="cancel">Cancel</button>

      </div>
    </form>

  </div>
</template>


<script>
import { getUserById, updateUser } from '../services/userService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'EditUser',
  data() {
    return {
      user: null,
      loading: false,
      submitting: false
    }
  },
  mounted() {
    this.loadUser()
  },
  methods: {
    async loadUser() {
      this.loading = true
      try {
        const userId = this.$route.params.id
        this.user = await getUserById(userId)
      } 
      catch(err) {
        sendMessage('failed to load user')
      } 
      finally {
        this.loading = false
      }
    },
    async handleSubmit() {
      this.submitting = true
      try {
        const userId = this.$route.params.id
        await updateUser(userId, {
          username: this.user.username,
          email: this.user.email,
          fullName: this.user.fullName,
          role: this.user.role
        })
        sendMessage('user updated successfully')
        this.$router.push('/users')
      }
      catch(err) {
        sendMessage('failed to update user')
      } 
      finally {
        this.submitting = false
      }
    },
    cancel() {
      this.$router.push('/users')
    }
  }
}
</script>
