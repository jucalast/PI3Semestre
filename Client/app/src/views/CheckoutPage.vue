<template>
  <AdminLayout>
    <div class="checkout-page flex flex-row h-full p-4">
      <!-- ESQUERDA -->
      <div class="left-section w-full flex flex-col space-y-4 h-full">
        <!-- HEADER -->
        <header class="checkout-header p-4">
          <img src="../assets/logo.png" alt="Logo">
        </header>
        <!-- Produtos e Endereço lado a lado -->
        <div class="product-and-address flex flex-row justify-between items-start space-x-4 equal-height">
          <!-- PRODUTOS -->
          <ProductSection :productDetails="productDetails" :productCount="productCount" class="equal-height" />
          <!-- ENDEREÇO -->
          <div class="section address equal-height">
            <GoogleMap ref="googleMap" @addressClicked="handleAddressClick" />
            <div class="elements-card">
              <h2>{{ clickedAddress || 'Nenhum endereço selecionado' }}</h2>
              <div>
                <button @click="addAddress" class="icon-button">
                  <font-awesome-icon icon="fa-solid fa-plus" />
                </button>
                <button @click="showModal = true" class="icon-button">
                  <font-awesome-icon icon="fa-solid fa-edit" />
                </button>
              </div>
            </div>
          </div>
        </div>
        <!-- Acordeão de Resumo dos Produtos e Lista de Endereços -->
        <div class="accordion-and-address-list flex flex-row justify-between items-start space-x-4">
          <!-- Acordeão de Resumo dos Produtos -->
          <div class="accordion w-full">
            <div class="accordion-header" @click="toggleAccordion">
              <span>Resumo dos produtos no carrinho</span>
              <font-awesome-icon :icon="accordionOpen ? 'fa-solid fa-chevron-up' : 'fa-solid fa-chevron-down'" />
            </div>
            <div v-if="accordionOpen" class="accordion-content">
              <div v-for="(product, index) in productDetails" :key="index" :class="['product-summary-card', getCardClass(index)]">
                <img :src="product.imagens[0]" :alt="product.nome" class="product-summary-image" />
                <div class="product-summary-info">
                  <h4>{{ product.nome }}</h4>
                  <p class="product-price">R$ {{ product.preco.toFixed(2) }}</p>
                </div>
              </div>
            </div>
          </div>
          <!-- Lista de Endereços -->
          <AddressList :addresses="allAddresses" :selectedAddressId="selectedAddressId" @update:selectedAddressId="updateSelectedAddressId" />
        </div>
        <!-- INFORMAÇÃO DE ENTREGA -->
        <div v-if="savedAddress" class="section delivery-info bg-gray-200 p-4 rounded-lg shadow-md">
          <p>Endereço de Entrega: {{ savedAddress }}</p>
          <br>
          <p>Seu pedido será entregue em {{ deliveryDate }}</p>
        </div>
      </div>
      <!-- DIREITA (Detalhes da Compra) -->
      <RightSection :cardDetails="cardDetails" @saveCardDetails="saveCardDetails" class="right-section" :allAddresses="allAddresses" />
      <AddressModal :isVisible="showModal" @close="showModal = false" @submit-address="handleManualAddress" />
    </div>
  </AdminLayout>
</template>

<script>

import cartaoTemplate from '@/components/CartaoTemplate.vue';
import GoogleMap from '@/components/GoogleMap.vue';
import AddressModal from '@/components/AddressModal.vue';
import AddressList from '@/components/AddressList.vue';
import axiosInstance from "@/utils/axiosInstance";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faPlus, faEdit, faCheck, faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons';
import AdminLayout from '@/layouts/AdminLayout.vue';
import RightSection from '@/components/RightSection.vue'; // Importar o novo componente
import ProductSection from '@/components/ProductSection.vue'; // Importar o novo componente

library.add(faPlus, faEdit, faCheck, faChevronDown, faChevronUp);

