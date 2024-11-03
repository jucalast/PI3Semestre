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
            <img :src="produto.imagem" :alt="produto.nome" class="product-image" />
          </div>
          <div class="name">
            <h3 class="product-name" @click="openModal(produto)">
              {{ produto.nome }}
            </h3>
            <p class="product-description">
              {{ formattedDescription(produto.descricao) }}
            </p>
            <div class="priceandfav">
              <p class="product-price">{{ produto.preco.toFixed(2) }}</p>
            </div>
          </div>
          <div v-if="showButtons === produto.id" class="action-buttons">
            <button class="excluir" @click.stop="confirmDeleteProduct(produto.id)">
              <i class="fas fa-trash"></i>
            </button>
            <button class="editar" @click.stop="editProduct(produto)">
              <i class="fas fa-edit"></i>
            </button>
          </div>
        </div>
      </div>

      <!-- Modal de Edição -->
      <EditProductModal
        :product="productToEdit"
        :isVisible="isEditModalVisible"
        @close="isEditModalVisible = false"
        @save="saveProductEdit"
      />

      <!-- Modal de produto existente -->
      <ProductModal
        :product="selectedProduct"
        :isVisible="isModalVisible"
        @close="isModalVisible = false"
      />
      <!-- Confirmação de exclusão -->
      <div v-if="isConfirmationVisible" class="confirmation-modal">
        <p>Tem certeza que deseja excluir este produto?</p>
        <button class="deletesim" @click="deleteProduct">Sim</button>
        <button class="deletenao" @click="isConfirmationVisible = false">Não</button>
      </div>
    </div>
    <div class="not" v-else>
      <p>Nenhum produto encontrado.</p>
    </div>
  </div>
</template>

<script>
import ProductModal from "@/components/ProductModal.vue";
import EditProductModal from "@/components/EditProductModal.vue";
import axios from "axios";
import { useToast } from "vue-toastification";

