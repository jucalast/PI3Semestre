<template>
  <DefaultLayout>
    <div class="home-page">
      <div class="sessionone">
        <img class="banner" src="../assets/bannerhomepage.png" alt="Banner" />
      </div>
      <div class="sessiontwo">
        <div class="product-list">
          <ProductCard />
        </div>
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
    ProductCard,
  },
};
</script>
  
  <style scoped>


  .home-page {
    text-align: center;
  }
  .product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding-top:2rem ;
  }

  .banner {
    width: 95%;
    height: 15rem;
    padding: 2rem;
  }

  .sessionone {
    background: #c2cdff;

  }

  /* .sessiontwo {
    background: #fff2d2;
    z-index: -2;
  } */

  </style>
  