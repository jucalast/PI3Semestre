<template>
    <DefaultLayout>
      <div class="home-page">
        <h2>Welcome to Our Store!</h2>
        <div class="product-list">
          <ProductCard
            v-for="product in products"
            :key="product.id"
            :image="product.image"
            :title="product.title"
            :description="product.description"
            :price="product.price"
          />
        </div>
      </div>
    </DefaultLayout>
  </template>
  
  <script>
  import DefaultLayout from '@/layouts/DefaultLayout.vue';
  import ProductCard from '@/components/ProductCard.vue';
  
  export default {
    components: {
      DefaultLayout,
      ProductCard
    },
    data() {
      return {
        products: [
          { id: 1, image: 'path/to/image1.jpg', title: 'Product 1', description: 'Description for Product 1', price: 29.99 },
          { id: 2, image: 'path/to/image2.jpg', title: 'Product 2', description: 'Description for Product 2', price: 39.99 },
          // Add more products as needed
        ]
      };
    }
  }
  </script>
  
  <style scoped>
  .home-page {
    text-align: center;
  }
  .product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  </style>
  