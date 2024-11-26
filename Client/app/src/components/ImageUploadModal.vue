<template>
  <div class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <span><button class="close-button" @click="closeModal">x</button></span>
      <div class="modal-header">
        <h2>Adicione os Banners mais chamativos para a sua loja</h2>
      </div>
      <div class="images-uploads">
        <div v-for="(image, index) in uploadedImages" :key="index" class="image-upload-group">
          <input v-model="image.title" placeholder="Nome do Banner" class="title-input" />
          <img :src="image.src" alt="Preview" class="image-preview" />
          <div class="button-group">
            <button class="icon-button" @click="updateBanner(image.id, image.title, image.src)">
              <i class="fas fa-sync-alt"></i>
            </button>
            <button class="icon-button delete-icon" @click="deleteBanner(image.id)">
              <i class="fas fa-trash-alt"></i>
            </button>
          </div>
        </div>
      </div>
      <ImageUploader :onImageUpload="handleImageUpload" :initialImages="initialImages" />
      <button class="send-button" @click="createBanners">Enviar</button>
    </div>
  </div>
</template>

<script>
import ImageUploader from '@/components/ImageUploader.vue';
import axiosInstance from '../utils/axiosInstance';

export default {
  components: {
    ImageUploader,
  },
  props: {
    initialImages: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      uploadedImages: [],
    };
  },
  async mounted() {
    await this.fetchBanners();
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    handleImageUpload(images) {
      console.log('Imagens carregadas:', images);
      this.uploadedImages = images.map(image => ({ src: image, title: '' }));
    },
    async fetchBanners() {
      try {
        const response = await axiosInstance.get('/api/banners');
        this.uploadedImages = response.data.map(banner => ({
          id: banner.id,
          src: banner.imageBase64,
          title: banner.title,
        }));
      } catch (error) {
        console.error('Erro ao buscar os banners:', error);
      }
    },
    async createBanners() {
      const banners = this.uploadedImages.map(image => ({
        title: image.title,
        imageBase64: image.src,
      }));

      if (banners.length > 0) {
        try {
          const response = await axiosInstance.post('/api/banners/bulk', banners);
          console.log('Banners criados:', response.data);
          this.$emit('banners-created', response.data);
        } catch (error) {
          console.error('Erro ao criar os banners:', error);
        }
        this.closeModal();
      } else {
        console.log('Nenhuma imagem carregada para enviar.');
      }
    },
    async updateBanner(id, title, imageBase64) {
      try {
        const response = await axiosInstance.put(`/api/banners/${id}`, { title, imageBase64 });
        console.log('Banner atualizado:', response.data);
        await this.fetchBanners();
      } catch (error) {
        console.error('Erro ao atualizar o banner:', error);
      }
    },
    async deleteBanner(id) {
      try {
        await axiosInstance.delete(`/api/banners/${id}`);
        console.log('Banner removido');
        await this.fetchBanners();
      } catch (error) {
        console.error('Erro ao remover o banner:', error);
      }
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 0 0 2rem 2rem;
  position: relative;
  width: 80%;
}

input {
  width: 4rem;
  color: #3a5bff;
  border: solid 1px #3a5bff !important;
  background: transparent;
  border-radius: 1rem;
  height: 2.5rem; /* Para inputs */
  padding: 0.5rem !important;
  font-size: 1.5rem !important;
  outline: none;   
}

input::placeholder {
  color: #c6d0ff;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.modal-header h2 {
  margin: 0;
  position: absolute;
  background: #00000000;
  font-size: 2rem;
  color: #ffffff;
  width: 100%;
  right: 0;
  top: -30%;
  padding: 2rem;
  border-radius: 2rem 2rem 0 0;
  backdrop-filter: blur(20px);
}

.close-button {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
}

.send-button {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background-color: #5f75e4;
  color: white;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  font-size: 1rem;
}

.send-button:hover {
  background-color: #4e60c4;
}

.button-group {
  display: flex;
  gap: 0.5rem;
}

.icon-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.5rem;
  color: #5f75e4;
}

.icon-button:hover {
  color: #4e60c4;
}

.delete-icon {
  color: #ff4d4d;
}

.delete-icon:hover {
  color: #ff0000;
}

.images-uploads {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
  background-color: #ebeeff;
  border: solid 1px #8698ff;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 2rem;
  margin-top: 4rem;
}

.title-input {
  width: 90%;
  padding: 1.5rem;
  border: 1px solid #ccc;
  font-size: 1rem;
}

.image-upload-group {
  width: 20%;
    display: flex
;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
}

.image-preview {
  max-width: 50%;
  margin: 1rem;
}

span {
  position: absolute;
  top: 0;
  right: -3rem;
  background: #b7b7b776;
  width: 2.2rem;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  backdrop-filter: Blur(5px);
  color: #ffffff;
}
</style>