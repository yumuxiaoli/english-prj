<template>
	<el-upload
	  ref="uploadFile"
	  :accept="format"
	  :action="frontUploadUrl"
	  :auto-upload="true"
	  :limit="1"
	  :data="tokenObject"
	  :before-upload="beforeUpload"
	  :on-success="uploadSuccess"
	  :on-remove="onRemove"
	  >
	  <template #trigger>
	    <el-button size="small" type="primary">选择文件</el-button>
	  </template>
	  <!--具名插槽-->
	  <template #tip>
	     仅支持{{format}}
	  </template>
	</el-upload>
</template>

<script setup>
import { defineComponent, ref, reactive, nextTick, onMounted, getCurrentInstance, toRefs } from 'vue'
import { ElMessage } from 'element-plus'
import { post, get, upload } from '../utils/request.js'
// 数据
const uploadFile = ref(null)
const frontUploadUrl = ref('')
frontUploadUrl.value = getCurrentInstance()?.appContext.config.globalProperties.$frontuploadUrl 
const domainUrl = getCurrentInstance()?.appContext.config.globalProperties.$domainUrl //获取再main.js中定义的全局变量$uploadUrl
const tokenObject = reactive({
  token: '',
  key: ''
});
const url = ref('')
const size = ref(0)
const props = defineProps({ //接受从父组件传递过来的值
	format: String,
})
const {format} = toRefs(props) //使用父组件传递过来的值
const emits = defineEmits(["setInfo","removeInfo"]) //父传入子@setResourceUrl事件
// 方法
const beforeUpload = async(file)=>{
  const data = { // 每次加载数据发送的数据对象,对应分页数据和查询数据
      fileName: file.name
  };
  size.value = file.size
  await post('image/upload-token', data).then((resp) => { // then里的函数对应的是Promise中的resolve
   tokenObject.token = resp.data
   tokenObject.key = file.name //还要把生成token时的key传上去，否则会报错：{"error":"key doesn't match with scope"}
   console.log("token", tokenObject)
  }).catch((err) => {
    console.log(err)
    ElMessage({ message: '服务器异常', type: 'error', offset: 200, center: true })
  }) 
}
const uploadSuccess = (res)=>{
  url.value = domainUrl + res.key;//这个就是访问地址
  emits("setInfo", { 'url':url.value, 'size':size.value }) //触发子组件上的setResourceUrl事件，调用父组件中的setUrl方法
}
const onRemove = (file)=>{
  emits("removeInfo")
}
const clearUrl = () => {
  uploadFile.value.clearFiles() //清空当前的文件
}
defineExpose({ clearUrl }) //向父组件暴露getUrl方法 
</script>
<style scoped="scoped"></style>