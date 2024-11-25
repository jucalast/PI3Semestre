<template>
  <div class="section products equal-height flex-grow p-4">
    <div class="product-info">
      <h3>{{ productCount }} Produtos na Sacola</h3>
      <div class="product-images">
        <div v-for="(product, index) in productDetails" :key="index" class="product-display" :style="{ right: `${index * 2}rem` }">
          <div class="color-circle" :style="{ backgroundColor: getColorFromImage(product.imagens ? product.imagens[0] : '') }"></div>
          <img :src="product.imagens ? product.imagens[0] : ''" :alt="product.nome" class="product-checkout-image" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    productDetails: {
      type: Array,
      required: true
    },
    productCount: {
      type: Number,
      required: true
    }
  },
  methods: {
    getColorFromImage(imageSrc) {
      // Função para extrair a cor principal da imagem
      // Aqui você pode usar uma biblioteca como 'color-thief' para obter a cor dominante
      // Exemplo:
      // const colorThief = new ColorThief();
      // const img = new Image();
      // img.src = imageSrc;
      // img.crossOrigin = 'Anonymous';
      // img.onload = () => {
      //   const color = colorThief.getColor(img);
      //   return `rgb(${color[0]}, ${color[1]}, ${color[2]})`;
      // };
      // return 'rgb(0, 0, 0)'; // Cor padrão enquanto a imagem carrega
      return '#000'; // Substitua por lógica real para obter a cor da imagem
    }
  }
};
</script>

<style scoped>
.section {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 2rem !important;
  position: relative;
  justify-content: flex-end;
}

.products {
  background: #ffffff;
  flex-direction: column;
  height: 100% !important;
}

.product-images {
  display: flex;
  align-items: center;
  position: absolute;
  top: 10px;
  right: 10px;
  height: 100px;
}

.product-display {
  position: relative;
  transform: translateX(calc(-1 * var(--index) * 2rem));
  z-index: calc(100 - var(--index));
}

.product-checkout-image {
  width: 50px;
  height: auto;
}
h3 {
  width: 80%;
  font-size: 2.5rem;
  line-height: 1 !important;
  padding-bottom: 2rem;
  height: 100%;
  display: flex;
}

.product-info {
  width: 100%;
  text-align: center;
  margin-bottom: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.color-circle {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  position: absolute;
  top: 0;
  left: 0;
  transform: translate(-50%, -50%);
  z-index: -1; /* Mantém o círculo atrás da imagem */
}

</style>