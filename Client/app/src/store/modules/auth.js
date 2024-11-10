import axiosInstance from "@/utils/axiosInstance";

const state = {
  isAuthenticated: JSON.parse(localStorage.getItem("isAuthenticated")) || false,
  username: localStorage.getItem("username") || null,
};

const mutations = {
  setAuthentication(state, { authenticated, username }) {
    state.isAuthenticated = authenticated;
    state.username = username || null;

    localStorage.setItem("isAuthenticated", authenticated);
    if (username) {
      localStorage.setItem("username", username);
    } else {
      localStorage.removeItem("username");
    }
  },
};

const actions = {
  async checkAuth({ commit }) {
    try {
      const response = await axiosInstance.get("/check-auth");
      if (response.status === 200) {
        commit("setAuthentication", { authenticated: true, username: response.data.username });
      }
    } catch (error) {
      commit("setAuthentication", { authenticated: false });
    }
  },
  login({ commit }, username) {
    commit("setAuthentication", { authenticated: true, username });
  },
  logout({ commit }) {
    commit("setAuthentication", { authenticated: false });
  },
};

const getters = {
  isAuthenticated: (state) => state.isAuthenticated,
  username: (state) => state.username,
};

export default {
  namespaced: true, 
  state,
  mutations,
  actions,
  getters,
};
