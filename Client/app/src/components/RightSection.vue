<template>
  <div class="right-section w-full md:w-2/5">
    <div class="card-container1">
      <cartaoTemplate :cardDetails="cardDetails" @updateColor="updateBoxShadowColor" />
    </div>
    <div class="info-card" :style="{ boxShadow: boxShadowStyle }">
      <div class="header-info-card">
        <button @click="toggleCardInputs" :class="['add-card-btn', { 'filled': showCardInputs && isCardFilled }]">
          <font-awesome-icon :icon="showCardInputs ? 'fa-solid fa-check' : 'fa-solid fa-plus'" /> {{ showCardInputs ? 'OK' : 'Add' }}
        </button>
        <button class="payment-btn">
          <font-awesome-icon icon="fa-solid fa-qrcode" /> Pix
        </button>
        <button class="payment-btn">
          <font-awesome-icon icon="fa-solid fa-file-invoice" /> Boleto
        </button>
        <button class="payment-btn">
          <font-awesome-icon icon="fa-solid fa-credit-card" /> Débito
        </button>
      </div>
      <div v-if="showCardInputs" class="card-inputs">
        <div class="card-inputs-top">
          <input v-mask="'#### #### #### ####'" v-model="cardDetails.number" placeholder="0000 0000 0000 0000" required class="card-number" />
          <input v-mask="'##/##'" v-model="cardDetails.expiry" placeholder="00/00" required class="card-expiry" />
        </div>
        <input v-model="cardDetails.name" placeholder="Nome no Titular" required class="card-name" />
      </div>
      <div v-if="isCardFilled && !showCardInputs" class="card-summary">
        <label class="card-option">
          <input class="radio-option" type="radio" name="card" />
          <span>Final {{ cardDetails.number.slice(-4) }}</span>
          <span class="card-type">Crédito</span>
        </label>
      </div>
      <div class="price-details">
        <div class="price-item">
          <span>Preço dos Produtos:</span>
          <span>R$ {{ totalProductPrice }}</span>
        </div>
        <div class="price-item">
          <span>Preço do Frete:</span>
          <span>R$ {{ formattedShippingPrice }}</span>
        </div>
        <button class="total-price">Total: R$ {{ totalPrice }}</button>
      </div>
    </div>
  </div>
</template>

<script>
import cartaoTemplate from '@/components/CartaoTemplate.vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faPlus, faCheck, faQrcode, faFileInvoice, faCreditCard } from '@fortawesome/free-solid-svg-icons';

library.add(faPlus, faCheck, faQrcode, faFileInvoice, faCreditCard);

export default {
  components: {
    cartaoTemplate,
    FontAwesomeIcon
  },
  props: {
    cardDetails: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      showCardInputs: false,
      boxShadowColor: 'rgba(128, 128, 128, 0.5)', // Cor padrão inicial
      selectedAddressId: null // ID do endereço selecionado
    };
  },
  computed: {
    isCardFilled() {
      return this.cardDetails.number && this.cardDetails.name && this.cardDetails.expiry;
    },
    totalProductPrice() {
      // Calcula a soma dos preços dos produtos
      return this.$parent.totalProductPrice ? this.$parent.totalProductPrice : '0.00';
    },
    formattedShippingPrice() {
      return this.$parent.shippingPrice ? this.$parent.shippingPrice.toFixed(2) : '0.00';
    },
    totalPrice() {
      return this.$parent.totalPrice ? this.$parent.totalPrice : '0.00';
    },
    boxShadowStyle() {
      return `0px -200px 150px -90px ${this.boxShadowColor}`;
    }
  },
  methods: {
    toggleCardInputs() {
      if (this.showCardInputs && this.isCardFilled) {
        this.showCardInputs = false;
      } else {
        this.showCardInputs = true;
      }
    },
    updateBoxShadowColor(color) {
      const primaryColor = color.match(/#([0-9a-f]{6})/i)[0]; // Extrai a primeira cor do gradiente
      this.boxShadowColor = primaryColor;
    }
  }
};
</script>

