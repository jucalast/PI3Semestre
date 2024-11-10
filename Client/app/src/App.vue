<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'App',
  mounted() {
    this.checkAuth();
  },
  methods: {
    ...mapActions('auth', ['checkAuth']) 
  }
};
</script>

<style src="./assets/styles/global.css"></style>
