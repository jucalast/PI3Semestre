<template>
  <div class="right-section w-full md:w-2/5">
    <div class="card-container1">
      <cartaoTemplate :cardDetails="cardDetails" @updateColor="updateBoxShadowColor" />
    </div>
    <div class="info-card" :style="{ boxShadow: boxShadowStyle }">
      <div class="header-info-card">
        <button @click="showCardInputs = true" class="add-card-btn">
          <font-awesome-icon icon="fa-solid fa-plus" /> Add
        </button>
        <button v-if="showCardInputs" @click="saveCardDetails" :class="['save-card-btn', { 'filled': isCardFilled }]">
          <font-awesome-icon icon="fa-solid fa-check" />
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
        <input v-mask="'#### #### #### ####'" v-model="cardDetails.number" placeholder="Número do Cartão" required />
        <input v-model="cardDetails.name" placeholder="Nome no Cartão" required />
        <input v-mask="'##/##'" v-model="cardDetails.expiry" placeholder="Data de Validade (MM/AA)" required />
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
      boxShadowColor: 'rgba(128, 128, 128, 0.5)' // Cor padrão inicial
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
    saveCardDetails() {
      this.showCardInputs = false;
      this.$emit('saveCardDetails');
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
}

.card-container1 {
  width: 100% !important;
  position: absolute;
  width: 40%;
  height: 25% !important;
  top: 4%;
  right: -10%;
}

.info-card {
  background: #ffffff;
  background: #ffffff;
    width: 100%;
    height: 80%;
    border-radius: 2rem;
    padding: 2rem;
    display: flex
;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    padding-top: 8rem;
}

.card-inputs {
    display: flex
;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 10px;
    width: -moz-fit-content;
    width: fit-content;
    background: #e8e8e8;
    padding: 3rem 2rem;
    border-radius: 2rem;
    align-items: center;
    justify-content: flex-start;

}

.header-info-card {
  display: flex;
  width: 100%;
  gap: 10px; /* Adiciona espaço entre os botões */
}

.card-inputs input {
    color: #3a5bff;
    border: solid 1px #aeaeaeb6;
    background: #ededed;
    border-radius: 2rem;
    height: 4rem;
    padding-left: 1rem;
    font-size: 1.3rem;
    outline: none;
    font-family: "Poppins", sans-serif;
    width: 45%;
}

.card-inputs input::placeholder {
  color: #6d6d6d;
}

.card-inputs input:focus {
  outline: none;
  border: 1px solid #3a5bff;
  box-shadow: 1px 0px 20px 1px #3a5bff3b;
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
  padding: 1.5rem 1.6rem; /* Tamanho reduzido */
  font-size: 1.3rem; /* Tamanho reduzido */
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: flex-start; /* Encosta o botão na esquerda */
}

.add-card-btn:hover, .payment-btn:hover {
  background-color: #f4f4f4;
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