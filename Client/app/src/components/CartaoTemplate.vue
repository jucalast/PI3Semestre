<template>
  <div class="card-container" :style="{ backgroundColor: cardColor }">
    <!-- Número do Cartão -->
    <div class="card-number">{{ formattedCardNumber }}</div>

    <!-- Nome e Validade -->
    <div class="card-details">
      <span class="card-holder">{{ cardDetails.name || 'NOME DO TITULAR' }}</span>
      <span class="card-expiry">{{ cardDetails.expiry || 'MM/AA' }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "cartaoTemplate",
  props: {
    cardDetails: {
      type: Object,
      default: () => ({
        number: '',
        name: '',
        expiry: ''
      })
    }
  },
  computed: {
    // Formata o número do cartão com espaços a cada 4 dígitos
    formattedCardNumber() {
      return this.cardDetails.number
          ? this.cardDetails.number.replace(/\s+/g, '').replace(/(\d{4})/g, '$1 ').trim()
          : '**** **** **** ****'; // Exibe asteriscos se o número estiver vazio
    },
    // Determina a cor do cartão com base no número
    cardColor() {
      const bin = this.cardDetails.number.slice(0, 6); // Pega os primeiros 6 dígitos
      switch (bin) {
        case '516292':
        case '531353':
        case '548935':
          return 'deepskyblue'; // Nubank
        case '549167':
        case '544731':
        case '536773':
          return 'orange'; // Itaú
        case '527571':
        case '506699':
        case '540033':
          return 'green'; // Bradesco
        default:
          return 'gray'; // Cor padrão
      }
    }
  }
};
</script>

<style scoped>
.card-container {
  width: 300px;
  height: 180px;
  border-radius: 8px;
  padding: 20px;
  color: white;
  font-family: Arial, sans-serif;
  display: flex;
  flex-direction: column;
  justify-content: flex-end; /* Alinha os dados na parte inferior do cartão */
  position: relative;
}
.card-number {
  font-size: 20px;
  letter-spacing: 2px;
  margin-bottom: 10px;
}
.card-details {
  display: flex;
  justify-content: space-between;
}
.card-holder {
  font-size: 14px;
  text-transform: uppercase;
}
.card-expiry {
  font-size: 14px;
}
</style>
