// src/store/index.js
import { createStore } from 'vuex';

const store = createStore({
  state: {
    produtosFiltrados: [],
  },
  mutations: {
    setProdutosFiltrados(state, produtos) {
      state.produtosFiltrados = produtos;
    },
  },
  actions: {
    updateProdutosFiltrados({ commit }, produtos) {
      commit('setProdutosFiltrados', produtos);
    },
  },
  getters: {
    produtosFiltrados: (state) => state.produtosFiltrados,
  },
});

export default store;
