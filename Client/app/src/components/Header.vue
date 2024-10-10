<template>
  <header class="header">
    <div class="topheader">
      <nav class="nav">
        <router-link to="/products" active-class="active-link" exact-active-class="exact-active-link">
          <img src="@/assets/icons8-coffee-beans-90(1).png" alt="Produtos" />
          Produtos
        </router-link>
        <router-link to="/cart" active-class="active-link" exact-active-class="exact-active-link">
          <img src="@/assets/icons8-repository-64.png" alt="Receitas" />
          Receitas
        </router-link>
      </nav>

      <form @submit.prevent="fetchFilteredProducts">
        <div class="search-container">
          <input
            type="text"
            placeholder="Buscar grãos, métodos e muito mais..."
            class="search-input"
            v-model="searchQuery"
            @input="handleSearch"
          />
          <button type="submit">Buscar</button>
        </div>
      </form>

      <div class="logo-container" @click="goToHome">
        <img src="@/assets/logo.png" alt="Logo" class="logo" />
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
    </div>
    <div class="divnav"></div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: "", 
      produtos: [],    
    };
  },
  methods: {
    async fetchFilteredProducts() {
      try {
        console.log("Realizando busca com a query:", this.searchQuery);
        const response = await fetch(`http://localhost:8080/api/produtos/search?search=${this.searchQuery}`);
        const data = await response.json();
        console.log("Produtos retornados pela API:", data);
        this.produtos = data;
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      }
    },
    handleSearchSubmit() {
      console.log("Submissão de pesquisa detectada!");
      this.fetchFilteredProducts();
    },
    handleSearch() {
      this.$emit('search', this.searchQuery);
    },
  },
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

/* Remova ou mantenha o estilo do a e do router-link como necessário */
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
  transition: transform 0.3s ease, color 0.3s ease;
  font-size: 1rem !important;
}

.nav {
  display: flex;
  margin: 0 15px;
  flex-direction: row;
  gap: 1rem;
}

a img {
  width: 1.5rem;
  margin-right: 0.5rem;
}

a:hover {
  background: var(--inputs-color);
  transform: scale(1.1); /* Aumenta o ícone em 20% */
}

/* Estilo para o link ativo */
.active-link {
  background: #c4ceff; /* Cor azul quando ativo */
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
  color: var(--text-color);
  height: 3rem;
  padding-left: 1rem !important;
  font-size: 1rem !important;
  outline: none;
  padding: 0;
}

.search-input:focus {
  color: var(--text-color); /* Altere a cor do contorno conforme necessário */
}

.search-input::placeholder {
  color: var(--text-color);
}

header .action-buttons {
  display: flex;
  align-items: center;
  justify-content: space-around;
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
