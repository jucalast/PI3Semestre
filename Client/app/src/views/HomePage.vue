<!-- src/views/HomePage.vue -->
<template>
    <DefaultLayout>
      <ProductList :products="products" />
    </DefaultLayout>
  </template>
  
  <script>
  import DefaultLayout from '@/layouts/DefaultLayout.vue';
  import ProductList from '@/components/ProductList.vue';
  
  export default {
    name: 'HomePage',
    components: {
      DefaultLayout,
      ProductList,
    },
    data() {
      return {
        products: [
          { id: 1, name: 'Product 1', price: 100, image: '/path/to/image1.jpg' },
          { id: 2, name: 'Product 2', price: 200, image: '/path/to/image2.jpg' },
          // mais produtos...
        ],
      };
    },
  };
  </script>
  