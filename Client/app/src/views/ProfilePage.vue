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
.profile-container {
  max-width: 90%;
  margin: 0 auto;
  padding: 20px;
}

.profile-content {
  display: flex;
  gap: 20%;
}

.profile-photo {
  flex: 1;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile-photo img {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  border: 2px solid #ddd;
  object-fit: cover;
  margin-bottom: 10px;
}

.profile-photo .user-name {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-top: 10px;
  text-align: center; 
}

.profile-photo button {
  margin-right: auto;
  padding: 8px 16px;
  background-color: #f4b400; 
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.profile-photo button:hover {
  background-color: #e0a400; 
}

.profile-form {
  flex: 2;
}

h3 {
  margin-bottom: 10px;
}

form {
  display: flex;
  flex-direction: column;
}

.form-section {
  margin-bottom: 15px;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.btn-submit {
  padding: 10px;
  background-color: #f4b400;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-submit:hover {
  background-color: #e0a400;
}

.success {
  color: green;
}

.error {
  color: red;
}
</style>
