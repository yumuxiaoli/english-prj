<template>
	<el-dialog
		:title="'新增'"
		:close-on-click-modal="false"
		v-model="visible"
		width="1080px"
	>
		<el-form :model="dataForm" ref="df" :rules="dataRule" label-width="120px" 
      v-loading.fullscreen.lock="dataLoading"
      element-loading-text="数据上传中..."
    >
			<el-form-item label="用户名:" prop="username" >
				<el-input v-model="dataForm.username"  size="default" style="width:100%" clearable />
			</el-form-item>
      <el-form-item label="密码:" prop="password" >
				<el-input v-model="dataForm.password"  size="default" style="width:100%" clearable />
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
		<template #footer>
			<span class="dialog-footer">
				<el-button size="default" @click="visible = false">取消</el-button>
				<el-button type="primary" size="default" @click="dataFormSubmit">确定</el-button>
			</span>
		</template>
	</el-dialog>
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
    nickname: null,
    password: null,
    image: null,
    imageSize: 0,
    email: null,
    phone: null,
  })
  const init = async () => { //弹层初始化
    visible.value = true;  //显示弹窗
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
        await post('user/insert', dataForm).then((resp) =>{
          handleResponse({ resp, dataLoading },
            () => {
              visible.value = false //隐藏弹层
              ElMessage({ message: '操作成功', type: 'success', offset: 200, duration: 1200 });
              emits("refreshDataList")
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
const validatorRule = () => { //封装客户端数据验证规则
  const dataRule = ref('');
  dataRule.value = {
    username: [
      { required: true, message: "姓名格式错误", trigger: "blur" },
      { whitespace: true, message: "姓名格式错误", trigger: "blur" }
    ],
    nickname: [
      { required: true, message: "昵称格式错误", trigger: "blur" },
      { whitespace: true, message: "昵称格式错误", trigger: "blur" }
    ],
    email: [
      { required: true, message: "邮箱格式错误", trigger: "blur" },
      { whitespace: true, message: "邮箱格式错误", trigger: "blur" }
    ],
    phone: [
      { required: true, message: "电话格式错误", trigger: "blur" },
      { whitespace: true, message: "电话格式错误", trigger: "blur" }
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