export default {
  components: {
    AdminLayout,
    cartaoTemplate,
    GoogleMap,
    AddressModal,
    AddressList,
    FontAwesomeIcon,
    RightSection,
    ProductSection // Registrar o novo componente
  },
  data() {
    return {
      currentDate: new Date(),
      clickedAddress: null,
      savedAddress: null,
      productIds: [],
      productDetails: [],
      productCount: 0,
      shippingPrice: 15.00,
      showModal: false,
      showCardInputs: false, // Controle de visibilidade dos inputs de cartão
      cardDetails: {
        number: '',
        name: '',
        expiry: ''
      },
      accordionOpen: false, // Controle de visibilidade do acordeão
      addresses: [], // Lista de endereços
      selectedAddressId: null, // ID do endereço selecionado
      allAddresses: [] // Lista de todos os endereços cadastrados
    };
  },
  created() {
    this.processQuery();
    this.fetchAddresses();
    this.fetchAllAddresses();
  },
  watch: {
    productId(newVal) {
      if (newVal) {
        this.fetchProductDetails();
      }
    }
  },
  methods: {
    handleCardDetails(card) {
      this.cardDetails = card; // Salva os dados do cartão
      this.showCardModal = false; // Fecha o modal
    },
    async processQuery() {
      const ids = this.$route.query.ids;
      if (ids) {
        this.productIds = ids.split(',');
        this.fetchProductDetails();
      } else {
        await this.fetchCartItems();
      }
    },
    async fetchCartItems() {
      try {
        const response = await axiosInstance.get('/api/carrinho/');
        this.productDetails = response.data;
        this.productCount = this.productDetails.length;
      } catch (error) {
        console.error('Erro ao buscar itens do carrinho:', error);
      }
    },
    async fetchProductDetails() {
      this.productDetails = await Promise.all(
          this.productIds.map(id =>
              axiosInstance.get(`/api/produtos/${id}`)
                  .then(response => {
                    return response.data;
                  })
                  .catch(error => {
                    console.error('Erro ao buscar detalhes do produto:', error);
                    return null;
                  })
          )
      );
      this.productDetails = this.productDetails.filter(detail => detail !== null);
      this.productCount = this.productDetails.length;
    },
    async fetchAddresses() {
      try {
        const response = await axiosInstance.get('/api/addresses/user');
        this.addresses = response.data;
      } catch (error) {
        if (error.response && error.response.status === 401) {
          console.error('Usuário não autenticado');
          this.$router.push('/login');
        } else {
          console.error('Erro ao buscar endereços:', error);
        }
      }
    },
    async fetchAllAddresses() {
      try {
        const response = await axiosInstance.get('/api/addresses/user');
        this.allAddresses = response.data;
      } catch (error) {
        if (error.response && error.response.status === 401) {
          console.error('Usuário não autenticado');
          this.$router.push('/login');
        } else {
          console.error('Erro ao buscar todos os endereços:', error);
        }
      }
    },
    handleAddressClick(address) {
      this.clickedAddress = address;
    },
    async addAddress() {
      if (this.clickedAddress) {
        try {
          const addressParts = this.clickedAddress.split(',');
          const street = addressParts[0] || 'Rua Desconhecida';
          const number = addressParts[1] ? addressParts[1].trim().split(' ')[0] : 'S/N';
          const neighborhood = addressParts[2] ? addressParts[2].trim() : 'Bairro Desconhecido';
          const city = addressParts[3] ? addressParts[3].trim() : 'Cidade Desconhecida';
          const state = addressParts[4] ? addressParts[4].trim() : 'Estado Desconhecido';
          const zipCode = addressParts[5] ? addressParts[5].trim() : '00000-000'; // Ajuste conforme necessário

          const response = await axiosInstance.post('/api/addresses/create', {
            street,
            number,
            neighborhood,
            city,
            state,
            zipCode
          }, {
            withCredentials: true // Certifique-se de enviar cookies de autenticação
          });
          this.savedAddress = response.data;
          this.clickedAddress = null; // Limpa o texto do endereço exibido
          this.fetchAddresses(); // Atualiza a lista de endereços
        } catch (error) {
          console.error('Erro ao adicionar endereço:', error);
        }
      }
    },
    async handleManualAddress(fullAddress) {
      this.clickedAddress = fullAddress;
      this.savedAddress = fullAddress;
      try {
        await this.$refs.googleMap.centerMapOnAddress(fullAddress);
      } catch (err) {
        console.error('Erro ao centralizar o mapa:', err);
      }
    },
    saveCardDetails() {
      this.showCardInputs = false; // Esconde os inputs após salvar
    },
    toggleAccordion() {
      this.accordionOpen = !this.accordionOpen;
    },
    getCardClass(index) {
      const classes = ['bg-blue', 'bg-yellow', 'bg-green', 'bg-pink'];
      return classes[index % classes.length];
    },
    updateSelectedAddressId(addressId) {
      this.selectedAddressId = addressId;
    }
  }
};
</script>

