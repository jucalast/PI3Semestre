<template>
  <div class="atributos">
    <div v-if="atributos">
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
            <label :for="`${key}-${valor}`"> <!-- Usando ID dinâmico para o label -->
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
  // Se o valor já estiver selecionado, desmarque-o
  if (this.selectedValues[key] === valor) {
    console.log(`Desmarcando o rádio com ID: ${key}-${valor}`);
    this.selectedValues[key] = null; // Desmarcando
  } else {
    this.selectedValues[key] = valor; // Marcando
  }
  
  this.updateFilteredProducts(); // Atualiza os produtos filtrados
  this.$emit('update-selected-radios', this.selectedValues); // Atualiza o estado no pai
},
    async updateFilteredProducts() {
      const selectedFilters = Object.entries(this.selectedValues).filter(([key, value]) => value);
      
      this.$emit('update-selected-radios', this.selectedValues);
      
      if (selectedFilters.length > 0) {
        const filterPromises = selectedFilters.map(([atributo, valor]) => {
          return fetch(`http://localhost:8080/api/produtos/buscar-por-atributo/${atributo}/${valor}`)
            .then(response => response.json());
        });

        const resultados = await Promise.all(filterPromises);
        this.produtosFiltrados = resultados.reduce((acc, produtos) => this.combineFilteredProducts(produtos, acc), []);
        
        this.$emit('produtos-filtrados-atualizados', this.produtosFiltrados);
        
        console.log('Produtos filtrados:', JSON.stringify(this.produtosFiltrados, null, 2)); 
      } else {
        this.produtosFiltrados = [];
        this.$emit('produtos-filtrados-atualizados', this.produtosFiltrados);
      }
    },

    combineFilteredProducts(newProducts, existingProducts = []) {
      const uniqueProductsMap = new Map();
      
      existingProducts.forEach(produto => {
        uniqueProductsMap.set(produto.id, produto);
      });
      newProducts.forEach(produto => {
        uniqueProductsMap.set(produto.id, produto);
      });

      return Array.from(uniqueProductsMap.values());
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
      console.log('Produtos filtrados por data:', JSON.stringify(this.produtosFiltrados, null, 2));
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
      console.log('Produtos filtrados por preço:', JSON.stringify(this.produtosFiltrados, null, 2));
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
      return titleMap[key] || this.capitalize(key);
    },
    capitalize(text) {
      return text.split(' ').map((word) => {
        return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
      }).join(' ');
    },
  },
  mounted() {
    this.fetchAtributos();
  },
};
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');

.atributos {
  padding-left: 2rem;
  width: 30%;
  margin-top: 10rem;
  font-family: 'Poppins', sans-serif;
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

.input-data::placeholder,
.input-preco::placeholder {
  color: #9d9d9d !important;
}
</style>
