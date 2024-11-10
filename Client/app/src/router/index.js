import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import ProductsPage from '@/views/ProductsPage.vue';
import CheckoutPage from '@/views/CheckoutPage.vue';
import AddressPage from '@/views/AddressPage.vue';
import FavoritePage from '@/views/FavoritePage.vue';
import AdminDashboard from '@/views/AdminDashboard.vue';
import ProfilePage from '@/views/ProfilePage.vue';

const routes = [
  { path: '/', component: HomePage },
  { path: '/products', component: ProductsPage, name: 'products' },
  { path: '/checkout', component: CheckoutPage, name: 'checkout' },
  { path: '/Address', component: AddressPage, name: 'address' },
  { path: '/favorites', component: FavoritePage, name: 'FavoritePage' },
  { path: '/admin', component: AdminDashboard, name: 'AdminDashboard' },
  { path: '/profile', component: ProfilePage, name: 'ProfilePage' },
  { path: '/:notFound(.*)', redirect: '/' },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