<style scoped>
/* Estilo para o container principal que segura as duas seções */
.checkout-page {
  display: flex;
  flex-direction: row;
  width: 100vw !important;
  height: 100vh !important;
  background: #ececec;
}

/* Estilo para o cabeçalho */
.checkout-header {
  background: transparent;
}

.checkout-header img {
  width: 5rem; /* Reduz o tamanho do logo */
  filter: invert(1);
}

/* Seção da esquerda */
.left-section {
  flex-grow: 1; /* Permite que a seção da esquerda cresça para preencher o espaço */
  flex-basis: 70%; /* Define a base inicial como 70% do espaço disponível */
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

/* Seção da direita */
.right-section {
  flex-basis: 30%; /* Define a base inicial como 30% do espaço disponível */
  height: 100%; /* Ocupa 100% da altura da tela */
}

.info-card {
  background: #ffffff;
  width: 100%;
  height: 80%;
  border-radius: 2rem;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding-top: 10rem;
}

.card-inputs {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;
  width: fit-content;
}

.header-info-card {
  display: flex;
  width: 100%;
}

.card-inputs input {
  color: #3a5bff;
  border: solid 1px #aeaeaeb6;
  background: #ededed;
  border-radius: 2rem;
  height: 5rem;
  padding-left: 2rem;
  font-size: 1.5rem;
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
}

.price-details p {
  margin: 20px 0;
}

/* Estilo para os subcontainers de produtos e endere��o dentro da seção de detalhes */
.products, .address {
  display: flex; /* Usa flexbox para melhor controle de layout */
  flex-direction: column; /* Organiza os conteúdos verticalmente */
  justify-content: space-between; /* Distribui o espaço igualmente entre os elementos internos */
  align-items: center; /* Centraliza os elementos horizontalmente */
  padding: 20px; /* Adiciona um pouco de espa��o interno para não tocar as bordas */
  flex-basis: 30%; /* Define a base inicial como 30% do espaço disponível */
  padding-top:0;
  display: flex;
  flex-direction: column !important;
  height: 100% !important;
  padding-bottom: 0;
}

.section {
  margin: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 2rem !important;
}

.space-x-4 {
  margin-left:0 !important;
}


.section-card {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  border-radius: 2rem !important;
  background: #00000035;
}

.card-container1 {
  display: flex;
  height: 40%;
  width: 100%;
  align-items: center;
  justify-content: center;
  position: absolute;
}

.product-checkout-image, .map-image {
  max-width: 100px; /* Define a largura máxima das imagens */
  height: auto; /* Mantém a proporção das imagens ajustando a altura automaticamente */
}

.add-address-btn {
  padding: 10px 20px;
  background-color: #4CAF50; /* Verde */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px; /* Espaço acima do botão */
  font-size: 1rem; /* Tamanho do texto */
}

.elements-card {
  background-color: #ffffff;
  border-radius:  0 0 2rem 2rem;
  display: flex;
  justify-content: space-around;
  margin: 0;
  font-size: 2rem;
  position: relative;
  z-index: 2;
  padding: 1rem;
  text-align: center;
  width: 100%; /* Ocupa a largura total do mapa */
  color: #1e1e1e;
}

.icon-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 2rem;
  color: #1a1a1a;
}

