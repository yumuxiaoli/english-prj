<template>
	<div class="site-wrapper" :class="{ 'site-sidebar--fold': sidebarFold }" v-loading.fullscreen.lock="loading"
		element-loading-text="拼命加载中">
		<nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
			<div class="site-navbar__header">
				<h1 class="site-navbar__brand">
					<a class="site-navbar__brand-lg">网站后台</a>
					<a class="site-navbar__brand-mini">OA</a>
				</h1>
			</div>
			<div class="site-navbar__body clearfix">
				<el-dropdown style="padding-top:8px;"
					class="site-navbar__avatar site-navbar__menu site-navbar__menu--right">
					<span class="el-dropdown-link">
						<img v-if="photo!=null" :src="photo" />
						<!--除管理员外的其他用户显示默认头像-->
						<img v-if="photo==null" src="../assets/header.png" />
						{{ name }}
					</span>
					<template #dropdown>
						<el-dropdown-menu>
							<el-dropdown-item @click="showUpdatePassword()">
								修改密码
							</el-dropdown-item>
							<el-dropdown-item @click="logout">退出</el-dropdown-item>
						</el-dropdown-menu>
					</template>
				</el-dropdown>
			</div>
			<update-password ref="updateWin"></update-password>
		</nav>
		<aside class="site-sidebar site-sidebar--dark">
			<div class="site-sidebar__inner">
				<!-- :default-openeds:默认打开-->
				<el-menu active-text-color="#ffd04b" background-color="#007BBB" class="el-menu-vertical-demo"
					default-active="2" text-color="#fff" @open="handleOpen" @close="handleClose" :default-openeds="openeds">
					<el-sub-menu index="1">
						<template #title>
							<span class="iconfont">&#xe60e;</span>
							<span>首页</span>
						</template>
						<el-menu-item index="1-1"
						@click="$router.push({ name: 'User' })">
						学生管理</el-menu-item>
						<el-menu-item index="1-2"
						@click="$router.push({ name: 'Teacher' })">
						讲师管理</el-menu-item>
						<el-menu-item index="1-3"
						@click="$router.push({ name: 'Words' })">
						单词管理</el-menu-item>
						<el-menu-item index="1-4"
						@click="$router.push({ name: 'Assort' })">
						分类管理</el-menu-item>
						<el-menu-item index="1-5"
						@click="$router.push({ name: 'Course' })">
						课程管理</el-menu-item>
						<el-menu-item index="1-6"
						@click="$router.push({ name: 'Span' })">
						文章管理</el-menu-item>
						<el-menu-item index="1-7"
						@click="$router.push({ name: 'Video' })">
						视频管理</el-menu-item>
						<el-menu-item index="1-8"
						@click="$router.push({ name: 'Excise' })">
						练习管理</el-menu-item>
					</el-sub-menu>
				</el-menu>
			</div>
		</aside>
		<div class="site-content__wrapper" :style="{ 'min-height': documentClientHeight + 'px' }">
			<main class="site-content" :class="{ 'site-content--tabs': $route.meta.isTab }">
				<el-tabs v-if="$route.meta.isTab" v-model="mainTabsActiveName" :closable="true"
					@tab-click="selectedTabHandle" @tab-remove="removeTabHandle">
					<el-dropdown class="site-tabs__tools" trigger="click">
						<i class="el-icon-arrow-down el-icon--right"></i>
						<template #dropdown>
							<el-dropdown-menu slot="dropdown">
								<el-dropdown-item @click.native="tabsCloseCurrentHandle">
									关闭当前标签页
								</el-dropdown-item>
								<el-dropdown-item @click.native="tabsCloseOtherHandle">
									关闭其它标签页
								</el-dropdown-item>
								<el-dropdown-item @click.native="tabsCloseAllHandle">
									关闭全部标签页
								</el-dropdown-item>
							</el-dropdown-menu>
						</template>
					</el-dropdown>
					<el-tab-pane v-for="item in mainTabs" :label="item.title" :name="item.name">
						<el-card :body-style="siteContentViewHeight">
							<iframe v-if="item.type === 'iframe'" :src="item.iframeUrl" width="100%" height="100%"
								frameborder="0" scrolling="yes"></iframe>
							<router-view v-if="item.name === mainTabsActiveName" />
						</el-card>
					</el-tab-pane>
				</el-tabs>
				<!-- 主入口标签页 e -->
				<el-card v-else :body-style="siteContentViewHeight">
					<router-view />
				</el-card>
			</main>
		</div>
	</div>
</template>

