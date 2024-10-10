import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import ProductsPage from '@/views/ProductsPage.vue';

const routes = [
  { path: '/', component: HomePage },
  { path: '/products', component: ProductsPage, name: 'products' }, // Definindo o nome da rota
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
