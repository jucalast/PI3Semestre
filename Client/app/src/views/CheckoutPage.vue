<template>
  <AdminLayout>
    <div class="checkout-page flex flex-row h-full p-4">
      <!-- ESQUERDA -->
      <div class="left-section w-full flex flex-col space-y-4 h-full">
        <!-- HEADER -->
        <header class="checkout-header p-4">
          <img src="../assets/logo.png" alt="Logo" />
          <h3 class="header-title">Quase tudo pronto!</h3>
        </header>
        <!-- Acordeão de Resumo dos Produtos -->
        <div class="accordion w-full">
          <div class="accordion-header" @click="toggleAccordion">
            <span>Resumo dos produtos no carrinho</span>
            <font-awesome-icon
              :icon="accordionOpen ? 'fa-solid fa-chevron-up' : 'fa-solid fa-chevron-down'"
            />
          </div>
          <div v-if="accordionOpen" class="accordion-content">
            <div
              v-for="(product, index) in productDetails"
              :key="index"
              :class="['product-summary-card', getCardClass(index)]"
            >
              <img
                :src="product.imagens ? product.imagens[0] : ''"
                :alt="product.nome"
                class="product-summary-image"
              />
              <div class="product-summary-info">
                <h4>{{ product.nome }}</h4>
                <p class="product-quantity">Quantidade: {{ getProductQuantity(product.id) }}</p>
                <p class="product-price">R$ {{ product.preco ? product.preco.toFixed(2) : '0.00' }}</p>
              </div>
            </div>
          </div>
        </div>
        <!-- Produtos e Endereço lado a lado -->
        <div class="product-and-address flex flex-row justify-between items-start equal-height">
          <!-- Lista de Endereços -->
          <AddressList
            :addresses="allAddresses"
            :selectedAddressId="selectedAddressId"
            @update:selectedAddressId="updateSelectedAddressId"
            @addressSelected="centerMapOnAddress"
          />
          <!-- ENDEREÇO -->
          <div class="section address equal-height">
            <GoogleMap ref="googleMap" @addressClicked="handleAddressClick" />
            <div class="elements-card">
              <h2>{{ truncatedAddress }}</h2>
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
      </div>
      <!-- DIREITA (Detalhes da Compra) -->
      <RightSection
        :cardDetails="cardDetails"
        @saveCardDetails="saveCardDetails"
        class="right-section"
        :allAddresses="allAddresses"
        @finalizePurchase="finalizePurchase"
      />
      <AddressModal
        :isVisible="showModal"
        @close="showModal = false"
        @submit-address="handleManualAddress"
      />
    </div>
  </AdminLayout>
</template>

