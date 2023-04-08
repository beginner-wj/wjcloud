import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import axios, {HttpStatusCode} from "axios";
import filter from "@/filter/filter";

Vue.config.productionTip = false
Vue.prototype.$ajax = axios


//解决每次ajax请求对应的sessionId不一致的问题
axios.defaults.withCredentials = true;

/***
 * axios 拦截器
 */
axios.interceptors.request.use(function (config) {
  console.log("请求:",config);
  let token = Tool.getLoginUser().token;
  if(Tool.isNotEmpty(token)){
    config.headers.token = token;
  }
  return config;
})

axios.interceptors.response.use(function (response) {
  console.log("返回结果:",response);
  return response;
},error => {
  console.log("error==>",error);
  let status = error.response.status
  Loading.hide();
  if(status == 401){
    toast.error("未授权")
  }else{
    toast.error(error)
  }
})

//全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key,filter[key])
});


//路由登录拦截
router.beforeEach((to,from,next) => {
   if(to.matched.some(function (item) {
     return item.meta.loginRequire
   })){
     let loginUser = Tool.getLoginUser();
     if(Tool.isEmpty(loginUser)){
       next('login');
     }else{
       next()
     }
   }else{
     next();
   }
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

console.log("环境：",process.env.NODE_ENV);
