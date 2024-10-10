// src/plugins/fontawesome.js
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

// Importando ícones específicos
import { faStar } from '@fortawesome/free-solid-svg-icons'

// Adicionando os ícones à biblioteca
library.add(faStar)

export default FontAwesomeIcon
