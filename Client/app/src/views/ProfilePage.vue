<template>
  <DefaultLayout>
    <div class="profile-page p-6 bg-gray-100 min-h-screen flex flex-col">
      <div class="bg-white p-6 rounded-lg shadow-md mb-6 flex-grow">
        <h2 class="text-2xl font-semibold mb-4">Perfil do Usuário</h2>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div class="col-span-1">
            <label class="block text-gray-700" for="name">Nome</label>
            <input
              type="text"
              v-model="user.name"
              id="name"
              disabled
              class="input-field"
            />
          </div>

          <div class="col-span-1">
            <label class="block text-gray-700" for="email">E-mail</label>
            <input
              type="email"
              v-model="user.email"
              id="email"
              disabled
              class="input-field"
            />
          </div>

          <div class="col-span-1">
            <label class="block text-gray-700" for="phone">Telefone</label>
            <input
              type="text"
              v-model="user.phone"
              id="phone"
              disabled
              class="input-field"
            />
          </div>

          <div class="col-span-1">
            <label class="block text-gray-700" for="address">Endereço</label>
            <input
              type="text"
              v-model="user.address"
              id="address"
              disabled
              class="input-field"
            />
          </div>
        </div>

        <div class="flex mt-6">
          <button
            @click="editProfile"
            class="mr-4 px-4 py-2 bg-headerBackground text-white rounded-md hover:bg-yellow-600 transition duration-200"
          >
            Editar Perfil
          </button>
        </div>
      </div>

      <div class="bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-2xl font-semibold mb-4">Alterar Senha</h2>

        <form @submit.prevent="submitPasswordChange">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="col-span-1">
              <label class="block text-gray-700" for="currentPassword">
                Senha Atual
              </label>
              <input
                type="password"
                v-model="passwords.currentPassword"
                id="currentPassword"
                required
                class="input-field"
              />
            </div>

            <div class="col-span-1">
              <label class="block text-gray-700" for="newPassword">
                Nova Senha
              </label>
              <input
                type="password"
                v-model="passwords.newPassword"
                id="newPassword"
                required
                class="input-field"
              />
            </div>

            <div class="col-span-1">
              <label class="block text-gray-700" for="confirmPassword">
                Confirmar Nova Senha
              </label>
              <input
                type="password"
                v-model="passwords.confirmPassword"
                id="confirmPassword"
                required
                class="input-field"
              />
            </div>
          </div>

          <div class="flex mt-6">
            <button
              type="submit"
              class="px-4 py-2 bg-yellow-500 text-white rounded-md hover:bg-yellow-600 transition duration-200"
            >
              Alterar Senha
            </button>
          </div>
        </form>
      </div>
    </div>
  </DefaultLayout>
</template>

<script>
  import axiosInstance from '@/utils/axiosInstance';
  import DefaultLayout from '@/layouts/DefaultLayout.vue';

  export default {
    components: {
      DefaultLayout,
    },
    data() {
      return {
        user: {
          name: '',
          email: '',
          phone: '',
          address: '',
        },
        passwords: {
          currentPassword: '',
          newPassword: '',
          confirmPassword: '',
        },
        isEditing: false,
      };
    },
    created() {
      this.fetchUserProfile();
    },
    methods: {
      async fetchUserProfile() {
        try {
          const response = await axiosInstance.get('/api/user/profile');
          this.user = response.data;
        } catch (error) {
          console.error('Erro ao buscar perfil do usuário:', error);
        }
      },
      editProfile() {
        this.isEditing = true;
      },
      async submitPasswordChange() {
        if (this.passwords.newPassword !== this.passwords.confirmPassword) {
          alert('As senhas não coincidem!');
          return;
        }

        try {
          await axiosInstance.post('/api/user/change-password', {
            currentPassword: this.passwords.currentPassword,
            newPassword: this.passwords.newPassword,
          });
          alert('Senha alterada com sucesso!');
          this.passwords = {
            currentPassword: '',
            newPassword: '',
            confirmPassword: '',
          };
        } catch (error) {
          console.error('Erro ao alterar a senha:', error);
        }
      },
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
