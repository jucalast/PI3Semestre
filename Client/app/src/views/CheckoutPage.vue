<template>
  <AdminLayout>
    <div class="checkout-page flex flex-row h-full p-4">
      <div class="left-section flex flex-col h-full">
        <header class="checkout-header p-4">
          <img src="../assets/logo.png" alt="Logo" />
          <h3 class="header-title">Quase tudo pronto!</h3>
        </header>
        <div class="content-left">
          <div class="summary-and-address">
            <div class="product-summary">
              <span>Resumo dos produtos no carrinho</span>
              <div class="product-summary-content">
                <div
                    v-for="(product, index) in productDetails"
                    :key="index"
                    class="product-summary-card"
                >
                  <img
                      :src="product.imagens ? product.imagens[0] : ''"
                      :alt="product.nome"
                      class="product-summary-image"
                  />
                  <div class="product-summary-info">
                    <h4>{{ product.nome }}</h4>
                    <p class="product-quantity">{{ getProductQuantity(product.id) }}x</p>
                    <p class="product-price">R$ {{ product.preco ? product.preco.toFixed(2) : '0.00' }}</p>
                  </div>
                </div>
              </div>
            </div>
            <div class="address-list">
              <div class="address-list-header flex justify-between items-center">
                <span>Lista de Endereços</span>
                <button class="add-address-btn" @click="showModal = true">
                  <FontAwesomeIcon :icon="['fas', 'plus']" /> Adicionar Endereço
                </button>
              </div>
              <div class="address-list-content">
                <div
                    v-for="(address, index) in allAddresses"
                    :key="index"
                    class="address-option"
                    @click="updateSelectedAddressId(address.address.id)"
                    :class="{ 'selected': selectedAddressId === address.address.id }"
                >
                  <label class="address-label">
                    <input
                        type="radio"
                        name="address"
                        :value="address.address.id"
                        v-model="selectedAddressId"
                    />
                    <span>{{ address.address.street }}, {{ address.address.number }}</span>
                    <span class="zip-code">{{ address.address.zipCode }}</span>
                  </label>
                </div>
              </div>
            </div>

          </div>
          <OrderSummary
              ref="orderSummary"
              :selectedAddress="selectedAddress"
              :truncatedAddress="truncatedAddress"
              :productDetails="productDetails"
              @addressClicked="handleAddressClick"
          />
        </div>
      </div>
      <RightSection
          :cardDetails="cardDetails"
          @update-card-details="updateCardDetails"
          @finalize-purchase="finalizePurchase"
          :allAddresses="allAddresses"
          :productDetails="productDetails"
          :shippingPrice="shippingPrice"
      />


      <AddressModal
          :isVisible="showModal"
          @close="showModal = false"
          @submit-address="handleManualAddress"
      />
    </div>
    <button class="debug-info-btn" @click="printDebugInfo">Exibir Informações de Debug</button>

  </AdminLayout>
</template>

<script>
import cartaoTemplate from '@/components/CartaoTemplate.vue';
import GoogleMap from '@/components/GoogleMap.vue';
import AddressModal from '@/components/AddressModal.vue';
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
import OrderSummary from '@/components/OrderSummary.vue'; // Importar o novo componente

library.add(faPlus, faEdit, faCheck, faChevronDown, faChevronUp);

