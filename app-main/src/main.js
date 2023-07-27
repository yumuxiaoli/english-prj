import { createApp } from 'vue'
import App from './App.vue'

const app = createApp(App) //创建VUE对象

//导入路由配置
import router from './router'
app.use(router) //挂载路由插件

//导入vuex配置
import store from './store'
app.use(store) //挂载vuex插件

//导入Cookie库，可以读写Cookie数据
import VueCookies from 'vue3-cookies'
app.use(VueCookies); //挂载Cookie插件

//导入ElementUI
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
//挂载ElementUl-Plus插件
app.use(ElementPlus)

//导入echarts
import * as echarts from 'echarts'
app.config.globalProperties.$echarts = echarts  //设置全局变量$echarts

//封装用于判断用户是否具有某些权限的公共函数
app.config.globalProperties.isAuth = function(permission) {
	let permissions = localStorage.getItem("permissions");
	let flag = false
	if(permissions!=null){
		for (let one of permission) {
			if (permissions.includes(one)) {
				flag = true
				break;
			}
		}	
	}
	return flag;
}


//从format.js导入转换函数,加载全局过滤器
import { formatTime, formatDate, formatFileSize, formatOpt } from './utils/format.js'
app.config.globalProperties.$filters = {
  formatTime, formatDate, formatFileSize, formatOpt
}

//全局导入element-plus/icons-vue
//英文修改
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

/**
 * axios拦截器
 */
import axios from 'axios'
axios.interceptors.request.use(function(config){
  console.log("请求：",config);
  return config;
}, error => {});

axios.interceptors.response.use(function(response){
  console.log("返回结果：",response);
  return response;
}, error => {});


// chkeditor
// 全局引入
// import CKEditor from '@ckeditor/ckeditor5-vue'; 
// app.use(CKEditor)


//定义全局变量，设置富文本编辑器的图片上次地址：

app.config.globalProperties.$frontuploadUrl="http://upload-z2.qiniup.com" //前端上传地址

app.config.globalProperties.$domainUrl='http://rxzbgo2zp.hn-bkt.clouddn.com/'//七牛云图片访问域名
//app.config.globalProperties.$domainUrl='http://www.sx10086.com/'//七牛云图片访问域名

//app.config.globalProperties.$uploadUrl=import.meta.env.VITE_BASE_URL + ":8090/smarts-api/image/upload-wang" //wangEditor上传地址

// app.config.globalProperties.$ckuploadUrl=import.meta.env.VITE_BASE_URL + ":8090/smarts-api/image/upload-ck" //ckeditor上传地址

//app.config.globalProperties.$weixinUploadUrl=import.meta.env.VITE_BASE_URL + ":8090/smarts-api/weixin/upload-resource" //定义富文本上传附件到微信公众平台

app.mount('#app')