<template>
  <header class="header">
    <div class="logo-container">
      <img src="@/assets/logo.png" alt="Logo" class="logo" />
    </div>
    <div id="searchandnav">
    <div class="search-container">
      <input type="text" placeholder="Buscar grãos, métodos e muito mais..." class="search-input" />
    </div>
    <nav class="nav">
      <a href="/">Home</a>
      <a href="/products">Products</a>
      <a href="/cart">Cart</a>
    </nav>
  </div>
  <div class="action-buttons">
    <button class="action-button favorite-button" @click="handleFavoriteClick">
      <img src="@/assets/estrela.png" alt="Favorites" />
    </button>
    <button class="action-button cart-button" @click="handleCartClick">
      <img src="@/assets/carrinho.png" alt="Cart" />
    </button>
    <button class="action-button user-button" @click="handleUserClick">
      <img src="@/assets/user.png" alt="User" />
    </button>
  </div>
  </header>
</template>

<script>
export default {
  name: 'Header'
}
</script>

<style scoped>
.header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  padding: 1rem;
  margin-top: 0.5rem;
  background-color: var(--background-color);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-size: 1rem !important;
}

.logo {
  height: 50px;
  margin-left: 1rem;
}

.nav {
  flex: 2;
  display: flex;
  justify-content: center;
  gap: 0.1rem;
  
}

.nav a {
  margin: 0 15px;
  text-decoration: none;
  color: #333;
  background: #191919;
  padding: 0.5rem;
  padding-left: 0.7rem;
  padding-right: 0.7rem;
  margin-top: 1rem !important;
  border-radius: 2rem;
  margin: 0.1rem;
  font-size: 1rem !important;
}

#searchandnav {
  width:50% ;
}

.search-container {
  flex: 2;
  display: flex;
  justify-content: flex-end;
}

.search-input {
  padding: 0.5rem;
  border: 1px solid #232323;
  background: #232323;
  border-radius: 2rem;
  width: 100%;
  color: #3f3f3f;
  height: 1.5rem;
  padding-left: 1rem;
  font-size: 1rem !important;
  outline: none;
}

.search-input:focus {
  color: #f0f0f0; /* Altere a cor do contorno conforme necessário */
}

.search-input::placeholder {
  color: #3f3f3f;
}

.action-buttons {
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 0.1rem;
  background: #202020;
  border-radius: 3rem;
}

.action-button {
  justify-content: center;
  background: #303030;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0.1rem;
  border-radius: 50%;
  padding: 0.5rem;
}

.action-button img {
  width: 20px;
  height: 20px;
}

</style>
