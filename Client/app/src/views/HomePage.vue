<template>
  <DefaultLayout @search="updateSearchQuery">
    <div class="home-page">
      <div class="sessionone">
        <img class="banner" src="../assets/bannerhomepage.png" alt="Banner" />
      </div>
      <div class="sessiontwo">
        <div class="product-list">
           <ProductCard :produtos="filteredProducts" :searchQuery="searchQuery" :isLoading="isLoading" />
        </div>
      </div>
    </div>
  
  </DefaultLayout>
</template>

<script>
import DefaultLayout from '@/layouts/DefaultLayout.vue';
import ProductCard from '@/components/ProductCard.vue';
import axios from 'axios';

export default {
  components: {
    DefaultLayout,
    ProductCard,
  },
  data() {
    return {
      produtos: [],
      isLoading: true,
      searchQuery: "",
    };
  },
  computed: {
    filteredProducts() {
      if (!this.searchQuery) {
        return this.produtos;
      }
      return this.produtos.filter(produto => 
        produto.nome.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
  },
  methods: {
    async fetchProdutos() {
      try {
        const response = await axios.get("http://localhost:8080/api/produtos");
        this.produtos = response.data;
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      } finally {
        this.isLoading = false;
      }
    },
    updateSearchQuery(query) {
      this.searchQuery = query;
    },
  },
  mounted() {
    this.fetchProdutos(); // Chama a função uma vez ao montar o componente
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

.banner {
  position: absolute;
  width: 95%;
  height: 20rem;
  padding: 2rem;
  margin-top: 7rem;
}

.sessionone {
  background: #c2cdff;
  display: flex;
}
</style>
