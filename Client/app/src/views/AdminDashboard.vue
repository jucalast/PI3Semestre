<template>
  
  <AdminLayout>
    <img src="@/assets/backgroundadm.png" alt="">
    <section class="centered-section">

      <h1>Tudo na palma das suas mãos</h1>
      <p>Explore e gerencie todos seu negócio com facilidade e rapidez.</p>
    </section>
    <section class="principal-section">
    <CreateProductComponent />
  </section>
    <!-- Outros conteúdos do dashboard -->
  </AdminLayout>
</template>

<script>
import AdminLayout from '@/layouts/AdminLayout.vue';
import CreateProductComponent from '@/components/CreateProductComponent.vue';

export default {
  name: 'AdminDashboard',
  components: {
    AdminLayout,
    CreateProductComponent,
  },
};
</script>

<style scoped>
.centered-section {
  margin-top: 6rem;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
  padding: 2rem;

}

.principal-section {
  background: white;
  height: 100vh;
  margin: 5rem;
  padding: 5rem;
  border-radius: 2rem;
}

h1 {
  font-size: 12rem;
  line-height: 0.8;
  color: #1b1b1b;
  letter-spacing: -0.05em; /* Ajuste este valor conforme necessário */
}


img {
  position: absolute;
  width: 100%;
  z-index: -1;
}

p {
  font-size: 3rem;
  margin-top: 1rem;
  background: transparent;
  width: 80%;
  backdrop-filter: blur(0);
  color: #3a5bff;
}
</style>