.card-inputs {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;
  width: fit-content;
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

.add-card-btn {
  background-color: #efefef;
  color: #1e1e1e;
  border-radius: 2rem;
  padding: 1rem; /* Tamanho reduzido */
  font-size: 1.5rem; /* Tamanho reduzido */
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: flex-start; /* Encosta o botão na esquerda */
}

.add-card-btn:hover {
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



.accordion {
  margin-right:2rem;
  background-color: #ffffff;
  border-radius: 2rem;
  margin-top: 1rem;
  padding: 2rem;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

}

.accordion-header {
  display: flex;
  justify-content: space-between; /* Ajuste para distribuir espaço entre os itens */
  align-items: center;
  font-size: 1.5rem;
  height: 4rem;
  cursor: pointer;
}

.accordion-content {
  margin-top: 1rem;
}

.product-summary-card {
  display: flex;
  align-items: center;
  justify-content: space-between; /* Adiciona espaço entre os elementos */
  margin-bottom: 1rem;
  padding: 1rem;
  border-radius: 1rem;
}

.product-summary-info {
  display: flex
;
    flex-direction: row;
    align-items: center;
    flex-grow: 1;
    justify-content: space-between;

}

.product-summary-info h4 {
  margin: 0;
  font-size: 2rem !important;  
  margin-right: 1rem; /* Adiciona espaço entre o nome e o preço */
}

.product-price {
  font-size: 2rem;

}

.bg-blue {
  background: #d8dfff;
  color: #5271ff;
}

.bg-yellow {
  background: #fff5da;
  color: #e4bf5f;
}

.bg-green {
  background: #ebffe6;
  color: #6ec757;
}

.bg-pink {
  background: #ffdeff;
  color: #ff96ff;
}

.product-summary-image {
  width: 30px; /* Reduz a largura da imagem */
  height: auto;
  margin-right: 1rem;
}

.accordion-and-address-list {
  margin-top: 1rem;
}

.accordion, .address-list {
  width: 50%; /* Cada componente ocupa 50% do espaço disponível */
}

.accordion-header, .address-list .accordion-header {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  height: 4rem;
  cursor: pointer;
}

.accordion-header span, .address-list .accordion-header h3 {
  margin-right: 1rem;
}

.accordion-content {
  margin-top: 1rem;
}

.address-list {
  margin-left: 2rem;
  background-color: #ffffff; /* Background para a div pai de endereço */
  padding: 1rem;

  padding: 2rem;
}

.address-option {
  margin-bottom: 1rem;
  background-color: #efefef; /* Background para cada card de endereço */
  padding: 1rem;
  border-radius: 1rem;
  opacity: 1; /* Opacidade padrão */
  transition: opacity 0.3s; /* Transição suave para a opacidade */
}

.address-option:not(.selected) {
  opacity: 0.5; /* Opacidade reduzida para cards não selecionados */
}

.address-label {
  display: flex;
  justify-content: space-between; /* Ajuste para distribuir espaço entre os itens */
  align-items: center;
  width: 100%;
  height: 5rem;
  padding: 1rem;
  margin-bottom: 1rem;
  font-size: 2rem;
}

.address-label span.zip-code {
  color: #5271ff; /* Azul padrão */
  margin-left: 1rem; /* Espaço entre o nome da rua e o CEP */
}

.address-label input {
  margin-right: 1rem;
  transform: scale(2); /* Aumenta o tamanho do botão de rádio */
  accent-color: black; /* Altera a cor do botão de rádio para preto */
}
</style>
