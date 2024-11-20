<template>
  <div>
    <h3>Edição de preço em Massa</h3>
    <div v-if="isLoading" class="loading-gif">
      <img :src="loadingGif" alt="Carregando..." />
    </div>
    <div v-else>
      <div v-for="produto in produtos" :key="produto.id" class="product-edit">
        <p>{{ produto.nome }}</p>
        <input type="number" v-model.number="produto.preco" placeholder="Novo preço" />
      </div>
      <button @click="submitBulkEdit" class="submit-button">Salvar Alterações</button>
    </div>
  </div>
</template>

<script>
import loadingGif from '@/assets/Logo Maven.gif';

export default {
  name: "BulkEditDropdown",
  props: {
    produtos: Array,
  },
  data() {
    return {
      updatedProducts: [...this.produtos],
      isLoading: false,
      loadingGif,
    };
  },
  methods: {
    async submitBulkEdit() {
      this.isLoading = true;
      await this.$emit("bulk-edit", this.updatedProducts);
      this.isLoading = false;
    },
  },
};
</script>

<style scoped>
.loading-gif {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.product-edit {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.submit-button {
  display: flex;
  align-items: center;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  border: solid 2px #dfdfdf !important;
  padding: 1rem;
  border-radius: 1rem;
  margin-top: 1rem;
}

h3 {
    font-size:1.5rem;
    width: 80%;

    line-height: 1 !important;
    padding-bottom: 2rem;
    color: #3a5bff;
}
.submit-button:hover {
  background: #ebebeb;
}

input {
width: 4rem;
  color: #3a5bff;
  border: solid 1px #aeaeaeb6;
  background: #ededed;
  border-radius: 1rem;
  height: 2.5rem; /* Para inputs */
  padding: 0.5rem !important;
  font-size: 1.5rem !important;
  outline: none;   
}
</style>