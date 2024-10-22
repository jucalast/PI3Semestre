<template>
  <div class="address-selector p-4 bg-black-100 rounded-lg shadow-md h-full w-full">
    <h3 class="text-lg font-semibold mb-4">Endereços Cadastrados</h3>
    <ul class="space-y-2 ">
      <li v-for="address in addresses" :key="address.id" class="flex items-center">
        <label class="flex items-center cursor-pointer">
          <input
            type="radio"
            :value="address"
            v-model="selectedAddress"
            @change="selectAddress(address)"
            class="mr-2"
          />
          <span class="text-gray-800">{{ address.label }} - {{ address.details }}</span>
        </label>
      </li>
    </ul>

    <button @click="redirectToAddAddress" class="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition duration-200">
      Adicionar Novo Endereço
    </button>
  </div>
</template>

<script>
export default {
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
        const response = await fetch('/api/addresses');
        this.addresses = await response.json();
      } catch (error) {
        console.error('Erro ao buscar endereços:', error);
      }
    },
    selectAddress(address) {
      this.selectedAddress = address;
      this.$emit('address-selected', address);
    },
    redirectToAddAddress() {
      this.$router.push('/add-address'); 
    },
  },
};
</script>

<style scoped>
</style>
