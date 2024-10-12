<template>
  <div class="atributos">
    <div v-if="atributos">
      <div v-for="(valores, key) in atributos" :key="key">
        <h3>{{ key }}:</h3>
        <ul>
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
// In your component script


export default {
  directives: {
    connectTo: {
      // Definindo a diretiva sem usar defineDirective
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
          line.style.width = '2px';
          line.style.height = `${elRect.top - headerRect.bottom - offset}px`;
        };

        updateLine();
        window.addEventListener('resize', updateLine);
      },
    },
  },

  data() {
    return {
      atributos: {},
      erro: null,
      selectedValues: {}, // Armazenará os valores selecionados para cada atributo
    };
  },
  methods: {

    
    async fetchAtributos() {
      try {
        const response = await fetch('http://localhost:8080/api/produtos/atributos');
        const data = await response.json();

        // Cria um objeto para armazenar os atributos
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
        };

        // Itera sobre os dados e armazena os valores únicos
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
        });

        // Converte os Sets para arrays e atribui aos atributos
        this.atributos = Object.fromEntries(
          Object.entries(atributosMap).map(([key, value]) => [key, Array.from(value)])
        );

      } catch (error) {
        this.erro = error.message || 'Erro desconhecido';
      }
    },
  },
  mounted() {
    this.fetchAtributos();
  },
};
</script>

<style scoped>
.atributos {
  padding-left: 2rem;
  width: 20%;
  margin-top: 10rem;
}

h3 {
  font-size: 2rem;
  margin-top: 15px;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin: 5px 0;
  font-size: 1.5rem;
}
input[type="radio"] {
  margin-left: 1rem; /* Espaço entre o botão e o texto */
  transform: scale(2); /* Aumenta o tamanho do botão de opção */
  cursor: pointer; /* Muda o cursor para pointer ao passar sobre o botão */
}

.connector {
  position: absolute;
  background-color: #ccc;
  z-index: -1;
  /* Adicione transições para animações suaves */
  transition: all 0.3s ease;
}
</style>
