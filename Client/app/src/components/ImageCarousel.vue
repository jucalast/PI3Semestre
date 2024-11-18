<template>
  <div class="carousel">
    <div>
      <img
        v-for="(image, index) in images"
        v-show="currentImageIndex === index"
        :key="image"
        :src="image"
        class="carousel-image"
        :alt="'Imagem ' + (index + 1)"
      />
    </div>
    <div class="indicators">
      <span
        v-for="(image, index) in images"
        :key="index"
        :class="['indicator', { active: currentImageIndex === index }]"
        @click="currentImageIndex = index"
      ></span>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from "vue";

export default {
  props: {
    images: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    const currentImageIndex = ref(0);
    let intervalId;

    onMounted(() => {
      intervalId = setInterval(() => {
        currentImageIndex.value = (currentImageIndex.value + 1) % props.images.length;
      }, 5000);
    });

    onUnmounted(() => {
      clearInterval(intervalId);
    });

    return {
      currentImageIndex,
    };
  },
};
</script>

<style scoped>
.carousel {
  position: relative;
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  font-family: 'Arial', sans-serif; /* Definir padrão de fontes */
  font-size: 16px; /* Definir padrão de font-size */
}

.carousel-image {
  width: 100%;
  height: 100%; /* Alterado para ocupar 100% da altura */
  object-fit: cover; /* Ajustar a imagem para cobrir o container */
  border-radius: 10px;
}

.indicators {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.indicator {
  width: 10px;
  height: 10px;
  background-color: rgb(221, 221, 221);
  border-radius: 50%;
  margin: 0 5px;
  cursor: pointer;
}

.indicator.active {
  background-color: #5f75e4;
}
</style>