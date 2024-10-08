<template>
  <header class="header">
    <div class="topheader">
      <nav class="nav">
        <a  href="/products"><img src="@/assets/icons8-coffee-beans-90(1).png" alt="Produtos" /> Produtos</a>
        <a href="/cart"><img src="@/assets/icons8-repository-64.png" alt="Receitas" />receitas</a>
      </nav>
      <div id="searchandnav">
        <div class="search-container">
          <input
            type="text"
            placeholder="Buscar grãos, métodos e muito mais..."
            class="search-input"
          />
        </div>
      </div>
      <div class="logo-container">
        <img src="@/assets/logo.png" alt="Logo" class="logo" />
      </div>
      <div class="action-buttons">
        <button
          class="action-button favorite-button"
          @click="handleFavoriteClick"
        >
          <img src="@/assets/estrela.png" alt="Favorites" />
        </button>
        <button class="action-button cart-button" @click="handleCartClick">
          <img src="@/assets/carrinho.png" alt="Cart" />
        </button>
        <button class="action-button user-button" @click="handleUserClick">
          <img src="@/assets/user.png" alt="User" />
        </button>
      </div>
    </div>
    <div class="divnav"></div>
  </header>
</template>

<script>
export default {
  name: "Header",
};
</script>

<style scoped>
@import "@/assets/css/variables.css";

.header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  padding: 1rem;
  padding-bottom: 0;
  margin-top: 0.5rem;
  background-color: var(--background-color);
  margin: 0;
  font-size: 1rem !important;
  flex-direction: column;
}
.topheader {
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
}

.logo {
  height: 50px;
  margin-left: 1rem;
  filter: invert(1);
}

.logo-container {
  width: 15%;
}

.nav {
  gap: 0.1rem;
}

a {

  display: flex;
  flex-direction: row;
    align-items: center;
    justify-content: center;
    text-decoration: none;
  color: var(--text-color);
  padding: 0.5rem;
  padding-left: 0.7rem;
  padding-right: 1rem;

  border-radius: 2rem;

  font-size: 1rem !important;
}

.nav  {
  display: flex;
  margin: 0 15px;
  flex-direction: row ;
}

a img {
  width: 30px;
  margin-right: 0.5rem;
}

a:hover {
  background: var(--inputs-color);
}

#searchandnav {
  width: 30%;
}

.search-container {
  flex: 2;
  display: flex;
  justify-content: flex-end;
}

.search-input {
  border: none;
  background: var(--inputs-color);
  border-radius: 2rem;
  width: 100%;
  color: #3f3f3f;
  height: 3rem;
  padding-left: 1rem !important;
  font-size: 1rem !important;
  outline: none;
  padding: 0;
}

.search-input:focus {
  color: #292929; /* Altere a cor do contorno conforme necessário */
}

.search-input::placeholder {
  color: #3f3f3f;
}

.action-buttons {
  display: flex;
  align-items: center;
  justify-content: space-around;
  background: #c6c6c6;
  border-radius: 2rem;
  height: 3rem;
  width: 13%;
  padding-left: 0.5rem !important;
  padding-right: 0.5rem !important;
}

.action-button {
  justify-content: center;
  background: transparent;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0.1rem;
  border-radius: 50%;
}

.action-button img {
  width: 1.5rem;
  height: 1.5rem;
  filter: invert(1);
}
</style>
