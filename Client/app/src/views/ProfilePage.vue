<template>
  <DefaultLayout>
    <div class="profile-container">
      <div class="profile-content">
        <div class="profile-photo">
          <img :src="getUserAvatar()" alt="Foto do usuário" />
          <p class="user-name">{{ user.name || username }}</p> 
        </div>

        <!-- Formulário de perfil -->
        <div class="profile-form">
          <form @submit.prevent="updateProfile">
            <div class="form-section">
              <label for="name">Nome</label>
              <input type="text" v-model="user.name" id="name" />
            </div>
            <div class="form-section">
              <label for="phone">Telefone</label>
              <input type="text" v-model="user.phone" id="phone" />
            </div>
            <div class="form-section">
              <label for="current-password">Senha Atual</label>
              <input
                type="password"
                v-model="user.currentPassword"
                id="current-password"
              />
            </div>
            <div class="form-section">
              <label for="new-password">Nova Senha</label>
              <input
                type="password"
                v-model="user.newPassword"
                id="new-password"
              />
            </div>
            <button type="submit" class="btn-submit">Salvar</button>
          </form>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script>
import DefaultLayout from '@/layouts/DefaultLayout.vue';
import { mapGetters, mapActions } from 'vuex';
import axiosInstance from '@/utils/axiosInstance';

export default {
  components: {
    DefaultLayout,
  },
  data() {
    return {
      user: {
        name: '',
        phone: '',
        currentPassword: '',
        newPassword: '',
      },
    };
  },
  computed: {
    ...mapGetters('auth', ['user', 'photoUrl', 'username', 'isAuthenticated']),
  },
  methods: {
    ...mapActions('auth', ['updateUserProfile']),
    async updateProfile() {
      const updatedUser = {};

      if (this.user.name) updatedUser.userName = this.user.name;
      if (this.user.phone) updatedUser.mobileNumber = this.user.phone;
      if (this.user.currentPassword)
        updatedUser.currentPassword = this.user.currentPassword;
      if (this.user.newPassword)
        updatedUser.newPassword = this.user.newPassword;

      try {
        await axiosInstance.put('/api/profile', updatedUser);
        alert('Perfil atualizado com sucesso!');
      } catch (error) {
        console.error('Erro ao atualizar perfil:', error);
        alert('Erro ao atualizar perfil.');
      }
    },
    getUserAvatar() {
      if (this.isAuthenticated) {
        if (this.photoUrl) {
          return this.photoUrl;
        }
        return `https://api.dicebear.com/9.x/thumbs/svg?seed=${this.username}`;
      } else {
        return '/src/assets/user.png';
      }
    },
  },
  async mounted() {
    if (this.isAuthenticated) {
      try {
        const response = await axiosInstance.get('/login/user-info');
        const userInfo = response.data;

        // Atualizar os dados do usuário com a resposta da API
        this.user.name = userInfo.name || this.user.name;
        this.user.phone = userInfo.phone || this.user.phone;
      } catch (error) {
        console.error('Erro ao buscar informações do usuário:', error);
      }
    }
  },
};
</script>

<style scoped>
  @import url("https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap");

  .profile-page {
    font-family: 'Poppins', sans-serif;
  }

  .input-field {
    margin-top: 0.25rem;
    display: block;
    width: 100%;
    height: 3rem;
    border: 1px solid #d1d5db;
    border-radius: 0.375rem;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
    padding: 0.5rem;
    font-size: 1rem;
  }

  .input-field:focus {
    border-color: #d97706;
    outline: none;
    box-shadow: 0 0 0 0.2rem rgba(255, 165, 0, 0.25);
  }

  h2 {
    font-size: 2rem;
    font-weight: 600;
    margin-bottom: 1rem;
  }

  label {
    font-size: 1rem;
    color: #4a5568;
  }

  button {
    font-size: 1rem;
    padding: 0.75rem 1.5rem;
    border-radius: 0.375rem;
  }
</style>
