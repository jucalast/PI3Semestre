<template>
  <div 
    class="image-uploader"
    @dragover.prevent
    @dragenter.prevent="isDragging = true"
    @dragleave.prevent="isDragging = false"
    @drop.prevent="handleDrop"
  >
    <div 
      :class="['dropbox', { dragging: isDragging }]"
      @click="triggerFileInput"
    >
      <p>Arraste e solte suas imagens aqui ou clique para carregar</p>
    </div>
    <input 
      type="file" 
      ref="fileInput" 
      @change="onFileChange" 
      multiple 
      accept="image/*" 
      style="display: none" 
    />
   
  </div>
</template>

<script>
export default {
  props: {
    onImageUpload: {
      type: Function,
      required: true,
    },
    initialImages: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      imagePreviews: this.initialImages, // Usa as imagens iniciais passadas como prop
      isDragging: false, // Controla o estado do drag-and-drop
      hoverIndex: null, // Índice da imagem sendo sobrevoada
    };
  },
  methods: {
  onFileChange(event) {
    const files = Array.from(event.target.files);
    console.log("Arquivos selecionados pelo usuário:", files);
    this.processFiles(files);
  },
  handleDrop(event) {
    this.isDragging = false;
    const files = Array.from(event.dataTransfer.files);
    console.log("Arquivos arrastados e soltos:", files);
    this.processFiles(files);
  },
  processFiles(files) {
    const newBase64Images = [];

    files.forEach((file) => {
      if (file.type.startsWith("image/")) {
        console.log("Processando arquivo de imagem:", file);
        const reader = new FileReader();

        reader.onload = (e) => {
          newBase64Images.push(e.target.result); // Adiciona a imagem ao novo array base64

          // Atualiza o estado após processar todas as imagens
          if (newBase64Images.length === files.length) {
            console.log("Imagens base64 geradas:", newBase64Images);
            this.imagePreviews = [...this.imagePreviews, ...newBase64Images];
            console.log("Pré-visualizações atuais:", this.imagePreviews);
            this.onImageUpload([...this.imagePreviews]); // Envia todas as imagens ao pai
          }
        };

        reader.readAsDataURL(file);
      } else {
        console.log("Arquivo não é uma imagem, ignorando:", file);
      }
    });
  },
  triggerFileInput() {
    console.log("Abrindo o seletor de arquivos...");
    this.$refs.fileInput.click();
  },
  removeImage(index) {
    this.imagePreviews.splice(index, 1);
    this.onImageUpload([...this.imagePreviews]); // Atualiza o pai com a nova lista de imagens
  },
},
};
</script>

<style scoped>
.image-uploader {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px;
  height: 40%;
}

.dropbox {
  display: flex;
  border: 2px dashed #5f75e4;
  border-radius: 2rem;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s;
  height: 100%;
  color: #5e5e5e;
  font-size: 2rem;
  align-items: center;
  justify-content: center;
}

.dropbox.dragging {
  background-color: #5f75e425;
}

.image-preview {
  display: flex;
  flex-wrap: wrap;
  margin-top: 20px;
}

.preview {
  position: relative;
  margin: 10px;
  display: inline-block;
}

.preview img {
  max-width: 100px;
  max-height: 100px;
  border-radius: 5px;
}

.remove-btn {
  position: absolute;
  top: 5px;
  right: 5px;
  background: transparent;
  color: transparent;
  border: none;
  cursor: pointer;
  font-size: 20px;
  transition: color 0.3s;
}

.preview:hover .remove-btn {
  color: #ff4d4d;
}

.remove-btn {
  position: absolute;
  top: 0px;
  right: -10px;
  background: transparent;
  color: #ff4d4d;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  padding: 2px 5px;
  font-size: 12px;
  transition: background-color 0.3s;
  width: 20px;
  height: 20px;
  display: flex;
}

.remove-btn:hover {
  background-color: #ff4d4d2f;
}
</style>
