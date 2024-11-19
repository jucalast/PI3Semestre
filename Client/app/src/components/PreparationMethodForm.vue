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

          <!-- Campos do método de preparo -->
          <div class="form-group">
            <label v-if="isEditMode" for="tipo_preparo">Tipo de Preparo:</label>
            <input id="tipo_preparo" v-model="localProduct.metodoPreparo.tipo_preparo" placeholder="Tipo de Preparo" />
          </div>
          
          <div class="form-group">
            <label v-if="isEditMode" for="material">Material:</label>
            <input id="material" v-model="localProduct.metodoPreparo.material" placeholder="Material" />
          </div>
          
          <div class="form-group">
            <label v-if="isEditMode" for="acessorios">Acessórios:</label>
            <input id="acessorios" v-model="localProduct.metodoPreparo.acessorios" placeholder="Acessórios" />
          </div>
          
          <div class="form-group">
            <label v-if="isEditMode" for="complexidade">Complexidade:</label>
            <select id="complexidade" v-model="localProduct.metodoPreparo.complexidade" required>
              <option value="" disabled>Selecione a Complexidade</option>
              <option value="Fácil">Fácil</option>
              <option value="Intermediário">Intermediário</option>
              <option value="Avançado">Avançado</option>
            </select>
          </div>

          <div class="form-group">
            <label v-if="isEditMode" for="marca">Marca:</label>
            <input id="marca" v-model="localProduct.metodoPreparo.marca" placeholder="Marca" />
          </div>
        </div>

        <div class="imagenanddrop">
          <!-- Passando o método handleImageUpload como prop para o componente de upload -->
          <ImageUpload :onImageUpload="handleImageUpload" />
          <div class="image-preview" v-if="localProduct.imagens.length">
            <div v-for="(image, index) in localProduct.imagens" :key="index" class="image-container">
              <img :src="image" alt="Imagem do produto" class="preview-image" />
              <button @click="removeImage(index)" class="remove-image"></button>
            </div>
          </div>
        </div>
      </div>

      <button :class="['enviar', { 'disabled': !isFormValid || !isFormChanged }]" type="submit" :disabled="!isFormValid || !isFormChanged">{{ isEditMode ? 'Atualizar método de preparo' : 'Criar método de preparo' }}</button>
    </form>
  </div>
</template>


<script>
import ImageUpload from "./ImageUploader.vue"; // Importando o componente de upload de imagem

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
        metodoPreparo: {
          tipo_preparo: "",
          material: "",
          acessorios: "",
          complexidade: "",
          marca: "",
        },
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
      const formattedProduct = {
        ...this.localProduct,
        preco: parseFloat(this.localProduct.preco.replace(/\./g, '').replace(',', '.')), // Converte para BigDecimal
        metodoPreparo: Object.entries(this.localProduct.metodoPreparo).reduce(
          (acc, [key, value]) => {
            if (value) acc[key] = value;
            return acc;
          },
          {}
        ),
      };

      if (!Object.keys(formattedProduct.metodoPreparo).length) {
        delete formattedProduct.metodoPreparo;
      }

      console.log("Produto enviado:", formattedProduct); // Exibe os dados no console para teste
      this.$emit("submit-product", formattedProduct); // Emite o evento para o pai com os dados do produto
    },
    formatPrice(event) {
      let value = event.target.value;
      value = value.replace(/\D/g, ""); // Remove tudo que não é dígito
      value = value.replace(/(\d)(\d{2})$/, "$1,$2"); // Coloca a vírgula antes dos últimos 2 dígitos
      value = value.replace(/(?=(\d{3})+(\D))\B/g, "."); // Coloca o ponto a cada 3 dígitos
      this.localProduct.preco = value;
    },
    validateForm() {
      const { nome, descricao, preco, quantidade_estoque, metodoPreparo, imagens } = this.localProduct;
      this.isFormValid = nome && descricao && preco && quantidade_estoque && imagens.length > 0 &&
        Object.values(metodoPreparo).every(value => value);
    },
    checkFormChanges() {
      this.isFormChanged = JSON.stringify(this.localProduct) !== JSON.stringify(this.product);
    },
  },
};
</script>

<style scoped>
.form-container {
  margin: 20px;
}
.dados-imagem {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.dados {
  flex: 1;
  margin-right: 20px;
}
.imagenanddrop {
  flex: 1;
}
</style>


<style scoped>
.dados-imagem {
  min-width: 100% !important;
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

.enviar.disabled {
  background-color: transparent;
  border: 2px solid #818181;
  color: #818181;
  cursor: not-allowed;
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
  min-width: 100% !important;
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
select,
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
  box-shadow: 1px 0px 20px 1px #3a5bff38;
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
  position: absolute;
  top: 13%;
  right: 13%;
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
  color: white;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;

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
