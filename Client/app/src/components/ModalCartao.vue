<template>
  <div v-if="isVisible" class="modal">
    <div class="modal-content">
      <span @click="closeModal" class="close">&times;</span>
      <h2>Adicionar Cartão</h2>
      <form @submit.prevent="submitCardDetails">
        <input v-mask="'#### #### #### ####'" v-model="card.number" placeholder="Número do Cartão" required />
        <input v-model="card.name" placeholder="Nome no Cartão" required />
        <input v-mask="'##/##'" v-model="card.expiry" placeholder="Data de Validade (MM/AA)" required />
        <button type="submit" class="submit-btn">Salvar Cartão</button>
      </form>
    </div>
  </div>
</template>

<script>
import { mask } from 'vue-the-mask'

export default {
  name: "ModalCartao",
  directives: {
    mask
  },
  props: {
    isVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      card: {
        number: '',
        name: '',
        expiry: ''
      }
    };
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    submitCardDetails() {
      this.$emit('submitCard', { ...this.card });
      this.card = { number: '', name: '', expiry: '' }; // Limpa o formulário
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
.submit-btn {
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
