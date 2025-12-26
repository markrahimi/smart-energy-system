<template>
  <error-overlay />

  <div class="container">
    <h1>Smart Energy System</h1>
  </div>
</template>

<script>
import ErrorOverlay from './ErrorOverlay.vue'

export default {
  components: {
    ErrorOverlay
  }
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  text-align: center;
  color: #333;
}
</style>
