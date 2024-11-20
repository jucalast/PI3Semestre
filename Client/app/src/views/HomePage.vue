<template>
  <DefaultLayout @search="updateSearchQuery">
    <div class="home-page">
      <div class="sessionone">
        <ImageCarousel :images="bannerImages" />
      </div>
      <div class="sessiontwo">
        <div class="product-list">
          <ProductCard
            :produtos="filteredProducts"
            :searchQuery="searchQuery"
            :isLoading="isLoading"
          />
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script>
  import DefaultLayout from '@/layouts/DefaultLayout.vue';
  import ProductCard from '@/components/ProductCardComponent.vue';
  import ImageCarousel from '@/components/ImageCarousel.vue';
  import axios from 'axios';

  export default {
    components: {
      DefaultLayout,
      ProductCard,
      ImageCarousel,
    },
    data() {
      return {
        produtos: [],
        isLoading: true,
        searchQuery: '',
        bannerImages: [],
      };
    },
    computed: {
      filteredProducts() {
        if (!this.searchQuery) {
          return this.produtos.filter(produto => produto.ativo);
        }
        return this.produtos.filter(
          (produto) =>
            produto.nome.toLowerCase().includes(this.searchQuery.toLowerCase()) &&
            produto.ativo
        );
      },
    },
    methods: {
      async fetchProdutos() {
        try {
          const response = await axios.get(
            'http://localhost:8080/api/produtos'
          );
          this.produtos = response.data;
        } catch (error) {
          console.error('Erro ao buscar produtos:', error);
        } finally {
          this.isLoading = false;
        }
      },
      async fetchBanners() {
        try {
          const response = await axios.get('http://localhost:8080/api/banners');
          this.bannerImages = response.data.map(banner => banner.imageBase64);
        } catch (error) {
          console.error('Erro ao buscar banners:', error);
        }
      },
      updateSearchQuery(query) {
        this.searchQuery = query;
      },
    },
    mounted() {
      this.fetchProdutos(); // Chama a função uma vez ao montar o componente
      this.fetchBanners(); // Chama a função para buscar os banners ao montar o componente
    },
  };
</script>

<style scoped>
  html {
    background-color: white !important;
  }
  .product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding-top: 2rem;
  }

  .banner {
    width: 95%;
    height: 20rem;
    padding: 2rem;
  }

  .home-page .card-container {
    margin-top: 0rem !important;
  }
  .sessionone {
    display: flex;
  }
</style>
