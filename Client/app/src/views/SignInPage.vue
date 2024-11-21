<template>
  <div class="login-container">
    <div class="left-side"></div>
    <div class="right-side">
      <h1 class="main-title">
        Prepare-se para o
        <br />
        <strong class="melhorescafes">sabor único</strong>
      </h1>
      <div class="form-container">
        <form @submit.prevent="handleRegister" class="login-form">
          <div class="forms">
            <input
              type="text"
              v-model="userName"
              placeholder="Nome Completo"
              required
              class="input-field"
            />
            <input
              type="email"
              v-model="email"
              placeholder="E-mail"
              required
              class="input-field"
            />
          </div>
          <div class="forms">
            <vue-tel-input
              v-model="phone"
              :input-options="{ placeholder: phonePlaceholder }"
              class="input-field phone"
              v-mask="'(##) #####-####'"
            />
            <input
              type="text"
              v-model="cpf"
              v-mask="'###.###.###-##'"
              placeholder="CPF"
              required
              class="input-field cpf"
            />
          </div>
          <div class="forms">
            <div class="password-input-wrapper">
              <input
                :type="showPassword ? 'text' : 'password'"
                v-model="password"
                placeholder="Senha"
                required
                class="input-field senha-input"
                @focus="showPasswordTooltip = true"
                @blur="showPasswordTooltip = false"
                @input="validatePassword"
              />
              <span class="eye-icon" @click="togglePasswordVisibility">
                <i :class="showPassword ? 'fa fa-eye-slash' : 'fa fa-eye'"></i>
              </span>
              <div v-show="showPasswordTooltip" class="password-tooltip">
                <p :class="{ valid: passwordLengthValid }">
                  <span class="icon"></span>
                  Mínimo de 6 caracteres
                </p>
                <p :class="{ valid: specialCharValid }">
                  <span class="icon"></span>
                  Pelo menos 1 caractere especial
                </p>
                <p :class="{ valid: noSequentialCharsValid }">
                  <span class="icon"></span>
                  Sem sequência numérica
                </p>
              </div>
            </div>

            <input
              type="password"
              v-model="passwordConfirm"
              placeholder="Confirme Senha"
              required
              class="input-field senha-confirm"
              @blur="checkPasswords"
            />
          </div>
          <transition name="fade">
            <p v-if="showPasswordError" class="error-text">
              As senhas não coincidem.
            </p>
          </transition>
          <!-- Botão de cadastro com efeito de desabilitação -->
          <button
            type="submit"
            class="login-button"
            :disabled="!isFormValid"
            :class="{ 'button-disabled': !isFormValid }"
          >
            Cadastrar
          </button>
        </form>
        <button class="login-with-google-btn" @click="handleGoogleLogin">
          Logar com o Google
        </button>
        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>
        <div class="register-container">
          <p>
            Já tem uma conta?
            <a href="/login">Faça login ></a>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { VueTelInput } from 'vue-tel-input';
import axiosInstance from '@/utils/axiosInstance';

