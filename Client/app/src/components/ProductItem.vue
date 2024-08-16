<!-- src/components/ProductItem.vue -->
<template>
    <div class="product-item">
      <img :src="product.image" :alt="product.name" />
      <h3>{{ product.name }}</h3>
      <p>{{ formattedPrice }}</p>
      <button @click="addToCart">Add to Cart</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ProductItem',
    props: {
      product: {
        type: Object,
        required: true,
      },
    },
    computed: {
      formattedPrice() {
        return new Intl.NumberFormat('en-US', {
          style: 'currency',
          currency: 'USD',
        }).format(this.product.price);
      },
    },
    methods: {
      addToCart() {
        // Lógica para adicionar ao carrinho
        this.$emit('add-to-cart', this.product);
      },
    },
  };
  </script>
  
  <style scoped>
  .product-item {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: center;
  }
  
  .product-item img {
    max-width: 100%;
    height: auto;
  }
  
  .product-item h3 {
    font-size: 1.2em;
    margin: 10px 0;
  }
  </style>
  