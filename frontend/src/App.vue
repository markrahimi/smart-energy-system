<template>
  <error-overlay />

  <div class="app">
    <nav-bar />

    <div class="container">
      <router-view></router-view>
    </div>
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
