<template>
  <div>
    <div v-if="isLoading">
      <p>Carregando produtos...</p>
    </div>
    <div v-else-if="produtos.length > 0" class="card-container">
      <div v-for="produto in produtos" :key="produto.id" class="product-card">
        <img :src="produto.imagem" :alt="produto.nome" class="product-image" />
        <div class="elements-card" >
        <p>{{ produto.preco.toFixed(2) }}</p>
        <button class=" favorire-button" @click="handleFavoriteClick">
      <img src="@/assets/estrela.png" alt="Favorites" />
    </button>
      </div>
        <h2>{{ produto.nome }}</h2>
      </div>
      
    </div>
    <div v-else>
      <p>Nenhum produto encontrado.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      produtos: [],
      isLoading: true,
    };
  },
  mounted() {
    this.fetchProdutos();
  },
  methods: {
    async fetchProdutos() {
      try {
        const response = await axios.get('http://localhost:8080/api/produtos');
        console.log('Produtos recebidos:', response.data);
        console.log('Quantidade de produtos:', response.data.length);
        this.produtos = response.data;
      } catch (error) {
        console.error('Erro ao buscar produtos:', error);
      } finally {
        console.log('Carregamento concluído');
        this.isLoading = false;
      }
    },
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
  border-radius: 3rem;
  width: 200px;
  height: 260px;
  text-align: center;
 background: #292929;
 color: white;
margin-bottom: 5rem;
}

.product-image {
  width: 100%;
  height: auto;
  border-radius:2rem;
}

.elements-card {
background-color: rgba(255, 255, 255, 0.26);
backdrop-filter:blur(5px);
border-radius: 3rem;
  display: flex;
  justify-content: space-around;
  margin: 0;
  font-size: 2rem;
  position: relative;
  bottom: 2rem;
  padding: 0.5rem;
}

p {
  padding: 0.7rem;

backdrop-filter:blur(5px);
  border-radius: 3rem;
  margin: 0;
  
}


h2 {
  margin: 0;
  background: #ffd83b;
  color: #362c02;
  border-radius: 3rem;
}



.favorire-button {
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.26);
  backdrop-filter:blur(5px);
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0.1rem;
  border-radius: 50%;
  padding: 0.5rem;
}

.favorire-button img {
  width: 50px !important;
  height: 50px !important;
}
</style>

