<template>
    <DefaultLayout>
        <div class="login-container">
            <h1 class="login-title">Login</h1>
            <form @submit.prevent="handleLogin" class="login-form">
                <div class="form-group">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" v-model="email" required placeholder="Digite seu e-mail" />
                </div>
                <div class="form-group">
                    <label for="password">Senha</label>
                    <input type="password" id="password" v-model="password" required placeholder="Digite sua senha" />
                </div>
                <button type="submit" class="login-button">Entrar</button>
                <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
            </form>
        </div>
    </DefaultLayout>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            email: "", // Email do usuário
            password: "", // Senha do usuário
            errorMessage: "", // Para armazenar mensagens de erro
        };
    },
    methods: {
        async handleLogin() {
            try {
                // Monta a URL com os parâmetros na query string
                const url = `http://localhost:8080/login/form-process?email=${encodeURIComponent(this.email)}&password=${encodeURIComponent(this.password)}`;

                console.log("Fazendo login em:", url);
                console.log("Enviando credenciais:", { email: this.email, password: this.password });

                // Faz a requisição com Axios (Envia os dados como query params na URL)
                const response = await axios.post(url, null, { });

                console.log("Usuário autenticado com sucesso:", response.data);

                // Redireciona para a página inicial ou para a página desejada após login bem-sucedido
                this.$router.push("/home");
            } catch (error) {
                console.error("Erro no login:", error.response || error);

                // Mensagem de erro personalizada dependendo do status
                this.errorMessage = error.response?.status === 401
                    ? "Credenciais inválidas. Verifique o e-mail e senha."
                    : "Erro ao tentar fazer login. Tente novamente mais tarde.";
            }
        },
    },
};
</script>



<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f3f4f6;
}

.login-title {
    font-size: 2rem;
    font-weight: bold;
    color: #333;
    margin-bottom: 1rem;
}

.login-form {
    background-color: #fff;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
}

.form-group {
    margin-bottom: 1rem;
}

label {
    font-weight: 600;
    color: #333;
    display: block;
    margin-bottom: 0.5rem;
}

input {
    width: 100%;
    padding: 0.8rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1rem;
}

input:focus {
    border-color: #4a90e2;
    outline: none;
}

.login-button {
    width: 100%;
    padding: 0.8rem;
    background-color: #4a90e2;
    color: white;
    font-weight: 600;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.login-button:hover {
    background-color: #3578e5;
}

.error-message {
    color: red;
    font-size: 0.9rem;
    margin-top: 1rem;
    text-align: center;
}
</style>