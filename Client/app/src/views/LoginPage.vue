<template>
  <div class="login-container">
    <!-- Lado esquerdo -->
    <div class="left-side"></div>

    <!-- Lado direito -->
    <div class="right-side">
      <h1 class="main-title">
        A um passo dos<br />
        <strong class="melhorescafes">melhores cafés</strong><br />Brasil
      </h1>
      <div class="form-container">
        <!-- Formulário de login -->
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

        <!-- Botão de login com o Google -->
        <form @submit.prevent="handleGoogleLogin" style="margin-top: 20px">
          <button type="submit" class="login-with-google-btn">
            Entrar com o Google
          </button>
        </form>

        <div class="register-container">
          <p>
            Ainda não tem uma conta?
            <router-link to="/register">Registre-se ></router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    handleLogin() {
      const loginData = {
        email: this.email,
        password: this.password
      };
      console.log('Login com:', loginData);
    },
    handleGoogleLogin() {
      window.location.href = '/oauth2/authorization/google';
    }
  }
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  background: transparent;
}

body {
  font-family: 'Poppins', sans-serif;
}

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
  background: transparent;
}

.main-title {
  font-size: 36px;
  color: #2d2d2d;
  text-align: center;
  line-height: 1.3;
  margin-bottom: 20px;
}

.melhorescafes {
  font-weight: bold;
  color: #e4cc2c;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.form-container {
  width: 100%;
  max-width: 400px;
}

.form-container form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-container input {
  border: solid 1px #aeaeaeb6;
  background: #ededed;
  border-radius: 2rem;
  height: 50px;
  padding: 10px 20px;
  font-size: 16px;
  font-family: 'Poppins', sans-serif;
}

.form-container input::placeholder {
  color: #6d6d6d;
}

.form-container input:focus {
  outline: none;
  border: 1px solid #ff4d4d;
  box-shadow: 1px 0px 20px 1px #ff4d4d54;
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

.login-with-google-btn {
  width: 100%;
  height: 50px;
  padding: 0 42px;
  border: none;
  border-radius: 2rem;
  box-shadow: 0 1px 3px rgba(77, 77, 77, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  color: #3e3e3e;
  font-size: 16px;
  font-weight: 500;
  background-image: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTgiIGhlaWdodD0iMTgiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIj48cGF0aCBkPSJNMTcuNiA5LjJsLS4xLTEuOEg5djMuNGg0LjhDMTMuNiAxMiAxMyAxMyAxMiAxMy42djIuMmgzYTguOCA4LjggMCAwIDAgMi42LTYuNnoiIGZpbGw9IiM0Mjg1RjQiIGZpbGwtcnVsZT0ibm9uemVybyIvPjxwYXRoIGQ9Ik05IDE4YzIuNCAwIDQuNS0uOCA2LTIuMmwtMy0yLjJhNS40IDUuNCAwIDAgMS04LTIuOUgxVjEzYTkgOSAwIDAgMCA4IDV6IiBmaWxsPSIjMzRBODUzIiBmaWxsLXJ1bGU9Im5vbnplcm8iLz48cGF0aCBkPSJNNCAxMC43YTUuNCA1LjQgMCAwIDEgMC0zLjRWNUgxYTkgOSAwIDAgMCAwIDhsMy0yLjN6IiBmaWxsPSIjRkJCQzA1IiBmaWxsLXJ1bGU9Im5vbnplcm8iLz48cGF0aCBkPSJNOSAzLjZjMS4zIDAgMi41LjQgMy40IDEuM0wxNSAyLjNBOSA5IDAgMCAwIDEgNWwzIDIuNGE1LjQgNS40IDAgMCAxIDUtMy43eiIgZmlsbD0iI0VBNDMzNSIgZmlsLXJ1bGU9Im5vbnplcm8iLz48cGF0aCBkPSJNMCAwaDE4djE4SDB6Ii8+PC9nPjwvc3ZnPg==);
  background-color: #ffffff;
  background-repeat: no-repeat;
  background-position: 12px center;
  background-size: 20px;
}

.login-with-google-btn:hover {
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.44);
}

.register-container {
  margin-top: 20px;
  font-size: 16px;
  text-align: center;
}

.register-container a {
  color: #5271ff;
  text-decoration: none;
  font-weight: 600;
}
</style>
