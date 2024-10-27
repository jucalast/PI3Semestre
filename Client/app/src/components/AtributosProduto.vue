<template>
  <div class="atributos">
    <div v-if="atributos">
      <!-- Exibindo os cards criados através da computed property -->
      <div class="cards-atributos">
        <div v-for="(valor, key) in filteredSelectedValues" :key="key" class="card">
          {{ valor }}
          <button @click="removeCard(key)">X</button>
        </div>
      </div>
      <div v-for="(valores, key) in atributos" :key="key" class="accordion">
        <h3 @click="toggle(key)">
          {{ formatTitle(key) }} <span>{{ isOpen(key) ? '-' : '+' }}</span>
        </h3>
        <ul v-show="isOpen(key)">
          <!-- Inputs de Data e Preço -->
          <li v-if="key === 'dataTorra' || key === 'dataValidade'">
            <div class="input-group">
              <label for="dataDe">De:</label>
              <input 
                type="date" 
                id="dataDe" 
                class="input-data" 
                placeholder="Data de" 
                @change="onDateChange('dataDe', $event.target.value)" 
              />
            </div>
            <div class="input-group">
              <label for="dataAte">Até:</label>
              <input 
                type="date" 
                id="dataAte" 
                class="input-data" 
                placeholder="Data até" 
                @change="onDateChange('dataAte', $event.target.value)" 
              />
            </div>
          </li>
          <li v-if="key === 'preco'">
            <div class="input-group">
              <label for="precoDe">De:</label>
              <input 
                type="number" 
                id="precoDe" 
                class="input-preco" 
                placeholder="Preço de" 
                @change="onPriceChange('precoDe', $event.target.value)" 
              />
            </div>
            <div class="input-group">
              <label for="precoAte">Até:</label>
              <input 
                type="number" 
                id="precoAte" 
                class="input-preco" 
                placeholder="Preço até" 
                @change="onPriceChange('precoAte', $event.target.value)" 
              />
            </div>
          </li>
          <li v-for="(valor) in valores" :key="valor">
            <label :for="`${key}-${valor}`" class="custom-radio">
              {{ valor }}
              <input 
                class="radio" 
                type="radio" 
                :id="`${key}-${valor}`" 
                :name="key" 
                :value="valor" 
                v-model="selectedValues[key]" 
                @click="toggleRadio(key, valor)" 
              />
              <span class="checkmark"></span>
            </label>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      atributos: {},
      erro: null,
      selectedValues: {},
      openKeys: new Set(),
      dateRange: {
        dataDe: null,
        dataAte: null
      },
      priceRange: {
        precoDe: null,
        precoAte: null
      },
      produtosFiltrados: []
    };
  },
  computed: {
    filteredSelectedValues() {
      return Object.fromEntries(
        Object.entries(this.selectedValues).filter(([key, value]) => value)
      );
    }
  },
  methods: {
    async fetchAtributos() {
      try {
        const response = await fetch('http://localhost:8080/api/produtos/atributos');
        const data = await response.json();
        const atributosMap = this.initializeAttributesMap(data);
        
        this.atributos = Object.fromEntries(
          Object.entries(atributosMap).map(([key, value]) => [key, Array.from(value)]),
        );

      } catch (error) {
        this.erro = error.message || 'Erro desconhecido';
      }
    },
    initializeAttributesMap(data) {
      const atributosMap = {
        torra: new Set(),
        origem: new Set(),
        torrefacao: new Set(),
        notasSensoriais: new Set(),
        variedade: new Set(),
        marca: new Set(),
        material: new Set(),
        complexidade: new Set(),
        tipoPreparo: new Set(),
        dataTorra: new Set(),
        dataValidade: new Set(),
        preco: new Set(),
      };

      data.forEach((item) => {
        Object.keys(atributosMap).forEach((key) => {
          if (item[key]) atributosMap[key].add(item[key]);
        });
      });

      return atributosMap;
    },
    toggleRadio(key, valor) {
      if (this.selectedValues[key] === valor) {
        this.selectedValues[key] = null;
      } else {
        this.selectedValues[key] = valor;
      }
      
      this.updateFilteredProducts();
      this.$emit('update-selected-radios', this.selectedValues);
    },
    removeCard(key) {
      this.selectedValues[key] = null;
      this.updateFilteredProducts();
    },
    async updateFilteredProducts() {
    const selectedFilters = Object.entries(this.selectedValues).filter(([key, value]) => value);
    const { dataDe, dataAte } = this.dateRange;
    const { precoDe, precoAte } = this.priceRange;

    this.$emit('update-selected-radios', this.selectedValues);

    // Montando os parâmetros de consulta
    let queryParams = selectedFilters.map(([atributo, valor]) => `${atributo}=${valor}`).join('&');

    // Adicionando filtros de data
    if (dataDe) queryParams += `&dataDe=${dataDe}`;
    if (dataAte) queryParams += `&dataAte=${dataAte}`;
    if (precoDe) queryParams += `&precoDe=${precoDe}`;
    if (precoAte) queryParams += `&precoAte=${precoAte}`;

    // Fazendo a requisição
    const response = await fetch(`http://localhost:8080/api/produtos/buscar-por-atributos?${queryParams}`);
    const produtosFiltrados = await response.json();
    this.produtosFiltrados = produtosFiltrados;
    this.$emit('produtos-filtrados-atualizados', this.produtosFiltrados);
  },

    onDateChange(type, value) {
      this.dateRange[type] = value;
      this.updateFilteredByDate();
    },
    async updateFilteredByDate() {
      const { dataDe, dataAte } = this.dateRange;
      const response = await fetch(`http://localhost:8080/api/produtos/buscar-por-data/${dataDe}/${dataAte}`);
      const produtosFiltrados = await response.json();
      this.produtosFiltrados = this.combineFilteredProducts(produtosFiltrados);
      this.$emit('produtos-filtrados-atualizados', this.produtosFiltrados);
    },
    onPriceChange(type, value) {
      this.priceRange[type] = value;
      this.updateFilteredByPrice();
    },
    async updateFilteredByPrice() {
      const { precoDe, precoAte } = this.priceRange;
      const response = await fetch(`http://localhost:8080/api/produtos/buscar-por-preco/${precoDe}/${precoAte}`);
      const produtosFiltrados = await response.json();
      this.produtosFiltrados = this.combineFilteredProducts(produtosFiltrados);
      this.$emit('produtos-filtrados-atualizados', this.produtosFiltrados);
    },
    toggle(key) {
      if (this.openKeys.has(key)) {
        this.openKeys.delete(key);
      } else {
        this.openKeys.add(key);
      }
    },
    isOpen(key) {
      return this.openKeys.has(key);
    },
    formatTitle(key) {
      const titleMap = {
        dataTorra: 'Data da Torra',
        dataValidade: 'Data da Validade',
        preco: 'Preço',
      };
      return titleMap[key] || key.charAt(0).toUpperCase() + key.slice(1);
    },
  },
  mounted() {
    this.fetchAtributos();
  },
};
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');

