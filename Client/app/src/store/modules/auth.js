import axiosInstance from '@/utils/axiosInstance';

const state = {
  isAuthenticated: JSON.parse(localStorage.getItem('isAuthenticated')) || false,
  email: localStorage.getItem('email') || null,
  username: localStorage.getItem('username') || null,
  roles: JSON.parse(localStorage.getItem('roles')) || [],
  photoUrl: localStorage.getItem('photoUrl') || null, 
};

const mutations = {
  setAuthentication(state, { authenticated, email, username, roles, photoUrl }) {
    state.isAuthenticated = authenticated;
    state.email = email || null;
    state.username = username || null;
    state.roles = roles || [];
    state.photoUrl = photoUrl || null; 

    localStorage.setItem('isAuthenticated', authenticated);
    if (email) {
      localStorage.setItem('email', email);
    } else {
      localStorage.removeItem('email');
    }

    if (username) {
      localStorage.setItem('username', username);
    } else {
      localStorage.removeItem('username');
    }

    if (roles) {
      localStorage.setItem('roles', JSON.stringify(roles));
    } else {
      localStorage.removeItem('roles');
    }

    if (photoUrl) {
      localStorage.setItem('photoUrl', photoUrl); 
    } else {
      localStorage.removeItem('photoUrl');
    }
  },
};

const actions = {
  async checkAuth({ commit }) {
    try {
      const response = await axiosInstance.get('/check-auth');
      if (response.status === 200) {
        commit('setAuthentication', {
          authenticated: true,
          email: response.data.email,
          username: response.data.username,
          roles: response.data.roles,
          photoUrl: response.data.photoUrl,
        });
      }
    } catch (error) {
      commit('setAuthentication', { authenticated: false, roles: [] });
    }
  },
  login({ commit }, { email, username, roles, photoUrl }) {
    commit('setAuthentication', {
      authenticated: true,
      email,
      username,
      roles,
      photoUrl, 
    });
  },
  logout({ commit }) {
    commit('setAuthentication', { authenticated: false, roles: [], photoUrl: null });
  },
};

const getters = {
  isAuthenticated: (state) => state.isAuthenticated,
  email: (state) => state.email,
  username: (state) => state.username,
  roles: (state) => state.roles,
  photoUrl: (state) => state.photoUrl,
  isAdmin: (state) => state.roles.includes('ROLE_ADMIN'),
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
