import router from '@/router'
import axios from 'axios'
import VueAxios from 'vue-axios'

export function registerPlugins(app) {
  app
    .use(router)
    .use(VueAxios, axios)
}
