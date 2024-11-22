<template>
  <DefaultLayout>
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
          <div class="section address bg-gray-200 flex-grow p-4 rounded-lg shadow-md">
            <GoogleMap ref="googleMap" @addressClicked="handleAddressClick" />
            <p v-if="clickedAddress">{{ clickedAddress }}</p>
            <button @click="addAddress" class="add-address-btn">Adicionar Endereço</button>
            <button @click="showModal = true" class="add-address-btn">Digitar Endereço</button>
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
      <div class="right-section w-full md:w-1/3 pl-4">
        <div class="section bg-gray-400 h-full p-4 rounded-lg shadow-md">
          <cartaoTemplate :cardDetails="cardDetails" />
          <button @click="showCardModal = true" class="add-card-btn">Adicionar Cartão</button>
          <div class="price-details">
            <p>Preço dos Produtos: R$ {{ totalProductPrice }}</p>
            <p>Preço do Frete: R$ {{ shippingPrice.toFixed(2) }}</p>
            <button class="total-price">Total: R$ {{ totalPrice }}</button>
          </div>
        </div>

        <ModalCartao
            :isVisible="showCardModal"
            @close="showCardModal = false"
            @submitCard="handleCardDetails"
        />
      </div>
    </div>
    <AddressModal :isVisible="showModal" @close="showModal = false" @submit-address="handleManualAddress" />
  </DefaultLayout>
</template>

<script>
import DefaultLayout from '@/layouts/DefaultLayout.vue';
import cartaoTemplate from '@/components/cartaoTemplate.vue';
import GoogleMap from '@/components/GoogleMap.vue';
import AddressModal from '@/components/AddressModal.vue';
import axiosInstance from "@/utils/axiosInstance";
import ModalCartao from '@/components/ModalCartao.vue';


export default {
  components: {
    DefaultLayout,
    cartaoTemplate,
    GoogleMap,
    AddressModal,
    ModalCartao
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
      showCardModal: false, // Controle de visibilidade do modal de cartão
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
      this.savedAddress = fullAddress;
      try {
        await this.$refs.googleMap.centerMapOnAddress(fullAddress);
      } catch (err) {
        console.error('Erro ao centralizar o mapa:', err);
      }
    }


  }
};
</script>

<style scoped>
/* Estilo para o container principal que segura as duas seções */
.checkout-page {
  display: flex;
  flex-direction: row;
  height: calc(100vh - 100px);
}

/* Seção da esquerda */
.left-section {
  flex-grow: 1; /* Permite que a seção da esquerda cresça para preencher o espaço */
  flex-basis: 65%; /* Define a base inicial como 65% do espaço disponível */
}

/* Seção da direita */
.right-section {
  flex-basis: 35%; /* Define a base inicial como 35% do espaço disponível */
  width: 35%; /* Definindo explicitamente a largura como 35% */
}

/* Estilo para os subcontainers de produtos e endereço dentro da seção de detalhes */
.products, .address {
  flex: 1;  /* Atribui a mesma proporção de crescimento para ambos */
  display: flex; /* Usa flexbox para melhor controle de layout */
  flex-direction: column; /* Organiza os conteúdos verticalmente */
  justify-content: space-between; /* Distribui o espaço igualmente entre os elementos internos */
  align-items: center; /* Centraliza os elementos horizontalmente */
  padding: 20px; /* Adiciona um pouco de espaço interno para não tocar as bordas */
  margin: 10px; /* Garante um pequeno espaço entre os modais */
  height: 100%; /* Faz cada seção usar todo o espaço vertical disponível */
}

.section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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

.price-details{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
}

.price-details p{
  margin: 20px 0;
}


.total-price{
  padding: 10px 20px;
  background-color: #4CAF50; /* Verde */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px; /* Espaço acima do botão */
  font-size: 1rem; /* Tamanho do texto */
}

.add-card-btn {
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
