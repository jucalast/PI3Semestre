<!-- src/App.vue -->
<template>
  <router-view /> <!-- Renderiza a rota atual -->
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
@import '@/assets/css/variables.css';
body {
margin: 0;
background: var(--bacground-color);
font-family:Arial, Helvetica, sans-serif;
}
</style>
