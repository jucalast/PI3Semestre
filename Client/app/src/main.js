import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import './assets/tailwind.css';
import '@fortawesome/fontawesome-free/css/all.css';
import './plugins/fontawesome';
import VueTheMask from 'vue-the-mask';
import Toast, { POSITION } from 'vue-toastification';
import 'vue-toastification/dist/index.css';

const app = createApp(App);

// Configuração dos plugins
app.use(router);
app.use(store);
app.use(VueTheMask);

// Configuração do Vue Toastification
app.use(Toast, {
  position: POSITION.TOP_RIGHT, // Posição da notificação
  timeout: 3000,                // Duração da notificação em milissegundos
});

app.component('font-awesome-icon', FontAwesomeIcon);

app.mount('#app');
