<template>
  <div class="address-list w-full  p-4 ">
    <div class="accordion-header" @click="toggleAccordion">
      <h3>Todos os Endereços Cadastrados</h3>
      <font-awesome-icon :icon="accordionOpen ? 'fa-solid fa-chevron-up' : 'fa-solid fa-chevron-down'" />
    </div>
    <div v-if="accordionOpen" class="accordion-content">
      <div v-for="address in addresses" :key="address.id" :class="['address-option', { selected: address.id === selectedAddressId }]">
        <label class="address-label">
          <input type="radio" :value="address.id" :checked="address.id === selectedAddressId" @change="$emit('update:selectedAddressId', address.id)" />
          <span>{{ address.street }}</span>
          <span class="zip-code">{{ address.zipCode }}</span>
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
  methods: {
    toggleAccordion() {
      this.accordionOpen = !this.accordionOpen;
    }
  }
};
</script>

<style scoped>
.address-list {
    margin-right: 2rem !important;
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

.accordion-header h3 {
  margin-right: 1rem;
}

.accordion-content {
  margin-top: 1rem;
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

.space-x-4 {
margin: 0 !important;
}

.address-label input {
  margin-right: 1rem;
  transform: scale(2); /* Aumenta o tamanho do botão de rádio */
  accent-color: black; /* Altera a cor do botão de rádio para preto */
}
</style>