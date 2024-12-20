<template>
  <div class="generic">
    <div v-if="isLoading">
      <p>Carregando produtos...</p>
    </div>
    <div v-else-if="produtos.length > 0" class="card-container">
      <div class="cards">
        <div
          v-for="produto in produtos"
          :key="produto.id"
          class="product-card"
          @mouseover="showButtons = produto.id"
          @mouseleave="showButtons = null"
        >
          <!-- Ícone de olho -->
          <div class="eye-icon" @click="toggleProductStatus(produto)">
            <i :class="produto.ativo ? 'fas fa-eye' : 'fas fa-eye-slash'"></i>
          </div>
          <div class="imgcardcont">
            <img :src="produto.imagens[0]" :alt="produto.nome" class="product-image" />
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
            <button class="editar" @click.stop="handleEdit(produto)">
              <i class="fas fa-edit"></i>
            </button>
          </div>
        </div>
      </div>

      <EditProductModal
        v-if="isEditModalVisible"
        :product="selectedProduct"
        :isVisible="isEditModalVisible"
        @close="isEditModalVisible = false"
        @save="handleSave"
      />
      <CreateProductModal
        v-if="isCreateModalVisible"
        @close="isCreateModalVisible = false"
        @product-created="handleProductCreated"
      />

      <ProductModal
        v-if="selectedProduct"
        :product="selectedProduct"
        :isVisible="isModalVisible"
        @close="isModalVisible = false"
      />

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
import axiosInstance from "../utils/axiosInstance";
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
      isModalVisible: false,
      isEditModalVisible: false,
      selectedProduct: null,
      showButtons: null,
      isEyeOpen: {},
      isCreateModalVisible: false,
    };
  },
  setup() {
    const toast = useToast();
    return { toast };
  },
  methods: {
    formattedDescription(descricao) {
      return descricao.length > 30 ? descricao.slice(0, 30) + "..." : descricao;
    },

    async fetchProductDetails(productId) {
      try {
        if (this.selectedProduct.cafeEspecial === null && this.selectedProduct.metodoPreparo === null) {
          const produtoResponse = await axiosInstance.get(
            `http://localhost:8080/api/produtos/sem-especializacoes/${productId}`
          );
          if (produtoResponse.data) {
            this.selectedProduct = produtoResponse.data;
          } else {
            throw new Error(
              `Produto com ID ${productId} não encontrado em nenhum dos endpoints.`
            );
          }
        } else {
          const cafeResponse = await axiosInstance.get(
            `http://localhost:8080/api/cafes-especiais/produto/${productId}`
          );
          if (cafeResponse.data && Object.keys(cafeResponse.data).length > 0) {
            this.selectedProduct.cafeEspecial = cafeResponse.data;
          } else {
            const metodoResponse = await axiosInstance.get(
              `http://localhost:8080/api/metodo-preparo/produto/${productId}`
            );
            if (metodoResponse.data && Object.keys(metodoResponse.data).length > 0) {
              this.selectedProduct.metodoPreparo = metodoResponse.data;
            } else {
              throw new Error(
                `Produto com ID ${productId} não encontrado em nenhum dos endpoints.`
              );
            }
          }
        }
      } catch (error) {
        console.error("Erro ao buscar detalhes do produto:", error.message);
        this.selectedProduct = null;
        alert(`Erro: ${error.message}`);
      }
    },

    async openModal(product) {
      console.log("Produto clicado:", product);
      this.selectedProduct = { ...product };
      await this.fetchProductDetails(product.id);
      this.isModalVisible = true;
    },

    handleEdit(produto) {
      this.selectedProduct = { ...produto };
      this.isEditModalVisible = true;
      console.log(produto);
    },

    async handleSave(updatedProduct) {
      const productData = {
        ...updatedProduct,
        cafeEspecial: updatedProduct.cafeEspecial || {},
        metodoPreparo: updatedProduct.metodoPreparo || {},
      };

      // Remover cafeEspecial se estiver vazio
      if (Object.keys(productData.cafeEspecial).length === 0) {
        delete productData.cafeEspecial;
      }

      // Remover metodoPreparo se estiver vazio
      if (Object.keys(productData.metodoPreparo).length === 0) {
        delete productData.metodoPreparo;
      }

      // Logar o corpo da requisição antes de enviar
      console.log("Dados enviados para o servidor:", productData);

      try {
        const response = await axiosInstance.put(
          `http://localhost:8080/api/produtos/protected/update/${updatedProduct.id}`,
          productData
        );

        if (response.status === 200) {
          // Atualiza a lista de produtos no pai
          const index = this.produtos.findIndex(
            (prod) => prod.id === updatedProduct.id
          );
          if (index !== -1) {
            this.produtos.splice(index, 1, updatedProduct);
          }

          this.toast.success("Produto atualizado com sucesso.");
          this.isEditModalVisible = false;
        } else {
          this.toast.error("Erro ao atualizar produto.");
        }
      } catch (error) {
        this.toast.error("Erro ao atualizar produto.");
        console.error("Erro ao salvar produto:", error.message);
      }
    },

    async confirmDeleteProduct(id) {
      this.productToDelete = id;
      this.isConfirmationVisible = true;
    },

    async deleteProduct() {
      if (!this.productToDelete) return;

      try {
        const response = await axiosInstance.delete(
          `http://localhost:8080/api/produtos/${this.productToDelete}`
        );

        if (response.status === 200) {
          this.produtos = this.produtos.filter(
            (produto) => produto.id !== this.productToDelete
          );
          this.isConfirmationVisible = false;
          this.toast.success("Produto excluído com sucesso.");
        } else {
          this.toast.error("Erro ao excluir produto.");
        }
      } catch (error) {
        console.error("Erro ao excluir produto:", error.message);
        this.toast.error("Erro ao excluir produto.");
      }
    },

    toggleEyeIcon(productId) {
      this.$set(this.isEyeOpen, productId, !this.isEyeOpen[productId]);
    },

    async toggleProductStatus(produto) {
      try {
        const endpoint = produto.ativo
          ? `http://localhost:8080/api/produtos/protected/deactivate/${produto.id}`
          : `http://localhost:8080/api/produtos/protected/activate/${produto.id}`;
        const response = await axiosInstance.put(endpoint);

        if (response.status === 200) {
          produto.ativo = !produto.ativo;
          this.toast.success(`Produto ${produto.ativo ? 'ativado' : 'desativado'} com sucesso.`);
        } else {
          this.toast.error(`Erro ao ${produto.ativo ? 'desativar' : 'ativar'} produto.`);
        }
      } catch (error) {
        console.error(`Erro ao ${produto.ativo ? 'desativar' : 'ativar'} produto:`, error.message);
        this.toast.error(`Erro ao ${produto.ativo ? 'desativar' : 'ativar'} produto.`);
      }
    },

    openCreateModal() {
      this.isCreateModalVisible = true;
    },
    handleProductCreated(newProduct) {
      this.produtos.push(newProduct);
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
height:70vh;
  overflow-y: auto;
  scrollbar-width: none; /* Firefox */
}

.card-container::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
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
  gap:1rem;
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
  right: -53px;
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
  width: 5rem;
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

.eye-icon {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  font-size: 2rem; /* Ajustar o tamanho para ser igual aos outros ícones */
  color: #6b6b6b; /* Ajustar a cor para ser igual aos outros ícones */
}
</style>