<style scoped>
.right-section {
  display: flex;
  background: #ffffff;
  border-radius: 2rem;
  flex-basis: 40%;
  width: 35%;
  justify-content: flex-end;
  flex-direction: column;
  padding: 2rem;
  height: 100%;
}

.card-container1 {
  width: 100% !important;
  position: absolute;
  width: 25%;
  height: 25% !important;
  top: 5%;
  right: 3%;
}

.info-card {
  background: #ffffff;
  width: 100%;
  height: 90%;
  border-radius: 2rem;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding-top: 10rem;
}

.card-inputs {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 1rem; /* Ajusta a margem superior para ficar logo abaixo do header-info-card */
  width: -moz-fit-content;
  width: fit-content;
  background: #e8e8e8;
  border-radius: 2rem;
  align-items: center;
  justify-content: flex-start;
  padding: 1rem;
}

.card-inputs-top {
  display: flex;
  gap: 10px;
  width: 100%;
}

.card-number {
  width: 80%; /* Define a largura do input de número do cartão como 80% */
}

.card-expiry {
  width: 20%; /* Define a largura do input de data como 20% */
}

.card-name {
  width: 100%; /* Define a largura do input de nome do titular como 100% */
}

.header-info-card {
  display: flex;
  justify-content: space-between; /* Ajuste para distribuir espaço entre os itens */
  width: 90%;
  gap: 10px; /* Adiciona espaço entre os botões */
}

.card-inputs input {
  color: #3a5bff;
  border: solid 1px #aeaeaeb6;
  background: #ffffff;
  border-radius: 1.5rem;
  height: 4rem;
  padding-left: 1rem;
  font-size: 1.3rem;
  outline: none;
  font-family: "Poppins", sans-serif;
}

.card-inputs input::placeholder {
  color: #6d6d6d;
}

.card-inputs input:focus {
  outline: none;
  border: 1px solid #3a5bff;
  box-shadow: 1px 0px 20px 1px #3a5bff3b;
}

.card-summary {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 1rem;
  width: 95%;
}

.card-option {
  display: flex;
  justify-content: space-between; /* Ajuste para distribuir espaço entre os itens */
  align-items: center;
  width: 100%;
  height: 5rem;
  padding: 1rem;
  background: #efefef;
  border-radius: 1rem;
  margin-bottom: 1rem;
  font-size: 2rem;
}

.radio-option {
  margin-left: 1rem;
  transform: scale(2); /* Aumenta o tamanho do botão de rádio */
  accent-color: black; /* Altera a cor do botão de rádio para preto */
}

.card-type {
  font-weight: bold;
}

.submit-btn, .total-price {
  background-color: #ff4d4d;
  color: white;
  border: none;
  border-radius: 2rem;
  padding: 1rem 2rem;
  font-size: 2rem;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 10px;
}

.submit-btn:hover, .total-price:hover {
  background-color: #e04343;
}

.price-details {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.price-details .price-item {
  display: flex;
  justify-content: space-between;
  width: 100%;
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.price-details .total-price {
  align-self: flex-end;
  font-size: 2rem;
  margin-top: auto;
}

.add-card-btn, .payment-btn {
  background-color: #efefef;
  color: #1e1e1e;
  border-radius: 1.5rem;
  padding: 1rem 1rem; /* Tamanho reduzido */
  font-size: 1.3rem; /* Tamanho reduzido */
  cursor: pointer;
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: flex-start; /* Encosta o botão na esquerda */
}

.add-card-btn:hover, .payment-btn:hover {
  background-color: #000000;
  color: #ffffff;
}

.add-card-btn.filled {
  background-color: #3a5bff; /* Azul padrão */
  color: white;
}

.add-card-btn:not(.filled):hover {
  background-color: #000000;
  color: #ffffff;
}

.save-card-btn {
  background-color: #ffffff;
  color: #1e1e1e;
  border-radius: 2rem;
  padding: 1rem;
  font-size: 1.5rem;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: center;
  width: 5rem;
}

.save-card-btn:hover {
  background-color: #f4f4f4;
}

.save-card-btn.filled {
  background-color: #3a5bff;
  color: white;
}
</style>