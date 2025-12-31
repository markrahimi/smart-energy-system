<template>
  <div>
    <div class="header">
      <h2>Users</h2>
      <button @click="addUser" class="btn-add">Add User</button>
    </div>

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
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.fullName }}</td>
            <td>{{ user.role }}</td>
            <td>
              <button @click="viewUser(user.id)" class="btn-view">View</button>
            </td>
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
    },
    addUser(){
      this.$router.push('/users/add')
    },
    viewUser(id){
      this.$router.push(`/users/${id}`)
    }
  }
}
</script>

<style scoped>
.header{
  display:flex;
  justify-content:space-between;
  align-items:center;
  margin-bottom:20px;
}

.btn-add{
  padding:10px 20px;
  background-color:#3498db;
  color:white;
  border:none;
  border-radius:4px;
  cursor:pointer;
}

.btn-add:hover{
  opacity:0.9;
}

.btn-view{
  padding:5px 15px;
  background-color:#2ecc71;
  color:white;
  border:none;
  border-radius:4px;
  cursor:pointer;
}

.btn-view:hover{
  opacity:0.9;
}
</style>
