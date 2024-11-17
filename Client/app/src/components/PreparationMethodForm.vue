<template>
  <div class="form-container">
    <form @submit.prevent="submitForm">
      <input v-model="product.nome" placeholder="Nome" required />
      <textarea
        v-model="product.descricao"
        placeholder="Descrição"
        required
      ></textarea>
      <input
        v-model.number="product.preco"
        type="number"
        placeholder="Preço"
        required
      />
      <input
        v-model.number="product.quantidade_estoque"
        type="number"
        placeholder="Quantidade em Estoque"
        required
      />
      <input
        class="imagem"
        v-model="product.imagem"
        type="url"
        placeholder="URL da Imagem"
        required
      />

      <input
        v-model="product.metodoPreparo.tipo_preparo"
        placeholder="Tipo de Preparo"
      />
      <input v-model="product.metodoPreparo.material" placeholder="Material" />
      <input
        v-model="product.metodoPreparo.acessorios"
        placeholder="Acessórios"
      />
      <input
        v-model="product.metodoPreparo.complexidade"
        placeholder="Complexidade"
      />
      <input v-model="product.metodoPreparo.marca" placeholder="Marca" />

      <button type="submit">Criar Produto</button>
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
          metodoPreparo: {
            tipo_preparo: '',
            material: '',
            acessorios: '',
            complexidade: '',
            marca: '',
          },
        },
      };
    },
    methods: {
      submitForm() {
        this.$emit('submit-product', this.product);
      },
    },
  };
</script>

<style scoped>
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

  .input {
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
