<template>
  <div class="right-section w-full md:w-2/5">
    <div class="card-container1">
      <cartaoTemplate :cardDetails="localCardDetails" @updateColor="updateBoxShadowColor" />
    </div>
    <div class="info-card" :style="{ boxShadow: boxShadowStyle }">
      <div class="header-info-card">
        <button @click="showCardInputs && isCardFilled ? saveCard() : toggleCardInputs()" :class="['add-card-btn', { 'filled': showCardInputs && isCardFilled }]">
          <font-awesome-icon :icon="showCardInputs ? 'fa-solid fa-check' : 'fa-solid fa-plus'" /> {{ showCardInputs ? 'OK' : 'Add Cartão' }}
        </button>
        <button class="payment-btn">
          <font-awesome-icon icon="fa-solid fa-qrcode" /> Pix
        </button>
        <button class="payment-btn">
          <font-awesome-icon icon="fa-solid fa-file-invoice" /> Boleto
        </button>
      </div>
      <div v-if="showCardInputs" class="card-inputs">
        <div class="card-inputs-top">
          <input v-mask="'#### #### #### ####'" v-model="localCardDetails.number" placeholder="0000 0000 0000 0000" required class="card-number" />
          <input v-mask="'##/##'" v-model="localCardDetails.expiry" placeholder="00/00" required class="card-expiry" />
        </div>
        <input v-model="localCardDetails.name" placeholder="Nome no Titular" required class="card-name" />
        <div class="card-inputs-bottom">
          <input v-model="localCardDetails.cpf" placeholder="CPF do Titular" required class="card-cpf" />
          <input v-model="localCardDetails.bandeira" placeholder="Bandeira" required class="card-bandeira" />
        </div>
        <label class="save-card-checkbox">
          <input type="checkbox" v-model="saveForNextPurchase" /> Salvar para a próxima compra
        </label>
      </div>
      <div class="card-summary">
        <div class="card-list">
          <div class="card-option-container fixed-card" @click="selectCard(filteredUserCards[0]?.number)">
            <label class="card-option">
              <input class="radio-option" type="radio" name="card" v-model="selectedCard" :value="filteredUserCards[0]?.number" @change="moveCardToTop(filteredUserCards[0]?.number)" />
              <span> {{ filteredUserCards[0]?.number.slice(-4) }}</span>
              <div v-if="selectedCard === filteredUserCards[0]?.number" class="toggle-container">
                <label class="switch">
                  <input type="checkbox" v-model="isCredit" />
                  <span class="slider round"></span>
                </label>
                <span>{{ isCredit ? 'Crédito' : 'Débito' }}</span>
              </div>
            </label>
            <font-awesome-icon class="accordion-toggle" :icon="showCardList ? 'fa-chevron-up' : 'fa-chevron-down'" @click="toggleCardAccordion" />
          </div>
          <div v-if="showCardList" class="scrollable-card-list">
            <label v-for="card in filteredUserCards.slice(1)" :key="card.number" class="card-option" @click="selectCard(card.number)">
              <input class="radio-option" type="radio" name="card" v-model="selectedCard" :value="card.number" @change="moveCardToTop(card.number)" />
              <span> {{ card.number.slice(-4) }}</span>
              <div v-if="selectedCard === card.number" class="toggle-container">
                <label class="switch">
                  <input type="checkbox" v-model="isCredit" />
                  <span class="slider round"></span>
                </label>
                <span>{{ isCredit ? 'Crédito' : 'Débito' }}</span>
              </div>
            </label>
          </div>
        </div>
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
import { faPlus, faCheck, faQrcode, faFileInvoice, faCreditCard, faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons';
import axiosInstance from '../utils/axiosInstance'; // Importe o axiosInstance

library.add(faPlus, faCheck, faQrcode, faFileInvoice, faCreditCard, faChevronDown, faChevronUp);

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
      showCardList: false,
      boxShadowColor: 'rgba(128, 128, 128, 0.5)', // Cor padrão inicial
      selectedAddressId: null, // ID do endereço selecionado
      localCardDetails: { ...this.cardDetails }, // Copia os detalhes do cartão para evitar conflito
      saveForNextPurchase: true, // Valor padrão true
      userCards: [], // Lista de cartões do usuário
      selectedCard: null, // Cartão selecionado
      paymentType: 'Crédito', // Tipo de pagamento selecionado
      isCredit: true // Tipo de pagamento selecionado (true para crédito, false para débito)
    };
  },
  computed: {
    isCardFilled() {
      return this.localCardDetails.number && this.localCardDetails.name && this.localCardDetails.expiry;
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
    },
    filteredUserCards() {
      return this.userCards.filter(card => card.numeroCartao !== this.localCardDetails.number.replace(/\s+/g, ''));
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
    toggleCardList() {
      this.showCardList = !this.showCardList;
    },
    toggleCardAccordion() {
      this.showCardList = !this.showCardList;
    },
    updateBoxShadowColor(color) {
      if (color) {
        const primaryColor = color.match(/#([0-9a-f]{6})/i)[0]; // Extrai a primeira cor do gradiente
        this.boxShadowColor = primaryColor;
      }
    },
    finalizePurchase() {
      this.$emit('finalizePurchase');
    },
    async saveCard() {
      try {
        const cardDetails = { ...this.localCardDetails, number: this.localCardDetails.number.replace(/\s+/g, '') };
        const response = await axiosInstance.post('/api/cartoes/salvar-cartao', cardDetails, {
          withCredentials: true // Certifique-se de enviar cookies de autenticação
        });
        alert('Cartão salvo com sucesso!');
        this.showCardInputs = false; // Fecha o formulário de cartão
        this.fetchUserCards(); // Atualiza a lista de cartões do usuário
      } catch (error) {
        alert('Erro ao salvar o cartão.');
      }
      this.finalizePurchase();
    },
    async fetchUserCards() {
      try {
        const response = await axiosInstance.get('/api/cartoes/meus-cartoes', {
          withCredentials: true // Certifique-se de enviar cookies de autenticação
        });
        this.userCards = response.data;
        console.log('Cartões recebidos:', this.userCards); // Verifique os dados recebidos
        console.log('Números dos cartões:', this.userCards.map(card => card.number)); // Use 'number' para corresponder ao JSON
      } catch (error) {
        console.error('Erro ao buscar cartões do usuário:', error);
      }
    },
    moveCardToTop(cardNumber) {
      const index = this.userCards.findIndex(card => card.number === cardNumber);
      if (index > 0) {
        const [selectedCard] = this.userCards.splice(index, 1);
        this.userCards.unshift(selectedCard);
      }
    },
    selectCard(cardNumber) {
      this.selectedCard = cardNumber;
      this.moveCardToTop(cardNumber);
    }
  },
  created() {
    this.fetchUserCards(); // Busca os cartões do usuário ao criar o componente
  }
};
</script>

<style scoped>
.right-section {
  display: flex;
  background: #ffffff;
  border-radius: 2rem;
  width: 35%;
  justify-content: flex-end;
  flex-direction: column;
  padding: 2rem;
  height: 100%;
  position: relative; /* Adiciona posição relativa */
}

@media (max-width: 768px) {
  .right-section {
    flex-basis: 100%;
    width: 100%;
    padding: 1rem;
  }
}

.card-container1 {
position:absolute;
  height:30%;
  width: 80%;
  top:19%;
  left:10%;
  transform: translateY(-50%); /* Centraliza verticalmente */
  z-index: 2; /* Garante que o cartão fique na frente */
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
  padding-top: 12rem;
  position: relative; /* Adiciona posição relativa */
  z-index: 1; /* Garante que o info-card fique atrás do cartão */
}

@media (max-width: 768px) {
  .info-card {
    padding-top: 5rem;
  }
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

.card-inputs-bottom {
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

.card-cpf {
  width: 70%; /* Define a largura do input de CPF como 70% */
}

.card-bandeira {
  width: 30%; /* Define a largura do input de bandeira como 30% */
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
  width: 100%;
}

.card-list {
  width: 100%;
}

.fixed-card {
  position: sticky;
  top: 0;
  background: #efefef;
  z-index: 1;
}

.scrollable-card-list {
  max-height: 10rem;
  overflow-y: auto;
  border-radius: 2rem;
  padding: 1rem;
}

.card-option-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 2rem;
}

.card-option {
  display: flex
;
    align-items: center;
    width: 100%;
    height: 5rem;
    padding: 1rem;
    background: #efefef;
    border-radius: 1rem;
    font-size: 2rem;
    margin-bottom: 1rem;
    justify-content: flex-start;
    flex-direction: row;
    gap:2rem;
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
  position: absolute; /* Adiciona posição absoluta */
  bottom: 2rem; /* Posiciona na parte inferior */
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

.save-card-checkbox {
  display: flex;
  align-items: center;
  margin-top: 10px;
  font-size: 1.3rem;
}

.save-card-checkbox input {
  margin-right: 10px;
  box-shadow: none; /* Remove o box shadow */
}

.icon-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.5rem;
  color: #1a1a1a;
}



.accordion-toggle {
  cursor: pointer;
  font-size: 1.5rem;
  margin-left: 1rem;
}

.toggle-container {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-left: 1rem;
}

.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.4s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:checked + .slider:before {
  transform: translateX(26px);
}
</style>