<script>
	import { isURL } from '../utils/validate';
	import UpdatePassword from './update-password.vue';
	import { get } from '../utils/request.js'
	import {
		useRouter,
		useRoute
	} from 'vue-router'
	import {
		ElMessage
	} from 'element-plus'
	import {
		defineComponent,
		reactive,
		toRefs,
		inject,
		nextTick,
		ref,
		provide,
		onMounted
	} from 'vue'
	const pageInitEffect = () => { // 封装页面初始化逻辑
		// 数据
		const name = ref([])
		const photo = ref([])
		// 方法
		const getInitData = async () => {
			await get('user/loadUserInfo').then((resp) => {
				console.log(resp)
				name.value = resp.data.name
				photo.value = resp.data.photo
			}).catch((err) => {
				console.log(err)
				ElMessage({
					message: '服务器异常',
					type: 'error',
					offset: 200,
					center: true
				})
			})
		}
		
		return {
			name,
			photo,
			getInitData
		}
	}
	const logEffect = () => { // 封装登录相关逻辑：登出、修改密码等
		const router = useRouter()
		// 数据
		const updateWin = ref(null) // 获得页面的dom元素,此处的updatewWin必须要在setup中return
		// 方法
		const logout = async () => {
			await get('user/logout').then((resp) => {
				//退出登陆之后没有必要在storage中保存用户权限，所以删除permissions
				localStorage.removeItem('token');
				//跳转到登陆页面
				router.push({
					name: 'Login'
				})
			}).catch((err) => {
				console.log(err)
				ElMessage({
					message: '服务器异常',
					type: 'error',
					offset: 200,
					center: true
				})
				privide()
			})
		}
		const showUpdatePassword = () => {
			updateWin.value.init() // 调用子组件中的init方法，对弹出层进行初始化
		}
		return {
			logout,
			showUpdatePassword,
			updateWin
		}
	}
	const sidebarEffect = () => { // 封装左侧菜单栏逻辑
		// 数据
		const navbarLayoutType = ref('')
		const sidebarFold = ref(false)
		const sidebarLayoutSkin = ref('dark')
		const openeds = ref(['1'])
		// 方法
		const handleSwitch = () => {
			if (sidebarFold.value) {
				navbarLayoutType.value = '';
			} else {
				navbarLayoutType.value = 'fold';
			}
			sidebarFold.value = !sidebarFold.value;
		}
		//折叠菜单事件
		const handleOpen = (key, keyPath) => {
		  console.log(key, keyPath)
		}
		const handleClose = (key, keyPath) => {
		  console.log(key, keyPath)
		}
		return {
			navbarLayoutType,
			sidebarFold,
			sidebarLayoutSkin,
			handleSwitch,
			handleOpen,
			handleClose,
			openeds
		}
	}
	const tabEffect = () => { // 封装选项卡逻辑
		const route = useRoute()
		const router = useRouter()
		// 数据
		const documentClientHeight = ref(0)
		let siteContentViewHeight = reactive({})
		const height = ref(null)
		const mainTabs = ref([])
		const mainTabsActiveName = ref('')
		const menuActiveName = ref('')
		// 方法
		const loadSiteContentViewHeight = () => {
			let height = documentClientHeight.value - 50 - 30 - 2;
			if (route.meta.isTab) {
				height -= 40;
				siteContentViewHeight = isURL(route.meta.iframeUrl) ? {
					height: height + 'px'
				} : {
					minHeight: height + 'px'
				};
				// height = provide('height',{ height: height-40 + 'px' })
			}
			siteContentViewHeight = {
				minHeight: height + 'px'
			};
		}
		const resetDocumentClientHeight = () => {
			documentClientHeight.value = document.documentElement['clientHeight'];
			// console.log(documentClientHeight.value)
			window.onresize = () => {
				documentClientHeight.value = document.documentElement['clientHeight'];
				loadSiteContentViewHeight();
			};
		}
		const routeHandle = () => {
			//每次切换页面，重新计算页面高度和内容区高度
			resetDocumentClientHeight();
			loadSiteContentViewHeight();
			if (route.meta.isTab) {
				// tab选中, 不存在先添加
				let tab = mainTabs.value.filter(item => item.name === route.name)[0];
				if (!tab) {
					tab = {
						menuId: route.meta.menuId || route.name,
						name: route.name,
						title: route.meta.title,
						type: isURL(route.meta.iframeUrl) ? 'iframe' : 'module',
						iframeUrl: route.meta.iframeUrl || '',
						params: route.params,
						query: route.query
					};
					mainTabs.value = mainTabs.value.concat(tab);
				}
				menuActiveName.value = tab.menuId + '';
				mainTabsActiveName.value = tab.name;
			}
		}
		const selectedTabHandle = (tab, e) => {
			tab = mainTabs.value.filter(item => item.name === tab.paneName);
			if (tab.length >= 1) {
				router.push({
					name: tab[0].name,
					query: tab[0].query,
					params: tab[0].params
				});
			}
		}
		const removeTabHandle = (tabName) => {
			mainTabs.value = mainTabs.value.filter(item => item.name !== tabName);
			if (mainTabs.value.length >= 1) {
				// 当前选中tab被删除
				if (tabName === mainTabsActiveName.value) {
					let tab = mainTabs.value[mainTabs.value.length - 1];
					router.push({
							name: tab.name,
							query: tab.query,
							params: tab.params
						},
						() => {
							mainTabsActiveName.value = route.name;
						}
					);
				}
			} else {
				menuActiveName.value = '';
				router.push({
					name: 'Home'
				});
			}
		}
		// tabs, 关闭当前
		const tabsCloseCurrentHandle = () => {
			removeTabHandle(mainTabsActiveName.value);
		}
		// tabs, 关闭其它
		const tabsCloseOtherHandle = () => {
			mainTabs.value = mainTabs.value.filter(item => item.name === mainTabsActiveName.value);
		}
		// tabs, 关闭全部
		const tabsCloseAllHandle = () => {
			mainTabs.value = [];
			menuActiveName.value = '';
			router.push({
				name: 'Home'
			});
		}
		return {
			documentClientHeight,
			siteContentViewHeight,
			height,
			mainTabs,
			mainTabsActiveName,
			menuActiveName,
			loadSiteContentViewHeight,
			resetDocumentClientHeight,
			routeHandle,
			selectedTabHandle,
			removeTabHandle,
			tabsCloseCurrentHandle,
			tabsCloseOtherHandle,
			tabsCloseAllHandle
		}
	}
	export default defineComponent({
		name: 'Main',
		components: {
			UpdatePassword
		},
		setup() {
			const {
				name,
				photo,
				getInitData
				
			} = pageInitEffect()
			const {
				logout,
				showUpdatePassword,
				updateWin
			} = logEffect()
			const {
				navbarLayoutType,
				sidebarFold,
				sidebarLayoutSkin,
				handleSwitch,
				handleOpen,
				handleClose,
				openeds
			} = sidebarEffect()
			const {
				documentClientHeight,
				siteContentViewHeight,
				height,
				mainTabs,
				mainTabsActiveName,
				menuActiveName,
				loadSiteContentViewHeight,
				resetDocumentClientHeight,
				routeHandle,
				selectedTabHandle,
				removeTabHandle,
				tabsCloseCurrentHandle,
				tabsCloseOtherHandle,
				tabsCloseAllHandle
			} = tabEffect()
			getInitData() // 执行页面初始化，加载所需数据
			onMounted(() => {
				resetDocumentClientHeight()
				loadSiteContentViewHeight()
			})
			return {
				name,
				photo,
				logout,
				showUpdatePassword,
				updateWin,
				navbarLayoutType,
				sidebarFold,
				sidebarLayoutSkin,
				handleSwitch,
				documentClientHeight,
				siteContentViewHeight,
				height,
				mainTabs,
				mainTabsActiveName,
				menuActiveName,
				loadSiteContentViewHeight,
				resetDocumentClientHeight,
				routeHandle,
				selectedTabHandle,
				removeTabHandle,
				tabsCloseCurrentHandle,
				tabsCloseOtherHandle,
				tabsCloseAllHandle,
				handleOpen,
				handleClose,
				openeds
			}
		},
		data: function() {
			return {
				loading: ''
			};
		},
		created() {
			let that = this;
			that.routeHandle(that.$route);
			// that.$options.sockets.onopen = function(resp) {
			// 	//发送心跳检测，避免超时后服务端切断连接
			// 	setInterval(function() {
			// 		that.$socket.sendObj({ opt: 'ping' });
			// 	}, 60 * 1000);
			// };
		},
		watch: {
			$route: {
				handler(to, from) {
					if (to.path != from.path) {
						// this.$router.push(to);
						this.routeHandle(to);
					}
				}
			}
		}
	});
</script>

<style lang="scss">
	@import '../assets/scss/index.scss';
  
  //设置一级菜单和二级菜单的背景颜色
  .el-menu-item {
    background-color: #39befc !important;
  }
  //设置选鼠标指针浮动在一级菜单的设置
  .el-menu-item:hover{
    background-color: #55aaff !important;;
  }
  //设置当前被选中的一级菜单
  .el-menu-item.is-active {  
    color: #ffd04b !important;  
    background: #55aaff !important;  
  } 
  
</style>
