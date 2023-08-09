<template>
	<el-dialog title="提示" v-model="visible" width="25%">
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
		<template #footer>
			<span class="dialog-footer">
				<el-button size="medium" @click="visible = false">取消</el-button>	
				<el-button type="primary" size="medium" @click="dataFormSubmit">确定</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script>
import { defineComponent, ref, reactive, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { post } from '../utils/request.js'
const pwdInitEffect = () => { // 封装页面初始化逻辑
  // 数据
  const visible = ref(false)
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
		await post('user/updatePassword', data).then((resp) => {
		  if (resp.data.rows == 1) {
			  ElMessage({ message: '密码修改成功', type: 'success', offset: 200, duration: 1200 });
		      visible.value = false;
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
  return { init, visible, df, dataForm, dataFormSubmit }
}
export default defineComponent({
	name: 'Update-password',
	setup (props, context) {
	  const { init, visible, df, dataForm, dataFormSubmit } = pwdInitEffect()
	  return {
	    init, visible, df, dataForm, dataFormSubmit
	  }
	},
	data() {
		const validateConfirmPassword = (rule, value, callback) => {
			if (value != this.dataForm.newPassword) {
				callback(new Error('两次输入的密码不一致'));
			} else {
				callback();
			}
		};
		return {
			dataRule: {
				password: [{ required: true, pattern: '^[a-zA-Z0-9]{6,20}$', message: '密码格式错误' }],
				newPassword: [{ required: true, pattern: '^[a-zA-Z0-9]{6,20}$', message: '密码格式错误' }],
				confirmPassword: [
					{ required: true, pattern: '^[a-zA-Z0-9]{6,20}$', message: '密码格式错误' },
					{ validator: validateConfirmPassword, trigger: 'blur' }
				]
			}
		};
	}
});

</script>

<style></style>
