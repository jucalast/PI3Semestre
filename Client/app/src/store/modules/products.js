const state = {
  produtosFiltrados: [],
};

const mutations = {
  setProdutosFiltrados(state, produtos) {
    state.produtosFiltrados = produtos;
  },
};

const actions = {
  updateProdutosFiltrados({ commit }, produtos) {
    commit("setProdutosFiltrados", produtos);
  },
};

const getters = {
  produtosFiltrados: (state) => state.produtosFiltrados,
};

export default {
  state,
  mutations,
  actions,
  getters,
};
