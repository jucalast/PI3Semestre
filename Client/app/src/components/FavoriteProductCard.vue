<template>
  <div id="card-container">
    <div @click="openModal" class="product-card">
      <div class="product-image-container">
        <img :src="produto.imagem" :alt="produto.nome" class="product-image" />
      </div>
      <div class="product-info">
        <h3>{{ produto.nome }}</h3>
        <p>{{ produto.descricao }}</p>
        <p>{{ produto.preco.toFixed(2) }}</p>

        <div>
          <button class="comprar-btn">Comprar</button>
          <button class="delete-btn" @click="deleteProduct(produto.id)">Excluir</button>

        </div>

      </div>
    </div>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <h3>{{ produto.nome}}</h3>
        <img :src="produto.imagem" :alt="produto.nome" class="modal-image" />
        <p>{{ produto.preco.toFixed(2)}}</p>
      </div>
    </div>
  </div>
</template>

<script>
import axiosInstance from "@/utils/axiosInstance";

export default {
  props: {
    produto: Object
  },
  data() {
    return {
      showModal: false,
    };
  },
  methods: {
    openModal() {
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
    },
    async deleteProduct(productId) {
      try {
        const response = await axiosInstance.delete(`/api/favorites/remove?productId=${productId}`);
        if (response.status === 200) {
          this.$emit('product-deleted', productId);
        } else {
          console.error('Falha ao excluir o produto');
        }
      } catch (error) {
        console.error('Erro ao enviar requisição DELETE:', error);
      }
    }
  },
  filters: {
    currency(value) {
      return `R$${value.toFixed(2)}`;
    }
  }
};
</script>

<style scoped>
#card-container {
  padding: 40px 40px 0; /* Padding on the sides and top, none at the bottom */
}

.product-card {
  display: flex;
  flex-direction: row;
  align-items: center;
  cursor: pointer;
  width: 100%; /* Use full available space */
  margin: 0; /* No margin between cards */
  padding: 0; /* No padding inside the card */
  color: black; /* Text color */
  border: 1px solid #ccc; /* Restoring border around each card */
}

.product-image-container {
  flex: 1;
  padding: 0; /* No padding */

}

.product-image {
  width: 100%;
  max-width: 200px;
  height: auto;
  object-fit: cover;
}

.product-info {
  flex: 3;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  padding: 20px; /* Padding inside the info area */

}

.modal {
  position: fixed;
  top: 2rem;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  text-align: center;
}

.close {
  position: absolute;
  top: 10px;
  right: 20px;
  font-size: 24px;
  cursor: pointer;
}

.delete-btn {
  margin: 10px;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  background-color: white;
  color: black;
  cursor: pointer;
}
.comprar-btn {
  margin-top: 10px;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  background-color: #5aec6f;
  color: white;
  cursor: pointer;
}

p{
  background: none;
}

</style>
