<template>
  <div class="modal-overlay" @click="handleOverlayClick">
    <div class="modal-content" @click.stop>
      <div class="botoes">
        <button
          @click="selectForm('cafeEspecial')"
          :class="{ 'active-link': selectedForm === 'cafeEspecial' }"
        >
          <img
            class="imgprod"
            src="@/assets/icons8-coffee-beans-90(1).png"
            alt=""
          />Café Especial
        </button>
        <button
          @click="selectForm('metodoPreparo')"
          :class="{ 'active-link': selectedForm === 'metodoPreparo' }"
        >
          <img src="@/assets/icons8-v60-coffee-dripper-100 (1).png" alt="" />
          Método de Preparo
        </button>
      </div>

      <SpecialCoffeeForm
        v-if="selectedForm === 'cafeEspecial'"
        @submit-product="submitProduct"
        :disabled="isSubmitting"
      />
      <PreparationMethodForm
        v-else-if="selectedForm === 'metodoPreparo'"
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
import { useToast } from "vue-toastification";
import axiosInstance from "../utils/axiosInstance";

export default {
  name: "CreateProductModal",
  components: {
    SpecialCoffeeForm,
    PreparationMethodForm,
  },
  data() {
    return {
      selectedForm: null,
      isSubmitting: false,
    };
  },
  methods: {
    handleOverlayClick() {
      this.$emit("close");
    },
    selectForm(formType) {
      this.selectedForm = formType;
    },
    async submitProduct(product) {
      const toast = useToast();
      if (this.isSubmitting) return;

      this.isSubmitting = true;
      try {
        const response = await axiosInstance.post(
          "/api/produtos/protected/create",
          product,
          {
            timeout: 60000 // Aumenta o tempo limite para 60 segundos
          }
        );

        if (response.status === 200) {
          console.log("Produto criado:", response.data);
          toast.success("Produto criado com sucesso!");
          this.$emit("product-created", response.data); // Emite o evento com o novo produto
          this.$emit("close");
        } else {
          throw new Error("Erro ao criar o produto");
        }
      } catch (error) {
        console.error("Erro ao criar o produto:", error);
        toast.error("Erro ao criar o produto");
      } finally {
        this.isSubmitting = false;
      }
    },
    handleImageUpload(newImages) {
      this.product.imagens = [...this.product.imagens, ...newImages];
    },
  },
};
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
  width: 2rem; /* Ajuste o tamanho do GIF conforme necessário */
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

img {
  width: 3rem;
}

.botoes {
  display: flex;

  gap: 2rem;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 2rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 80%;
  height: 80%;
}

button {
  display: flex;
  gap: 1rem;
  padding: 1.5rem;
  border-radius: 2.5rem;
  font-size: 2rem;
}
button:hover {
  background: rgb(233, 233, 233);
}
</style>