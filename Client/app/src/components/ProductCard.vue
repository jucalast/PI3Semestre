<template>
  <div>
    <div v-if="isLoading">
      <p>Carregando produtos...</p>
    </div>
    <div v-else-if="filteredProdutos.length > 0" class="card-container">
      <div class="cards">
        <div
          v-for="produto in filteredProdutos"
          :key="produto.id"
          class="product-card"
          @click="openModal(produto)"
        >
          <h2>{{ produto.nome }}</h2>
          <div class="backcard">
            <img
              :src="produto.imagem"
              :alt="produto.nome"
              class="product-image"
            />
            <div class="elements-card">
              <div class="priceandfav">
                <p>{{ produto.preco.toFixed(2) }}</p>
                <button
                  class="favorire-button"
                  @click.stop="handleFavoriteClick"
                >
                  <font-awesome-icon icon="star" class="favoritocard" />
                </button>
                <button
                  class="cart-button"
                  @click.stop="handleAddCart(produto)"
                >
                  <font-awesome-icon
                    icon="fa-solid fa-shopping-cart"
                    class="caricon"
                  />
                </button>
              </div>
            </div>
          </div>
        </div>
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
import ProductModal from "@/components/ProductModal.vue";
import axios from "axios";

export default {
  props: {
    produtos: {
      type: Array,
      default: () => [],
    },
    isLoading: {
      type: Boolean,
      default: false,
    },
    searchQuery: {
      type: String,
      default: "",
    },
  },
  components: {
    ProductModal,
  },
  data() {
    return {
      isModalVisible: false,
      selectedProduct: null,
    };
  },
  computed: {
    filteredProdutos() {
      if (!this.searchQuery) {
        return this.produtos;
      }
      return this.produtos.filter((produto) =>
        produto.nome.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    async openModal(product) {
      this.selectedProduct = { ...product }; // Cria uma cópia do produto
      await this.fetchProductDetails(product.id);

      if (
        !this.selectedProduct.cafeEspecial &&
        !this.selectedProduct.metodoPreparo
      ) {
        this.selectedProduct = null; // Limpa o produto se não encontrado
        alert("Nenhum detalhe encontrado para este produto.");
      } else {
        this.isModalVisible = true; // Abrir o modal apenas se os detalhes foram carregados
      }
    },
    async fetchProductDetails(productId) {
      try {
        // Primeiro, tente buscar o produto em "cafes-especiais"
        const cafeResponse = await axios.get(
          `http://localhost:8080/api/cafes-especiais/produto/${productId}`
        );

        if (cafeResponse.data && Object.keys(cafeResponse.data).length > 0) {
          this.selectedProduct.cafeEspecial = cafeResponse.data;
          console.log("Café especial encontrado:", cafeResponse.data);
        } else {
          console.log(
            "Café especial não encontrado, tentando buscar método de preparo..."
          );
          const metodoResponse = await axios.get(
            `http://localhost:8080/api/metodo-preparo/produto/${productId}`
          );

          if (
            metodoResponse.data &&
            Object.keys(metodoResponse.data).length > 0
          ) {
            this.selectedProduct.metodoPreparo = metodoResponse.data;
            console.log("Método de preparo encontrado:", metodoResponse.data);
          } else {
            throw new Error(
              `Produto com ID ${productId} não encontrado em nenhum dos endpoints.`
            );
          }
        }
      } catch (error) {
        console.error("Erro ao buscar detalhes do produto:", error.message);
        this.selectedProduct = {}; // Limpa o produto se não encontrado
        alert(`Erro: ${error.message}`);
      }
    },
    async fetchProdutos() {
      try {
        const response = await axios.get("http://localhost:8080/api/produtos");
        this.produtos = response.data; // Armazena os produtos apenas uma vez
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      }
    },
    handleAddCart(produto){
      console.log(produto);
    },
  },
  mounted() {
    this.fetchProdutos(); // Chama a função uma vez ao montar o componente
  },
};
</script>

<style scoped>
.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: center;
}
.cards {
  margin-top: 22rem;
  margin-bottom: 10rem;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  align-content: center;
  gap: 2rem;
  width: 80%;
}


.product-card {
  border-radius: 2rem;
  width: 16.5rem;
  height: 21.5rem;
  text-align: center;
  background: transparent !important;
  color: var(--card-text-color);
  margin-bottom: 5rem;
  transition: transform 0.3s ease, color 0.3s ease, box-shadow 0.3s ease; /* Adicione box-shadow à transição */
  z-index: 5;
}

.product-card:hover {
  transform: scale(1.05); /* Aumenta o tamanho do cartão */
}




.priceandfav {
  display: flex;
  margin: 0.7rem;
  gap: 0rem;
  border-radius: 2rem;
}

.product-image {
  width: 80%;
  height: auto;
  border-radius: 2rem;
  z-index: 2;
}

.backcard {
  background: #d8d8d8;
  height: 23rem;
  border-radius: 3rem;
}

.elements-card {
  background-color: var(--element-background);
  backdrop-filter: blur(5px);
  border-radius: 2.5rem;
  display: flex;
  justify-content: space-around;
  margin: 0;
  font-size: 2rem;
  position: relative;
  bottom: 6.7rem;
  z-index: 2;
}

p {
  padding: 0.7rem;
  background: var(--price-background);
  backdrop-filter: blur(5px);
  border-radius: 3rem;
  margin: 0;
}

h2 {
  display: flex;
  margin: 0;
  background: var(--header-background);
  color: var(--header-text-color);
  border-radius: 0 0 2rem 2rem;
  font-size: 1.5rem;
  flex-direction: column;
  justify-content: flex-end;
  z-index: -1;
  position: relative;
  top: 27rem;
  height: 7rem;
  padding-bottom: 1rem;
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
  color: var(--favoritocard-color);
  font-size: 1.5rem; /* Tamanho do ícone */
  transition: transform 0.3s ease, color 0.3s ease;
  width: 2rem !important;
  height: 2rem !important;
}

.caricon {
  
  color: var(--favoritocard-color);
  font-size: 1.5rem; /* Tamanho do ícone */
  transition: transform 0.3s ease, color 0.3s ease;
  width: 2rem !important;
  height: 2rem !important;

}

.caricon:hover {
  color: var(--secondary-color);
  transform: scale(1.2); /* Aumenta o ícone em 20% */
}

.favoritocard:hover {
  color: var(--favoritocard-hover-color);
  transform: scale(1.2); /* Aumenta o ícone em 20% */
}

.favorire-button {
  background: none;
  border: none;
  cursor: pointer;
}
</style>
