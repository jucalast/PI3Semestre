<template>
    <form @submit.prevent="submitForm">
      <h3>Método de Preparo</h3>
      <input v-model="product.nome" placeholder="Nome" required />
      <textarea v-model="product.descricao" placeholder="Descrição" required></textarea>
      <input v-model.number="product.preco" type="number" placeholder="Preço" required />
      <input v-model="product.imagem" type="url" placeholder="URL da Imagem" required />
      <input v-model.number="product.quantidade_estoque" type="number" placeholder="Quantidade em Estoque" required />
  
      <h4>Detalhes do Método de Preparo</h4>
      <input v-model="product.metodoPreparo.tipo_preparo" placeholder="Tipo de Preparo" />
      <input v-model="product.metodoPreparo.material" placeholder="Material" />
      <input v-model="product.metodoPreparo.acessorios" placeholder="Acessórios" />
      <input v-model="product.metodoPreparo.complexidade" placeholder="Complexidade" />
      <input v-model="product.metodoPreparo.marca" placeholder="Marca" />
      
      <button type="submit">Criar Produto</button>
    </form>
  </template>
  
  <script>
  export default {
    data() {
      return {
        product: {
          nome: '',
          descricao: '',
          preco: null,
          imagem: '',
          quantidade_estoque: null,
          metodoPreparo: {
            tipo_preparo: '',
            material: '',
            acessorios: '',
            complexidade: '',
            marca: ''
          }
        }
      };
    },
    methods: {
      submitForm() {
        this.$emit('submit-product', this.product);
      }
    }
  };
  </script>
  