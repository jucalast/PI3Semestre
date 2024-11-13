<template>
  <div class="form-container">
    <form @submit.prevent="submitForm">
      <div class="dados-imagem">
        <div class="dados">
          <!-- Campos básicos do produto -->
          <input v-model="product.nome" placeholder="Nome" required />
          <textarea v-model="product.descricao" placeholder="Descrição" required></textarea>
          <input v-model.number="product.preco" type="number" placeholder="Preço" required />
          <input v-model.number="product.quantidade_estoque" type="number" placeholder="Quantidade em Estoque" required />

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
        </div>
<div class="imagenanddrop">


        <!-- Upload de Imagens -->
        <div
          class="imagem-container"
          @drop.prevent="onDrop"
          @dragover.prevent
        >
          <input
            class="imagem"
            type="file"
            multiple
            @change="onFileChange"
            accept="image/*"
          />
          <p>Arraste e solte as imagens aqui ou clique para selecionar</p>
        </div>

        <!-- Pré-visualização das Imagens -->
        <div class="preview-container">
          <div v-for="(image, index) in imagePreviews" :key="index" class="preview-item">
            <img :src="image.preview" alt="Preview" class="preview" />
            <button @click="removeImage(index)">Remover</button>
          </div>
        </div>
      </div>
      </div>

      <button class="enviar" type="submit">Salvar Alterações</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      product: {
        nome: "",
        descricao: "",
        preco: null,
        imagens: [], // Guardará as imagens em VARBINARY
        quantidade_estoque: null,
        cafeEspecial: {
          notasSensoriais: "",
          origem: "",
          recomendacoesPreparo: "",
          torra: "",
          torrefacao: "",
          variedade: "",
          beneficiamento: "",
          dataTorra: "",
          dataValidade: "",
        },
      },
      imagePreviews: [], // Guarda pré-visualizações e dados binários das imagens
    };
  },
  methods: {
    async onFileChange(event) {
      const files = event.target.files;
      await this.handleFiles(files);
    },
    async onDrop(event) {
      const files = event.dataTransfer.files;
      await this.handleFiles(files);
    },
    async handleFiles(files) {
      for (const file of files) {
        await this.convertToBinary(file);
      }
    },
    async convertToBinary(file) {
      if (!file) return;

      const reader = new FileReader();
      reader.onload = (e) => {
        this.imagePreviews.push({
          preview: e.target.result,
          binary: null, // Inicializamos o binário como null para definir posteriormente
        });
      };
      reader.readAsDataURL(file);

      const binaryReader = new FileReader();
      binaryReader.onload = (e) => {
        const binaryData = e.target.result;
        this.product.imagens.push(binaryData);
        this.imagePreviews[this.imagePreviews.length - 1].binary = binaryData;
      };
      binaryReader.readAsArrayBuffer(file);
    },
    removeImage(index) {
      this.product.imagens.splice(index, 1);
      this.imagePreviews.splice(index, 1);
    },
    submitForm() {
      const { metodoDePreparo, ...filteredProduct } = this.product;

      const cafeEspecialFields = [
        "notasSensoriais",
        "origem",
        "recomendacoesPreparo",
        "torra",
        "torrefacao",
        "variedade",
        "beneficiamento",
        "dataTorra",
        "dataValidade",
      ];

      filteredProduct.cafeEspecial = {};
      cafeEspecialFields.forEach((field) => {
        if (this.product.cafeEspecial[field]) {
          filteredProduct.cafeEspecial[field] = this.product.cafeEspecial[field];
        }
      });

      if (Object.keys(filteredProduct.cafeEspecial).length === 0) {
        delete filteredProduct.cafeEspecial;
      }

      console.log("Produto Atualizado:", filteredProduct);
      this.$emit("submit-product", filteredProduct);
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
  background: #e3e3e3;
  width: 40%;
  border-radius: 2rem;
}

.imagem-container {
  border: 2px dashed #ccc;
  border-radius: 2rem;
  margin: 1rem;
  padding: 20px;
  text-align: center;
  position: relative;
  height: 30%;
  display: flex;
}

.imagem-container input[type="file"] {
  opacity: 0;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  cursor: pointer;
}

.preview-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;
}

.preview-item {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.preview {
  width: 100px;
  height: auto;
}

.preview-item button {
  background-color: #ff4d4d;
  border: none;
  color: white;
  cursor: pointer;
  padding: 5px;
  font-size: 12px;
  margin-top: 5px;
}



.enviar {
  position: absolute;
  top:13%;
  right: 13%;
}

.dados {
  display: flex;
    flex-direction: row;
    gap: 1rem !important;
    flex-wrap: wrap;
    width: 60%
}
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
  max-height: 80% !important;
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
  color: #ff4d4d;
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
