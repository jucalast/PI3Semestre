import axiosInstance from '@/utils/axiosInstance'; // Ajuste o caminho conforme necessário

const state = {
  isAuthenticated: false,
};

const mutations = {
  setAuthentication(state, status) {
    state.isAuthenticated = status;
  },
};

const actions = {
  async checkAuth({ commit }) {
    try {
      const response = await axiosInstance.get('/check-auth');
      if (response.status === 200) {
        commit('setAuthentication', true);
      }
    } catch (error) {
      commit('setAuthentication', false);
    }
  },
  login({ commit }) {
    commit('setAuthentication', true);
  },
  logout({ commit }) {
    commit('setAuthentication', false);
  },
};

const getters = {
  isAuthenticated: (state) => state.isAuthenticated,
};

export default {
  state,
  mutations,
  actions,
  getters,
};
