import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import '@fortawesome/fontawesome-free/css/all.css'; // Importação do CSS
import './plugins/fontawesome'; // Certifique-se de que este arquivo adiciona os ícones à biblioteca
import store from './store'; // Importa o store Vuex

const app = createApp(App);

app.use(router);
app.component('font-awesome-icon', FontAwesomeIcon); // Registro correto do ícone
app.mount('#app');
app.use(store) // Usa o store
