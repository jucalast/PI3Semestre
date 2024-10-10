<template>
    <div>
      <Header />
      <main>
        <slot></slot>
      </main>
      <Footer />
    </div>
  </template>
  
  <script>
  import Header from '@/components/Header.vue';
  import Footer from '@/components/Footer.vue';
  
  export default {
    components: {
      Header,
      Footer
    }
  }
  </script>
  
  <style scoped>
  main {
    padding: 20px 0;
  }
  </style>
  