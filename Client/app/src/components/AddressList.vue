<template>
  <div class="address-list w-full  p-4 ">
    <div class="accordion-header" @click="toggleAccordion">
      <h3>Todos os Endereços Cadastrados</h3>
      <font-awesome-icon :icon="accordionOpen ? 'fa-solid fa-chevron-up' : 'fa-solid fa-chevron-down'" />
    </div>
    <div v-if="accordionOpen" class="accordion-content">
      <div v-for="address in addresses" :key="address.address.id" :class="['address-option', { selected: address.address.id === selectedAddressId }]">
        <label class="address-label">
          <input type="radio" :value="address.address.id" :checked="address.address.id === selectedAddressId" @change="$emit('update:selectedAddressId', address.address.id)" />
          <span>{{ address.address.street }}</span>
          <span class="zip-code">{{ address.address.zipCode }}</span>
        </label>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons';

library.add(faChevronDown, faChevronUp);

export default {
  components: {
    FontAwesomeIcon
  },
  props: {
    addresses: {
      type: Array,
      required: true
    },
    selectedAddressId: {
      type: [Number, null],
      required: true
    }
  },
  data() {
    return {
      accordionOpen: false
    };
  },
  created() {
    this.selectDefaultAddress();
  },
  watch: {
    addresses: {
      immediate: true,
      handler() {
        this.selectDefaultAddress();
      }
    }
  },
  methods: {
    toggleAccordion() {
      this.accordionOpen = !this.accordionOpen;
    },
    selectDefaultAddress() {
      console.log("Endereços recebidos:", this.addresses);
      if (!this.selectedAddressId) {
        const defaultAddress = this.addresses.find(address => address.addressType === 'Casa');
        if (defaultAddress) {
          console.log("Endereço padrão encontrado:", defaultAddress);
          this.$emit('update:selectedAddressId', defaultAddress.address.id);
          this.$emit('addressSelected', defaultAddress.address); // Emitir evento para centralizar o mapa
        } else {
          console.log("Nenhum endereço do tipo 'Casa' encontrado.");
        }
      }
    }
  }
};
</script>

<style scoped>
.address-list {
    background-color: #ffffff;
    border-radius: 2rem;
    padding: 2rem;

    cursor: pointer;
    max-height: 100%;

}

.accordion-header {
  display: flex;
  justify-content: space-between; /* Ajuste para distribuir espaço entre os itens */
  align-items: center;
  font-size: 1.5rem;
  height: 2rem;
  cursor: pointer;
}

.accordion-header h3 {
  margin-right: 1rem;
}

.accordion-content {
  margin-top: 1rem;
  overflow-y: auto;
  height: 10rem;
}

.address-option {
  margin-bottom: 1rem;
  background-color: #e7e7e7; /* Background para cada card de endereço */
  border-radius: 1rem;
  opacity: 1; /* Opacidade padrão */
  transition: opacity 0.5s; /* Transição suave para a opacidade */
}

.address-option:not(.selected) {
  opacity: 0.5; /* Opacidade reduzida para cards não selecionados */
}

.address-label {
  display: flex;
  justify-content: space-between; /* Ajuste para distribuir espaço entre os itens */
  align-items: center;
  width: 100%;

  padding: 1rem;
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