<script>
  import cartaoTemplate from '@/components/CartaoTemplate.vue';
  import GoogleMap from '@/components/GoogleMap.vue';
  import AddressModal from '@/components/AddressModal.vue';
  import AddressList from '@/components/AddressList.vue';
  import axiosInstance from '@/utils/axiosInstance';
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  import { library } from '@fortawesome/fontawesome-svg-core';
  import {
    faPlus,
    faEdit,
    faCheck,
    faChevronDown,
    faChevronUp,
  } from '@fortawesome/free-solid-svg-icons';
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
      ProductSection, // Registrar o novo componente
    },
    data() {
      return {
        currentDate: new Date(),
        clickedAddress: null,
        savedAddress: null,
        productIds: [],
        productDetails: [],
        productCount: 0,
        shippingPrice: 15.0,
        showModal: false,
        showCardInputs: false, // Controle de visibilidade dos inputs de cartão
        cardDetails: {
          number: '',
          name: '',
          expiry: '',
        },
        accordionOpen: false, // Controle de visibilidade do acordeão
        addresses: [], // Lista de endereços
        selectedAddressId: null, // ID do endereço selecionado
        allAddresses: [], // Lista de todos os endereços cadastrados
        cartItems: [], // Adiciona a lista de itens do carrinho
      };
    },
    created() {
      this.processQuery();
      this.fetchAddresses();
      this.fetchAllAddresses();
      this.fetchUserCards(); // Busca os cartões do usuário ao criar o componente
    },
    watch: {
      productId(newVal) {
        if (newVal) {
          this.fetchProductDetails();
        }
      },
    },
    computed: {
      truncatedAddress() {
        const address =
          this.clickedAddress ||
          this.savedAddress ||
          'Nenhum endereço selecionado';
        return address.length > 15 ? address.slice(0, 15) + '...' : address;
      },
    },
    methods: {
      handleCardDetails(card) {
        this.cardDetails = card; // Salva os dados do cartão
        this.showCardInputs = false; // Fecha o formulário de cartão
      },
      async processQuery() {
        const userId = this.$route.query.userId;
        if (userId) {
            await this.fetchCartItems(userId);
        }
      },
      async fetchCartItems(userId) {
        try {
            const response = await axiosInstance.get(`/api/carrinho/user/${userId}`);
            this.cartItems = response.data;
            this.productDetails = this.cartItems.map(item => ({
                id: item.produtoId,
                imagens: item.imagens,
                nome: item.nome,
                preco: item.preco,
                quantidade: item.quantidade
            }));
            this.productCount = this.productDetails.length;
            console.log('Valores recebidos do carrinho:', this.cartItems);
        } catch (error) {
            console.error('Erro ao buscar itens do carrinho:', error);
        }
    },
      async fetchProductDetails() {
        this.productDetails = await Promise.all(
          this.productIds.map((id) =>
            axiosInstance
              .get(`/api/produtos/${id}`)
              .then((response) => {
                return response.data;
              })
              .catch((error) => {
                console.error('Erro ao buscar detalhes do produto:', error);
                return null;
              })
          )
        );
        this.productDetails = this.productDetails.filter(
          (detail) => detail !== null
        );
        this.productCount = this.productDetails.length;
      },
      async fetchAddresses() {
        try {
          const response = await axiosInstance.get('/api/addresses/user');
          this.addresses = response.data;
          this.setDefaultAddress();
        } catch (error) {
          if (error.response && error.response.status === 401) {
            console.error('Usuário não autenticado');
            this.$router.push('/login');
          } else {
            console.error('Erro ao buscar endereços:', error);
          }
        }
      },
      setDefaultAddress() {
        const defaultAddress = this.addresses.find(
          (address) => address.addressType === 'Casa'
        );
        if (defaultAddress) {
          this.savedAddress = `${defaultAddress.address.street}, ${defaultAddress.address.number}, ${defaultAddress.address.neighborhood}, ${defaultAddress.address.city}, ${defaultAddress.address.state}, ${defaultAddress.address.zipCode}`;
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
      async fetchUserCards() {
        try {
          const response = await axiosInstance.get('/api/cartoes/meus-cartoes', {
            withCredentials: true // Certifique-se de enviar cookies de autenticação
          });
          this.userCards = response.data;
          console.log('Números dos cartões:', this.userCards.map(card => card.number)); // Use 'number' para corresponder ao JSON
        } catch (error) {
          console.error('Erro ao buscar cartões do usuário:', error);
        }
      },
      handleAddressClick(address) {
        this.clickedAddress = address;
        this.savedAddress = address; // Atualiza o endereço salvo com o endereço clicado no mapa
      },
      async addAddress() {
        if (this.clickedAddress) {
          try {
            const addressParts = this.clickedAddress.split(',');
            const street = addressParts[0] || 'Rua Desconhecida';
            const number = addressParts[1]
              ? addressParts[1].trim().split(' ')[0]
              : 'S/N';
            const neighborhood = addressParts[2]
              ? addressParts[2].trim()
              : 'Bairro Desconhecido';
            const city = addressParts[3]
              ? addressParts[3].trim()
              : 'Cidade Desconhecida';
            const state = addressParts[4]
              ? addressParts[4].trim()
              : 'Estado Desconhecido';
            const zipCode = addressParts[5]
              ? addressParts[5].trim()
              : '00000-000'; // Ajuste conforme necessário
            const addressType = this.addressType || 'Casa'; // Obtenha o tipo de endereço do modal

            const requestBody = {
              street,
              number,
              neighborhood,
              city,
              state,
              zipCode,
              addressType, // Adicione o campo addressType
            };

            console.log('Enviando dados do endereço:', requestBody);

            const response = await axiosInstance.post(
              '/api/addresses/create',
              requestBody,
              {
                withCredentials: true, // Certifique-se de enviar cookies de autenticação
              }
            );
            this.savedAddress = response.data;
            this.clickedAddress = null; // Limpa o texto do endereço exibido
            this.fetchAddresses(); // Atualiza a lista de endereços
          } catch (error) {
            console.error('Erro ao adicionar endereço:', error);
          }
        }
      },
      async handleManualAddress(fullAddress) {
        const addressParts = fullAddress.split(',');
        const street = addressParts[0] || 'Rua Desconhecida';
        const number = addressParts[1]
          ? addressParts[1].trim().split(' ')[0]
          : 'S/N';
        const neighborhood = addressParts[2]
          ? addressParts[2].trim()
          : 'Bairro Desconhecido';
        const city = addressParts[3]
          ? addressParts[3].trim()
          : 'Cidade Desconhecida';
        const state = addressParts[4]
          ? addressParts[4].trim()
          : 'Estado Desconhecido';
        const zipCode = addressParts[5] ? addressParts[5].trim() : '00000-000';
        const addressType = addressParts[6] || 'Casa'; // Obtenha o tipo de endereço do modal

        this.clickedAddress = fullAddress;
        this.savedAddress = fullAddress;
        this.addressType = addressType; // Defina o tipo de endereço

        try {
          await this.$refs.googleMap.centerMapOnAddress(fullAddress);
        } catch (err) {
          console.error('Erro ao centralizar o mapa:', err);
        }
      },
      saveCardDetails() {
        this.showCardInputs = false; // Esconde os inputs após salvar
        this.$refs.rightSection.showCardInputs = false; // Fecha o formulário de cartão
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
        const selectedAddress = this.allAddresses.find(address => address.address.id === addressId);
        if (selectedAddress) {
          this.centerMapOnAddress(selectedAddress.address);
        }
      },
      async centerMapOnAddress(address) {
        try {
          const fullAddress = `${address.street}, ${address.number}, ${address.neighborhood}, ${address.city}, ${address.state}, ${address.zipCode}`;
          await this.$refs.googleMap.centerMapOnAddress(fullAddress);
          this.handleAddressClick(fullAddress); // Atualiza o elements-card com o endereço selecionado
        } catch (err) {
          console.error('Erro ao centralizar o mapa:', err);
        }
      },
      getProductQuantity(productId) {
        const cartItem = this.cartItems.find(item => item.produtoId === productId);
        return cartItem ? cartItem.quantidade : 0;
      },
      finalizePurchase() {
        console.log('Valores recebidos do carrinho:', this.cartItems);
        // Adicione aqui a lógica para finalizar a compra
      },
    },
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
    display: flex;
    flex-direction: row;
  }

  h2 {
    font-size: 1.5rem;
  }

  checkout-header img {
    width: 8rem; /* Ajuste o tamanho do logo para 8rem */
    filter: invert(1); /* Aplique o filtro invert */
  }

  .header-title {
    font-size: 3rem; /* Aumenta o tamanho da fonte */
    margin-left: 1rem; /* Adiciona espaço entre a imagem e o texto */
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
    display: flex;
    background: #ffffff;
    border-radius: 2rem;
    width: 35%;
    justify-content: flex-end;
    flex-direction: column;
    padding: 2rem;
    height: 100%;
  }

  @media (max-width: 768px) {
    .right-section {
      flex-basis: 100%;
      width: 100%;
      padding: 1rem;
    }
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
    font-family: 'Poppins', sans-serif;
  }

  .card-inputs input::placeholder {
    color: #6d6d6d;
  }

  .card-inputs input:focus {
    outline: none;
    border: 1px solid #3a5bff;
    box-shadow: 1px 0px 20px 1px #3a5bff3b;
  }

  .submit-btn,
  .total-price {
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

  .submit-btn:hover,
  .total-price:hover {
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
  .products,
  .address {
    display: flex; /* Usa flexbox para melhor controle de layout */
    flex-direction: column; /* Organiza os conteúdos verticalmente */
    justify-content: space-between; /* Distribui o espaço igualmente entre os elementos internos */
    align-items: center; /* Centraliza os elementos horizontalmente */
    padding: 20px; /* Adiciona um pouco de espa��o interno para não tocar as bordas */
    flex-basis: 30%; /* Define a base inicial como 30% do espaço disponível */
    padding-top: 0;
    display: flex;
    flex-direction: column !important;
    height: 100% !important;
    padding-bottom: 0;
  }

  .section {
    margin: 0;
    display: flex
;
    flex-direction: column;
    align-items: center;
    justify-content: flex-end;
    border-radius: 2rem !important;
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

  .product-checkout-image,
  .map-image {
    max-width: 100px; /* Define a largura máxima das imagens */
    height: auto; /* Mantém a proporção das imagens ajustando a altura automaticamente */
  }

  .add-address-btn {
    padding: 10px 20px;
    background-color: #4caf50; /* Verde */
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px; /* Espaço acima do botão */
    font-size: 1rem; /* Tamanho do texto */
  }

  .product-and-address {
    height: 30%;
  }

  .elements-card {
    background-color: #ffffff;
    border-radius: 0 0 2rem 2rem;
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
    font-size: 1.5rem;
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
    background-color: #4caf50;
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
    transition:
      background-color 0.3s,
      color 0.3s;
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
    transition:
      background-color 0.3s,
      color 0.3s;
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
    margin-right: 1rem;
    background-color: #ffffff;
    border-radius: 2rem;
    padding: 2rem;
    cursor: pointer;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    height:100% !important;
  
  }

  .accordion-header {
    display: flex
;
    justify-content: space-between;
    align-items: center;
    font-size: 1.5rem;
    cursor: pointer;
  }

  .accordion-content {
    margin-top: 1rem;
    overflow-y:auto;
    max-height: 90%;
  }

  header img {
    width: 9rem;
    filter: invert(1);
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
    display: flex;
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

  .product-quantity {
    font-size: 1.5rem;
    margin-right: 1rem; /* Adiciona espaço entre a quantidade e o preço */
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


  .address-list {
    width: 50%; /* Cada componente ocupa 50% do espaço disponível */
  }

.accordion {
  width:70%;
}

  .accordion-header,
  .address-list .accordion-header {
    display: flex
;
    justify-content: space-between;
    align-items: center;
    font-size: 1.5rem;
    cursor: pointer;
  }

  .accordion-header span,
  .address-list .accordion-header h3 {
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
    border-radius: 1rem;
    opacity: 1; /* Opacidade padrão */
    transition: opacity 0.3s; /* Transição suave para a opacidade */
    height:2rem;
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
