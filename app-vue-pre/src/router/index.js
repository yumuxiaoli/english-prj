import {
	createRouter,
	createWebHistory,
	createWebHashHistory
}
from 'vue-router'
import Login from '../views/login.vue'
import NotFound from "../views/404.vue"
import Register from "../views/register.vue"
import Home from "../views/home.vue"


const routes = [
	{
	  path: '/', // 默认路由
	  name: 'Index',
	  redirect: '/login' // 重定向到登录页面
	},
	{
		path: '/home',
		name: 'Home',
		component: Home
	},
	{
		path: '/recite',
		name: 'Recite',
		component: () => import('../views/recite.vue'),
	},
	{
		path: '/excise',
		name: 'Excise',
		component:() => import('../views/excise.vue')
	},
	{
		path: '/user',
		name: 'User',
		component:() => import('../views/user.vue')
	},{
		path: '/update-password',
		name: 'Update-password',
		component:() => import('../views/update-password.vue')
	},
	{
		path: '/course',
		name: 'Course',
		component:() => import('../views/course.vue')
	},
	{
		path: '/teacher',
		name: 'Teacher',
		component:() => import('../views/teacher.vue')		
	},
	{
		path: '/details',
		name: 'Details',
		component:() => import('../views/details.vue')
	},
	{
		path: '/span',
		name: 'Span',
		component:() => import('../views/span.vue')
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/register',
		name: 'Register',
		component: Register
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
		let token = localStorage.getItem("token")
		let avtivity_time =parseInt(localStorage.getItem("activity_time"));
		let now_time = parseInt(new Date().getTime()/1000);
		if((now_time - avtivity_time)>60*60 && !isNaN(avtivity_time)){//判断是否超过60分钟没有请求
			localStorage.removeItem("activity_time")
			next({ name: 'Login' }) // 进入登录页面
		}else{
			if (token == null || token == "") { // 未登录
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
