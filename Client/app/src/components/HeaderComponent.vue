<template>
  <header class="header">
    <div class="topheader">
      <!-- Parte da esquerda -->
      <nav class="nav">
        <router-link
          to="/products"
          active-class="active-link"
          exact-active-class="exact-active-link"
        >
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

        <router-link
          to="/cart"
          active-class="active-link"
          exact-active-class="exact-active-link"
        >
          <img src="@/assets/icons8-repository-64.png" alt="Receitas" />
          Receitas
        </router-link>
      </nav>

      <!-- Caixa de pesquisa -->
      <form @submit.prevent="handleSearchSubmit">
        <div class="search-container">
          <button type="submit" class="search-button">
            <i class="fas fa-search"></i>
          </button>
          <input
            type="text"
            placeholder="Buscar grãos, métodos e muito mais..."
            class="search-input"
            v-model="searchQuery"
            @input="handleSearch"
          />
          <button
            type="button"
            class="clear-button"
            v-if="searchQuery"
            @click="clearSearch"
          >
            <i class="fas fa-times"></i>
          </button>
        </div>
      </form>

      <!-- Logo -->
      <div class="logo-container" @click="goToHome">
        <img src="@/assets/logo.png" alt="Logo" class="logo" />
      </div>

      <!-- Parte da direita -->
      <div class="action-buttons">
        
        <!-- Favoritos -->
        <div
          class="favorites-container"
          @mouseover="handleFavoriteHover"
          @mouseleave="handleFavoriteLeave"
        >
          <button class="action-button favorite-button">
            <font-awesome-icon icon="star" class="favorite-icon" />
          </button>
          <div v-if="showModal" class="modal">
            <div class="headerfav">favoritos:</div>
            <div v-if="!authenticated">
              <p>Você precisa estar logado.</p>
              <button
                @click="redirectToLogin"
                style="
                  background: #2ecc71;
                  color: white;
                  border-radius: 20px;
                  padding: 10px;
                "
              >
                Login
              </button>
            </div>
            <div v-else class="product-scroll-container">
              <div>
                <div
                  v-for="product in favorite_products"
                  :key="product.id"
                  class="product-card"
                >
                  <div class="product-image">
                    <img :src="product.imagem" alt="Imagem do Produto" />
                  </div>
                  <div class="product-details">
                    <div class="nameandprice">
                      <h4>{{ product.nome }}</h4>

                      <button
                        class="excluir"
                        @click="deleteProduct(product.id)"
                        style="color: darkred"
                      >
                        Excluir
                      </button>
                    </div>
                    <div>
                      <p>{{ product.preco.toFixed(2) }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <button
              v-if="authenticated"
              @click="goToFavorites"
              class="all-favorites-button"
            >
              Ver todos os favoritos
            </button>
          </div>
        </div>

        <!-- Carrinho -->
        <button class="action-button cart-button" @click="handleCartClick">
          <img src="@/assets/carrinho.png" alt="Cart" />
        </button>

        <!-- Perfil -->
        <div class="user-dropdown">
          <button class="action-button user-button" @click="toggleDropdown">
            <span v-if="isAuthenticated" class="user-name">
              {{ username }}
            </span>
            <img :src="getUserAvatar()" alt="User" />
          </button>
          <div class="dropdown-content" v-if="dropdownVisible">
            <div v-if="isAuthenticated" class="useroptions">
              <router-link to="/profile">Perfil</router-link>
              <router-link to="/settings">Configurações</router-link>
              <router-link v-if="isAdmin" to="/manage">Gerenciar</router-link>
              <button class="exitbtn" @click="handleLogout">
                Sair
                <img src="@/assets/icons8-sair-96.png" alt="" />
              </button>
            </div>
            <div v-else>
              <router-link to="/login">Login</router-link>
              <router-link to="/signin">Registrar</router-link>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Conteúdo lateral -->
    <div class="divnav"></div>
    <CartModal
      :isModalVisible="isCartModalVisible"
      @close="isCartModalVisible = false"
    ></CartModal>
  </header>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import axiosInstance from '@/utils/axiosInstance';
import { updateFavorites } from '@/state.js';
import CartModal from './CartModal.vue';
import axios from 'axios';

export default {
  components: {
    CartModal,
  },
  data() {
    return {
      searchQuery: '',
      dropdownVisible: false,
      showModal: false,
      authenticated: true,
      favorite_products: [],
      baseURL: import.meta.env.VITE_API_BASE_URL,
      isCartModalVisible: false,
    };
  },
  computed: {
    ...mapGetters('auth', [
      'isAuthenticated',
      'isAdmin',
      'user',
      'photoUrl',
      'username',
      'roles',
    ]),
  },
  methods: {
    ...mapActions('auth', ['checkAuth', 'logout']),
    handleSearchSubmit() {
      this.$emit('search', this.searchQuery);
    },
    handleSearch() {
      this.$emit('search', this.searchQuery);
    },
    clearSearch() {
      this.searchQuery = '';
      this.handleSearch();
    },
    toggleDropdown() {
      this.dropdownVisible = !this.dropdownVisible;
    },
    handleLogout() {
      axiosInstance
        .post('/logout')
        .then(() => {
          localStorage.clear();
          this.logout();
          this.authenticated = false;
          window.location.reload();
        })
        .catch((error) => {
          console.error('Erro ao tentar fazer logout', error);
          this.$router.push('/login');
        });
    },
    goToHome() {
      this.$router.push('/');
    },
    goToFavorites() {
      this.$router.push('/favorites');
    },
    handleFavoriteLeave() {
      this.showModal = false;
      console.log('Mouse leave');
    },
    async handleFavoriteHover() {
      try {
        const response = await axiosInstance.get(
          `/api/favorites/favorited-products`
        );
        if (response.data.length) {
          this.favorite_products = response.data;
          this.authenticated = true;
          this.showModal = true;
          console.log(this.favorite_products);
        }
      } catch (error) {
        console.error('Erro ao verificar a autenticação do usuário', error);
        if (error.response && error.response.status === 401) {
          this.authenticated = false;
          this.showModal = true;
        } else {
          this.showModal = false;
        }
      }
    },
    async deleteProduct(productId) {
      try {
        const response = await axiosInstance.delete(
          `/api/favorites/remove?productId=${productId}`
        );
        if (response.status === 200) {
          this.favorite_products = this.favorite_products.filter(
            (product) => product.id !== productId
          );
          updateFavorites(this.favorite_products);
        }
      } catch (error) {
        console.error('Erro ao excluir o produto', error);
      }
    },
    getUserAvatar() {
      if (this.isAuthenticated) {
        if (this.photoUrl) {
          return this.photoUrl;
        }
        return `https://avatars.dicebear.com/api/shapes/${this.username}.svg`;
      } else {
        return '/src/assets/user.png';
      }
    },
    redirectToLogin() {
      window.location.href = `${this.baseURL}/login`;
    },
    async handleCartClick() {
      const responseCart = await axios.get(
        'http://localhost:8080/api/check-auth'
      );
      responseCart.status === 200
        ? (this.isCartModalVisible = !this.isCartModalVisible)
        : (window.location.href = 'http://localhost:8080/login');
    },
  },
  mounted() {
    this.checkAuth();
  },
};
</script>

<style scoped>
@import '@/assets/css/variables.css';

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
  margin-left: 1rem;
  filter: invert(1);
}

