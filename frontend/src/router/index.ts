import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Cart from '../views/Cart.vue'
import Join from '../views/Join.vue'

const routes = [
  {path: '/', component: Home},
  {path: '/login', component: Login},
  {path: '/cart', component: Cart},
  {path: '/join', component: Join},
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router
