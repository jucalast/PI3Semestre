<template>
  <DefaultLayout>
    <div class="checkout-page flex flex-col md:flex-row h-full p-4">
      
      <!-- ESQUERDA -->
      <div class="left-section flex flex-col w-full md:w-2/3 space-y-4 h-full">

        <!-- SUPERIOR -->
        <div class="section bg-gray-200 flex-1 p-4 rounded-lg shadow-md h-1/2">
          <p>Esquerda, cima</p>
        </div>

        <!-- INFERIOR -->
        <div class="section flex-1 h-1/2">
          <AddressSelector />
        </div>
      </div>

      <!-- DIREITA -->
      <div class="right-section w-full md:w-2/5 pl-4">
        <div class="section bg-gray-400 h-full p-4 rounded-lg shadow-md">
          <p>Direita</p>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script>
import DefaultLayout from '@/layouts/DefaultLayout.vue';
import AddressSelector from '@/components/AddressSelector.vue';

export default {
  components: {
    DefaultLayout,
    AddressSelector,
  },
};
</script>

<style scoped>
.checkout-page {
  height: calc(100vh - 100px);
}
.section {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}
</style>
