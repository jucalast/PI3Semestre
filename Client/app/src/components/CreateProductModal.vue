<template>
    <div class="modal-overlay" @click="handleOverlayClick">
      <div class="modal-content" @click.stop>
        <button @click="selectForm('cafeEspecial')">Café Especial</button>
        <button @click="selectForm('metodoPreparo')">Método de Preparo</button>
  
        <SpecialCoffeeForm 
          v-if="selectedForm === 'cafeEspecial'" 
          @submit-product="submitProduct" 
          :disabled="isSubmitting"
        />
        <PreparationMethodForm 
          v-else-if="selectedForm === 'metodoPreparo'" 
          @submit-product="submitProduct" 
          :disabled="isSubmitting"
        />
      </div>
    </div>
  </template>
  
  <script>
  import SpecialCoffeeForm from './SpecialCoffeeForm.vue';
  import PreparationMethodForm from './PreparationMethodForm.vue';
  
  export default {
    name: 'CreateProductModal',
    components: {
      SpecialCoffeeForm,
      PreparationMethodForm
    },
    data() {
      return {
        selectedForm: null,
        isSubmitting: false
      };
    },
    methods: {
      handleOverlayClick() {
        this.$emit('close');
      },
      selectForm(formType) {
        this.selectedForm = formType;
      },
      async submitProduct(product) {
        if (this.isSubmitting) return; // Previna múltiplos envios
        
        this.isSubmitting = true; // Desabilita o botão durante o envio
  
        try {
          const response = await fetch('http://localhost:8080/api/produtos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
          });
  
          if (!response.ok) {
            throw new Error('Erro ao criar o produto');
          }
  
          const data = await response.json();
          console.log('Produto criado:', data);
          this.$emit('close'); // Fecha o modal após criar o produto
        } catch (error) {
          console.error('Erro ao criar o produto:', error);
        } finally {
          this.isSubmitting = false; // Reativa o botão após a resposta
        }
      }
    }
  };
  </script>
  
  
  <style scoped>
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 400;
    backdrop-filter: blur(10px);
  }
  
  .modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 2rem;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    width: 80%;
    height: 80%;
  }
  </style>
  