<template>
  <error-overlay />

  <div class="app">
    <div class="app-title" v-if="$route.name !== 'Login'">
      <h1>Smart Energy System</h1>
    </div>
    
    <nav-bar v-if="$route.name !== 'Login'" />
    <div class="container" v-if="$route.name !== 'Login'">
      <router-view></router-view>
    </div>
    <router-view v-if="$route.name === 'Login'"></router-view>
  </div>
</template>

<script>
import ErrorOverlay from './ErrorOverlay.vue'
import NavBar from './components/NavBar.vue'

export default {
  components: {
    ErrorOverlay,
    NavBar
  }
}
</script>

<style scoped>
.app {
  min-height: 100vh;
}
</style>
