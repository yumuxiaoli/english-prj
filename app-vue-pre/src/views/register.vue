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
								<h2 style="font-size: 22px; line-height: 22px; height: 22px; margin-top:10px;">注册</h2>
							</div>
                            <el-form :model="dataForm" ref="df" :rules="dataRule" label-width="120px" 
                            v-loading.fullscreen.lock="dataLoading"
                            element-loading-text="数据上传中..."
                            >
                                <el-form-item label="用户名：" prop="username" >
                                    <el-input v-model="dataForm.username"  size="default" style="width:100%" clearable />
                                </el-form-item>
                                <el-form-item label="密码:" prop="password" >
                                    <el-input v-model="dataForm.password"  size="default" style="width:100%" clearable show-password/>
                                </el-form-item>
                                <el-form-item label="再次输入密码:" prop="password1" >
                                    <el-input v-model="dataForm.password1"  size="default" style="width:100%" clearable show-password/>
                                </el-form-item>
                                <el-form-item label="昵称:" prop="nickname" >
                                    <el-input v-model="dataForm.nickname"  size="default" style="width:100%" clearable />
                                </el-form-item>
                                <el-form-item label="头像:">
                                    <FrontUpload ref="imageUpload" @setInfo="setImageInfo" @removeInfo="removeImageInfo" :format="imageFormat"></FrontUpload>
                                </el-form-item>
                                <el-form-item label="头像路径:" prop="image">
                                    <el-input v-model="dataForm.image"  size="default" style="width:100%" readonly />
                                </el-form-item>
                                <el-form-item label="头像大小:" prop="imageSize">
                                    <el-input v-model="dataForm.imageSize"  size="default" style="width:100%" readonly />
                                </el-form-item>
                                <el-form-item label="邮箱:" prop="email" >
                                    <el-input v-model="dataForm.email"  size="default" style="width:100%" clearable />
                                </el-form-item>
                                <el-form-item label="电话:" prop="phone" >
                                    <el-input v-model="dataForm.phone"  size="default" style="width:100%" clearable />
                                </el-form-item>
                            </el-form>
                            <el-button type="primary" size="default" @click="dataFormSubmit">确定</el-button>
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
import dayjs from 'dayjs';
import { ref, reactive, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { post, get, handleResponse } from '../utils/request.js'
import { validateFileSize, validateSort } from '../utils/validate.js'
import FrontUpload from '../components/FrontUpload.vue'
const imageUploadEffect = (dataForm) => { // 封装文件上传逻辑
  const imageFormat = ref('.gif, .png, .jpeg, .jpg')
  //const imageFormat = ref('.exe, .zip, .rar, .pdf, .docx, .xlsx, .ppt')
  //const imageFormat = ref('.mp4')
  const setImageInfo = (data) => { //设置资源成功上传后的信息
    dataForm.image = data.url
    dataForm.imageSize = data.size
  }
  const removeImageInfo = () => {
    dataForm.image = ""
    dataForm.imageSize = 0
  }
  return { imageFormat, setImageInfo, removeImageInfo }
}
const operateEffect = () => { // 封装页面操作逻辑
  // 数据
  const visible = ref(false)
  const df = ref(null) // 获得页面的dom元素，对应页面的新增表单
  const dataLoading = ref(false) //动态加载
  const imageUpload = ref() //上传组件
  // 方法
  const dataForm = reactive({ //数据表单
    username: null,
    password: null,
    nickname: null,
    image: null,
    imageSize: 0,
    email: null,
    phone: null,
  })
  const init = async () => { //弹层初始化
    //因为清空表单控件是异步的，所以把清空表单控件放在下次DOM更新循环中
    nextTick(() => {
      df.value.resetFields() //清空表单
      imageUpload.value.clearUrl() //清空上传域
    })
  }
  const dataFormSubmit =  () => { //新增提交
    df.value.validate( async (valid) => {
      if (valid) { //表单验证
        dataLoading.value = true //显示动态加载
        await post('english/register', dataForm).then((resp) =>{
          handleResponse({ resp, dataLoading },
            () => {
              ElMessage({ message: '注册成功', type: 'success', offset: 200, duration: 1200 });
              emits("refreshDataList")
              router.push({name : 'Register'});
            }
          )
        })
      }
    });
  }
  return {
    init, visible, dataForm, dataFormSubmit, df, dataLoading, imageUpload,   
  }
}
const ConfirmPassword = (rule, value, callback) => {
    console.log(dataForm.password)
    if (value !== dataForm.password) {
        callback(new Error('两次输入密码不一致!'))
    } else {
        callback()
    }
}
const validatorRule = () => { //封装客户端数据验证规则
  const dataRule = ref('');
  
  dataRule.value = {
    username: [
      { required: true,message: "用户名格式错误", trigger: "blur" },
      { whitespace: true, message: "密码格式错误", trigger: "blur" },
      { 
        pattern: /^[a-zA-Z]\w{3,17}$/,
        trigger: ["blur", "change"],
        message: "格式错误",
     }
    ],
    password: [
        { required: true, message: "密码格式错误", trigger: "blur" },
        { whitespace: true, message: "密码格式错误", trigger: "blur" },
        { 
        pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]\w{4,17}$/,
        trigger: ["blur", "change"],
        message: "格式错误",
        }
    ],
    password1: [
        { trigger: 'blur', validator: ConfirmPassword }
    ],
    nickname: [
      { required: true, message: "昵称格式错误", trigger: "blur" },
      { whitespace: true, message: "昵称格式错误", trigger: "blur" }
    ],
    email: [
      { required: true, message: "邮箱格式错误", trigger: "blur" },
      { whitespace: true, message: "邮箱格式错误", trigger: "blur" },
      {
        pattern: /^([a-zA-Z\d][\w-]{2,})@(\w{2,})\.([a-z]{2,})(\.[a-z]{2,})?$/,
        trigger: ["blur", "change"],
        message: "格式错误",
      }
    ],
    phone: [
      { required: true, message: "电话格式错误", trigger: "blur" },
      { whitespace: true, message: "电话格式错误", trigger: "blur" },
      {
        pattern: /^1[3456789]\d{9}$/,
        trigger: ["blur", "change"],
        message: "格式错误",
      }
    ],
    image:[
      { required: true, message: "头像路径格式错误", trigger: ['blur'] },
      { whitespace: true, message: "头像路径格式错误", trigger: "blur" }
    ],
    imageSize:[
      { validator: validateFileSize, trigger: ['blur'] }
      //当通过js设置文本框的值时，不会触发，因此使用自定义的验证
      //{ required: true, pattern: '^[1-9]\\d*$', message: "文件大小无效", trigger: ['change'] },
    ],
  }
   
  return { dataRule }
}
const emits = defineEmits(["refreshDataList"]) //父传入子refreshDataList事件
const { init, visible, dataForm, dataFormSubmit, df, dataLoading, imageUpload,   } = operateEffect()
const { imageFormat, setImageInfo, removeImageInfo } = imageUploadEffect(dataForm)
const { dataRule } = validatorRule()
defineExpose({ init }) //向父组件暴露init方法
</script>
<style scoped="scoped"></style>