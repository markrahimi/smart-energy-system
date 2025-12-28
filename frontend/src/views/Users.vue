<template>
  <div>
    <h2>Users</h2>

    <div v-if="loading">
      <p>loading users...</p>
    </div>

    <div v-if="!loading && users.length === 0">
      <p>no users found</p>
    </div>

    <div v-if="!loading && users.length > 0">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Full Name</th>
            <th>Role</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.fullName }}</td>
            <td>{{ user.role }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { getUsers } from '../services/userService.js'
import { sendMessage } from '../services/errorService.js'

export default {
  name: 'Users',
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
      } catch(err) {
        sendMessage('failed to load users')
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
h2 {
  color: #333;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
  font-weight: bold;
}

tr:hover {
  background-color: #f9f9f9;
}
</style>
