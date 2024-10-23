import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import ProductsPage from '@/views/ProductsPage.vue';
import CheckoutPage from '@/views/CheckoutPage.vue';

const routes = [
  { path: '/', component: HomePage },
  { path: '/products', component: ProductsPage, name: 'products' }, 
  { path: '/checkout', component: CheckoutPage, name: 'checkout' }, 

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
