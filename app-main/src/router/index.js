import {
	createRouter,
	createWebHistory,
	createWebHashHistory
}
from 'vue-router'
import Login from '../views/login.vue'
//import Linechart from '../views/stat/linechart.vue'
import NotFound from "../views/404.vue"
import Home from "../views/home.vue"
import Main from "../views/main.vue"


const routes = [
	{
	  path: '/', // 默认路由
	  name: 'Index',
	  redirect: '/login' // 重定向到登录页面
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path:"/home",
		name:"Home",
		component: Home
	},
	{
		path:"/main",
		name:"Main",
		component:Main
	},
	{
		path: "/404",
		name: "NotFound",
		component: NotFound
	},
	{
		path: '/:pathMatch(.*)*',
		redirect: "/404"
	}
]

const router = createRouter({
  base:"/",
	history: createWebHashHistory(),
	//history: createWebHistory(),
	routes
})

router.beforeEach((to, from, next) => {
	if (to.name != "Login") { // 不是登录页面
		let permissions = localStorage.getItem("permissions")
		let token = localStorage.getItem("token")
		let avtivity_time =parseInt(localStorage.getItem("activity_time"));
		let now_time = parseInt(new Date().getTime()/1000);
		if((now_time - avtivity_time)>60*60 && !isNaN(avtivity_time)){//判断是否超过60分钟没有请求
			localStorage.removeItem("activity_time")
			next({ name: 'Login' }) // 进入登录页面
		}else{
			if (permissions == null || permissions == ""||token == null || token == "") { // 未登录
				next({ name: 'Login' }) // 进入登录页面
			} else {
				localStorage.setItem("activity_time",parseInt(new Date().getTime()/1000));
				next()
			}
		}
	} else {
		next() // 进入下一页面
	}
	// return
})



export default router