export default {
  components: {
    AdminLayout,
    cartaoTemplate,
    GoogleMap,
    AddressModal,

    FontAwesomeIcon,
    RightSection,
    ProductSection, // Registrar o novo componente
    OrderSummary, // Registrar o novo componente
  },
  data() {
    return {
      currentDate: new Date(),
      clickedAddress: null,
      savedAddress: null,
      productDetails: [],
      productCount: 0,
      shippingPrice: 15.0,
      showModal: false,
      showCardInputs: false,
      cardDetails: {
        number: '',
        name: '',
        expiry: ''
      },
      paymentType: 'Crédito', // Valor padrão
      accordionOpen: false,
      addresses: [],
      selectedAddressId: null,
      allAddresses: [],
      cartItems: []
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
    selectedAddress() {
      const selected = this.allAddresses.find(
          (address) => address.address.id === this.selectedAddressId
      );
      return selected ? selected.address : null;
    },
  },
  methods: {
    updateCardDetails({ cardDetails, paymentType }) {
      this.cardDetails = { ...cardDetails }; // Atualiza os detalhes do cartão selecionado
      this.paymentType = paymentType; // Salva se é crédito ou débito
      console.log('Cartão atualizado no Checkout:', this.cardDetails, 'Tipo:', this.paymentType);
    },
    printDebugInfo() {
      const endereco = this.savedAddress.split(',').map(part => part.trim());
      const cartao = this.cardDetails;
      const produtos = this.productDetails.map(product => ({
        produtoId: product.id,
        quantidade: product.quantidade
      }));

      const info = {
        estado: endereco[4],
        cep: endereco[5],
        cidade: endereco[3],
        bairro: endereco[2],
        numero: parseInt(endereco[1]),
        rua: endereco[0],
        produtos: produtos,
        pagamento: {
          tipoPagamento: this.paymentType.replace('Débito', 'DEBITO').replace('Crédito', 'CREDITO').toUpperCase(),
          cartao: {
            numeroCartao: cartao.number,
            nomeTitular: cartao.name,
            validade: cartao.expiry,
            bandeira: cartao.bandeira,
            cpfTitular: cartao.cpf
          }
        }
      };

      console.log(JSON.stringify(info, null, 2)); // Imprime o JSON formatado
    },
    async finalizePurchase({selectedInstallment}) {
      this.selectedInstallment = selectedInstallment;
      try {
        // Preparar os dados do endereço e dos produtos
        const endereco = this.savedAddress.split(',').map(part => part.trim());
        const cartao = this.cardDetails;
        const produtos = this.productDetails.map(product => ({
          produtoId: product.id,
          quantidade: product.quantidade,
        }));

        // Verificar o tipo de pagamento e montar o payload correspondente
        const payload = {
          estado: endereco[4],
          cep: endereco[5],
          cidade: endereco[3],
          bairro: endereco[2],
          numero: parseInt(endereco[1]),
          rua: endereco[0],
          produtos: produtos,
          pagamento: {
            tipoPagamento: this.paymentType.replace('Débito', 'DEBITO').replace('Crédito', 'CREDITO').toUpperCase(),
            cartao: {
              numeroCartao: cartao.number,
              nomeTitular: cartao.name,
              validade: cartao.expiry,
              bandeira: cartao.bandeira,
              cpfTitular: cartao.cpf,
            },
          },
        };

        // Adicionar número de parcelas se for crédito
        if (this.paymentType === 'Crédito' && this.selectedInstallment) {
          payload.pagamento.numeroParcelas = this.selectedInstallment;
        }

        console.log('Payload para requisição:', payload);

        // Fazer a requisição POST
        const response = await axiosInstance.post(`/api/pedidos`, payload);

        if (response.status === 200 || response.status === 201) {
          alert('Compra realizada com sucesso!');
          console.log('Resposta da API:', response.data);
        }
      } catch (error) {
        console.error('Erro ao finalizar compra:', error);
        alert('Ocorreu um erro ao finalizar a compra. Tente novamente.');
      }
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
        console.log('Endereços recebidos:', this.allAddresses); // Adicionar console.log para verificar os endereços recebidos
        this.selectDefaultAddress(); // Seleciona o endereço principal após buscar todos os endereços
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
      } catch (error) {
        console.error('Erro ao buscar cartões do usuário:', error);
      }
    },
    handleAddressClick(address) {
      this.clickedAddress = address;
      this.savedAddress = address;
      console.log(address)// Atualiza o endereço salvo com o endereço clicado no mapa
    },
    async handleManualAddress(address) {
      console.log('Endereço recebido no handleManualAddress:', address);

      if (typeof address === 'object') {
        const fullAddress = `${address.rua}, ${address.numero}, ${address.bairro}, ${address.cidade}, ${address.estado}, ${address.cep}`;
        console.log('Endereço formatado:', fullAddress);

        this.clickedAddress = fullAddress;
        this.savedAddress = fullAddress;

        const retryInterval = 500; // Intervalo para tentar novamente
        const maxRetries = 10; // Tentativas máximas
        let retries = 0;

        const waitForOrderSummary = async () => {
          while (!this.$refs.orderSummary || !this.$refs.orderSummary.centerMapOnAddress) {
            if (retries >= maxRetries) {
              console.error('OrderSummary não está pronto após várias tentativas.');
              return;
            }
            console.warn('OrderSummary não está pronto. Retentando...');
            retries++;
            await new Promise(resolve => setTimeout(resolve, retryInterval));
          }
        };

        await waitForOrderSummary();

        try {
          console.log('Chamando método centerMapOnAddress no OrderSummary...');
          await this.$refs.orderSummary.centerMapOnAddress(fullAddress);
          console.log('Mapa centralizado com sucesso.');
        } catch (err) {
          console.error('Erro ao centralizar o mapa no OrderSummary:', err);
        }
      }
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
        await this.$refs.orderSummary.$refs.googleMap.centerMapOnAddress(fullAddress);
        this.handleAddressClick(fullAddress); // Atualiza o elements-card com o endereço selecionado
      } catch (err) {
        console.error('Erro ao centralizar o mapa:', err);
      }
    },
    getProductQuantity(productId) {
      const cartItem = this.cartItems.find(item => item.produtoId === productId);
      return cartItem ? cartItem.quantidade : 0;
    },
    selectDefaultAddress() {
      const defaultAddress = this.allAddresses.find(
          (address) => address.addressType === 'Principal'
      );
      if (defaultAddress) {
        this.selectedAddressId = defaultAddress.address.id;
        this.centerMapOnAddress(defaultAddress.address);
      }
    },
  },
};
</script>

