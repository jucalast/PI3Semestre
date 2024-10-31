<template>
    <div class="generic">
      <div v-if="isLoading">
        <p>Carregando produtos...</p>
      </div>
      <div v-else-if="produtos.length > 0" class="card-container">
        <div class="cards">
          <div
            v-for="produto in filteredProdutos.length > 0 ? filteredProdutos : produtos"
            :key="produto.id"
            class="product-card"
            @mouseover="showButtons = produto.id"
            @mouseleave="showButtons = null"
          >
            <div class="imgcardcont">
              <img
                :src="produto.imagem"
                :alt="produto.nome"
                class="product-image"
              />
            </div>
            <div class="name">
              <h3 class="product-name">{{ produto.nome }}</h3>
              <p class="product-description">{{ formattedDescription(produto.descricao) }}</p>
              <div class="priceandfav">
                <p class="product-price">{{ produto.preco.toFixed(2) }}</p>
              </div>
            </div>
            <div v-if="showButtons === produto.id" class="action-buttons">
              <button class="excluir" @click.stop="deleteProduct(produto.id)">Excluir</button>
              <button class="editar" @click.stop="editProduct(produto)">Editar</button>
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
  import { globalState } from "@/state.js";
  import { computed } from "vue";
  
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
        showButtons: null, // Para controlar a visibilidade dos botões
      };
    },
    setup() {
      const favoriteProductIds = computed(() => globalState.favoriteProductIds);
      return {
        favoriteProductIds,
      };
    },
    computed: {
      filteredProdutos() {
        if (!this.produtos.length) return [];
        const searchFiltered = this.produtos.filter((produto) =>
          produto.nome.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
  
        if (!this.selectedValues || Object.keys(this.selectedValues).length === 0) {
          return searchFiltered;
        }
  
        return this.filterBySelectedAttributes(searchFiltered);
      },
    },
    methods: {
      formattedDescription(descricao) {
        return descricao.length > 30 ? descricao.slice(0, 30) + '...' : descricao;
      },
      async fetchFavorites() {
        try {
          const response = await axios.get('/api/favorites'); // Ajuste a URL conforme sua API
          globalState.favoriteProductIds = response.data.map(fav => fav.id); // Supondo que a resposta contenha IDs dos favoritos
        } catch (error) {
          console.error("Erro ao buscar favoritos:", error);
        }
      },
      async openModal(product) {
        this.selectedProduct = product; // Armazena o produto selecionado
        await this.fetchProductDetails(product.id); // Certifique-se de que o método está disponível
        if (this.selectedProduct) {
          this.isModalVisible = true; // Exibe o modal
        } else {
          alert("Nenhum detalhe encontrado para este produto.");
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
  
            if (metodoResponse.data && Object.keys(metodoResponse.data).length > 0) {
              this.selectedProduct.metodoPreparo = metodoResponse.data;
            } else {
              throw new Error(`Produto com ID ${productId} não encontrado em nenhum dos endpoints.`);
            }
          }
        } catch (error) {
          console.error("Erro ao buscar detalhes do produto:", error.message);
          this.selectedProduct = null; // Limpa o produto selecionado
          alert(`Erro: ${error.message}`);
        }
      },
      filterBySelectedAttributes(produtos) {
        const hasSelectedValues = Object.values(this.selectedValues).some(valor => valor);
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
      async handleFavoriteClick(produto) {
        try {
          const params = new URLSearchParams();
          params.append('productId', produto.id);
  
          const response = await axios.post(`/favorites/add?${params.toString()}`);
          if (response.status === 200) {
            console.log('Produto adicionado aos favoritos com sucesso!');
            await this.fetchFavorites(); // Atualiza a lista de favoritos após adicionar um novo
          } else {
            console.error('Falha ao adicionar produto aos favoritos');
          }
        } catch (error) {
          console.error('Erro ao enviar requisição para adicionar aos favoritos:', error);
        }
      },
      handleCartClick(produto) {
        // Lógica para adicionar produto ao carrinho
      },
      editProduct(produto) {
        // Lógica para editar o produto
        console.log("Editando produto:", produto);
      },
      deleteProduct(produtoId) {
        // Lógica para excluir o produto
        console.log("Excluindo produto com ID:", produtoId);
      },
    },
    async mounted() {
      await this.fetchFavorites();
    },
  };
  </script>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');
  
  body {
    font-family: 'Poppins', sans-serif; /* Define a fonte Poppins para todo o corpo */
  }
  
  .card-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    width: 100%;
    margin-top: 7rem;
  }
  
  .product-image {
    width: 6rem !important;
    margin: 1rem;
    height: 8rem;
    border-radius: 0;
  }
  
  .cards {
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    justify-content: flex-start;
    align-items: flex-start;
    align-content: flex-start;
    width: 100%;
  }
  
  .name {
    margin-left: 2rem;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    width: 100%;
  }
  
  .product-list {
    width: 100%;
  }
  
  .product-card {
    border: solid 1px #d2d2d2;
    display: flex;
    border-radius: 1.5rem;
    width: 100%;
    height: auto;
    text-align: center;
    background: #ffffff;
    padding: 1rem;
    
    transition: none;
    align-items: center;
    margin: 0;
    position: relative;
  }
  
  .product-card:hover {
    width: 70%;
    transform: translateY(0px);
    background: #f3f3f3 !important;
  }
  
  .imgcardcont {
    display: flex;
    justify-content: center;
  }
  
  .generic .product-name {
    font-size: 1.5rem;
    margin: 0 !important;
    color: #333;
  }
  
  .product-description {
    font-size: 1rem;
    color: #666;
    background: transparent;
    display: flex;
    align-items: flex-start;
    text-align: left;
  }
  
  .product-price {
    font-size: 2rem;
    font-weight: bold;
    color: #333;
    background: transparent !important;
    margin-left: -1rem;
  }
  
  p {
    padding: 0 !important;
  }
  
  .priceandfav {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  /* Estilos para os botões de ação */
  .action-buttons {
    height: 100%;
    position: absolute;
    right: -70px; 
    display: flex;
    flex-direction: column;
    opacity: 0; 
    transition: opacity 0.2s ease-in-out;
  }
  
  .product-card:hover .action-buttons {
    opacity: 1; 
  }
  
  .action-buttons button {
    margin: 0.2rem 0; 
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 1.5rem;
    cursor: pointer;
    transition: background-color 0.3s;
    height: 50%;
  }
  
  .editar {
    background: #d6ffde;
    color: #1d3e23;
    border: solid 1px #1d3e23;
  }
  
  .excluir {
    background: #ffdce1;
    color: #491a21;
    border: solid 1px #491a21;
  }
  </style>