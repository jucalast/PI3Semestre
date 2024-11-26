<template>
  <div class="order-summary-container">
    <GoogleMap ref="googleMap" @addressClicked="handleAddressClick" :truncatedAddress="truncatedAddress" />
    <div class="order-summary">
      <div class="invoice">
        <h3>Resumo do Pedido</h3>
        <div class="invoice-item" v-for="(product, index) in productDetails" :key="index">
          <span class="product-name">{{ product.nome }}</span>
          <span class="product-quantity">{{ product.quantidade }}x</span>
          <span class="product-price">R$ {{ product.preco.toFixed(2) }}</span>
          <span class="product-total">R$ {{ (product.preco * product.quantidade).toFixed(2) }}</span>
        </div>
        <div class="invoice-total">
          <span>Total:</span>
          <span>R$ {{ totalAmount.toFixed(2) }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import GoogleMap from '@/components/GoogleMap.vue';

export default {
  name: 'OrderSummary',
  components: {
    GoogleMap,
  },
  props: {
    truncatedAddress: {
      type: String,
      required: true,
    },
    selectedAddress: {
      type: Object,
      default: null,
    },
    productDetails: {
      type: Array,
      required: true,
      default: () => [],
    },
  },
  computed: {
    totalAmount() {
      return this.productDetails.reduce((total, product) => {
        return total + product.preco * product.quantidade;
      }, 0);
    },
  },
  watch: {
    selectedAddress(newAddress) {
      if (newAddress) {
        this.centerMapOnAddress(newAddress);
      }
    },
  },
  methods: {
    handleAddressClick(address) {
      this.$emit('addressClicked', address);
    },
    async centerMapOnAddress(address) {
      const fullAddress = `${address.street}, ${address.number}, ${address.neighborhood}, ${address.city}, ${address.state}, ${address.zipCode}`;
      await this.$refs.googleMap.centerMapOnAddress(fullAddress);
    },
  },
};
</script>

<style scoped>
.order-summary-container[data-v-1a8a5bb9] {
    display: flex
;
    flex-direction: column;
    gap: 1rem;
    width: 38%;
    justify-content: space-between;
}
.order-summary {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  background-color: #c0cbff;
  border-radius: 2rem;
  justify-content: space-between;
  height: 60%;
}
.invoice {
    border-radius: 2rem;
    padding: 2rem;
    color: #696969;
    font-family: 'Courier New', Courier, monospace;
    font-size: 1rem;
    height: 100%;
    display: flex
;

    flex-direction: column;
    justify-content: space-between;
box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.1);
}
.invoice-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  border-bottom: dashed 1px #696969; /* Adiciona a borda inferior */
}
.invoice-item span {
  flex: 1;
  text-align: center;
}
.product-name {
  display: flex;
  text-align: start;
}
.invoice-total {
  display: flex;
  justify-content: space-between;
  font-size: 2rem;
  margin-top: 2rem;
  color: #324dd2;
}
</style>