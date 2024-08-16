<!-- src/layouts/DefaultLayout.vue -->
<template>
    <div>
      <HeaderComponent />
      <main>
        <slot /> <!-- Conteúdo dinâmico -->
      </main>
      <FooterComponent />
    </div>
  </template>
  
  <script>
  import HeaderComponent from '@/components/HeaderComponent.vue';
  import FooterComponent from '@/components/FooterComponent.vue';
  
  export default {
    name: 'DefaultLayout',
    components: {
      HeaderComponent,
      FooterComponent,
    },
  };
  </script>
  
  <style scoped>
  /* Estilos específicos para o layout */
  </style>
  