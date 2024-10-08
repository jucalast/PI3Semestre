<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <button @click="close">Fechar</button>
      <h2>Detalhes do Produto</h2>
      <img :src="product.imagem" :alt="product.nome" class="product-image" />
      <p><strong>Nome:</strong> {{ product.nome }}</p>
      <p><strong>Preço:</strong> {{ product.preco.toFixed(2) }}</p>
      <p><strong>Descrição:</strong> {{ product.descricao }}</p>
      <p><strong>Quantidade em Estoque:</strong> {{ product.quantidadeEstoque }}</p>

      <div v-if="product.cafeEspecial">
        <h3>Café Especial</h3>
        <p><strong>Origem:</strong> {{ product.cafeEspecial.origem }}</p>
        <p><strong>Variedade:</strong> {{ product.cafeEspecial.variedade }}</p>
        <p><strong>Torra:</strong> {{ product.cafeEspecial.torra }}</p>
        <p><strong>Notas Sensoriais:</strong> {{ product.cafeEspecial.notasSensoriais }}</p>
        <p><strong>Data de Validade:</strong> {{ product.cafeEspecial.dataValidade }}</p>
        <p><strong>Recomendações de Preparo:</strong> {{ product.cafeEspecial.recomendacoesPreparo }}</p>
      </div>

      <div v-if="product.metodoPreparo && product.metodoPreparo.length > 0">
        <h3>Métodos de Preparo</h3>
        <div v-for="metodo in product.metodoPreparo" :key="metodo.id">
          <p><strong>Nome:</strong> {{ metodo.nome }}</p>
          <p><strong>Tipo de Preparo:</strong> {{ metodo.tipoPreparo }}</p>
          <p><strong>Material:</strong> {{ metodo.material }}</p>
          <p><strong>Acessórios:</strong> {{ metodo.acessorios }}</p>
          <p><strong>Complexidade:</strong> {{ metodo.complexidade }}</p>
          <p><strong>Marca:</strong> {{ metodo.marca }}</p>
          <p><strong>Descrição:</strong> {{ metodo.descricao }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    product: Object,
    isVisible: Boolean
  },
  methods: {
    close() {
      this.$emit('close');
    }
  }
};
</script>

<style>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  max-width: 600px;
  width: 100%;
}

.product-image {
  width: 100%;
  height: auto;
  border-radius: 10px;
}
</style>
