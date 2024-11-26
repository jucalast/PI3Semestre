
<template>
  <div :class="['form-container', { 'edit-mode': isEditMode }]">
    <form @submit.prevent="submitForm">
      <div class="dados-imagem">
        <div class="dados">
          <!-- Campos básicos do produto -->
          <div class="form-group">
            <label v-if="isEditMode" for="nome">Nome:</label>
            <input id="nome" v-model="localProduct.nome" placeholder="Nome" required />
          </div>
          
          <div class="form-group">
            <label v-if="isEditMode" for="descricao">Descrição:</label>
            <textarea id="descricao" v-model="localProduct.descricao" placeholder="Descrição" required></textarea>
          </div>
          
          <div class="form-group">
            <label v-if="isEditMode" for="preco">Preço:</label>
            <input id="preco" v-model="localProduct.preco" @input="formatPrice" type="text" placeholder="Preço" required />
          </div>
          
          <div class="form-group">
            <label v-if="isEditMode" for="quantidade_estoque">Quantidade em Estoque:</label>
            <input id="quantidade_estoque" v-model.number="localProduct.quantidade_estoque" type="number" placeholder="Quantidade em Estoque" required />
          </div>
        </div>

        <div class="imagenanddrop">
          <!-- Passando o método onImageUpload como prop para o ImageUpload -->
          <ImageUpload :onImageUpload="handleImageUpload" />
          <div class="image-preview" v-if="localProduct.imagens.length">
            <div v-for="(image, index) in localProduct.imagens" :key="index" class="image-container">
              <img :src="image" alt="Imagem do produto" class="preview-image" />
              <button @click="removeImage(index)" class="remove-image"></button>
            </div>
          </div>
        </div>
      </div>

      <button :class="['enviar', { 'disabled': !isFormValid || !isFormChanged }]" type="submit" :disabled="!isFormValid || !isFormChanged">{{ isEditMode ? 'Atualizar produto' : 'Criar produto' }}</button>
    </form>
  </div>
</template>

<script>
import ImageUpload from './ImageUploader.vue';

export default {
  components: {
    ImageUpload,
  },
  props: {
    product: {
      type: Object,
      default: () => ({
        nome: "",
        descricao: "",
        preco: null,
        imagens: [],
        quantidade_estoque: null,
      }),
    },
    isEditMode: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      localProduct: { ...this.product },
      isFormValid: false,
      isFormChanged: false,
    };
  },
  watch: {
    product: {
      immediate: true,
      handler(newVal) {
        this.localProduct = { ...newVal };
        this.validateForm();
        this.checkFormChanges();
      },
    },
    localProduct: {
      deep: true,
      handler() {
        this.validateForm();
        this.checkFormChanges();
      },
    },
  },
  methods: {
    handleImageUpload(newImages) {
      this.localProduct.imagens = [...this.localProduct.imagens, ...newImages];
    },
    removeImage(index) {
      this.localProduct.imagens.splice(index, 1);
    },
    submitForm() {
      const precoString = typeof this.localProduct.preco === 'string' ? this.localProduct.preco : this.localProduct.preco.toString();
      const filteredProduct = {
        ...this.localProduct,
        preco: parseFloat(precoString.replace(/\./g, '').replace(',', '.')), // Converte para BigDecimal
      };

      console.log("Produto Atualizado:", filteredProduct);
      this.$emit("submit-product", filteredProduct);
    },
    formatPrice(event) {
      let value = event.target.value;
      value = value.replace(/\D/g, ""); // Remove tudo que não é dígito
      value = value.replace(/(\d)(\d{2})$/, "$1,$2"); // Coloca a vírgula antes dos últimos 2 dígitos
      value = value.replace(/(?=(\d{3})+(\D))\B/g, "."); // Coloca o ponto a cada 3 dígitos
      this.localProduct.preco = value;
    },
    validateForm() {
      const { nome, descricao, preco, quantidade_estoque, imagens } = this.localProduct;
      this.isFormValid = nome && descricao && preco && quantidade_estoque && imagens.length > 0;
    },
    checkFormChanges() {
      this.isFormChanged = JSON.stringify(this.localProduct) !== JSON.stringify(this.product);
    },
  },
};
</script>

<style scoped>
.dados-imagem {
  display: flex;
  flex-direction: row;
  gap: 1rem !important;
}

.imagenanddrop {
  display: flex;
  flex-direction: column;
  width: 40%;
  border-radius: 2rem;

}

.enviar {
  position: absolute;
  top: 13%;
  right: 13%;
}

.dados {
  display: flex;
  flex-direction: row;
  gap: 1rem !important;
  flex-wrap: wrap;
  width: 60%;
  flex-wrap: wrap;
}

.form-group {
  display: flex;
  flex-direction: column;
}

label {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
  color: #6d6d6d;
}

.dates {
  display: flex;
  flex-direction: column;
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
  max-height: 80% !important;
}

.form-container.edit-mode {
  min-width: 100% !important;
  max-height: 83% !important;
  margin-top: 7rem;

}

.form-container form {
  display: flex;
  gap: 20px;
  background: transparent !important;
  align-items: center;
  width: fit-content !important;
  padding: 1rem;
  flex-direction: row;
}

.form-container input,
.form-container textarea {
  color: #3a5bff;
  border: solid 1px #aeaeaeb6;
  background: #ededed;
  border-radius: 2rem;
  height: 5rem; /* Para inputs */
  padding-left: 2rem !important;
  font-size: 1.5rem !important;
  outline: none;
  padding: 0;
  font-family: "Poppins", sans-serif; /* Aplicando Poppins aos inputs */
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
  border: 1px solid #3a5bff;
  box-shadow: 1px 0px 20px 1px #3a5bff3b;
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



.enviar.disabled {
  background-color: transparent;
  border: 2px solid #818181;
  color: #818181;
  cursor: not-allowed;
}

.image-preview {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-top: 1rem;
}

.image-container {
  position: relative;
}

.preview-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 0.5rem;
}

.remove-image {
  position: absolute;
  top: 0;
  right: 0;
  background: red;
  color: white;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  padding: 0.5rem;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.remove-image::before {
  content: "x";
  font-size: 1rem;
}
</style>