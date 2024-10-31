<!-- src/components/EditProductModal.vue -->
<template>
    <div v-if="isVisible" class="modal-backdrop">
      <div class="modal-content">
        <button class="close-btn" @click="$emit('close')">X</button>
        <h2>Editar Produto</h2>
        <form @submit.prevent="handleSubmit">
          <label for="nome">Nome:</label>
          <input v-model="formData.nome" type="text" id="nome" required />
  
          <label for="descricao">Descrição:</label>
          <textarea v-model="formData.descricao" id="descricao" required></textarea>
  
          <label for="preco">Preço:</label>
          <input v-model.number="formData.preco" type="number" id="preco" required />
  
          <button type="submit">Salvar Alterações</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      product: {
        type: Object,
        default: null,
      },
      isVisible: {
        type: Boolean,
        default: false,
      },
    },
    data() {
      return {
        formData: {
          nome: this.product?.nome || '',
          descricao: this.product?.descricao || '',
          preco: this.product?.preco || 0,
        },
      };
    },
    methods: {
      handleSubmit() {
        this.$emit("save", { ...this.formData, id: this.product.id });
        this.$emit("close"); // Fecha o modal após salvar
      },
    },
  };
  </script>
  
  <style scoped>
  /* Estilos para o modal */
  .modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .modal-content {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    width: 300px;
  }
  
  .close-btn {
    background: none;
    border: none;
    font-size: 18px;
    position: absolute;
    top: 10px;
    right: 10px;
    cursor: pointer;
  }
  </style>
  