.logo-container {

}

.exitbtn {
  display: flex !important;
  border: none !important;
  justify-content: space-between !important;
}

.exitbtn img {
  width: 2rem;
}

a {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  color: var(--text-color);
  padding: 0.5rem;
  /* padding-left: 1.5rem;
  padding-right: 1.5rem; */
  border-radius: 2rem;
  transition: transform 0.3s ease, color 0.3s ease;
  font-size: 2rem !important;
}

.nav {
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
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
  position: relative;
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
  width: 17%;
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
  margin: 0.1rem 0.1rem; 
  border-radius: 50%;
}

.action-button img {
  width: 3rem;
  height: 3rem;
  filter: invert(1);
}

.user-dropdown {
  position: relative;
}

.user-dropdown .user-button {
  display: flex;
  align-items: center;
}

.user-dropdown .user-name {
  margin-left: 10%;
  margin-right: 10%;
  font-weight: bold;
  color: #333;
  font-size: 1rem;
}

.user-button img {  
  width: 60px;
  height: 60px;
  border-radius: 50%;
  object-fit: cover;
}

.dropdown-content {
  display: block;
  right: -2rem;
  top: 5rem;
  position: absolute;
  background-color: #ededed;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
  border: 1px solid #c1c1c1;
  border-radius: 2rem;
  font-size: 1rem !important;
}

.dropdown-content a,
.dropdown-content button {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: flex;
  justify-content: center;
  font-size: 1.5rem !important;
  width: 100%;
  border-radius: 0;
}

.dropdown-content a:not(:last-child),
.dropdown-content button:not(:last-child) {
  border-bottom: 1px solid #9d9d9d;
}

.favorite-icon:hover {
  font-size: 2.5rem;
}

.modal {
  position: absolute;
  right: 2px;
  top: 7rem;
  width: 30%;
  max-height: 80vh;
  background-color: white;
  padding: 10px;
  border: 1px solid #ccc;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  z-index: 10;
  display: flex;
  flex-direction: column;
  border-radius: 2rem;
}

.product-scroll-container {
  overflow-y: auto;
  overflow-x: hidden;
  flex-grow: 1;
}

.all-favorites-button {
  width: 100%;
  padding: 10px;
  background-color: transparent;
  color: #3a5bff;
  border: none;
  cursor: pointer;

  font-size: 1.3rem;
  display: flex;
}

.excluir {
  margin-top: 1rem;
  color: #ff4d4d !important;
  background: transparent;
  border: none;
  padding-right: 1rem;
  padding-left: 1rem;
  border-radius: 2rem;
}

.excluir:hover {
  background: #ff4d4d2f;
}

.headerfav {
  padding: 1rem;
}

.product-card {
  display: flex;
  height: auto;
  margin: 5px;
  border: none;
  border-radius: 2rem;
  background: #dfdfdf !important;
  padding: 1rem;
  gap: 2rem;
}

.product-image img {
  width: 5rem !important;
  height: auto;
}

.product-details {
  color: black;
  display: flex;
  margin-right: 0.5em;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-start;
  width: 100%;
}


</style>
