<template>
  <div>
    <div v-if="isLoading">
      <p>Carregando produtos...</p>
    </div>
    <div v-else-if="produtos.length > 0" class="card-container">
      <div class="cards">
        <div
          v-for="produto in filteredProdutos.length > 0
            ? filteredProdutos
            : produtos"
          :key="produto.id"
          class="product-card"
          @click="openModal(produto)"
        >
          <h2>{{ produto.nome }}</h2>
          <div class="backcard">
            <div class="imgcardcont">
              <img
                :src="produto.imagem"
                :alt="produto.nome"
                class="product-image"
              />
            </div>
            <div class="elements-card">
              <div class="priceandfav">
                <p>{{ produto.preco.toFixed(2) }}</p>
                <button
                  class="favorire-button"
                  @click.stop="handleFavoriteClick(produto)"
                >
                  <font-awesome-icon icon="star" class="favoritocard" />
                </button>
                <button
                  class="favorire-button"
                  @click.stop="handleCartClick(produto)"
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
    <div class="not" v-else>
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
    selectedValues: {
      type: Object,
      default: () => ({}),
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
    if (!this.produtos.length) return [];
    // Filtragem baseada na busca
    const searchFiltered = this.produtos.filter((produto) =>
      produto.nome.toLowerCase().includes(this.searchQuery.toLowerCase())
    );

    // Retorna todos os produtos se não houver filtros selecionados
    if (!this.selectedValues || Object.keys(this.selectedValues).length === 0) {
      return searchFiltered; // Retorna todos os produtos filtrados pela busca
    }

    const finalFiltered = this.filterBySelectedAttributes(searchFiltered);
    return finalFiltered;
  },
  },
  methods: {
    async openModal(product) {
      this.selectedProduct = { ...product };
      await this.fetchProductDetails(product.id);

      if (
        !this.selectedProduct.cafeEspecial &&
        !this.selectedProduct.metodoPreparo
      ) {
        this.selectedProduct = null;
        alert("Nenhum detalhe encontrado para este produto.");
      } else {
        this.isModalVisible = true;
      }
    },
    async fetchProductDetails(productId) {
      try {
        const cafeResponse = await axios.get(
          `http://localhost:8080/api/cafes-especiais/produto/${productId}`
        );

        if (cafeResponse.data && Object.keys(cafeResponse.data).length > 0) {
          this.selectedProduct.cafeEspecial = cafeResponse.data;
        } else {
          const metodoResponse = await axios.get(
            `http://localhost:8080/api/metodo-preparo/produto/${productId}`
          );

          if (
            metodoResponse.data &&
            Object.keys(metodoResponse.data).length > 0
          ) {
            this.selectedProduct.metodoPreparo = metodoResponse.data;
          } else {
            throw new Error(
              `Produto com ID ${productId} não encontrado em nenhum dos endpoints.`
            );
          }
        }
      } catch (error) {
        console.error("Erro ao buscar detalhes do produto:", error.message);
        this.selectedProduct = {};
        alert(`Erro: ${error.message}`);
      }
    },
    filterBySelectedAttributes(produtos) {
      const hasSelectedValues = Object.values(this.selectedValues).some(
        (valor) => valor
      );
      if (!hasSelectedValues) {
        return produtos; // Retorna todos os produtos se nenhum filtro estiver selecionado
      }

      let filtered = produtos;
      for (const [atributo, valor] of Object.entries(this.selectedValues)) {
        if (valor) {
          filtered = filtered.filter((produto) => produto[atributo] === valor);
        }
      }

      return filtered;
    },
    handleFavoriteClick(produto) {
      // Lógica para adicionar/remover produto aos favoritos
    },
    handleCartClick(produto) {
      // Lógica para adicionar produto ao carrinho
    },
  },

  watch: {
    selectedValues: {
      handler(newValue) {
        this.$forceUpdate(); // Força a atualização do componente
      },
      deep: true,
    },
  },
};
</script>

<style>
.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: center;
  width: 100%;
  margin-top: 5rem;
}

.not {
  margin-top: 20rem ;
}
.cards {
  margin-top: -7rem;
  margin-bottom: 10rem;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: center;
  align-content: center;
  gap: 2rem;
  width: 100%;
}

.imgcardcont {
  display: flex;
  flex-direction: row;
  justify-content: center;
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
  bottom: 6rem;
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
