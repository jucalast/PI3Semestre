import axiosInstance from "@/utils/axiosInstance";

const state = {
  isAuthenticated: JSON.parse(localStorage.getItem("isAuthenticated")) || false,
};

const mutations = {
  SET_AUTHENTICATION(state, isAuthenticated) {
    state.isAuthenticated = isAuthenticated;
    localStorage.setItem("isAuthenticated", JSON.stringify(isAuthenticated));
  },
};

const actions = {
  async checkAuthentication({ commit }) {
    try {
      const response = await axiosInstance.get("/is-authenticated");
      if (response.status === 200) {
        commit("SET_AUTHENTICATION", true);
      } else {
        commit("SET_AUTHENTICATION", false);
      }
    } catch (error) {
      console.error("Erro ao verificar autenticação:", error);
      commit("SET_AUTHENTICATION", false);
    }
  },

  async logout({ commit }) {
    try {
      await axiosInstance.post("/logout");
      commit("SET_AUTHENTICATION", false);
      localStorage.removeItem("isAuthenticated");
    } catch (error) {
      console.error("Error on logout:", error);
    }
  },
};

const getters = {
  isAuthenticated: (state) => state.isAuthenticated,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};