
<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <h2>Compra realizada com sucesso!</h2>
      <p>Sua compra foi realizada com sucesso. A data prevista de entrega é {{ deliveryDate }}.</p>
      <button @click="$emit('close')">OK</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
    deliveryDate: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index:5;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 1rem;
  text-align: center;
}

button {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background: #3a5bff;
  color: white;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
}
</style>