<template>
  <AdminLayout>
    <section class="centered-section">
      <h1>Tudo na palma das <strong>suas mãos</strong> </h1>
      <p>Explore e gerencie todos seu negócio com facilidade e rapidez.</p>
    </section>
    <section class="principal-section">
      <div class="left-column">
        <CreateProductComponent @product-created="handleProductCreated" />
        <FavoriteCountComponent />
      </div>
      <div class="right-column">
        <div class="product-list">
          <div class="header">
            <h3>Edite ou exclua produtos</h3>
            <button @click="fetchProdutos" class="refresh-button">
              <i class="fas fa-sync-alt"></i> Atualizar
            </button>
          </div>
          <CardGeneric :produtos="filteredProducts" :searchQuery="searchQuery" :isLoading="isLoading" />
        </div>
      </div>
    </section>
  </AdminLayout>
</template>


<script>
import AdminLayout from '@/layouts/AdminLayout.vue';
import CreateProductComponent from '@/components/CreateProductComponent.vue';
import CardGeneric from '@/components/CardGeneric.vue';
import FavoriteCountComponent from '@/components/FavoriteCountComponent.vue';
import axios from 'axios';

export default {
  name: 'AdminDashboard',
  components: {
    AdminLayout,
    CreateProductComponent,
    CardGeneric, // Certifique-se de que o nome está correto
    FavoriteCountComponent,
  },
  data() {
    return {
      produtos: [],
      isLoading: true,
      searchQuery: "",
      favoriteCounts: {},
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
    handleProductCreated(newProduct) {
      this.produtos.push(newProduct);  // Adiciona o novo produto diretamente à lista
    },
    updateSearchQuery(query) {
      this.searchQuery = query;
    },
    async fetchFavoriteCounts() {
      try {
        const response = await axios.get("http://localhost:8080/api/favorites/count-by-product");
        this.favoriteCounts = response.data;
      } catch (error) {
        console.error("Erro ao buscar contagem de favoritos:", error);
      }
    },
  },
  mounted() {
    this.fetchProdutos(); // Chama a função uma vez ao montar o componente
    this.fetchFavoriteCounts();
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
  flex-wrap: wrap;
}

.left-column, .right-column {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

h1 {
  font-size: 10rem;
  line-height: 0.8;
  color: #cdd5ff;
  letter-spacing: -0.05em; /* Ajuste este valor conforme necessário */
}

strong {
  color: #e6eaff !important;
}

.product-list {
    background: #ffffff;
    border: solid 1px #d2d2d2;
    border-radius: 1.5rem;
    padding: 2rem;

}

img {
  position: absolute;
  width: 100%;
  z-index: -1;
}

p {
  font-size: 2.5rem;
  margin-top: 1rem;
  background: transparent;
  width: 80%;
  backdrop-filter: blur(0);
  color: #16225e;
}

h3 {
  width: 80%;
  font-size: 3rem;
  line-height: 1 !important;
  padding-bottom:2rem ;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.refresh-button {
  display: flex;
  align-items: center;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  border:solid 2px #dfdfdf !important;
  padding: 1rem;
  border-radius:1rem ;
}

.refresh-button:hover {
  background: #ebebeb;
}

.refresh-button i {
  margin-right: 0.5rem;
}

.favorites-section {
  margin-top: 2rem;
  padding: 2rem;
  background: #f9f9f9;
  border-radius: 1rem;
}
</style>
