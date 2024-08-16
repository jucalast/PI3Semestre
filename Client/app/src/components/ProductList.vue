<!-- src/components/ProductList.vue -->
<template>
    <div class="product-list">
      <ProductItem
        v-for="product in products"
        :key="product.id"
        :product="product"
      />
    </div>
  </template>
  
  <script>
  import ProductItem from '@/components/ProductItem.vue';
  
  export default {
    name: 'ProductList',
    components: {
      ProductItem,
    },
    props: {
      products: {
        type: Array,
        required: true,
      },
    },
  };
  </script>
  
  <style scoped>
  .product-list {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
  }
  </style>
  