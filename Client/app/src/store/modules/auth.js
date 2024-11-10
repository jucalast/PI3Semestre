import axiosInstance from "@/utils/axiosInstance";

const state = {
  isAuthenticated: JSON.parse(localStorage.getItem("isAuthenticated")) || false,
  username: localStorage.getItem("username") || null,
  roles: JSON.parse(localStorage.getItem("roles")) || [],
};

const mutations = {
  setAuthentication(state, { authenticated, username, roles }) {
    state.isAuthenticated = authenticated;
    state.username = username || null;
    state.roles = roles || [];

    localStorage.setItem("isAuthenticated", authenticated);
    if (username) {
      localStorage.setItem("username", username);
    } else {
      localStorage.removeItem("username");
    }

    if (roles) {
      localStorage.setItem("roles", JSON.stringify(roles));
    } else {
      localStorage.removeItem("roles");
    }
  },
};

const actions = {
  async checkAuth({ commit }) {
    try {
      const response = await axiosInstance.get("/check-auth");
      if (response.status === 200) {
        commit("setAuthentication", {
          authenticated: true,
          username: response.data.username,
          roles: response.data.roles,
        });
      }
    } catch (error) {
      commit("setAuthentication", { authenticated: false, roles: [] });
    }
  },
  login({ commit }, { username, roles }) {
    commit("setAuthentication", { authenticated: true, username, roles });
  },
  logout({ commit }) {
    commit("setAuthentication", { authenticated: false, roles: [] });
  },
};

const getters = {
  isAuthenticated: (state) => state.isAuthenticated,
  username: (state) => state.username,
  roles: (state) => state.roles,
  isAdmin: (state) => state.roles.includes("ROLE_ADMIN"),
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
