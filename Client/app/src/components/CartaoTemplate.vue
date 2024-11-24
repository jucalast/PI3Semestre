<template>
  <div class="card-container" :style="{ background: cardColor }">
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
  data() {
    return {
      currentColor: 'linear-gradient(135deg, rgba(128, 128, 128, 0.5), rgba(128, 128, 128, 0.5))', // Cor padrão inicial
      finalColor: null // Cor final após 16 dígitos
    };
  },
  computed: {
    // Formata o número do cartão com espaços a cada 4 dígitos
    formattedCardNumber() {
      return this.cardDetails.number
          ? this.cardDetails.number.replace(/\s+/g, '').replace(/(\d{4})/g, '$1 ').trim()
          : '**** **** **** ****'; // Exibe asteriscos se o número estiver vazio
    },
    // Gera uma cor aleatória em gradiente a cada 4 números até 16 dígitos
    cardColor() {
      if (this.cardDetails.number && this.cardDetails.number.length % 4 === 0 && this.cardDetails.number.length <= 16) {
        const randomColor1 = Math.floor(Math.random() * 16777215).toString(16);
        const randomColor2 = Math.floor(Math.random() * 16777215).toString(16);
        this.currentColor = `linear-gradient(135deg, #${randomColor1}, #${randomColor2})`;
        this.$emit('updateColor', `#${randomColor1}`); // Emitir a cor principal do gradiente
        if (this.cardDetails.number.length === 16) {
          this.finalColor = this.currentColor;
        }
      }
      return this.finalColor || this.currentColor;
    }
  }
};
</script>

<style scoped>
.card-container {
  width: 60%;
  height: 120%;
  border-radius: 2rem;
  padding: 20px;
  color: white;
  font-family: Arial, sans-serif;
  display: flex;
  flex-direction: column;
  justify-content: flex-end; /* Alinha os dados na parte inferior do cartão */
  backdrop-filter: blur(20px); /* Adiciona o efeito de desfoque */
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
