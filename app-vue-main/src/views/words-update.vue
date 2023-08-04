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
			<el-form-item label="单词:" prop="words" >
				<el-input v-model="dataForm.words"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="读音:" prop="pron" >
				<el-input v-model="dataForm.pron"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="解释:" prop="explains" >
				<el-input v-model="dataForm.explains"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="等级:" prop="grades" >
				<el-input v-model="dataForm.grades"  size="default" style="width:100%" clearable />
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
const operateEffect = () => { // 封装页面操作逻辑
  // 数据
  const visible = ref(false)
  const df = ref(null) // 获得页面的dom元素，对应页面的新增表单
  const dataLoading = ref(false) //动态加载
  // 方法
  const dataForm = reactive({ //数据表单
  	id: 0,
  	words: null,
  	pron: null,
  	explains: null,
  	grades: null,
  })
  const init = async ( id ) => { //弹层初始化
    dataForm.id = id
    visible.value = true;  //显示弹窗
    //因为清空表单控件是异步的，所以把清空表单控件放在下次DOM更新循环中
    nextTick(() => {
      df.value.resetFields() //清空表单
    })
    await multiStrictGet([get('words/searchById', {id})])
      .then((result) => {
          if(result[0].data?.data){
            Object.assign(dataForm, result[0].data?.data) //将返回数据复制到dataForm中
            dataForm.updatedAt = dayjs(dataForm.updatedAt).format('YYYY-MM-DD HH:mm:ss') //格式化更新时间
          }else{
            ElMessage({ message: '未查到对应数据', type: 'warning', offset: 200, center: true })
          }
          
      }).catch((err) => {
        ElMessage({ message: '服务器错误', type: 'error', offset: 200, center: true })
        console.log(err)
      })
  }
  const dataFormSubmit =  () => { //新增提交
    df.value.validate( async (valid) => {
      if (valid) { //表单验证
        dataLoading.value = true //显示动态加载
        await post('words/update', dataForm).then((resp) =>{
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
    init, visible, dataForm, dataFormSubmit, df, dataLoading,    
  }
}
const validatorRule = () => { //封装客户端数据验证规则
  const dataRule = ref('');
  dataRule.value = {
    words: [
      { required: true, message: "单词格式错误", trigger: "blur" },
      { whitespace: true, message: "单词格式错误", trigger: "blur" }
    ],
    pron: [
      { required: true, message: "读音格式错误", trigger: "blur" },
      { whitespace: true, message: "读音格式错误", trigger: "blur" }
    ],
    explains: [
      { required: true, message: "解释格式错误", trigger: "blur" },
      { whitespace: true, message: "解释格式错误", trigger: "blur" }
    ],
    grades: [
      { required: true, message: "等级格式错误", trigger: "blur" },
      { whitespace: true, message: "等级格式错误", trigger: "blur" }
    ],
  }
  return { dataRule }
}
const emits = defineEmits(["refreshDataList"]) //父传入子refreshDataList事件
const { init, visible, dataForm, dataFormSubmit, df, dataLoading,   } = operateEffect()
const { dataRule } = validatorRule()
defineExpose({ init }) //向父组件暴露init方法
</script>
<style scoped="scoped"></style>