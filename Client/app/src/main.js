import '@fortawesome/fontawesome-free/css/all.css';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { createApp } from 'vue';
import VueTheMask from 'vue-the-mask';
import Toast, { POSITION } from 'vue-toastification';
import 'vue-toastification/dist/index.css';
import 'vue-tel-input/dist/vue-tel-input.css';
import App from './App.vue';
import './assets/tailwind.css';
import './plugins/fontawesome';
import router from './router';
import store from './store/store';
import VueTelInput from 'vue-tel-input';

const app = createApp(App);

app.use(router);
app.use(store);
app.use(VueTheMask);

const globalOptions = {
  placeholder: 'teste',
  initialCountry: 'br', 
  preferredCountries: ['br', 'us', 'gb', 'ca'], 
  separateDialCode: true,
  utilsScript: 'https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.19/js/utils.js', 
};

app.use(VueTelInput, globalOptions); 

app.use(Toast, {
  position: POSITION.TOP_RIGHT,
  timeout: 3000,
});

app.component('font-awesome-icon', FontAwesomeIcon);

app.mount('#app');
