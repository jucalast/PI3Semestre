<template>
  <DefaultLayout>
    <div class="product-page">
      <div class="sessiontwo">
        <form @submit.prevent="fetchFilteredProducts">
          <div class="search-container">
            <input
              type="text"
              placeholder="Buscar grãos, métodos e muito mais..."
              v-model="searchQuery"
            />
            <button type="submit">Buscar</button>
          </div>
        </form>
        <div class="product-list">
          <ProductCard :produtos="produtos" :isLoading="isLoading" />
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script>
import DefaultLayout from '@/layouts/DefaultLayout.vue';
import ProductCard from '@/components/ProductCard.vue';
import axios from "axios";

export default {
  components: {
    DefaultLayout,
    ProductCard,
  },
  data() {
    return {
      produtos: [],
      searchQuery: "",
      isLoading: false,
    };
  },
  methods: {
    async fetchFilteredProducts() {
      this.isLoading = true;
      try {
        const response = await axios.get(`http://localhost:8080/api/produtos/search?search=${this.searchQuery}`);
        this.produtos = response.data; // Atualiza a lista de produtos
        console.log("Produtos filtrados:", this.produtos);
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      } finally {
        this.isLoading = false; // Desativa o estado de carregamento
      }
    },
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
