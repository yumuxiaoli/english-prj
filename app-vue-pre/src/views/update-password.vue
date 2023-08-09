<template>
    <div class="body">
        <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
            style="position: fixed;overflow: hidden;width: 100%;z-index: 9999"
        >
            <el-menu-item index="0">
                LOGO
            </el-menu-item>
            <div class="flex-grow" />
            <el-menu-item index="1" @click="$router.push({ name: 'Home' })">
                首页
            </el-menu-item>
            <el-sub-menu index="2" >
                <template #title>单词卡</template>
                <el-menu-item index="2-1" @click="$router.push({ name: 'Recite' })">背诵</el-menu-item>
                <el-menu-item index="2-2" @click="$router.push({ name: 'Excise' })">测试</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="3">
                <template #title>课程</template>
                <el-menu-item index="3-1" @click="$router.push({ name: 'Course'})">视频</el-menu-item>
                <el-menu-item index="3-2" @click="$router.push({ name: 'Span'})">文章</el-menu-item>
                <el-menu-item index="3-3" @click="$router.push({ name: 'Teacher'})">老师</el-menu-item>
            </el-sub-menu>
            <el-menu-item class="img-head">
                <!-- <div> -->
                <el-popover
                    :width="300"
                    popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
                    >
                    <template #reference>
                        <el-avatar :size="55" @click="drawer = true"><img :src="image"></el-avatar>
                    </template>
                    <template #default>
                        <div
                            class="demo-rich-conent"
                            style="display: flex; gap: 16px; justify-content: center; flex-direction: row"
                            >
                            <el-avatar :size="60"  @error="errorHandler">
                                <img
                                    :src="image"
                                />
                            </el-avatar>
                                <div style="display: flex; flex-direction: column">
                                    <div style="display: flex; flex-direction: row;padding-bottom:10px ;">
                                        <div style="width: 100px;">name: </div>
                                        <div>{{ name }} </div>
                                    </div>
                                    <div style="display: flex; flex-direction: row;padding-bottom:10px ;">
                                        <div style="width: 100px;">nickname: </div>
                                        <div>{{ nickname }} </div>
                                    </div> 
                                </div>
                        </div>
                        <el-menu-item style="justify-content: center;" @click="$router.push({name:'User'})">
                            个人中心
                        </el-menu-item>
                        <el-menu-item style="justify-content: center;" @click="logout">
                            退出登录
                        </el-menu-item>
                    </template>
                </el-popover>
                <!-- </div> -->
            </el-menu-item>
        </el-menu>

        <div style="height: 40px;"></div>
        <div class="user-con">
            <div class="user-aside">
                <el-card class="user-box-card">
                    <div>
                        <el-menu router
                            :default-active="this.$router.path"
                            active-text-color="#ffd04b"
                            background-color="#545c64"
                            class="el-menu-vertical-demo"
                            text-color="#fff"
                            style="height: 550px">
                            <el-menu-item  style="display: flex; justify-content: center;align-self: center; height: 150px;">
                                <div>
                                    <el-avatar :size="60" src="https://empty" @error="errorHandler">
                                    <img
                                        :src="image"
                                    />
                                    </el-avatar>
                                </div>
                            </el-menu-item>
                            <el-menu-item index="/user" style="justify-content: center;font-size: 16px;">
                                个人中心
                            </el-menu-item>
                            <el-menu-item index="/update-password" style="justify-content: center;font-size: 16px;">
                                修改密码
                            </el-menu-item>
                            <el-menu-item index="3" style="justify-content: center;font-size: 16px;">
                                进度查看
                            </el-menu-item>
                        </el-menu>
                    </div> 
                </el-card>
                
            </div>
            <div class="user-main">
            <el-card class="box-card">
                <div class="user-contain" >
               <el-menu mode="horizontal">
                    <p>修改密码</p>
               </el-menu>
               <div class="user-upwd">
                    <div >
                        <el-form :model="dataForm" :rules="dataRule" ref="df" label-width="80px">
                            <el-form-item label="原密码" prop="password">
                                <el-input type="password" v-model="dataForm.password" size="medium" clearable />
                            </el-form-item>
                            <!-- prop属性和验证规则中的名字相对应 -->
                            <el-form-item label="新密码" prop="newPassword">
                                <el-input type="password" v-model="dataForm.newPassword" size="medium" clearable />
                            </el-form-item>
                            <el-form-item label="确认密码" prop="confirmPassword">
                                <el-input type="password" v-model="dataForm.confirmPassword" size="medium" clearable />
                            </el-form-item>
                        </el-form>
                    </div>   
                    <div class="dialog-footer">
                        <el-button size="medium" @click="visible = false">取消</el-button>	
                        <el-button type="primary" size="medium" @click="dataFormSubmit">确定</el-button>
                    </div>
               </div>
               
            </div>
            </el-card> 
            </div>
        </div>
    </div>
