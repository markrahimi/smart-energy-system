<template>
  <div class="login-page">
    <div class="login-container">
      <h2>Smart Energy System</h2>
      <p class="subtitle">Select a user to login</p>

      <div v-if="loading" class="loading">
        <p>
          Loading users...
        </p>
      </div>

      <div v-if="!loading && users.length > 0" class="users-list">
        <div
          v-for="user in users"
          :key="user.id"
          class="user-item"
        >
          <div class="user-info">
            <p class="name">{{ user.fullName }}</p>
            <p class="email">{{ user.email }}</p>
          </div>
          <button @click="handleLogin(user)" class="login-button">Login</button>
        </div>
      </div>

      <div v-if="!loading && users.length === 0">
        <p>No users found</p>
      </div>
    </div>
  </div>
</template>

<script>
import { getUsers } from '../services/userService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'Login',
  data() {
    return {
      users: [],
      loading: false
    }
  },
  mounted() {
    this.loadUsers()
  },
  methods: {
    async loadUsers() {
      this.loading = true
      try {
        this.users = await getUsers()
      } catch (err) {
        sendMessage('failed to load users')
      } finally {
        this.loading = false
      }
    },
    handleLogin(user) {
      sendMessage(`welcome ${user.fullName}!`)
      this.$router.push('/dashboard')
    }
  }
}
</script>

<style scoped>
.login-page {
  background: #f5f5f5;
  padding: 60px 20px;
  min-height: 100vh;
}

.login-container {
  background: white;
  padding: 30px;
  max-width: 450px;
  margin: 0 auto;
  border: 1px solid #ddd;
}

.login-container h2 {
  text-align: center;
  margin: 0 0 5px 0;
  font-size: 22px;
}

.subtitle {
  text-align: center;
  color: #666;
  margin: 0 0 25px 0;
  font-size: 14px;
}

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
}

.users-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.user-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px;
  border: 1px solid #ddd;
}

.user-info {
  flex: 1;
}

.user-info .name {
  margin: 0 0 3px 0;
  font-size: 15px;
}

.user-info .email {
  margin: 0;
  color: #666;
  font-size: 13px;
}

.login-button {
  padding: 8px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  cursor: pointer;
}
</style>
