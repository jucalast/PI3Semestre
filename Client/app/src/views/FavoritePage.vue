<template>
  <DefaultLayout>
    <div class="favorite-page">
      <div class="product-list">
        <FavoriteProductCard
          v-for="produto in produtosFiltrados"
          :key="produto.id"
          :produto="produto"
          @product-deleted="handleProductDeleted"
        />
      </div>
    </div>
  </DefaultLayout>
</template>

<script>
  import DefaultLayout from '@/layouts/DefaultLayout.vue';
  import FavoriteProductCard from '@/components/FavoriteProductCard.vue';
  import axiosInstance from '@/utils/axiosInstance';

  export default {
    components: {
      DefaultLayout,
      FavoriteProductCard,
    },
    data() {
      return {
        produtos: [],
      };
    },
    computed: {
      produtosFiltrados() {
        return this.produtos.filter((produto) => produto != null);
      },
    },
    methods: {
      async fetchFavoritos() {
        try {
          const response = await axiosInstance.get(
            '/api/favorites/favorited-products'
          );
          if (response && response.data) {
            this.produtos = response.data;
          } else {
            this.produtos = []; // Garantindo que a variável produtos esteja sempre em formato de array.
          }
        } catch (error) {
          console.error('Erro ao buscar produtos favoritos:', error);
          this.produtos = []; // Lidar com erro definindo produtos como um array vazio.
        }
      },
      handleProductDeleted(productId) {
        this.produtos = this.produtos.filter((p) => p.id !== productId);
      },
    },
    mounted() {
      this.fetchFavoritos(); // Chama a função uma vez ao montar o componente.
    },
  };
</script>

<style scoped>
  .product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding-top: 2rem;
  }
</style>
