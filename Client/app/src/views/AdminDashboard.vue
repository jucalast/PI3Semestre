<template>
  <AdminLayout>
    <FloatingHeader @open-image-upload="showImageUploadModal = true" />
    <section class="centered-section">
      <h1>
        Tudo na palma das
        <strong>suas mãos</strong>
      </h1>
      <p>Explore e gerencie todos seu negócio com facilidade e rapidez.</p>
    </section>
    <section class="principal-section">
      <div class="left-column">
        <CreateProductComponent @product-created="handleProductCreated" />
        <FavoriteCountComponent />
      </div>
      <div class="right-column">
        <div class="product-list">
          <div class="header">
            <h3>Edite ou exclua produtos</h3>
            <button @click="fetchProdutos" class="refresh-button">
              <i class="fas fa-sync-alt"></i> Atualizar
            </button>
            <div class="dropdown">
              <button @click="toggleBulkEditDropdown" class="bulk-edit-button">
                <i class="fas fa-edit"></i> Edição em Massa
              </button>
              <div v-if="isBulkEditDropdownVisible" class="dropdown-content">
                <BulkEditDropdown
                  :produtos="filteredProducts"
                  @bulk-edit="handleBulkEdit"
                />
              </div>
            </div>
          </div>
          <div v-if="isLoading" class="loading-gif">
            <img :src="loadingGif" alt="Carregando..." />
          </div>
          <CardGeneric v-else :produtos="filteredProducts" :searchQuery="searchQuery" :isLoading="isLoading" />
        </div>
      </div>
      <ImageUploadModal v-if="showImageUploadModal" @close="showImageUploadModal = false" @images-uploaded="handleImagesUploaded" />
    </section>
  </AdminLayout>
</template>


<script>
import AdminLayout from '@/layouts/AdminLayout.vue';
import FloatingHeader from '@/components/FloatingHeader.vue';
import CreateProductComponent from '@/components/CreateProductComponent.vue';
import CardGeneric from '@/components/CardGeneric.vue';
import FavoriteCountComponent from '@/components/FavoriteCountComponent.vue';
import BulkEditDropdown from '@/components/BulkEditDropdown.vue';
import ImageUploadModal from '@/components/ImageUploadModal.vue';
import axiosInstance from '../utils/axiosInstance';
import { useToast } from 'vue-toastification';
import 'vue-toastification/dist/index.css';
import loadingGif from '@/assets/Logo Maven.gif';

