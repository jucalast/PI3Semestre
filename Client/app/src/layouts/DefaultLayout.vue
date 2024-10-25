<template>
  <div class="layout">
    <Header @search="handleSearch" />
    <main>
      <slot></slot>
    </main>
    <FooterComponent />
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import CartModal from '@/components/CartModal.vue';
import FooterComponent from '@/components/FooterComponent.vue';

export default {
  components: {
    Header,
    FooterComponent,
    CartModal
  },
  methods: {
    handleSearch(query) {
      this.$emit('search', query);
    }
  }
}
</script>

<style scoped>
.layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* Faz o layout ocupar pelo menos 100% da altura da janela */
}

main {
  flex: 1; /* Faz o main ocupar o espaço restante entre o header e o footer */
}
</style>
