<template>
  <footer>
    <p>&copy; 2024 My E-commerce</p>
  </footer>
</template>

<script>
export default {
  name: 'FooterComponent',
};
</script>

<style scoped>
footer {
  background-color: #212121;
  color: white;
  text-align: center;
  padding: 10px;
  /* Remova a margem superior para evitar espaçamento indesejado */
  margin-top: 0; 
  width: 100vw;
  height: 10rem;
}

footer p {
  background: transparent;
}
</style>
