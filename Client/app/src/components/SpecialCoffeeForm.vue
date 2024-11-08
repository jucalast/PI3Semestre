<template>
  <div class="form-container">
      <form @submit.prevent="submitForm">
          <!-- Campos básicos do produto -->
          <input v-model="product.nome" placeholder="Nome" required />
          <textarea v-model="product.descricao" placeholder="Descrição" required></textarea>
          <input v-model.number="product.preco" type="number" placeholder="Preço" required />
          <input v-model.number="product.quantidade_estoque" type="number" placeholder="Quantidade em Estoque" required />
          <input class="imagem" v-model="product.imagem" type="url" placeholder="URL da Imagem" required />
          
          <!-- Inputs específicos de propriedades dentro de cafeEspecial -->
          <input v-model="product.cafeEspecial.notasSensoriais" placeholder="Notas Sensoriais" />
          <input v-model="product.cafeEspecial.origem" placeholder="Origem" />
          <input v-model="product.cafeEspecial.recomendacoesPreparo" placeholder="Recomendações de Preparo" />
          <input v-model="product.cafeEspecial.torra" placeholder="Torra" />
          <input v-model="product.cafeEspecial.torrefacao" placeholder="Torrefação" />
          <input v-model="product.cafeEspecial.variedade" placeholder="Variedade" />
          <input v-model="product.cafeEspecial.beneficiamento" placeholder="Beneficiamento" />

          <!-- Datas dentro de cafeEspecial -->
          <div class="dates">
              <label for="dataTorra">Data de Torra:</label>
              <input id="dataTorra" v-model="product.cafeEspecial.dataTorra" type="date" />
          </div>
          <div class="dates">
              <label for="dataValidade">Data de Validade:</label>
              <input id="dataValidade" v-model="product.cafeEspecial.dataValidade" type="date" />
          </div>

          <button type="submit">Salvar Alterações</button>
      </form>
  </div>
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
        cafeEspecial: {
          notasSensoriais: '',
          origem: '',
          recomendacoesPreparo: '',
          torra: '',
          torrefacao: '',
          variedade: '',
          beneficiamento: '',
          dataTorra: '',
          dataValidade: ''
        },
        metodoDePreparo: ''  // Declarado, mas ignorado no envio
      }
    };
  },
  methods: {
    submitForm() {
    // Cria um novo objeto excluindo 'metodoDePreparo'
    const { metodoDePreparo, ...filteredProduct } = this.product;

    // Remove 'cafeEspecial' se estiver vazio
    const cafeEspecialFields = [
      "notasSensoriais",
      "origem",
      "recomendacoesPreparo",
      "torra",
      "torrefacao",
      "variedade",
      "beneficiamento",
      "dataTorra",
      "dataValidade"
    ];

    // Clona apenas os campos preenchidos em 'cafeEspecial'
    filteredProduct.cafeEspecial = {};
    cafeEspecialFields.forEach(field => {
      if (this.product.cafeEspecial[field]) {
        filteredProduct.cafeEspecial[field] = this.product.cafeEspecial[field];
      }
    });

    // Remove 'cafeEspecial' do produto final se estiver vazio
    if (Object.keys(filteredProduct.cafeEspecial).length === 0) {
      delete filteredProduct.cafeEspecial;
    }

    // Exibe o objeto atualizado no console
    console.log("Produto Atualizado:", filteredProduct);

    // Emitimos o produto sem 'metodoDePreparo' e com 'cafeEspecial' ajustado
    this.$emit('submit-product', filteredProduct);
  },
},
};
</script>


<style scoped>
.c1 {
  display: flex;
  gap: 1rem !important;
}

.c2 {
  display: flex;
  flex-direction: column;
  gap: 1rem !important;
}

.dates {
  display: flex;
  flex-direction: column;
}

label {
  font-size: 1.5rem;
  margin-left: 1rem;
  color: #6d6d6d;
}

.c3 {
  max-width: 95%;
}

.imagem {
  width: 180%;
}

.form-container {
  gap: 1rem !important;
  display: flex;
  flex-direction: row;
  gap: 20px;
  background: transparent !important;
  width: 100% !important;
  flex-wrap: wrap;
  align-items: center;
  overflow-y: auto;
  max-height: 95% !important;
}

.form-container form {
  display: flex;
  flex-direction: row;
  gap: 20px;
  background: transparent !important;
  align-items: center;
  width: fit-content !important;
  flex-wrap: wrap;
  padding: 1rem;
}

.form-container input,
.form-container textarea {
  color: #ff4d4d;
  border: solid 1px #aeaeaeb6;
  background: #ededed;
  border-radius: 2rem;

  height: 5rem; /* Para inputs */
  padding-left: 2rem !important;
  font-size: 1.5rem !important;
  outline: none;
  padding: 0;
  font-family: 'Poppins', sans-serif; /* Aplicando Poppins aos inputs */
}

input {
  width: fit-content !important;
}

.form-container textarea {
  height: auto; /* Permite que a altura do textarea seja dinâmica */
  min-height: 100px; /* Define uma altura mínima para o textarea */
}

.form-container input::placeholder,
.form-container textarea::placeholder {
  color: #6d6d6d;
}

.form-container input:focus,
.form-container textarea:focus {
  outline: none;
  border: 1px solid #ff4d4d;
  box-shadow: 1px 0px 20px 1px #ff4d4d54;
}

.form-container button {
  background-color: #ff4d4d; /* Cor do botão */
  color: white; /* Cor do texto no botão */
  border: none; /* Remove borda */
  border-radius: 2rem; /* Bordas arredondadas */
  padding: 1rem 2rem; /* Espaçamento interno */
  font-size: 2rem; /* Tamanho do texto */
  cursor: pointer; /* Cursor de mão ao passar o mouse */
  transition: background-color 0.3s; /* Efeito de transição para cor do fundo */
}

.form-container button:hover {
  background-color: #e04e4e; /* Cor ao passar o mouse */
}
</style>
