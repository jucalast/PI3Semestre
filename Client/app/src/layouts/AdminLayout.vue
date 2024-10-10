<!-- src/layouts/AdminLayout.vue -->
<template>
    <div>
      <header>Admin Dashboard</header>
      <div class="admin-content">
        <slot /> <!-- Conteúdo dinâmico -->
      </div>
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
    background-color: #f4f4f4;
  }
  </style>
  