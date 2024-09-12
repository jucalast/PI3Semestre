<template>
    <header>
      <h1>My E-Commerce</h1>
      <nav>
        <a href="/">Home</a>
        <a href="/products">Products</a>
        <a href="/cart">Cart</a>
      </nav>
    </header>
  </template>
  
  <script>
  export default {
    name: 'Header'
  }
  </script>
  
  <style scoped>
  header {
    background-color: var(--primary-color);
    color: white;
    padding: 10px;
    text-align: center;
  }
  nav a {
    color: white;
    margin: 0 15px;
    text-decoration: none;
  }
  </style>
  