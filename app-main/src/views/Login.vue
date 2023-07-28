<template>
	<div class="page">
		<el-row type="flex" justify="center" align="middle" class="container">
			<el-col :lg="14" :xl="10" class="hidden-md-and-down">
				<el-row class="panel">
					<el-col :span="12">
						<!-- <div class="left">
							<img src="../assets/login/big-1.png" class="big" style="margin-top: 30px;"/>
						</div> -->
					</el-col>
					<el-col :span="12">
						<div class="right">
							<div class="title-container">
								<h2 style="font-size: 22px; line-height: 22px; height: 22px; margin-top:10px;">信息发布系统</h2>
								<span>( 1.0 )</span>
							</div>
							<div>
								<div class="row">
									<el-input
										v-model="username"
										placeholder="用户名"
										prefix-icon="el-icon-user"
										clearable="clearable"
									/>
								</div>
								<div class="row">
									<el-input
										type="password"
										v-model="password"
										placeholder="密码"
										prefix-icon="el-icon-lock"
										clearable="clearable"
									/>
								</div>
								<div class="row">
									<el-button type="primary" class="btn" @click="handleLogin">登陆系统</el-button>
								</div>
							</div>
							<div>
								<!-- <div class="qrCode-container">
									<img src="../assets/login/phone.png" height="148" />
								</div> -->
							</div>
						</div>
					</el-col>
				</el-row>
			</el-col>
		</el-row>
	</div>
</template>

<script setup>
import router from '../router/index.js';
import { isUsername, isPassword } from '../utils/validate.js'
import { post } from '../utils/request.js'
import { defineComponent, reactive, toRefs, inject } from 'vue'
import { ElMessage } from 'element-plus'
import { useCookies } from "vue3-cookies"
const useLoginEffect = () => { // 封装登录逻辑
  const { cookies } = useCookies();
  // 数据
  const user = reactive({
    username: '',
    password: ''
  })
  
  // 方法
  const handleLogin = async () => {
    if (!isUsername(user.username)) {
        ElMessage({ message: '用户名格式不正确', type: 'error', offset: 200, duration: 1200 });
    } else if (!isPassword(user.password)) {
		ElMessage({ message: '密码格式不正确', type: 'error', offset: 200, duration: 1200 });
    } else {
        //发送登陆请求
		await post('user/login', user).then((resp) => { // then里的函数对应的是Promise中的resolve
		  console.log(resp)
		  if (resp.data.result) {
			const token = cookies.get('token')
			console.log(token)
		localStorage.setItem('token', token);
        router.push({ name: 'Main'});
		  } else {
			  ElMessage({ message: '登陆失败', type: 'error', offset: 200, duration: 1200 });
		  }
		}).catch((err) => { 
		  console.log(err)
		  ElMessage({ message: '服务器异常', type: 'error', offset: 200, center: true })
		})
    }
  }
  const { username, password } = toRefs(user) //对user解构，toRefs的作用是使解构返回响应式数据
  return {
    username, password, handleLogin
  }
}
const { username, password, handleLogin } = useLoginEffect()
</script>

<style lang="less" scoped="scoped">
@import url('login.less');
</style>
