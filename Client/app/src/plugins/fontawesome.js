// src/plugins/fontawesome.js
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faShoppingCart, faStar } from '@fortawesome/free-solid-svg-icons'; // Adicione faShoppingCart aqui

library.add(faShoppingCart, faStar); // Certifique-se de adicionar o ícone à biblioteca

export { FontAwesomeIcon };
