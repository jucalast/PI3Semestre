<template>
  <div class="address-selector p-6 bg-white rounded-lg shadow-lg h-full w-full">
    <h3 class="text-2xl font-semibold mb-4 text-gray-800 justify-start">Endereços Cadastrados</h3>
    <ul class="space-y-4">
      <li v-for="address in addresses" :key="address.id" class="flex items-center bg-gray-50 p-4 rounded-md shadow hover:bg-gray-100 transition duration-200">
        <label class="flex items-center cursor-pointer w-full">
          <input
            type="radio"
            :value="address"
            v-model="selectedAddress"
            @change="selectAddress(address)"
            class="mr-4 text-blue-600 focus:ring-blue-500"
          />
          <span class="text-gray-800 text-lg">
            {{ address.street }}, {{ address.number }} - {{ address.neighborhood }},
            {{ address.city }} - {{ address.state }} ({{ address.zipCode }})
          </span>
        </label>
      </li>
    </ul>

    <button
      @click="redirectToAddAddress"
      class="mt-6 w-full px-4 py-2 bg-headerBackground text-white font-semibold rounded hover:bg-yellow-400 transition duration-200"
    >
      Adicionar Novo Endereço
    </button>
  </div>
</template>

<script>
import axiosInstance from '@/utils/axiosInstance';

export default {
  name: 'AddressSelector',
  data() {
    return {
      addresses: [],
      selectedAddress: null,
    };
  },
  created() {
    this.fetchAddresses();
  },
  methods: {
    async fetchAddresses() {
      try {
        const response = await axiosInstance.get('/api/addresses');
        this.addresses = response.data; 
      } catch (error) {
        console.error('Erro ao buscar endereços:', error);
      }
    },
    selectAddress(address) {
      this.selectedAddress = address;
      this.$emit('address-selected', address);
    },
    redirectToAddAddress() {
      this.$router.push('/address');
    },
  },
};
</script>

<style scoped>
</style>