export default {
  components: {
    VueTelInput,
  },
  data() {
    return {
      phonePlaceholder: '(XX) XXXXX-XXXX',
      userName: '',
      email: '',
      phone: '',
      cpf: '',
      password: '',
      passwordConfirm: '',
      errorMessage: null,
      showPasswordError: false,
      showPasswordTooltip: false,
      passwordLengthValid: false,
      specialCharValid: false,
      noSequentialCharsValid: false,
      showPassword: false,
    };
  },
  computed: {
    isFormValid() {
      const allFieldsFilled =
        this.userName &&
        this.email &&
        this.phone &&
        this.cpf &&
        this.password &&
        this.passwordConfirm;

      const passwordsMatch = this.password === this.passwordConfirm;
      const passwordValid =
        this.passwordLengthValid &&
        this.specialCharValid &&
        this.noSequentialCharsValid;

      return allFieldsFilled && passwordsMatch && passwordValid;
    },
  },
  methods: {
    checkPasswords() {
      this.showPasswordError = this.password !== this.passwordConfirm;
    },
    validatePassword() {
      this.passwordLengthValid = this.password.length >= 6;
      this.specialCharValid = /[!@#$%^&*(),.?":{}|<>]/.test(this.password);
      this.noSequentialCharsValid = !/(\d)\1\1/.test(this.password);
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
    async handleRegister() {
      if (this.showPasswordError) {
        this.errorMessage = 'As senhas não coincidem.';
        return;
      }

      try {
        const response = await axiosInstance.post('/register', null, {
          params: {
            userName: this.userName,
            emailId: this.email,
            mobileNumber: this.phone,
            cpf: this.cpf,
            password: this.password,
          },
        });

        if (response.status === 200) {
          console.log(response.data);
          this.$router.push('/login');
        }
      } catch (error) {
        console.error('Erro no registro:', error);
        this.errorMessage = 'Ocorreu um erro. Tente novamente mais tarde.';
      }
    },
    handleGoogleLogin() {
      const apiBaseUrl = import.meta.env.VITE_API_BASE_URL;
      window.location.href = `${apiBaseUrl}/oauth2/authorization/google`;
    },
  },
  watch: {
    password() {
      this.validatePassword();
    },
    passwordConfirm() {
      this.checkPasswords();
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
  width: 40%;
  border: none;
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
  max-width: 700px;
  background: transparent;
}

.forms {
  display: flex;
  gap: 1.5rem;
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

.cpf {
  width: 50%;
}

.senha-input {
  width: 100%;
}

.senha-confirm {
  width: 50%;
}

.phone {
  width: 75%;
  color: #ff4d4d;
  border: solid 2px #ff4d4d;
  background: #ededed;
}

.phone .vue-tel-input__input {
  color: #ff4d4d;
  border: none;
  background-color: transparent;
  font-size: 1.4rem;
  font-family: 'Poppins', sans-serif;
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

.login-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
  opacity: 0.6;
  box-shadow: none;
  transform: none;
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
  background-repeat: no-repeat;
  background-position: 12px center;
  background-size: 30px;
  cursor: pointer;
  transition: 0.2s;
}

.login-with-google-btn:hover {
  background-color: #f6f6f6;
}

.error-message {
  color: #ff4d4d;
  font-size: 1.2rem;
  margin-top: 50px;
}

.register-container {
  margin-top: 20px;
  text-align: center;
}

.register-container a {
  color: #5271ff;
  text-decoration: none;
  font-weight: bold;
}

.input-field.phone {
  background-color: white;
}

.error-text {
  color: #ff4d4d;
  font-size: 1.2rem;

  margin-bottom: 15px;
  text-align: center;
  background: #ffe6e6;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(255, 77, 77, 0.2);
  animation: slide-down 0.3s ease-out;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

@keyframes slide-down {
  from {
    transform: translateY(-10px);
    opacity: 0;
  }

  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.password-input-wrapper {
  position: relative;
}

.password-tooltip {
  position: absolute;
  left: -85%;
  top: 0;
  width: 250px;
  padding: 12px;
  background-color: #ffffff;
  border-radius: 8px;
  border: 1px solid #ddd;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  font-size: 1.2rem;
  z-index: 10;
  opacity: 0;
  visibility: hidden;
  transform: translateY(-10px);
  transition: opacity 0.3s ease-in-out, transform 0.3s ease-in-out;
}

.password-tooltip .icon {
  margin-right: 10px;
  display: inline-block;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  text-align: center;
  line-height: 20px;
  font-size: 14px;
  font-weight: bold;
  transition: all 0.3s ease;
}

.password-tooltip p {
  margin: 5px 0;
  color: #ff4d4d;
  transition: color 0.3s ease-in-out;
}

.password-tooltip p.valid {
  color: #4caf50;
  font-weight: bold;
}

.password-input-wrapper:hover .password-tooltip {
  opacity: 1;
  visibility: visible;
  transform: translateY(0);
}

.password-tooltip::before {
  content: '';
  position: absolute;
  top: 10px;
  right: -16px;
  width: 0;
  height: 0;
  border-width: 10px;
  border-style: solid;
  border-color: transparent transparent transparent #ffffff;
}

.password-tooltip p.valid {
  color: #4caf50;
}

/* Para o ícone de erro (❌) */
.password-tooltip p:not(.valid) .icon {
  background-color: #ff4d4d;
  color: white;
  content: '❌';
}

.password-tooltip p.valid .icon {
  background-color: #4caf50;
  color: white;
  content: '✔';
}

.password-tooltip p {
  color: #ff4d4d;
}

@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.eye-icon {
  position: absolute;
  right: 5%;
  top: 40%;
  transform: translateY(-50%);
  cursor: pointer;
  color: #ff4d4d;
  font-size: 1.5rem;
}

.eye-icon i {
  font-size: 1.8rem;
}
</style>
