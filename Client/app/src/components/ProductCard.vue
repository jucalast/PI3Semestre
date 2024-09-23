<template>
  <div>
    <h1>Produtos</h1>
    <div v-if="isLoading">
      <p>Carregando produtos...</p>
    </div>
    <div v-else-if="produtos.length > 0" class="card-container">
      <div v-for="produto in produtos" :key="produto.id" class="product-card">
        <img :src="produto.imagem" :alt="produto.nome" class="product-image" />
        <h2>{{ produto.nome }}</h2>
        <p>{{ produto.descricao }}</p>
        <p>Preço: R$ {{ produto.preco.toFixed(2) }}</p>
        <p>Avaliação: {{ produto.avaliacao }} estrelas</p>
        <p>Estoque: {{ produto.quantidade_estoque }}</p>
      </div>
    </div>
    <div v-else>
      <p>Nenhum produto encontrado.</p>
    </div>
    <pre>{{ produtos }}</pre>
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
 
  border-radius: 8px;
  padding: 16px;
  width: 200px;
  text-align: center;
 background: #292929;
 color: white;
}

.product-image {
  width: 100%;
  height: auto;
  border-radius: 8px;
}
</style>
