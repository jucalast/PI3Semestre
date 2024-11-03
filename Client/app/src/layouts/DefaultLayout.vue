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
import Header from '@/components/HeaderComponent.vue';
import FooterComponent from '@/components/FooterComponent.vue';

export default {
  components: {
    Header,
    FooterComponent,
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
  padding-top: 6.7rem; /* João, meu consagrado, coloquei este padding p/ SEMPRE evitar q o conteúdo sobreponha o header, e deixei no layout p/ não ter que configurar isso em cada view, se for ajustar o tamanho do header muda aq tb */
}
</style>
