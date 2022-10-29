import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Cart from '../views/Cart.vue'

const routes = [
  {path: '/', component: Home},
  {path: '/login', component: Login},
  {path: '/cart', component: Cart},
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router