</template>

<script setup>
    import {  defineComponent, ref, reactive, nextTick } from 'vue'
    import { post, get, handleResponse } from '../utils/request.js'
    import { ElMessage, ElMessageBox } from 'element-plus'
    import { ifEmpty } from '../utils/tool.js'
    import {
		useRouter,
		useRoute
	} from 'vue-router'
    const pageInitEffect = () => { // 封装页面初始化逻辑
        // 数据
        const name = ref([])
        const image = ref([])
        const nickname = ref([])
        // 方法
        const getInitData = async () => {
            await get('user/loadUser').then((resp) => {
            console.log(resp)
            name.value = resp.data.username
            image.value = resp.data.image
                    nickname.value = resp.data.nickname
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
            image,
                nickname,
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
    const pwdInitEffect = () => { // 封装页面初始化逻辑
        // 数据
        const df = ref(null) // 获得页面的dom元素
        const dataForm = reactive({
            password: '',
            newPassword: '',
            confirmPassword: ''
        })
        // 方法
        const init = () => {
            visible.value = true;  //显示弹窗
            //因为清空表单控件是异步的，所以把清空表单控件放在下次DOM更新循环中
            // df.value.resetFields();//在此处执行，df为null,需要放在nextTick中执行
            nextTick(() => {
                df.value.resetFields();
            });
        }
        const dataFormSubmit =  () => {
            df.value.validate( async (valid) => {
            if (valid) {
                const data = { password: dataForm.confirmPassword }
                await post('english/updatePassword', data).then((resp) => {
                if (resp.data.rows == 1) {
                    ElMessage({ message: '密码修改成功', type: 'success', offset: 200, duration: 1200 });
                } else {
                    ElMessage({ message: '密码修改失败', type: 'error', offset: 200, duration: 1200 });
                }
                }).catch((err) => { 
                console.log(err)
                ElMessage({ message: '服务器异常', type: 'error', offset: 200, center: true, duration: 1200 })
                })
            }
            });
        }
    return { init, df, dataForm, dataFormSubmit }
    }
    const ConfirmPassword = (rule, value, callback) => {
        if (value !== dataForm.newPassword) {
            callback(new Error('两次输入密码不一致!'))
        } else {
            callback()
        }
    }
    const validatorRule = () => { //封装客户端数据验证规则
    const dataRule = ref('');
    
    dataRule.value = {
        password: [{ required: true, pattern: '^[a-zA-Z0-9]{6,20}$', message: '密码格式错误' }],
				newPassword: [{ required: true, pattern: '^[a-zA-Z0-9]{6,20}$', message: '密码格式错误' }],
				confirmPassword: [
					{ required: true, pattern: '^[a-zA-Z0-9]{6,20}$', message: '密码格式错误' },
					{ validator: ConfirmPassword, trigger: 'blur' }
				]
    }

    return { dataRule }
    }


    const errorHandler = () => true
    const {	name,image,getInitData,nickname } = pageInitEffect()
    const {logout,showUpdatePassword,updateWin} = logEffect()
    const { dataRule } = validatorRule()
    const { init, df, dataForm, dataFormSubmit }  = pwdInitEffect()
    getInitData();
</script>