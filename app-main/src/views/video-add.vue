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
			<el-form-item label="题目:" prop="title" >
				<el-input v-model="dataForm.title"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="视频:">
			  <FrontUpload ref="videoUpload" @setInfo="setVideoInfo" @removeInfo="removeVideoInfo" :format="videoFormat"></FrontUpload>
			</el-form-item>
			<el-form-item label="视频路径:" prop="video">
				<el-input v-model="dataForm.video"  size="default" style="width:100%" readonly />
			</el-form-item>
			<el-form-item label="视频大小:" prop="videoSize">
				<el-input v-model="dataForm.videoSize"  size="default" style="width:100%" readonly />
			</el-form-item>
			<el-form-item label="课程类型:" prop="courseId" >
				<el-select v-model="dataForm.courseId" class="m-2" placeholder="请选择课程" size="large">
					<el-option
						v-for="(item,index) in courseList" :key="index" :label="item.name" :value="item.id"
					/>
				</el-select>
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
const videoUploadEffect = (dataForm) => { // 封装文件上传逻辑
  // const videoFormat = ref('.gif, .png, .jpeg, .jpg')
  //const videoFormat = ref('.exe, .zip, .rar, .pdf, .docx, .xlsx, .ppt')
  const videoFormat = ref('.mp4')
  const setVideoInfo = (data) => { //设置资源成功上传后的信息
    dataForm.video = data.url
    dataForm.videoSize = data.size
  }
  const removeVideoInfo = () => {
    dataForm.video = ""
    dataForm.videoSize = 0
  }
  return { videoFormat, setVideoInfo, removeVideoInfo }
}
const operateEffect = () => { // 封装页面操作逻辑
  // 数据
  const visible = ref(false)
  const df = ref(null) // 获得页面的dom元素，对应页面的新增表单
  const dataLoading = ref(false) //动态加载
  const videoUpload = ref() //上传组件
  const courseList = ref([])
  // 方法
  const dataForm = reactive({ //数据表单
    title: null,
    video: null,
    videoSize: null,
    courseId: null,
  })
  const init = async () => { //弹层初始化
    visible.value = true;  //显示弹窗
    //因为清空表单控件是异步的，所以把清空表单控件放在下次DOM更新循环中
    nextTick(() => {
      df.value.resetFields() //清空表单
      videoUpload.value.clearUrl() //清空上传域
    })
    await get('course/listValidCourse').then((resp) => { //查询有效数据
      handleResponse({resp},
        () => {
          courseList.value = resp.data.dataList
        }
      )
    })
  }
  const dataFormSubmit =  () => { //新增提交
    df.value.validate( async (valid) => {
      if (valid) { //表单验证
        dataLoading.value = true //显示动态加载
        await post('video/insert', dataForm).then((resp) =>{
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
    init, visible, dataForm, dataFormSubmit, df, dataLoading, videoUpload, courseList,  
  }
}
const validatorRule = () => { //封装客户端数据验证规则
  const dataRule = ref('');
  dataRule.value = {
    title: [
      { required: true, message: "题目格式错误", trigger: "blur" },
      { whitespace: true, message: "题目格式错误", trigger: "blur" }
    ],
    courseId: [
      { required: true, message: '课程不能为空' },
    ],
    video:[
      { required: true, message: "视频路径格式错误", trigger: ['blur'] },
      { whitespace: true, message: "视频路径格式错误", trigger: "blur" }
    ],
    videoSize:[
      { validator: validateFileSize, trigger: ['blur'] }
      //当通过js设置文本框的值时，不会触发，因此使用自定义的验证
      //{ required: true, pattern: '^[1-9]\\d*$', message: "文件大小无效", trigger: ['change'] },
    ],
  }
  return { dataRule }
}
const emits = defineEmits(["refreshDataList"]) //父传入子refreshDataList事件
const { init, visible, dataForm, dataFormSubmit, df, dataLoading, videoUpload, courseList,  } = operateEffect()
const { videoFormat, setVideoInfo, removeVideoInfo } = videoUploadEffect(dataForm)
const { dataRule } = validatorRule()
defineExpose({ init }) //向父组件暴露init方法
</script>
<style scoped="scoped"></style>