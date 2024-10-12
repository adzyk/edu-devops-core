// Plugins
import { registerPlugins } from '@/plugins'

import './assets/main.css'

import App from './App.vue'
import { createApp } from 'vue'

import { login } from '@/plugins/keycloak'
import { setupInterceptors } from '@/plugins/axios'
import {setupRouteWatch} from '@/plugins/router'


login(() => {
  setupInterceptors()
  setupRouteWatch()

  const app = createApp(App)

  registerPlugins(app)

  app.mount('#app')
})
