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
                @click.stop 
              />
            </div>
            <div class="input-group">
              <label for="dataAte">Até:</label>
              <input 
                type="date" 
                id="dataAte" 
                class="input-data" 
                placeholder="Data até" 
                @click.stop 
              />
            </div>
          </li>
          <li v-if="key === 'preco'">
            <div class="input-group">
              <label for="precoDe">De:</label>
              <input type="number" id="precoDe" class="input-preco" placeholder="Preço de" />
            </div>
            <div class="input-group">
              <label for="precoAte">Até:</label>
              <input type="number" id="precoAte" class="input-preco" placeholder="Preço até" />
            </div>
          </li>
          <li v-for="(valor) in valores" :key="valor" v-connect-to="'.atributos h3'">
            {{ valor }}
            <input class="radio" type="radio" :name="key" :value="valor" v-model="selectedValues[key]" />
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  directives: {
    connectTo: {
      mounted(el, binding) {
        const header = document.querySelector(binding.value);
        if (!header) return;

        const line = document.createElement('div');
        line.classList.add('connector');
        el.parentNode.insertBefore(line, el);

        const updateLine = () => {
          const headerRect = header.getBoundingClientRect();
          const elRect = el.getBoundingClientRect();
          const offset = 10;

          line.style.left = `${headerRect.left + headerRect.width / 2 - 1}px`;
          line.style.top = `${headerRect.bottom + offset}px`;
          line.style.width = '4px';
          line.style.height = '2rem';
          line.style.backgroundColor = '#ff96ff';
        };

        updateLine();
        window.addEventListener('resize', updateLine);

        el.__updateLine__ = updateLine;
      },
      unmounted(el) {
        window.removeEventListener('resize', el.__updateLine__);
      },
    },
  },

  data() {
    return {
      atributos: {},
      erro: null,
      selectedValues: {},
      openKeys: new Set(),
    };
  },

  methods: {
    async fetchAtributos() {
      try {
        const response = await fetch('http://localhost:8080/api/produtos/atributos');
        const data = await response.json();

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
          if (item.torra) atributosMap.torra.add(item.torra);
          if (item.origem) atributosMap.origem.add(item.origem);
          if (item.torrefacao) atributosMap.torrefacao.add(item.torrefacao);
          if (item.notasSensoriais) atributosMap.notasSensoriais.add(item.notasSensoriais);
          if (item.variedade) atributosMap.variedade.add(item.variedade);
          if (item.marca) atributosMap.marca.add(item.marca);
          if (item.material) atributosMap.material.add(item.material);
          if (item.complexidade) atributosMap.complexidade.add(item.complexidade);
          if (item.tipoPreparo) atributosMap.tipoPreparo.add(item.tipoPreparo);
          if (item.dataTorra) atributosMap.dataTorra.add(item.dataTorra);
          if (item.dataValidade) atributosMap.dataValidade.add(item.dataValidade);
          if (item.preco) atributosMap.preco.add(item.preco);
        });

        this.atributos = Object.fromEntries(
          Object.entries(atributosMap).map(([key, value]) => [key, Array.from(value)]),
        );

      } catch (error) {
        this.erro = error.message || 'Erro desconhecido';
      }
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

.connector {
  position: absolute;
  z-index: 0;
  transition: all 0.3s ease;
  background-color: #ff96ff;
  height: 0.5rem !important;
  width: 2px;
}
</style>
