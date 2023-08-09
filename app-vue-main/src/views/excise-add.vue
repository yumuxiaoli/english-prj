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
			<el-form-item label="题型:" prop="questiontype" >
				<el-input v-model="dataForm.questiontype"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="正确答案:" prop="rightanswer" >
				<el-input v-model="dataForm.rightanswer"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="选项A:" prop="selecta" >
				<el-input v-model="dataForm.selecta"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="选项B:" prop="selectb" >
				<el-input v-model="dataForm.selectb"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="选项C:" prop="selectc" >
				<el-input v-model="dataForm.selectc"  size="default" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="选项D:" prop="selectd" >
				<el-input v-model="dataForm.selectd"  size="default" style="width:100%" clearable />
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
const operateEffect = () => { // 封装页面操作逻辑
  // 数据
  const visible = ref(false)
  const df = ref(null) // 获得页面的dom元素，对应页面的新增表单
  const dataLoading = ref(false) //动态加载
  // 方法
  const dataForm = reactive({ //数据表单
    title: null,
    questiontype: null,
    rightanswer: null,
    selecta: null,
    selectb: null,
    selectc: null,
    selectd: null,
    exlog: "0",
  })
  const init = async () => { //弹层初始化
    visible.value = true;  //显示弹窗
    //因为清空表单控件是异步的，所以把清空表单控件放在下次DOM更新循环中
    nextTick(() => {
      df.value.resetFields() //清空表单
    })
  }
  const dataFormSubmit =  () => { //新增提交
    df.value.validate( async (valid) => {
      if (valid) { //表单验证
        dataLoading.value = true //显示动态加载
        await post('excise/insert', dataForm).then((resp) =>{
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
    init, visible, dataForm, dataFormSubmit, df, dataLoading,    
  }
}
const validatorRule = () => { //封装客户端数据验证规则
  const dataRule = ref('');
  dataRule.value = {
    title: [
      { required: true, message: "题目格式错误", trigger: "blur" },
      { whitespace: true, message: "题目格式错误", trigger: "blur" }
    ],
    questiontype: [
      { required: true, message: "题型格式错误", trigger: "blur" },
      { whitespace: true, message: "题型格式错误", trigger: "blur" }
    ],
    rightanswer: [
      { required: true, message: "正确答案格式错误", trigger: "blur" },
      { whitespace: true, message: "正确答案格式错误", trigger: "blur" }
    ],
    selecta: [
      { required: true, message: "选项A格式错误", trigger: "blur" },
      { whitespace: true, message: "选项A格式错误", trigger: "blur" }
    ],
    selectb: [
      { required: true, message: "选项B格式错误", trigger: "blur" },
      { whitespace: true, message: "选项B格式错误", trigger: "blur" }
    ],
    selectc: [
      { required: true, message: "选项C格式错误", trigger: "blur" },
      { whitespace: true, message: "选项C格式错误", trigger: "blur" }
    ],
    selectd: [
      { required: true, message: "选项D格式错误", trigger: "blur" },
      { whitespace: true, message: "选项D格式错误", trigger: "blur" }
    ],
  }
  return { dataRule }
}
const emits = defineEmits(["refreshDataList"]) //父传入子refreshDataList事件
const { init, visible, dataForm, dataFormSubmit, df, dataLoading,    } = operateEffect()
const { dataRule } = validatorRule()
defineExpose({ init }) //向父组件暴露init方法
</script>
<style scoped="scoped"></style>