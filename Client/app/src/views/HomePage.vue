<template>
    <DefaultLayout>
      <div class="home-page">
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
  