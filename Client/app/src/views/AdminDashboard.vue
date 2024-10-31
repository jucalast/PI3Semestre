<template>
  <AdminLayout>
    <img src="@/assets/backgroundadm.png" alt="">
    <section class="centered-section">
      <h1>Tudo na palma das suas mãos</h1>
      <p>Explore e gerencie todos seu negócio com facilidade e rapidez.</p>
    </section>
    <section class="principal-section">
      <CreateProductComponent />
      <div class="product-list">
        <!-- Mude de ProductCard para CardGeneric -->
        <CardGeneric :produtos="filteredProducts" :searchQuery="searchQuery" :isLoading="isLoading" />
      </div>
    </section>
    <!-- Outros conteúdos do dashboard -->
  </AdminLayout>
</template>

<script>
import AdminLayout from '@/layouts/AdminLayout.vue';
import CreateProductComponent from '@/components/CreateProductComponent.vue';
import CardGeneric from '@/components/CardGeneric.vue';
import axios from 'axios';

export default {
  name: 'AdminDashboard',
  components: {
    AdminLayout,
    CreateProductComponent,
    CardGeneric, // Certifique-se de que o nome está correto
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
    }
  },
  mounted() {
    this.fetchProdutos(); // Chama a função uma vez ao montar o componente
  },
};
</script>

<style scoped>
.centered-section {
  margin-top: 6rem;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
  padding: 2rem;
}

.principal-section {
  background: white;
  height: fit-content;
  margin: 5rem;
  padding: 5rem;
  border-radius: 2rem;
  display: flex;
  gap: 2rem;
  justify-content: space-between;
}

h1 {
  font-size: 12rem;
  line-height: 0.8;
  color: #1b1b1b;
  letter-spacing: -0.05em; /* Ajuste este valor conforme necessário */
}

.product-list {
    width: 30%;
}

img {
  position: absolute;
  width: 100%;
  z-index: -1;
}

p {
  font-size: 3rem;
  margin-top: 1rem;
  background: transparent;
  width: 80%;
  backdrop-filter: blur(0);
  color: #3a5bff;
}
</style>
