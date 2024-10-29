<!-- src/components/CreateProdutoComponent.vue -->
<template>
    <div>
      <div class="card" @click="openModal">
        <h3>Crie produtos</h3>
        <p>Clique aqui para adicionar um novo produto.</p>
      </div>
  
      <CreateProductModal v-if="isModalOpen" @close="closeModal" />
    </div>
  </template>
  
  <script>
  import CreateProductModal from '@/components/CreateProductModal.vue';
  
  export default {
    name: 'CreateProductComponent',
    components: {
      CreateProductModal,
    },
    data() {
      return {
        isModalOpen: false,
      };
    },
    methods: {
      openModal() {
        this.isModalOpen = true;
      },
      closeModal() {
        this.isModalOpen = false;
      },
    },
  };
  </script>
  
  <style scoped>
  .card {
    width: 30%;
    border: 1px solid #ccc;
    padding: 2rem;
    cursor: pointer;
    border-radius: 2rem;
    transition: background-color 0.3s;
  }
  
  .card:hover {
    background-color: #f0f0f0;
  }

  p{
    padding: 1rem;
    background: transparent;
    font-size: 2rem;
  }

  H3 {
font-size: 3rem;
  }
  </style>
  