<style scoped>
.checkout-page {
  display: flex;
  flex-direction: row;
  width: 100vw !important;
  height: 100vh !important;
  background: #ececec;
}

.checkout-header {
  background: transparent;
  display: flex;
  flex-direction: row;
}

h2 {
  font-size: 1.5rem;
}

.checkout-header img {
  width: 6rem;
  filter: invert(1);
}

.header-title {
  font-size: 3rem;
  margin-left: 1rem;
}

.left-section {
  flex-grow: 1;
  flex-basis: 70%;
  display: flex;
  flex-direction: column;
  gap: 1rem;

}

.right-section {
  display: flex;
  background: #ffffff;
  border-radius: 2rem;
  width: 35%;
  justify-content: flex-end;
  flex-direction: column;
  padding: 2rem;
  height: 100%;
  position: relative;
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
  height: 90%;
  border-radius: 2rem;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding-top: 12rem;
  position: relative;
  z-index: 1;
}

.card-container1 {
  position: absolute;
  height: 30%;
  width: 80%;
  top: 19%;
  left: 10%;
  transform: translateY(-50%);
  z-index: 2;
}

.card-inputs {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 1rem;
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
  width: 80%;
}

.card-expiry {
  width: 20%;
}

.card-name {
  width: 100%;
}

.card-cpf {
  width: 70%;
}

.card-bandeira {
  width: 30%;
}

.header-info-card {
  display: flex;
  justify-content: space-between;
  width: 90%;
  gap: 10px;
  border-radius: 1rem;
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
  border-radius: 1rem;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  border: solid 1px #d3d3d3;
  margin-top: 0.5rem;
}

.card-option-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 1rem;
  width: 100%;
  gap: 1rem;
}

.card-option-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}

.card-option {
  display: flex;
  align-items: center;
  width: 100%;
  height: 5rem;
  padding: 1rem;
  background: #efefef;
  border-radius: 1rem;
  font-size: 2rem;
  justify-content: flex-start;
  flex-direction: row;
  gap: 2rem;
}

.radio-option {
  margin-left: 1rem;
  transform: scale(2);
  accent-color: black;
}

.card-type {
  font-weight: bold;
}

