import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import './assets/tailwind.css';
import '@fortawesome/fontawesome-free/css/all.css';
import './plugins/fontawesome';
import VueTheMask from 'vue-the-mask'

const app = createApp(App);

app.use(router);
app.use(store);
app.use(VueTheMask);
app.component('font-awesome-icon', FontAwesomeIcon);

app.mount('#app');