<!-- src/components/HeaderComponent.vue -->
<template>
  <header>
    <div class="header">
    <h1>My E-commerce</h1>
    <nav>
      <ul>
        <li><router-link to="/">Home</router-link></li>
        <li><router-link to="/products">Products</router-link></li>
        <li><router-link to="/cart">Cart</router-link></li>
      </ul>
    </nav>
  </div>
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
  background-color: var(--header-color);
  color: var(  --accent-color);
  padding: 1rem;
  margin: 0;

}

.header {
  display: flex;
  justify-content: space-between;
}

nav ul {
  display: flex;
  list-style: none;
}

nav ul li {
  margin-right: 20px;
  background: var(--accent-color);
  padding: 0.5rem;
  border-radius: 0.5rem;
  color: var(--accent-color);
}
</style>
