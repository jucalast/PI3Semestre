<template>
  <div class="floating-header">
    <button @click="$emit('open-image-upload')" class="customize-button"><i class="fas fa-sliders-h"></i></button>
    <button @click="$emit('open-create-product')" class="customize-button"><i class="fas fa-plus"></i></button>
  </div>
</template>

<script>
export default {
  name: 'FloatingHeader',
};
</script>

<style scoped>
.floating-header {
  
  position: fixed;
  top: 30%;
  gap: 1rem !important;
  display: flex;
  flex-direction: column;
}

button {
  display: flex;
  flex-direction: column;
  background: #00000017 !important;
  transform: translateY(-50%);
  border-radius: 50%;
  padding: 1rem;
  width: 7rem;
  justify-content: center;
    align-items: center;
    width: 6rem;
    height:6rem; 
}

.customize-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 2rem;
  color: #ffffff;
}

.customize-button:hover {
  color: #37458b;
}
</style>