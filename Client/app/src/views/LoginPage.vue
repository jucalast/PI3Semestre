<template>
  <div class="login-container">

    <div class="left-side"></div>

    <div class="right-side">
      <h1 class="main-title">
        <span>A um passo dos</span>
        <strong class="melhorescafes">melhores cafés</strong>
        <span>do Brasil</span>
      </h1>

      <div class="form-container">
        <form @submit.prevent="handleLogin" class="login-form">
          <input type="email" v-model="email" placeholder="E-mail" required class="input-field" />
          <input type="password" v-model="password" placeholder="Senha" required class="input-field" />
          <button type="submit" class="login-button">Entrar</button>
        </form>

        <button class="login-with-google-btn" @click="handleGoogleLogin">
          Logar com o Google
        </button>

        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>

        <div class="register-container">
          <p>
            Ainda não tem uma conta? <a href="/register">Registre-se ></a>
          </p>
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
    handleGoogleLogin() {
      const apiBaseUrl = import.meta.env.VITE_API_BASE_URL;
      window.location.href = `${apiBaseUrl}/oauth2/authorization/google`;
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Poppins', sans-serif;
  background: transparent;
}

.login-container {
  display: flex;
  width: 100vw;
  height: 100vh;
}

.left-side {
  background-size: cover;
  background-position: left;
  height: 100vh;
  width: 40% !important;
  border: none !important;
  background-image: url('@/assets/LoginBanner.png');
}

.right-side {
  width: 45%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.main-title {
  font-size: 2.8rem;

  color: #2d2d2d;
  text-align: center;
  line-height: 1.4;
  max-width: 75%;
  margin: 0 auto;
  font-weight: 400;

}

.main-title strong.melhorescafes {
  color: #e9cb31;
  font-weight: 700;
  text-transform: uppercase;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.15);
  font-size: 3rem;

}

.main-title span {
  display: block;
  margin-top: 8px;
  font-weight: 300;
  font-size: 2.4rem;

}

.form-container {
  margin-top: 20px;
  width: 100%;
  max-width: 500px;
  background: transparent;
}


.login-button {
  width: 100%;
  height: 4rem;
  border: none;
  border-radius: 1.5rem;
  background-color: #5271ff;
  color: #ffffff;
  font-size: 1.4rem;
  font-weight: bold;
  text-align: center;
  cursor: pointer;
  box-shadow: 0px 2px 5px rgba(82, 113, 255, 0.4);
  transition: box-shadow 0.3s, transform 0.3s;
  margin-bottom: 15px;
}

.login-button:hover {
  box-shadow: 0px 4px 10px rgba(82, 113, 255, 0.6);
  transform: scale(1.03);
}

.login-button:active {
  background-color: #405ad6;
}

.login-with-google-btn {
  width: 100%;
  height: 4rem;
  padding: 12px 16px 12px 42px;
  border: none;
  border-radius: 1.5rem;
  box-shadow: 0 1px 3px rgba(77, 77, 77, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  color: #3e3e3e;
  font-size: 16px;
  font-weight: 500;
  background-image: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTgiIGhlaWdodD0iMTgiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIj48cGF0aCBkPSJNMTcuNiA5LjJsLS4xLTEuOEg5djMuNGg0LjhDMTMuNiAxMiAxMyAxMyAxMiAxMy42djIuMmgzYTguOCA4LjggMCAwIDAgMi42LTYuNnoiIGZpbGw9IiM0Mjg1RjQiIGZpbGwtcnVsZT0ibm9uemVybyIvPjxwYXRoIGQ9Ik05IDE4YzIuNCAwIDQuNS0uOCA2LTIuMmwtMy0yLjJhNS40IDUuNCAwIDAgMS04LTIuOUgxVjEzYTkgOSAwIDAgMCA4IDV6IiBmaWxsPSIjMzRBODUzIiBmaWxsLXJ1bGU9Im5vbnplcm8iLz48cGF0aCBkPSJNNCAxMC43YTUuNCA1LjQgMCAwIDEgMC0zLjRWNUgxYTkgOSAwIDAgMCAwIDhsMy0yLjN6IiBmaWxsPSIjRkJCQzA1IiBmaWxsLXJ1bGU9Im5vbnplcm8iLz48cGF0aCBkPSJNOSAzLjZjMS4zIDAgMi41LjQgMy40IDEuM0wxNSAyLjNBOSA5IDAgMCAwIDEgNWwzIDIuNGE1LjQgNS40IDAgMCAxIDUtMy43eiIgZmlsbD0iI0VBNDMzNSIgZmlsbC1ydWxlPSJub256ZXJvIi8+PHBhdGggZD0iTTAgMGgxOHYxOEgweiIvPjwvZz48L3N2Zz4=);
  background-color: #ffffff;
  background-repeat: no-repeat;
  background-position: 12px 11px;
  background-size: 38px 38px;
  cursor: pointer;
  font-family: 'Poppins', sans-serif;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-content: center;
  align-items: center;
  margin-bottom: 15px;
}

.login-with-google-btn:hover {
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.44);
}

.login-with-google-btn:active {
  background-color: #eeeeee;
}


.input-field {
  color: #ff4d4d;
  border: solid 2px #ff4d4d;
  background: #ededed;
  border-radius: 1.5rem;
  width: 100%;
  height: 4rem;
  padding: 0 1.5rem;
  font-size: 1.4rem;
  outline: none;
  font-family: 'Poppins', sans-serif;
  margin-bottom: 15px;
  cursor: pointer;
  box-shadow: 0px 2px 5px rgba(255, 77, 77, 0.4);
  transition: box-shadow 0.3s, transform 0.3s;
}


.input-field::placeholder {
  color: #ff4d4d;
  opacity: 0.8;
}

.input-field:focus {
  border: 2px solid #ff4d4d;
  box-shadow: 0 0 15px #ff4d4d;
  transform: scale(1.02);
}

.error-message {
  margin-top: 15px;
  color: #ff4d4d;
  text-align: center;
  font-size: 1.4rem;
}

.register-container {
  text-align: center;
  margin-top: 15px;
  font-size: 1rem;
}

.register-container a {
  color: #5271ff;
  text-decoration: none;
  font-weight: 500;
}

.register-container a:hover {
  text-decoration: underline;
}
</style>