export default {
  name: 'AdminDashboard',
  components: {
    AdminLayout,
    FloatingHeader,
    CreateProductComponent,
    CardGeneric, // Certifique-se de que o nome está correto
    FavoriteCountComponent,
    BulkEditDropdown,
    ImageUploadModal,
  },
  data() {
    return {
      produtos: [],
      isLoading: true,
      searchQuery: "",
      favoriteCounts: {},
      isBulkEditDropdownVisible: false,
      loadingGif,
      showImageUploadModal: false,
    };
  },
  computed: {
    filteredProducts() {
      if (!this.searchQuery) {
        return this.produtos;
      }
      return this.produtos.filter(produto => 
        produto.nome.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
  },
  methods: {
    async fetchProdutos() {
      try {
        const response = await axiosInstance.get("http://localhost:8080/api/produtos");
        this.produtos = response.data;
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      } finally {
        this.isLoading = false;
      }
    },
    handleProductCreated(newProduct) {
      this.produtos.push(newProduct);  // Adiciona o novo produto diretamente à lista
    },
    updateSearchQuery(query) {
      this.searchQuery = query;
    },
    async fetchFavoriteCounts() {
      try {
        const response = await axiosInstance.get("http://localhost:8080/api/favorites/count-by-product");
        this.favoriteCounts = response.data;
      } catch (error) {
        console.error("Erro ao buscar contagem de favoritos:", error);
      }
    },
    toggleBulkEditDropdown() {
      this.isBulkEditDropdownVisible = !this.isBulkEditDropdownVisible;
    },
    async handleBulkEdit(updatedProducts) {
      const toast = useToast();
      try {
        const response = await axiosInstance.put("http://localhost:8080/api/produtos/protected/bulk-update", updatedProducts);
        if (response.status === 200) {
          this.fetchProdutos();
          this.isBulkEditDropdownVisible = false;
          toast.success("Produtos atualizados com sucesso!");
        } else {
          throw new Error("Erro ao atualizar produtos");
        }
      } catch (error) {
        console.error("Erro ao atualizar produtos:", error);
        toast.error("Erro ao atualizar produtos");
      }
    },
    async handleImagesUploaded(images) {
      try {
        const response = await axiosInstance.put(`http://localhost:8080/api/banners/${this.selectedBannerId}/image`, images[0]);
        this.fetchProdutos();
        this.showImageUploadModal = false;
      } catch (error) {
        console.error("Erro ao fazer upload da imagem:", error);
      }
    },
    async deleteBannerImage(bannerId) {
      try {
        await axiosInstance.delete(`http://localhost:8080/api/banners/${bannerId}/image`);
        this.fetchProdutos();
      } catch (error) {
        console.error("Erro ao deletar a imagem:", error);
      }
    },
    handleImagesUploaded(images) {
      console.log('Imagens carregadas:', images);
    },
  },
  mounted() {
    this.fetchProdutos(); // Chama a função uma vez ao montar o componente
    this.fetchFavoriteCounts();
  },
};
</script>

<style scoped>
  .centered-section {
    margin-top: 6rem;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    text-align: center;
    padding: 2rem;
  }

  .principal-section {
    background: white;
    height: fit-content;
    margin: 5rem;
    padding: 5rem;
    border-radius: 2rem;
    display: flex;
    gap: 2rem;
    justify-content: space-between;
  }

  h1 {
    font-size: 10rem;
    line-height: 0.8;
    color: #cdd5ff;
    letter-spacing: -0.05em; /* Ajuste este valor conforme necessário */
  }

.principal-section {
  background: white;
  height: fit-content;
  margin: 5rem;
  padding: 5rem;
  border-radius: 2rem;
  display: flex;
  gap: 2rem;
  justify-content: space-between;
  flex-wrap: wrap;
}

.left-column, .right-column {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

h1 {
  font-size: 10rem;
  line-height: 0.8;
  color: #cdd5ff;
  letter-spacing: -0.05em; /* Ajuste este valor conforme necessário */
}

strong {
  color: #e6eaff !important;
}

.product-list {
    background: #ffffff;
    border: solid 1px #d2d2d2;
    border-radius: 1.5rem;
    padding: 2rem;

}

  img {
    position: absolute;
    width: 100%;
    z-index: -1;
  }

  p {
    font-size: 2.5rem;
    margin-top: 1rem;
    background: transparent;
    width: 80%;
    backdrop-filter: blur(0);
    color: #16225e;
  }

h3 {
  width: 80%;
  font-size: 3rem;
  line-height: 1 !important;
  padding-bottom:2rem ;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.refresh-button {
  display: flex;
  align-items: center;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  border:solid 2px #dfdfdf !important;
  padding: 1rem;
  border-radius:1rem ;
}

.refresh-button:hover {
  background: #ebebeb;
}

.refresh-button i {
  margin-right: 0.5rem;
}

.favorites-section {
  margin-top: 2rem;
  padding: 2rem;
  background: #f9f9f9;
  border-radius: 1rem;
}

.bulk-edit-button {
  display: flex;
  align-items: center;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  border: solid 2px #dfdfdf !important;
  padding: 1rem;
  border-radius: 1rem;
  margin-left: 1rem;
}
.bulk-edit-button:hover {
  background: #ebebeb;
}
.bulk-edit-button i {
  margin-right: 0.5rem;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: block;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 200px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  border-radius: 1rem;
  padding: 1rem;
}

.loading-gif {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.image-upload-card {
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0f0f0;
  border: 2px dashed #ccc;
  border-radius: 1rem;
  padding: 2rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.image-upload-card:hover {
  background-color: #e0e0e0;
}

.image-upload-card p {
  font-size: 1.5rem;
  color: #333;
}
</style>