export default {
  props: {
    produtos: Array,
    isLoading: Boolean,
    searchQuery: String,
    selectedValues: Object,
  },
  components: {
    ProductModal,
    EditProductModal,
  },
  data() {
    return {
      isConfirmationVisible: false,
      productToDelete: null,
      isEditModalVisible: false,
      productToEdit: null,
      isModalVisible: false,
      selectedProduct: null,
      showButtons: null,
    };
  },
  setup() {
    const toast = useToast();
    return { toast };
  },
  computed: {
    filteredProdutos() {
      const searchFiltered = this.produtos.filter((produto) =>
        produto.nome.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
      return this.selectedValues && Object.keys(this.selectedValues).length
        ? this.filterBySelectedAttributes(searchFiltered)
        : searchFiltered;
    },
  },
  methods: {
    formattedDescription(descricao) {
      return descricao.length > 30 ? descricao.slice(0, 30) + "..." : descricao;
    },
    async openModal(product) {
      this.selectedProduct = product;
      await this.fetchProductDetails(product.id);
      this.isModalVisible = !!this.selectedProduct;
    },
    async fetchProductDetails(productId) {
      try {
        const cafeResponse = await axios.get(
          `http://localhost:8080/api/cafes-especiais/produto/${productId}`
        );
        if (cafeResponse.data) {
          this.selectedProduct.cafeEspecial = cafeResponse.data;
        } else {
          const metodoResponse = await axios.get(
            `http://localhost:8080/api/metodo-preparo/produto/${productId}`
          );
          if (metodoResponse.data) {
            this.selectedProduct.metodoPreparo = metodoResponse.data;
          } else {
            throw new Error(`Produto com ID ${productId} não encontrado.`);
          }
        }
      } catch (error) {
        console.error("Erro ao buscar detalhes do produto:", error.message);
        alert(`Erro: ${error.message}`);
      }
    },
    filterBySelectedAttributes(produtos) {
      let filtered = produtos;
      for (const [atributo, valor] of Object.entries(this.selectedValues)) {
        if (valor) {
          filtered = filtered.filter((produto) => produto[atributo] === valor);
        }
      }
      return filtered;
    },
    editProduct(produto) {
      this.productToEdit = { ...produto };
      console.log("Produto para edição:", this.productToEdit);

      this.isEditModalVisible = true;
    },
    async saveProductEdit(updatedProduct) {
  try {
    // Acesso ao ID do produto correto
    const productId = updatedProduct.id; // Use o ID do produto aqui

    // Montagem do payload
    const payload = {
      nome: updatedProduct.nome,
      descricao: updatedProduct.descricao,
      preco: updatedProduct.preco,
      imagem: updatedProduct.imagem,
      // Inclua outros campos que você deseja atualizar
    };

    console.log("Dados formatados para envio:", payload);

    // Chamada da API com o ID correto
    const response = await axios.put(`http://localhost:8080/api/produtos/${productId}`, payload);

    console.log("Produto atualizado com sucesso:", response.data);
    // Faça o que for necessário após a atualização (por exemplo, fechar o modal ou atualizar a lista de produtos)
  } catch (error) {
    console.error("Erro ao atualizar produto:", error.response.data);
    alert(`Erro ao atualizar produto: ${error.response.data.message}`);
  }
},
    confirmDeleteProduct(produtoId) {
      this.productToDelete = produtoId;
      this.isConfirmationVisible = true;
    },
    async deleteProduct() {
      try {
        await axios.delete(
          `http://localhost:8080/api/produtos/${this.productToDelete}`
        );
        const index = this.produtos.findIndex(
          (prod) => prod.id === this.productToDelete
        );
        if (index !== -1) {
          this.produtos.splice(index, 1);
          this.toast.success("Produto excluído com sucesso!");
        }
      } catch (error) {
        console.error("Erro ao excluir o produto:", error);
        this.toast.error("Erro ao excluir o produto.");
      } finally {
        this.isConfirmationVisible = false;
      }
    },
  },
};
</script>




<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap");

body {
  font-family: "Poppins", sans-serif; /* Define a fonte Poppins para todo o corpo */
}

.confirmation-modal {
  position: fixed;
  top: 10%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 2rem;
  background-color: #d1d9ff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 1000; /* Certifique-se de que o modal apareça acima de outros elementos */
  border-radius: 2rem;
}
.confirmation-modal p {
  font-size: 2rem;
  background: transparent;
  color: #3a5bff;
  margin-bottom: 1rem;
}

.deletesim {
  background: #d1d9ff;
  padding: 1rem;
  margin-right: 1rem;
  border-radius: 1rem;
  color: #ff4d4d;
}

.deletenao {
  background: #ff4d4d;
  padding: 1rem;
  margin-right: 1rem;
  border-radius: 1rem;
  color: hsl(0, 100%, 92%);
}
.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  margin-top: 7rem;
}

.deletesim:hover,
.deletenao:hover {
  border: solid 1px #ff4d4d;
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
  display: flex;
  border-radius: 1.5rem;
  width: 100%;
  height: auto;
  text-align: center;
  padding: 1rem;
  cursor: pointer;
  transition: none !important;
  align-items: center;
  margin: 0;
  position: relative;
  background: #dfdfdf !important;
  z-index: 0 !important;
}

.product-card:hover {
  width: 84%;
  transform: translateY(0px);
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

.generic .product-name:hover {
  font-size: 1.5rem;
  margin: 0 !important;
  color: #3a5bff;
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
  right: -74px;
  gap: 0rem;
  width: 4rem;
  display: flex;
  flex-direction: column;
  opacity: 0;
  transition: opacity 0.2s ease-in-out;
  border-radius: 1.5rem;
}

.product-card:hover .action-buttons {
  opacity: 1;
}

.action-buttons button {
  margin: 0.2rem 0;
  padding: 0.5rem 1rem;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s;
  height: 50%;
  border-radius: 1.5rem;
}

.editar {
  background: #dfdfdf !important;
}

.action-buttons i {
  color: #6b6b6b;
  font-size: 2rem !important;
}

.action-buttons .excluir i:hover {
  color: #ff4d4d;
}

.action-buttons .editar i:hover {
  color: #3a5bff;
}

.excluir {
  background: #dfdfdf !important;
}
</style>
