import {
	createRouter,
	createWebHistory,
	createWebHashHistory
}
from 'vue-router'
import Login from '../views/login.vue'
//import Linechart from '../views/stat/linechart.vue'
import NotFound from "../views/404.vue"


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
		path: '/main',
		name: 'Main',
    component: () => import('../views/main.vue'),
		redirect: '/home', // 设置home中的<router-view></router-view>的默认路由
		children: [
			{
				path: '/home',
				name: 'Home',
        component: () => import('../views/home.vue'),
				meta: {
					title: '首页',
				}
			},
			{
				path: "/user",
				name: "User",
				component: () => import('../views/user.vue'),
				meta: {
					title: "学生管理",
					isTab: true
				}
			},
			{
				path: "/teacher",
				name: "Teacher",
				component: () => import('../views/teacher.vue'),
				meta: {
					title: "讲师管理",
					isTab: true
				}
			},
			{
				path: "/assort",
				name: "Assort",
				component: () => import('../views/assort.vue'),
				meta: {
					title: "分类管理",
					isTab: true
				}
			},
			{
				path: "/course",
				name: "Course",
				component: () => import('../views/course.vue'),
				meta: {
					title: "课程管理",
					isTab: true
				}
			},
			{
				path: "/span",
				name: "Span",
				component: () => import('../views/span.vue'),
				meta: {
					title: "文章管理",
					isTab: true
				}
			},
			{
				path: "/video",
				name: "Video",
				component: () => import('../views/video.vue'),
				meta: {
					title: "视频管理",
					isTab: true
				}
			}
		]
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
