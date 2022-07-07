import Vue from 'vue'
import App from './App.vue'
import router from './router'
import request from "@/utils/request"
import ElementUI from 'element-ui'
// import cookie from 'vue-cookies'


import './assets/global.css'
import 'element-ui/lib/theme-chalk/index.css';

// Vue.use(cookie)
Vue.use(ElementUI, {size: "small"});
Vue.config.productionTip = false
Vue.prototype.request=request
// Vue.prototype.$cookie=cookie

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
