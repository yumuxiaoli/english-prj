<template>
	<el-dialog
		:title="'更新'"
		:close-on-click-modal="false"
		v-model="visible"
		width="1080px"
	>
		<el-form :model="dataForm" ref="df" :rules="dataRule" label-width="120px" 
      v-loading.fullscreen.lock="dataLoading"
      element-loading-text="数据上传中..."
    >
			<el-form-item label="课程名:" prop="name" >
				<el-input v-model="dataForm.name"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="简介:" prop="introduce">
				<CKEditor ref="introduceEditor" />
			</el-form-item>
			<el-form-item label="详情:" prop="details">
				<CKEditor ref="detailsEditor" />
			</el-form-item>
			<el-form-item label="图片:" prop="oldImage" >
				<el-image :src="dataForm.oldImage" size="default" style="width:200px" clearable lazy/>
			</el-form-item>
			<el-form-item label="修改图片:">
			  <FrontUpload ref="imageUpload" @setInfo="setImageInfo" @removeInfo="removeImageInfo" :format="imageFormat"></FrontUpload>
			</el-form-item>
			<el-form-item label="图片路径:" prop="image">
				<el-input v-model="dataForm.image"  size="default" style="width:100%" readonly />
			</el-form-item>
			<el-form-item label="图片大小:" prop="imageSize">
				<el-input v-model="dataForm.imageSize"  size="default" style="width:100%" readonly />
			</el-form-item>
			<el-form-item label="分类类型:" prop="assortId" >
				<el-select v-model="dataForm.assortId" class="m-2" placeholder="请选择分类" size="large">
					<el-option
						v-for="(item,index) in assortList" :key="index" :label="item.name" :value="item.id"
					/>
				</el-select>
			</el-form-item>
			<el-form-item label="老师类型:" prop="teacherId" >
				<el-select v-model="dataForm.teacherId" class="m-2" placeholder="请选择老师" size="large">
					<el-option
						v-for="(item,index) in teacherList" :key="index" :label="item.name" :value="item.id"
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
import { post, get, multiStrictGet, handleResponse } from '../utils/request.js'
import { validateFileSize, validateSort } from '../utils/validate.js'
import FrontUpload from '../components/FrontUpload.vue'
import CKEditor from '../components/CKEditor.vue'
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
  const teacherList = ref([])
  const assortList = ref([])
  const detailsEditor = ref(); //富文本编辑器
  // 方法
  const dataForm = reactive({ //数据表单
  	id: 0,
  	name: null,
  	introduce: null,
  	details: null,
  	image: null,
  	imageSize: 0,
  	assortId: null,
  	teacherId: null,
    oldImage: null, //旧图片
  })
  const init = async ( id ) => { //弹层初始化
    dataForm.id = id
    visible.value = true;  //显示弹窗
    //因为清空表单控件是异步的，所以把清空表单控件放在下次DOM更新循环中
    nextTick(() => {
      df.value.resetFields() //清空表单
      imageUpload.value.clearUrl() //清空上传域
      detailsEditor.value.clearData() // 清空富文本编辑器的内容
    })
    await multiStrictGet([get('course/searchById', {id}),get('assort/listValidAssort'), get('teacher/listValidTeacher')])
      .then((result) => {
          if(result[0].data?.data){
            Object.assign(dataForm, result[0].data?.data) //将返回数据复制到dataForm中
            dataForm.updatedAt = dayjs(dataForm.updatedAt).format('YYYY-MM-DD HH:mm:ss') //格式化更新时间
            dataForm.oldImage = dataForm.image //设置旧图片
            dataForm.image=null //新图片路径清空
            detailsEditor.value.setData(dataForm.details) //设置富文本编辑器的内容
          }else{
            ElMessage({ message: '未查到对应数据', type: 'warning', offset: 200, center: true })
          }
          teacherList.value = result[2]?.data.dataList
          assortList.value = result[1]?.data.dataList
      }).catch((err) => {
        ElMessage({ message: '服务器错误', type: 'error', offset: 200, center: true })
        console.log(err)
      })
  }
  const dataFormSubmit =  () => { //新增提交
    df.value.validate( async (valid) => {
      if (valid) { //表单验证
        dataForm.details = detailsEditor.value.getData() //获取富文本编辑器的值
        //富文本编辑器验证
        if (dataForm.details == '') {
          ElMessage({ message: '详情不能为空!', type: 'warning', offset: 200, center: true })
          return
        }
        dataLoading.value = true //显示动态加载
        await post('course/update', dataForm).then((resp) =>{
          handleResponse({resp, dataLoading, 'msg':'更新失败'},
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
    init, visible, dataForm, dataFormSubmit, df, dataLoading, imageUpload, teacherList, detailsEditor, assortList
  }
}
const validatorRule = () => { //封装客户端数据验证规则
  const dataRule = ref('');
  dataRule.value = {
    name: [
      { required: true, message: "课程名格式错误", trigger: "blur" },
      { whitespace: true, message: "课程名格式错误", trigger: "blur" }
    ],
    teacherId: [
      { required: true, message: '老师不能为空' },
    ],
    image:[
      //{ required: true, message: "图片路径格式错误", trigger: ['blur'] },//因为文件可以不修改，所有无需required
      { whitespace: true, message: "图片路径格式错误", trigger: "blur" }
    ],
  }
  return { dataRule }
}
const emits = defineEmits(["refreshDataList"]) //父传入子refreshDataList事件
const { init, visible, dataForm, dataFormSubmit, df, dataLoading, imageUpload, teacherList,assortList, detailsEditor,} = operateEffect()
const { imageFormat, setImageInfo, removeImageInfo } = imageUploadEffect(dataForm)
const { dataRule } = validatorRule()
defineExpose({ init }) //向父组件暴露init方法
</script>
<style scoped="scoped"></style>