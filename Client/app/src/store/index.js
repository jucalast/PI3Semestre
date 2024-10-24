import { createStore } from 'vuex';
import auth from './modules/auth'; 

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
  modules: {
    auth, 
  },
});

export default store;