.submit-btn, .total-price {
  background-color: #000000;
  color: white;
  border: none;
  border-radius: 1rem;
  padding: 1rem 2rem;
  font-size: 2rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-btn:hover, .total-price:hover {
  background-color: #131313;
}

.price-details {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 95%;
  position: absolute;
  bottom: -1rem;
  gap: 1rem;
}

.price-details .price-item {
  display: flex;
  justify-content: space-between;
  width: 100%;
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.content-left {
  display: flex;
}

.prices {
  background: #efefef;
  width: 100%;
  padding: 1rem;
  border-radius: 1rem;
}

.price-details .total-price {
  font-size: 2rem;
  margin-top: auto;
  width: 100%;
}

.add-card-btn, .payment-btn {
  background-color: #efefef;
  color: #1e1e1e;
  border-radius: 1rem;
  padding: 1rem 1rem;
  font-size: 1.3rem;
  cursor: pointer;
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: flex-start;
}

.add-card-btn:hover, .payment-btn:hover {
  background-color: #000000;
  color: #ffffff;
}

.add-card-btn.filled {
  background-color: #3a5bff;
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
  box-shadow: none;
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
  background-color: #3a5bff;
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
  background-color: #43ce7d;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

.accordion-toggle-container {
  display: flex;
  align-items: center;
  justify-content: center;
  background: #efefef;
  width: 16%;
  border-radius: 1rem;
  text-align: center;
  line-height: 6rem;
  padding: 1.5rem;
}

.accordion-toggle-container:hover {
  background-color: #000000;
  color: #ffffff;
}

.installment-options {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 1rem;
  width: 100%;
  background: #efefef;
  border-radius: 1rem;
  padding: 1rem;
  padding-left: 1rem;
  font-size: 1.5rem;
  max-height: 25%;
  overflow-y: auto;
}

.installment-option {
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: space-between;
  border-bottom: solid 1px #d3d3d3;
  padding-left: 1rem;
}

.installment-option input {
  transform: scale(1.5);
}

.submit-btn,
.total-price {
  background-color: #000000;
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
  background-color: #111111;
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

.products,
.address {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  flex-basis: 30%;
  padding-top: 0;
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
  max-width: 100px;
  height: auto;
}

.add-address-btn {
  padding: 10px 20px;
  background-color: #ffffff;
  color: rgb(19, 19, 19);
  border: none;
  border-radius: 1rem;
  cursor: pointer;
  border: solid 1px #d3d3d3;
  font-size: 1.5rem;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: background-color 0.3s, color 0.3s;
}

.add-address-btn:hover {
  background-color: #f8f8f8;
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
  width: 100%;
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
  padding: 1rem;
  font-size: 1.5rem;
  cursor: pointer;
  transition: background-color 0.3s,
  color 0.3s;
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: flex-start;
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
  transition: background-color 0.3s,
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

.product-summary {
  margin-right: 1rem;
  background-color: #ffffff;
  border-radius: 2rem;
  padding: 2rem;
  height: 45vh !important;

}

.product-summary-content {
  margin-top: 1rem;
  overflow-y: auto;
  max-height: 90%;
}

.product-summary-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
  padding: 1rem;
  border-radius: 1rem;
  background-color: #efefef;
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
  margin-right: 1rem;
}

.product-quantity {
  font-size: 1.5rem;
  margin-right: 1rem;
}

.product-price {
  font-size: 2rem;
}

.product-summary-image {
  width: 30px;
  height: auto;
  margin-right: 1rem;
}

.accordion-and-address-list {
  margin-top: 1rem;
}

.address-and-map {
  display: flex;
  flex-direction: row;
  gap: 1rem;
}

.address-list {
  margin-right: 1rem;
  background-color: #ffffff;
  border-radius: 2rem;
  padding: 2rem;
  height: 35vh !important;

  padding-top: 1rem;
}

.address-list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.address-list-content {
  margin-top: 1rem;
  overflow-y: auto;
  max-height: 80%;
}

.address-option {
  margin-bottom: 1rem;
  padding: 1rem;
  border-radius: 1rem;
  background-color: #efefef;
  opacity: 0.5;
  transition: opacity 0.3s;
}

.address-option.selected {
  opacity: 1;
}

.address-label {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  font-size: 2rem;
}

.address-label input {
  margin-right: 1rem;
  transform: scale(2);
  accent-color: black;
}

.address-label .zip-code {
  color: #5271ff;
  margin-left: 1rem;
}

.summary-and-address {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 60%;
}
</style>
