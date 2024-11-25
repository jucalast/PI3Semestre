<template>
  <div v-if="isVisible" class="modal">
    <div class="modal-content">
      <span @click="closeModal" class="close">&times;</span>
      <h2>Digite seu Endereço</h2>
      <form @submit.prevent="submitAddress">
        <input v-model="address.cep" placeholder="CEP" required>
        <input v-model="address.rua" placeholder="Rua" required>
        <input v-model="address.numero" placeholder="Número" required>
        <input v-model="address.bairro" placeholder="Bairro" required>
        <input v-model="address.cidade" placeholder="Cidade" required>
        <input v-model="address.estado" placeholder="Estado" required>
        <select v-model="address.tipo" required>
          <option value="Casa">Casa</option>
          <option value="Trabalho">Trabalho</option>
          <option value="Outro">Outro</option>
        </select>
        <button type="submit" class="add-address-btn">Adicionar Endereço</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "AddressModal",
  props: {
    isVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      address: {
        cep: '',
        rua: '',
        numero: '',
        bairro: '',
        cidade: '',
        estado: '',
        tipo: 'Casa' // Tipo de endereço padrão
      }
    };
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    submitAddress() {
      const fullAddress = `${this.address.rua}, ${this.address.numero}, ${this.address.bairro}, ${this.address.cidade}, ${this.address.estado}, ${this.address.cep}, ${this.address.tipo}`;
      this.$emit('submit-address', fullAddress);
      this.closeModal();
    }
  }
};
</script>

<style scoped>
.modal {
  position: fixed;
  z-index: 100;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: white;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.add-address-btn {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  margin-top: 10px;
}

</style>
