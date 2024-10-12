import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Themes from '@/components/Themes.vue'
import Tasks from '@/components/Tasks.vue'
import Runner from '@/components/Runner.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
        path: '/themes',
        name: 'themes',
        component: Themes
    },
    {
        path: '/theme/:id',
        name: 'theme',
        component: Tasks,
        props: true
    },
    {
        path: '/task/:id',
        name: 'task',
        component: Runner,
        props: true
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
        path: '/error',
        name: 'error',
        component: () => import('../views/ErrorView.vue')
    }
  ]
})

export default router
