<template>
  <div v-if="isVisible" class="modal-backdrop">
    <div class="modal-content">
      <button class="close-btn" @click="$emit('close')">X</button>
      <h2>Editar Produto</h2>
      <div class="form-container">
        <form @submit.prevent="handleSubmit">
          <div v-for="(value, key) in formData" :key="key" class="form-group">
            <label :for="key">{{ formatLabel(key) }}:</label>
            <input
              v-model="formData[key]"
              :type="key === 'preco' ? 'text' : typeof value === 'number' ? 'number' : 'text'"
              :id="key"
              @input="key === 'preco' ? formatPriceInput($event) : null"
              required
            />
          </div>

          <button type="submit">Salvar Alterações</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    product: {
      type: Object,
      default: null,
    },
    isVisible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      formData: {},
    };
  },
  watch: {
    product: {
      handler(newProduct) {
        if (newProduct) {
          this.formData = {
            ...newProduct,
            ...(newProduct.cafeEspecial || {}),
            ...(newProduct.metodoPreparo || {}),
          };
        }
      },
      immediate: true,
      deep: true,
    },
  },
  created() {
    if (this.product) {
      this.formData = {
        ...this.product,
        ...(this.product.cafeEspecial || {}),
        ...(this.product.metodoPreparo || {}),
      };
    }
  },
  methods: {
    formatLabel(key) {
      return key.charAt(0).toUpperCase() + key.slice(1);
    },
    formatPriceInput(event) {
      const value = event.target.value;
      this.formData.preco = value.replace(/[^0-9.,]/g, "").replace(",", ".");
    },
    handleSubmit() {
      this.$emit("save", this.formData);
    },
  },
};
</script>


<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(10px);
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  height: 80%;
  z-index: 2;
}

h2 {
  margin: 0 !important;
  top: 0;
  background: transparent;
  font-size: 3rem;
  color: #484848;
  justify-content: flex-start;
}

label {
  font-size: 1.5rem;
  color: #6d6d6d;
  margin-bottom: 0.5rem;
}

.form-container {
  gap: 1rem;
  display: flex;
  flex-direction: column;
  background: transparent;
  width: 100%;
  flex-wrap: wrap;
  align-items: flex-start;
  overflow-y: auto;
  max-height: 80%;
}

.form-group {
  display: flex;
  flex-direction: column;
  width: calc(32% - 10px); /* Duas colunas */
}

.form-container form {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  background: transparent;
  width: 100%;
}

.form-container input,
.form-container textarea {
  color: #ff4d4d;
  border: solid 1px #aeaeaeb6;
  background: #ededed;
  border-radius: 2rem;
  height: 5rem;
  padding-left: 2rem;
  font-size: 1.5rem;
  outline: none;
  font-family: 'Poppins', sans-serif;
}

.form-container textarea {
  min-height: 100px;
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
  background-color: #ff4d4d;
  color: white;
  border: none;
  border-radius: 2rem;
  padding: 1rem 2rem;
  font-size: 2rem;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 1rem;
}

.form-container button:hover {
  background-color: #e04e4e;
}

.close-btn {
  background: none;
  border: none;
  font-size: 18px;
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}
</style>
