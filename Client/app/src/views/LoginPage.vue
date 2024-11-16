<template>
  <div class="login-container">
    <div class="left-side"></div>
    <div class="right-side">
      <h1 class="main-title">
        A um passo dos<br />
        <strong class="melhorescafes">melhores cafés</strong><br />Brasil
      </h1>

      <div class="form-container">
        <form @submit.prevent="handleLogin">
          <input
            type="email"
            v-model="email"
            placeholder="E-mail"
            required
          />
          <input
            type="password"
            v-model="password"
            placeholder="Senha"
            required
          />
          <button type="submit" class="login-button">Entrar</button>
        </form>

        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axiosInstance from "@/utils/axiosInstance";

export default {
  data() {
    return {
      email: "",
      password: "",
      errorMessage: null,
    };
  },
  methods: {
    async handleLogin() {
      try {
        const response = await axiosInstance.post("/login/form-process", null, {
          params: {
            email: this.email,
            password: this.password,
          },
        });

        if (response.status === 200) {
          const { userId } = response.data;
          alert("Login bem-sucedido! Bem-vindo.");
          this.$router.push(`/home?userId=${userId}`);
        }
      } catch (error) {
        if (error.response && error.response.status === 401) {
          this.errorMessage = "E-mail ou senha inválidos.";
        } else {
          console.error("Erro no login:", error);
          this.errorMessage = "Ocorreu um erro. Tente novamente mais tarde.";
        }
      }
    },
  },
};
</script>

<style scoped>
/* Estilização herdada */
.login-container {
  display: flex;
  width: 100vw;
  height: 100vh;
}

.left-side {
  width: 40%;
  height: 100%;
  background-size: cover;
  background-position: left;
  background-image: url('@/assets/LoginBanner.png');
}

.right-side {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.main-title {
  font-size: 36px;
  color: #2d2d2d;
  text-align: center;
  line-height: 1.3;
  margin-bottom: 20px;
}

.form-container {
  width: 100%;
  max-width: 400px;
}

.error-message {
  margin-top: 20px;
  color: red;
  text-align: center;
  font-size: 16px;
}

.login-button {
  width: 100%;
  height: 50px;
  border: none;
  border-radius: 2rem;
  color: white;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  background-color: #5271ff;
  transition: background-color 0.3s;
}

.login-button:hover {
  background-color: #3d58ce;
}
</style>
