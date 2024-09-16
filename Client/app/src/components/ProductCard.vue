<template>
    <div class="product-card">
      <img :src="image" alt="Product Image" />
      <h3>{{ title }}</h3>
      <p>{{ description }}</p>
      <p>${{ price.toFixed(2) }}</p>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      image: String,
      title: String,
      description: String,
      price: Number
    }
  }
  </script>
  
  <style scoped>
  .product-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 10px;
    text-align: center;
  }
  .product-card img {
    width: 100%;
    height: auto;
  }
  </style>
  