import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import Users from '../views/Users.vue'
import AddUser from '../views/AddUser.vue'
import UserDetail from '../views/UserDetail.vue'
import Devices from '../views/Devices.vue'
import AddDevice from '../views/AddDevice.vue'
import Notifications from '../views/Notifications.vue'
import SensorData from '../views/SensorData.vue'

const routes = [
  {
    path: '/',
    name: 'Dashboard',
    component: Dashboard
  },
  {
    path: '/users',
    name: 'Users',
    component: Users
  },
  {
    path: '/users/add',
    name: 'AddUser',
    component: AddUser
  },
  {
    path: '/users/:id',
    name: 'UserDetail',
    component: UserDetail
  },
  {
    path: '/devices',
    name: 'Devices',
    component: Devices
  },
  {
    path: '/devices/add',
    name: 'AddDevice',
    component: AddDevice
  },
  {
    path: '/notifications',
    name: 'Notifications',
    component: Notifications
  },
  {
    path: '/sensors',
    name: 'SensorsData',
    component: SensorData
  }
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
