<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      
      <div class="infoprod">
      <p class="nome" ><strong> {{ product.nome }} </strong> </p>
      <p><strong>Preço:</strong> R$ {{ product.preco.toFixed(2) }}</p>
      <p><strong>Descrição:</strong> {{ product.descricao }}</p>
      <p><strong>Quantidade em Estoque:</strong> {{ product.quantidadeEstoque }}</p>
    </div>
      <div class="imagemproduto">
      <img :src="product.imagem" :alt="product.nome" class="product-image modalimage" />
    </div>

      <!-- Exibir detalhes do Café Especial -->
      <div class="infoprod" v-if="product.cafeEspecial && product.cafeEspecial.length > 0">
        <h3>Café Especial</h3>
        <p><strong>Origem:</strong> {{ product.cafeEspecial[0].origem }}</p>
        <p><strong>Variedade:</strong> {{ product.cafeEspecial[0].variedade }}</p>
        <p><strong>Torra:</strong> {{ product.cafeEspecial[0].torra }}</p>
        <p><strong>Notas Sensoriais:</strong> {{ product.cafeEspecial[0].notasSensoriais }}</p>
        <p><strong>Data de Validade:</strong> {{ product.cafeEspecial[0].dataValidade }}</p>
        <p><strong>Recomendações de Preparo:</strong> {{ product.cafeEspecial[0].recomendacoesPreparo }}</p>
      </div>

      <!-- Exibir detalhes dos Métodos de Preparo -->
      <div class="infoprod" v-if="product.metodoPreparo && product.metodoPreparo.length > 0">
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
      
      <!-- Mensagem caso não haja dados encontrados -->
      <div class="infoprod"  v-if="(!product.cafeEspecial || product.cafeEspecial.length === 0) && 
                   (!product.metodoPreparo || product.metodoPreparo.length === 0)">
        <p>Nenhum detalhe encontrado para este produto.</p>
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

.nome {
  display: flex;
font-size: 3rem;
}

.infoprod {
  display: flex;
    width: 30%;
    flex-direction: column;
    align-content: flex-start;
    align-items: flex-start;
}

.imagemproduto{
  width: 25%;
  height: 25%;
  margin-bottom: 15rem;
}

.modal-content {
  background-color: white;
    padding: 20px;
    border-radius: 2rem;
    width: 80%;
    height: 80%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    align-content: center;
    justify-content: space-evenly;
    align-items: center;
}



.modalimage {
  width: 100% !important;
}

.modal-content p, font {
  background: transparent !important;
    width: fit-content;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
}


</style>
