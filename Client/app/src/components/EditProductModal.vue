<template>
  <div class="modal-overlay" @click="handleOverlayClick">
    <div class="modal-content" @click.stop>
      <SpecialCoffeeForm
        v-if="selectedForm === 'cafeEspecial'"
        :product="product"
        :isEditMode="true"
        @submit-product="submitProduct"
        :disabled="isSubmitting"
      />
      <PreparationMethodForm
        v-else-if="selectedForm === 'metodoPreparo'"
        :product="product"
        :isEditMode="true"
        @submit-product="submitProduct"
        :disabled="isSubmitting"
      />
      <GenericProductForm
        v-else
        :product="product"
        :isEditMode="true"
        @submit-product="submitProduct"
        :disabled="isSubmitting"
      />

      <!-- Overlay de carregamento com GIF -->
      <div v-if="isSubmitting" class="loading-overlay">
        <img
          src="@/assets/Logo Maven.gif"
          alt="Carregando"
          class="loading-gif"
        />
      </div>
    </div>
  </div>
</template>

<script>
import SpecialCoffeeForm from "./SpecialCoffeeForm.vue";
import PreparationMethodForm from "./PreparationMethodForm.vue";
import GenericProductForm from "./GenericProductForm.vue";
import { useToast } from "vue-toastification";
import axiosInstance from "../utils/axiosInstance";

export default {
  name: "EditProductModal",
  components: {
    SpecialCoffeeForm,
    PreparationMethodForm,
    GenericProductForm,
  },
  props: {
    product: Object,
    isVisible: Boolean,
  },
  data() {
    return {
      selectedForm: null,
      isSubmitting: false,
    };
  },
  watch: {
    product: {
      immediate: true,
      handler(newVal) {
        if (newVal.cafeEspecial) {
          this.selectedForm = 'cafeEspecial';
        } else if (newVal.metodoPreparo) {
          this.selectedForm = 'metodoPreparo';
        } else {
          this.selectedForm = 'generic';
        }
      }
    }
  },
  methods: {
    handleOverlayClick() {
      this.$emit("close");
    },
    async submitProduct(product) {
      if (this.isSubmitting) return;

      this.isSubmitting = true;
      try {
        const response = await axiosInstance.put(
          `/api/produtos/protected/update/${product.id}`,
          product,
          {}
        );

        if (response.status === 200) {
          console.log("Produto atualizado:", response.data);
          this.$emit("save", response.data); // Emite o evento com o produto atualizado
          this.$emit("close");
        } else {
          throw new Error("Erro ao atualizar o produto");
        }
      } catch (error) {
        console.error("Erro ao atualizar o produto:", error);
      } finally {
        this.isSubmitting = false;
      }
    },
  }
}
</script>

<style scoped>
.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5);
}

.loading-gif {
  width: 10rem; /* Ajuste o tamanho do GIF conforme necessário */
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 400;
  backdrop-filter: blur(10px);
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 80%;
  height: 80%;
}
</style>
