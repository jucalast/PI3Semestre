<template>
  <div>
    <div v-if="isLoading">
      <p>Carregando produtos...</p>
    </div>
    <div v-else-if="produtos.length > 0" class="card-container">
      <div v-for="produto in produtos" :key="produto.id" class="product-card" @click="openModal(produto)">
        <img :src="produto.imagem" :alt="produto.nome" class="product-image" />
        <div class="elements-card">
          <div class="priceandfav">
            <p>{{ produto.preco.toFixed(2) }}</p>
            <button class="favorire-button" @click.stop="handleFavoriteClick">
              <font-awesome-icon icon="star" class="favoritocard" />
            </button>
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
import ProductModal from '@/components/ProductModal.vue';
import axios from "axios";

export default {
  components: {
    ProductModal,
  },
  data() {
    return {
      produtos: [],
      isLoading: true,
      isModalVisible: false,
      selectedProduct: null
    };
  },
  methods: {
    openModal(product) {
      this.selectedProduct = product;
      this.isModalVisible = true;
    },
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
  },
  mounted() {
    this.fetchProdutos();
  },
};
</script>


<style>
.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.product-card {
  border-radius: 2rem;
  width: 200px;
  height: 15.5rem;
  text-align: center;
  background: #e4bf5f;
  color: white;
  margin-bottom: 5rem;
  transition: transform 0.3s ease, color 0.3s ease;

  z-index: 1; /* Certifica que o card esteja acima do h2 */
}

.priceandfav {
  display: flex;
  margin: 0.7rem;
  gap: 0.5rem;
  border-radius: 2rem;
}

.product-card:hover {
  transform: scale(1.2);
  z-index: 3; /* Coloca o card acima de outros elementos ao aumentar */
}

.product-image {
  width: 100%;
  height: auto;
  border-radius: 2rem;
  z-index: 2;
}

.elements-card {
  background-color: rgba(59, 59, 59, 0.26);
  backdrop-filter: blur(5px);
  border-radius: 2.5rem;
  display: flex;
  justify-content: space-around;
  margin: 0;
  font-size: 2rem;
  position: relative;
  bottom: 6rem;
  
  z-index: 2;
}

p {
  padding: 0.7rem;
background: #49494933;
  backdrop-filter: blur(5px);
  border-radius: 3rem;
  margin: 0;
}

h2 {
  display: flex;
  margin: 0;
  background: #e4bf5f;
  color: #fff2d2;
  border-radius:  0 0 2rem 2rem;
 font-size: 1.5rem;
  flex-direction: column;
  justify-content: flex-end;
  z-index: -1;
  position: relative;
  bottom: 6rem;
}

.favorire-button {
  justify-content: center;
  background-color: rgba(94, 94, 94, 0.281);
  backdrop-filter: blur(5px);
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0.1rem;
  border-radius: 50%;
  padding: 0.5rem;
  width: 4rem;
}



.favoritocard {
  color: var(--Btns-color); 
  font-size: 1.5rem; /* Tamanho do ícone */
  transition: transform 0.3s ease, color 0.3s ease;
  width: 2rem !important;
  height: 2rem !important;
}

.favoritocard:hover {
  color: #e4bf5f; 
  transform: scale(1.2); /* Aumenta o ícone em 20% */
}


.favorire-button {
  background: none;
  border: none;
  cursor: pointer;
}

</style>
