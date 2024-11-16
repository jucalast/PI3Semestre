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

  .layout {
    display: flex;
    flex-direction: column;
    min-height: 100vh; /* Faz o layout ocupar pelo menos 100% da altura da janela */
    width: 100vw;
  }
</style>
