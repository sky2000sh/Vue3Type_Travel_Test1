import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Join from '../views/Join.vue'
import Cart from '../views/Cart.vue'
import Order from '../views/Order.vue'
import Orders from '../views/Orders.vue'

const routes = [
  {path: '/', component: Home},
  {path: '/login', component: Login},
  {path: '/join', component: Join},
  {path: '/cart', component: Cart},
  {path: '/order', component: Order},
  {path: '/orders', component: Orders},
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router
