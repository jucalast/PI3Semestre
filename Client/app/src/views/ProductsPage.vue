<template>
  <div>
    <Header @search="handleSearch" />
    
    <div v-if="isLoading">
      <p>Carregando produtos...</p>
    </div>
    <div v-else-if="filteredProducts.length > 0" class="card-container">
      <div
        v-for="produto in filteredProducts"
        :key="produto.id"
        class="product-card"
        @click="openModal(produto)"
      >
        <div>
          <img :src="produto.imagem" :alt="produto.nome" class="product-image" />
          <div class="elements-card">
            <div class="priceandfav">
              <p>{{ produto.preco.toFixed(2) }}</p>
              <button class="favorire-button" @click.stop="handleFavoriteClick(produto)">
                <font-awesome-icon icon="star" class="favoritocard" />
              </button>
            </div>
          </div>
        </div>
        <h2>{{ produto.nome }}</h2>
      </div>

      <ProductModal
        :product="selectedProduct"
        :isVisible="isModalVisible"
        @close="isModalVisible = false"
      />
    </div>
    <div v-else>
      <p>Nenhum produto encontrado.</p>
    </div>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import ProductModal from '@/components/ProductModal.vue';
import axios from "axios";

export default {
  components: {
    Header,
    ProductModal,
  },
  data() {
    return {
      produtos: [],
      filteredProducts: [],
      isLoading: true,
      isModalVisible: false,
      selectedProduct: null,
      searchTerm: "", // Adicionando o termo de busca
    };
  },
  watch: {
    searchTerm(newTerm) {
      this.filterProducts(newTerm);
    },
  },
  methods: {
    handleSearch(searchTerm) {
      this.searchTerm = searchTerm; // Atualiza o termo de busca
    },
    async openModal(product) {
      this.selectedProduct = { ...product }; // Cria uma cópia do produto
      await this.fetchProductDetails(product.id);

      if (!this.selectedProduct.cafeEspecial && !this.selectedProduct.metodoPreparo) {
        this.selectedProduct = null; // Limpa o produto se não encontrado
        alert("Nenhum detalhe encontrado para este produto.");
      } else {
        this.isModalVisible = true; // Abrir o modal apenas se os detalhes foram carregados
      }
    },
    async fetchProductDetails(productId) {
      // ... código para buscar detalhes do produto
    },
    async fetchProdutos() {
      try {
        const response = await axios.get("http://localhost:8080/api/produtos");
        this.produtos = response.data;
        this.filteredProducts = response.data; // Inicializa os produtos filtrados
        this.filterProducts(this.$route.query.search); // Filtra os produtos ao carregar a página
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      } finally {
        this.isLoading = false;
      }
    },
    filterProducts(searchTerm) {
      this.filteredProducts = this.produtos.filter(produto =>
        produto.nome.toLowerCase().includes(searchTerm.toLowerCase())
      );
    },
  },
  mounted() {
    this.fetchProdutos();
  },
};
</script>

<style>
.card-container {
  margin-top: 3rem !important;
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: center;
}

.product-card {
  border-radius: 2rem;
  width: 14rem;
  height: 21.5rem;
  text-align: center;
  background: #cacaca;
  color: white;
  margin-bottom: 5rem;
  transition: transform 0.3s ease, color 0.3s ease;
  z-index: 1; /* Certifica que o card esteja acima do h2 */
}

/* ... outros estilos ... */
</style>
