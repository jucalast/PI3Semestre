<!-- src/layouts/AdminLayout.vue -->
<template>
  <div class="layout">
    <main>
      <slot></slot>
    </main>
  </div>
</template>

<script>
  export default {
    name: 'AdminLayout',
  };
</script>

<style scoped>
  .admin-content {
    padding: 20px;
  }
  main {
    background: #3a5bff !important;
  }

</style>
