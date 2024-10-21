<template>
  <div class="selected-radios">
    <ul>
      <li v-for="(valor, key) in selectedRadios" :key="key" class="radio-card">
        <div class="card">
          <h3>{{ key }}</h3>
          <p>{{ valor }}</p>
          <button @click="deselectRadio(key)" class="remove-button">X</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    selectedRadios: {
      type: Object,
      required: true,
    },
  },
  methods: {
    deselectRadio(key) {
      // Mostra o ID do rádio que está sendo desmarcado
      console.log(`Desmarcando o rádio com ID: ${key}`);

      // Emite um evento para o componente pai informando que o rádio foi desmarcado
      const updatedSelectedValues = { ...this.selectedRadios };
      
      // Remove a chave correspondente
      delete updatedSelectedValues[key];
      
      // Desmarca o rádio definindo o valor como null
      this.$emit('update-selected-radios', updatedSelectedValues);
    },
  },
};
</script>

<style scoped>
.selected-radios {
  display: flex;
  flex-direction: column;
  margin-top: 10rem;
}

ul {
  list-style-type: none; /* Remove os marcadores da lista */
  padding: 0; /* Remove o padding padrão */
  margin: 0; /* Remove a margem padrão */
}

.radio-card {
  margin-bottom: 1rem;
}

.card {
  display: flex;
  padding: 1rem;
  border-radius: 2rem;
  width: fit-content;
  background: #c4ceff;
  flex-direction: row;
  align-items: center;
  justify-content: space-between; /* Altera para space-between para acomodar o botão "X" */
  gap: 1rem;
  padding: 0rem 1.5rem;
  font-size: 1.5rem;
  height: 4rem;
}

.remove-button {
  background: none; /* Estilo opcional para o botão */
  border: none;
  color: rgb(0, 0, 0); /* Cor do botão "X" */
  font-size: 1.5rem;
  cursor: pointer; /* Muda o cursor para indicar que é clicável */
}

p {
  background: #ffffff00;
  backdrop-filter: blur(0);
}
</style>
