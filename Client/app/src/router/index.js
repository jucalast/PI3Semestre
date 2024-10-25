import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import ProductsPage from '@/views/ProductsPage.vue';
import CheckoutPage from '@/views/CheckoutPage.vue';
import AddressPage from '@/views/AddressPage.vue';

const routes = [
  { path: '/', component: HomePage },
  { path: '/products', component: ProductsPage, name: 'products' }, 
  { path: '/checkout', component: CheckoutPage, name: 'checkout' }, 
  { path: '/Address', component: AddressPage, name: 'address' },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