.custom-radio {
  display: flex;
  align-items: center;
  cursor: pointer;
  position: relative;
  padding-left: 35px; /* Espaço para o círculo customizado */
  user-select: none;
}

/* Esconde o botão de rádio padrão */
.custom-radio input[type="radio"] {
  position: absolute;
  opacity: 0;
  cursor: pointer;
}

/* Estilo para o span que atuará como o botão de rádio customizado */
.custom-radio .checkmark {
  position: absolute;
  top: 0;
  left: 0;
  height: 20px;
  width: 20px;
  background-color: #eee;
  border-radius: 50%;
  border: 2px solid #ccc;
}

/* Estilo quando o rádio está selecionado */
.custom-radio input:checked + .checkmark {
  background-color: #ffffff; /* Cor do círculo quando selecionado */
  border-color: #3a5bff;
}

/* Círculo interior quando o rádio está selecionado */
.custom-radio .checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

.custom-radio input:checked + .checkmark:after {
  display: block;
}

.custom-radio .checkmark:after {
  top: 2px;
  left: 2px;
  width: 13px;
  height: 13px;
  border-radius: 50%;
  background: #3a5bff; /* Círculo interno branco */
}
.atributos {
  padding-left: 2rem;
  width: 30%;
  margin-top: -1rem;
  font-family: 'Poppins', sans-serif;
  z-index: 100;
}

.cards-atributos {
  display: flex;
width: 100vw;
gap: 1rem;
margin-left: -2rem;
margin-bottom: 2rem;
}


.card {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  background: #e4e4e4;
  padding: 1.5rem;
  font-size: 1.5rem;
  border-radius: 2rem;
  color: #a7a7a7;
  height: 4rem;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
  box-sizing: border-box; /* Mantém o tamanho da caixa incluindo bordas e padding */
  transition: border 0.3s ease, transform 0.3s ease; /* Adiciona animação suave para borda e transformação */
}

.card:hover {

  background: #d8d8d8;
  transform: scale(1.05); /* Aumenta ligeiramente o tamanho no hover */
}



button {
  background: transparent !important;
  border: none;
  font-size: 1rem;
  color: #8b8b8b;
  cursor:pointer;
}

.accordion {
  width: 100%;
  position: relative;
}

h3 {
  font-weight: lighter;
  display: flex;
  margin-right: 2rem !important;
  justify-content: space-between;
  font-size: 2rem;
  margin: 0;
  cursor: pointer;
  display: flex;
  align-items: center;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 5px 0;
  font-size: 1.5rem;
}

.input-group {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

input[type='radio'] {
  margin-left: 1rem;
  transform: scale(2);
  cursor: pointer;
}

.input-data,
.input-preco {
  border: solid 1px #aeaeaeb6;
  background: #ededed;
  border-radius: 2rem;
  width: 60%;
  color: var(--text-color);
  height: 5rem;
  padding-left: 1rem !important;
  font-size: 1.5rem !important;
  outline: none;
  margin-left: 1rem;
  font-family: 'Poppins', sans-serif;
}

.input-data::-webkit-calendar-picker-indicator {
  display: none;
}

span {
  display:flex;
  color: #c6c6c6;

  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  align-items: center;
  justify-content: center;
}

span:hover {
  color: #616161;
  background: #e0e0e0;
}

.input-data::placeholder,
.input-preco::placeholder {
  color: #9d9d9d !important;
}
</style>
