<template>
  <header class="header">
    <div class="topheader">
      <nav class="nav">
        <!-- Link para Produtos com a troca de imagem baseada na rota ativa -->
        <router-link to="/products" active-class="active-link" exact-active-class="exact-active-link">
          <!-- Usando v-if para trocar a imagem baseada no estado da rota ativa -->
          <img 
            v-if="$route.path === '/products'" 
            src="@/assets/produtoselected.png" 
            alt="Produtos" 
          />
          <img 
            v-else 
            src="@/assets/icons8-coffee-beans-90(1).png" 
            alt="Produtos" 
          />
          Produtos
        </router-link>

        <!-- Link para Receitas sem mudanças -->
        <router-link to="/cart" active-class="active-link" exact-active-class="exact-active-link">
          <img src="@/assets/icons8-repository-64.png" alt="Receitas" />
          Receitas
        </router-link>
      </nav>

      <form @submit.prevent="handleSearchSubmit">
        <div class="search-container">
          <button type="submit" class="search-button">
            <i class="fas fa-search"></i>
          </button>
          <input type="text" placeholder="Buscar grãos, métodos e muito mais..." class="search-input"
            v-model="searchQuery" @input="handleSearch" />
          <button type="button" class="clear-button" v-if="searchQuery" @click="clearSearch">
            <i class="fas fa-times"></i>
          </button>
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
        <div class="user-dropdown">
          <button class="action-button user-button" @click="toggleDropdown">
            <img src="@/assets/user.png" alt="User" />
          </button>
          <div class="dropdown-content" v-if="dropdownVisible">
            <div v-if="isAuthenticated">
              <router-link to="/profile">Perfil</router-link>
              <router-link to="/settings">Configurações</router-link>
              <button @click="handleLogout">Sair</button>
            </div>
            <div v-else>
              <a :href="`${baseURL}/login`">Login</a>
              <a :href="`${baseURL}/register`">Registrar</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="divnav"></div>
  </header>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  data() {
    return {
      searchQuery: "",
      dropdownVisible: false,
      baseURL: import.meta.env.VITE_API_BASE_URL, // Mantendo o baseURL
    };
  },
  computed: {
    ...mapGetters('auth', ['isAuthenticated', 'user']),
  },
  methods: {
    ...mapActions('auth', ['checkAuthentication', 'logout']),
    handleSearchSubmit() {
      this.$emit('search', this.searchQuery);
    },
    handleSearch() {
      this.$emit('search', this.searchQuery);
    },
    clearSearch() {
      this.searchQuery = "";
      this.handleSearch();
    },
    toggleDropdown() {
      this.dropdownVisible = !this.dropdownVisible;
    },
    handleLogout() {
      this.logout();
    },
    goToHome() {
      this.$router.push('/');
    },
  },
  mounted() {
    this.checkAuthentication();
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
  font-size: 2rem !important;
  flex-direction: column;
  position: fixed;
  width: 98vw;
  height: 6.7rem;
  z-index: 200;
}

.topheader {
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
}

.logo {
  height: 5rem;
  margin-left: 5rem;
  filter: invert(1);
}

.logo-container {
  width: 15%;
}

a {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  color: var(--text-color);
  padding: 0.5rem;
  padding-left: 1.5rem;
  padding-right: 1.5rem;
  border-radius: 2rem;
  transition: transform 0.3s ease, color 0.3s ease;
  font-size: 2rem !important;
}

.nav {
  display: flex;
  margin: 0 15px;
  flex-direction: row;
  gap: 1rem;
}

a img {
  width: 2rem;
  margin-right: 0.5rem;
}

a:hover {

  transform: scale(1.05);
}

.active-link {
  background: #c4ceff;
  color: #3a5bff;
}

.search-container {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  position: relative; /* Adicionando posição relativa para o container */
}

.search-button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  margin-right: 1rem;
}

.search-button i {
  font-size: 2rem;
  color: #505050;
}

form {
  width: 42%;
}

.search-input {
  border: solid 1px #aeaeaeb6;
  background: #ededed;
  border-radius: 2rem;
  width: 100%;
  color: var(--text-color);
  height: 5rem;
  padding-left: 1rem !important;
  font-size: 2rem !important;
  outline: none;
  padding: 0;
}

.search-input::placeholder {
  color: #9d9d9d !important;
}

.clear-button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: #505050;
  font-size: 1.5rem;
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
  width: 3rem;
  height: 3rem;
  filter: invert(1);
}

/* Estilização da parte do perfil */
.user-dropdown {
  position: relative;
}

.dropdown-content {
  display: block;
  position: absolute;
  background-color: white;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a, .dropdown-content button {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover, .dropdown-content button:hover {
  background-color: #ddd;
}
</style>
