<template>
  <AdminLayout>
    <div class="checkout-page flex flex-row h-full p-4">
      <!-- ESQUERDA -->
      <div class="left-section w-full md:w-2/3 flex flex-col space-y-4 h-full">
        <!-- Produtos e Endereço lado a lado -->
        <div class="product-and-address flex flex-row justify-between items-start space-x-4">
          <!-- PRODUTOS -->
          <div class="section products bg-gray-200 flex-grow p-4 rounded-lg shadow-md">
            <div class="product-info">
              <p>{{ productCount }} Produtos na Sacola</p>
              <div v-for="(product, index) in productDetails" :key="index" class="product-display">
                <img :src="product.imagem" :alt="product.nome" class="product-checkout-image" />
              </div>
            </div>
          </div>
          <!-- ENDEREÇO -->
          <div class="section address flex-grow p-4 rounded-lg">
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
        <!-- INFORMAÇÃO DE ENTREGA -->
        <div v-if="savedAddress" class="section delivery-info bg-gray-200 p-4 rounded-lg shadow-md">
          <p>Endereço de Entrega: {{ savedAddress }}</p>
          <br>
          <p>Seu pedido será entregue em {{ deliveryDate }}</p>
        </div>
      </div>
      <!-- DIREITA (Detalhes da Compra) -->
      <RightSection :cardDetails="cardDetails" @saveCardDetails="saveCardDetails" />
    </div>
    <AddressModal :isVisible="showModal" @close="showModal = false" @submit-address="handleManualAddress" />
  </AdminLayout>
</template>

<script>

import cartaoTemplate from '@/components/CartaoTemplate.vue';
import GoogleMap from '@/components/GoogleMap.vue';
import AddressModal from '@/components/AddressModal.vue';
import axiosInstance from "@/utils/axiosInstance";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faPlus, faEdit, faCheck } from '@fortawesome/free-solid-svg-icons';
import AdminLayout from '@/layouts/AdminLayout.vue';
import RightSection from '@/components/RightSection.vue'; // Importar o novo componente

library.add(faPlus, faEdit, faCheck);

export default {
  components: {
    AdminLayout,
    cartaoTemplate,
    GoogleMap,
    AddressModal,
    FontAwesomeIcon,
    RightSection
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
      }
    };
  },
  computed: {
    deliveryDate() {
      const resultDate = new Date(this.currentDate);
      resultDate.setDate(resultDate.getDate() + 14); // Adiciona duas semanas
      return resultDate.toLocaleDateString();
    },
    totalProductPrice() {
      // Calcula a soma dos preços dos produtos
      return this.productDetails.reduce((sum, product) => sum + product.preco, 0).toFixed(2);
    },
    totalPrice() {
      return (parseFloat(this.totalProductPrice) + this.shippingPrice).toFixed(2);
    },
    isCardFilled() {
      return this.cardDetails.number && this.cardDetails.name && this.cardDetails.expiry;
    }
  },
  created() {
    this.processQuery();
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
    processQuery() {
      const ids = this.$route.query.ids;
      if (ids) {
        this.productIds = ids.split(',');
        this.fetchProductDetails();
      } else {
        console.log("Nenhum ID de produto foi fornecido para a página de checkout.");
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
    handleAddressClick(address) {
      this.clickedAddress = address;
    },
    addAddress() {
      if (this.clickedAddress) {
        this.savedAddress = this.clickedAddress;
        this.clickedAddress = null; // Limpa o texto do endereço exibido
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


/* Seção da esquerda */
.left-section {
  flex-grow: 1; /* Permite que a seção da esquerda cresça para preencher o espaço */
  flex-basis: 70%; /* Define a base inicial como 60% do espaço disponível */
  display: flex;
  flex-direction: row;
  gap: 1rem;
}

/* Seção da direita */
.right-section {
  flex-basis:35%; /* Define a base inicial como 40% do espaço disponível */
  
}

.info-card {
  background: #ffffff;
    width: 100%;
    height: 80%;
    border-radius: 2rem;
    padding: 2rem;
    display: flex
;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
    padding-top:
    10rem;
}


.card-inputs {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;
  width: fit-content;
}

 .header-info-card
{
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

/* Estilo para os subcontainers de produtos e endereço dentro da seção de detalhes */
.products, .address {
  display: flex; /* Usa flexbox para melhor controle de layout */
  flex-direction: column; /* Organiza os conteúdos verticalmente */
  justify-content: space-between; /* Distribui o espaço igualmente entre os elementos internos */
  align-items: center; /* Centraliza os elementos horizontalmente */
  padding: 20px; /* Adiciona um pouco de espaço interno para não tocar as bordas */
  margin: 10px; /* Garante um pequeno espaço entre os modais */
  height: 100%; /* Faz cada seção usar todo o espaço vertical disponível */
  flex-basis: 30%; /* Define a base inicial como 30% do espaço disponível */
}

.section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 2rem !important;

}


.section-card {
  width: 100%;
  display: flex
;
    flex-direction: column;
    align-items: center;
    justify-content: flex-end;
    border-radius: 2rem !important;
    background: #00000035;
}

.products{
  flex-direction: row;
  height: 440px;

}

/* Detalhes específicos para melhorar a visualização dos elementos dentro de cada modal */
.product-info, .address-info {
  width: 100%; /* Usa toda a largura disponível dentro do modal */
  text-align: center; /* Centraliza o texto */
  margin-bottom: 10px; /* Espaço antes dos elementos abaixo, se houver */
}

.card-container1 {
  display: flex;
    height: 40%;
    width: 100%;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 0;
}
.product-display, .map-display {
  width: 100%; /* Ocupa toda a largura do modal */
  overflow: hidden; /* Esconde qualquer conteúdo que exceda o tamanho do modal */
}

.product-checkout-image, .map-image {
  max-width: 20%; /* As imagens não ultrapassam a largura do modal */
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
  background-color: #57575773;
  backdrop-filter: blur(5px);
  border-radius: 2.5rem;
  display: flex;
  justify-content: space-around;
  margin: 0;
  font-size: 2rem;
  position: relative;
  bottom: 8rem;
  z-index: 2;
  padding: 1rem;
  text-align: center;
  width: 100%; /* Ocupa a largura total do mapa */
}



.icon-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 2rem;
  color: #ffffff;
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
    display: flex
;
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
