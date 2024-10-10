import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'; // Import correto do componente FontAwesome
import './plugins/fontawesome'; // Certifique-se de que este arquivo adiciona os ícones à biblioteca

const app = createApp(App);

app.use(router);
app.component('font-awesome-icon', FontAwesomeIcon); // Registro correto do ícone
app.mount('#app');
