<!-- src/components/HeaderComponent.vue -->
<template>
  <header>
    <h1>My E-commerce</h1>
    <nav>
      <ul>
        <li><router-link to="/">Home</router-link></li>
        <li><router-link to="/products">Products</router-link></li>
        <li><router-link to="/cart">Cart</router-link></li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'HeaderComponent',
};
</script>

<style scoped>
@import '@/assets/css/variables.css';

header {
  background-color: var(--bacground-color);
  color: var(  --accent-color);
  padding: 2rem;
  margin: 0;

}

nav ul {
  display: flex;
  list-style: none;
}

nav ul li {
  margin-right: 20px;
}
</style>
