<template>
  <DefaultLayout>
    <div class="profile-container">
      <div class="profile-content">
        <div class="profile-photo">
          <img :src="previewProfilePic || getUserAvatar()" alt="Foto do usuário" />
          <p class="user-name">{{ userForm.userName || username }}</p>
          <p class="user-email">{{ userForm.email || '' }}</p> 
          <button @click="selectProfilePicture" class="choose-photo-btn">Escolher Foto</button>
          <input
            type="file"
            id="file-input"
            ref="fileInput"
            accept="image/*"
            style="display: none;"
            @change="handleFileUpload"
          />
        </div>

        <div class="profile-form">
          <form @submit.prevent="updateProfile">
            <div class="form-section">
              <label for="name">Nome</label>
              <input 
                type="text" 
                v-model="userForm.userName" 
                id="name" 
                :placeholder="userForm.userName || 'Nome'" 
                @input="checkChanges"
              />
            </div>
            <div class="form-section">
              <label for="phone">Telefone</label>
              <input 
                type="text" 
                v-model="userForm.mobileNumber" 
                id="phone" 
                :placeholder="userForm.mobileNumber || 'Telefone'" 
                @input="checkChanges"
                v-mask="'(##) #####-####'"
              />
            </div>
            <div class="form-section">
              <label for="current-password">Senha Atual</label>
              <input
                type="password"
                v-model="userForm.currentPassword"
                id="current-password"
                @input="checkChanges"
              />
            </div>
            <div class="form-section">
              <label for="new-password">Nova Senha</label>
              <input
                type="password"
                v-model="userForm.newPassword"
                id="new-password"
                @input="checkChanges"
              />
            </div>

            <!-- Botão de salvar -->
            <button
              type="submit"
              class="btn-submit"
              :class="{ 'has-changes': hasChanges, 'no-changes': !hasChanges }"
              :disabled="!hasChanges"
            >
              {{ hasChanges ? 'Salvar Alterações' : 'Salvar' }}
            </button>
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
      userForm: {
        userName: '',
        mobileNumber: '',
        email: '',
        currentPassword: '',
        newPassword: '',
        profilePic: '',
      },
      initialUser: {}, 
      previewProfilePic: '', 
      hasChanges: false, 
    };
  },
  computed: {
    ...mapGetters('auth', ['user', 'photoUrl', 'username', 'isAuthenticated']),
  },
  methods: {
    ...mapActions('auth', ['updateUserProfile', 'checkAuth']),
    async updateProfile() {
      const updatedUser = {};

      if (this.userForm.userName) updatedUser.userName = this.userForm.userName;
      if (this.userForm.mobileNumber) updatedUser.mobileNumber = this.userForm.mobileNumber;
      if (this.userForm.currentPassword) updatedUser.currentPassword = this.userForm.currentPassword;
      if (this.userForm.newPassword) updatedUser.newPassword = this.userForm.newPassword;
      if (this.userForm.profilePic) updatedUser.profilePic = this.userForm.profilePic;

      if (Object.keys(updatedUser).length === 0) {
        alert('Não há alterações para salvar.');
        return;
      }

      try {
        await axiosInstance.put('/api/profile', updatedUser);
        alert('Perfil atualizado com sucesso!');
        this.hasChanges = false; 
      } catch (error) {
        console.error('Erro ao atualizar perfil:', error);
        alert('Erro ao atualizar perfil.');
      }
    },
    getUserAvatar() {
      if (this.isAuthenticated) {
        if (this.photoUrl) {
          // Check if photoUrl already contains a full URL or is base64 encoded
          if (this.photoUrl.startsWith('http') || this.photoUrl.startsWith('data:image')) {
            return this.photoUrl;
          }
          // Only add base64 prefix if it's not already there and seems to be base64 data
          if (!this.photoUrl.startsWith('data:image') && this.photoUrl.length > 0) {
            return `data:image/png;base64,${this.photoUrl}`;
          }
          return this.photoUrl;
        }
        return `https://api.dicebear.com/9.x/thumbs/svg?seed=${this.username}`;
      } else {
        return '/src/assets/user.png';
      }
    },
    selectProfilePicture() {
      this.$refs.fileInput.click();
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          this.previewProfilePic = reader.result;
          this.userForm.profilePic = reader.result.split(',')[1]; 
          this.checkChanges(); 
        };
        reader.readAsDataURL(file);
      }
    },
    checkChanges() {
      this.hasChanges = (
        this.userForm.userName !== this.initialUser.userName ||
        this.userForm.mobileNumber !== this.initialUser.mobileNumber ||
        this.userForm.currentPassword !== this.initialUser.currentPassword ||
        this.userForm.newPassword !== this.initialUser.newPassword ||
        this.userForm.profilePic !== this.initialUser.profilePic
      );
    },
  },
  async mounted() {
    if (this.isAuthenticated) {
      try {
        const response = await axiosInstance.get('/login/user-info');
        const userInfo = response.data;

        this.userForm.userName = userInfo.name || this.userForm.userName;
        this.userForm.mobileNumber = userInfo.phone || this.userForm.mobileNumber;
        this.userForm.email = userInfo.email || this.userForm.email;
        this.userForm.profilePic = userInfo.profilePic || this.userForm.profilePic;

        this.initialUser = { ...this.userForm };

        this.checkChanges();
      } catch (error) {
        console.error('Erro ao buscar informações do usuário:', error);
      }
    }
    this.checkAuth();
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap");

.profile-photo .user-email {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.profile-page {
  font-family: 'Poppins', sans-serif;
}

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

.profile-photo .choose-photo-btn {
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #f4b400;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.profile-photo .choose-photo-btn:hover {
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

/* Alterações de cor do botão com base nas mudanças */
.btn-submit.no-changes {
  background-color: #ccc;
}

.btn-submit.has-changes {
  background-color: #f4b400; /* Cor original do botão */
}

.success {
  color: green;
}

.error {
  color: red;
}
</style>
