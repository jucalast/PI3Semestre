// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import AdminDashboard from '@/views/AdminDashboard.vue';
import ProductPage from '@/views/ProductPage.vue';
import CartPage from '@/views/CartPage.vue'; // Adicionei a importação para a página do carrinho

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomePage,
  },
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: AdminDashboard,
  },
  {
    path: '/products',
    name: 'ProductPage',
    component: ProductPage, // Certifique-se de que o componente existe
  },
  {
    path: '/cart',
    name: 'CartPage',
    component: CartPage, // Certifique-se de que o componente existe
  },
  // Outras rotas...
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
