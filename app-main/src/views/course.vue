<template>
	<div>
		<el-form :inline="true" :model="dataForm" :rules="dataRule" ref="df">
			<el-form-item prop="name">
				<el-input
					v-model="dataForm.name"
					placeholder="课程名" size="default"
					class="input" clearable="clearable"
					@blur="searchHandle()" @keyup.enter.native="$event.target.blur()"
			    />
			</el-form-item>
			<el-form-item>
				<el-button size="default" type="primary" @click="searchHandle()">查询</el-button>
				<el-button
					size="default" type="primary"
					@click="addHandle()"
				>
					新增
				</el-button>
				<el-button
					size="default" type="danger"
					@click="deleteHandle()"
				>
					批量删除
				</el-button>
			</el-form-item>
		</el-form>
		<el-table
			:data="dataList"
			border
			v-loading.fullscreen.lock="dataLoading"
			element-loading-text="数据加载中..."
			@selection-change="selectionChangeHandle"
			size="default"
			style="width: 100%;"
			@sort-change="onSortChange"
		>
			<el-table-column type="selection" header-align="center" align="center" width="50"/>
			<el-table-column type="index" header-align="center" align="center" width="60" label="序号">
				<template #default="scope">
					<span>{{ (pageIndex - 1) * pageSize + scope.$index + 1 }}</span>
				</template>
			</el-table-column>
			<el-table-column prop="name" header-align="center" align="center" label="课程名" min-width="150" sortable="true"/>	
		    <el-table-column prop="photoSize" header-align="center" align="center" label="大小" min-width="150">
		      	<template #default="scope">
		      		 <span >{{$filters.formatFileSize(scope.row.imageSize)}}</span> <!--格式化大小-->
		      	</template>
		    </el-table-column>
			<el-table-column prop="assortId" header-align="center" align="center" label="分类" min-width="150"/>	
			<el-table-column prop="teacherId" header-align="center" align="center" label="老师" min-width="150"/>	
			<el-table-column prop="sort" header-align="center" align="center" label="顺序" min-width="150" sortable="true"/>	
			<el-table-column prop="updatedAt" header-align="center" align="center" label="修改时间" min-width="150">
				<template #default="scope">
					 <span >{{$filters.formatTime(scope.row.updatedAt)}}</span> <!--格式化日期-->
				</template>
			</el-table-column>	
			<el-table-column header-align="center" align="center" width="150" label="操作">
				<template #default="scope">
					<el-button
						link type="primary" size="default"  @click="updateHandle(scope.row.id)"
					>
						修改
					</el-button>
					<el-button
						link type="primary" size="default"  @click="deleteHandle(scope.row.id)"
					>
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination
			@size-change="sizeChangeHandle"
			@current-change="currentChangeHandle"
			:current-page="pageIndex"
			:page-sizes="[5, 10, 20]"
			:page-size="pageSize"
			:total="totalCount"
			layout="total, sizes, prev, pager, next, jumper"
		></el-pagination>	
		<add ref="addWin" @refreshDataList="loadDataList"></add>
    <update ref="updateWin" @refreshDataList="loadDataList"></update>
	</div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { post, get, handleResponse } from '../utils/request.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ifEmpty } from '../utils/tool.js'
import Add from './course-add.vue';
import Update from './course-update.vue';
const showEffect = () => { //封装显示操作, 包含数据显示功能: 加载数据列表、查询、分页显示功能
  // 数据
  const pageIndex = ref(1) //页码
  const pageSize = ref(10) //每页条数
  const totalCount = ref(0) //总条数
  const dataLoading = ref(false) //加载列表
  const dataList = ref([]) //数据列表
  const df = ref(null) // 获得页面的查询表单，对应页面的<el-form ...... ref="df">
  // 方法
  const dataForm = reactive({ //查询表单
  	name: null,
    orderType:null, //排序类型: descending|ascending
    orderColumn:null //排序字段名，对应的是prop属性
  })
  const onSortChange = (column) => { //表单排序方法
    dataForm.orderType = column.order
    dataForm.orderColumn = column.prop
    loadDataList()
  }
  const loadDataList = async () => { //加载数据列表
      dataLoading.value = true;
      const data = { // 每次加载数据发送的数据对象,对应分页数据和查询数据
        page: pageIndex.value,
        length: pageSize.value,
        name: ifEmpty(dataForm.name),
        orderType: dataForm.orderType,
        orderColumn: dataForm.orderColumn
      };
      await post('course/listCourseByPage', data).then((resp) => {
        handleResponse({resp, dataLoading},
          () => {
            const page = resp.data.page;
            dataList.value = page.list;
            totalCount.value = page.totalCount;
          }
        )
      })
  }
  const currentChangeHandle = (val) => { //分页导航 每次值改变就去请求接口
    pageIndex.value = val
    loadDataList()
  }
  const sizeChangeHandle = (val) => { //更改每页显示记录数量后，都从第一页开始查询
    pageSize.value = val;
    pageIndex.value = 1;
    loadDataList();
  }
  const searchHandle = () => { //查询
    df.value.validate(valid => { //先执行表单验证
       if (valid) {
          df.value.clearValidate();//清理页面上的表单验证结果
          dataForm.name = ifEmpty(dataForm.name)//因为服务器端进行正则验证，不允许上传空字符串给后端，但是可以传null值，
          if (pageIndex.value != 1) { //如果当前页面不是第一页，则跳转到第一页显示查询的结果
            pageIndex.value = 1;
          }
          loadDataList();
       } else {
          return false;
       }
    });
  }
  return { pageIndex, pageSize, totalCount, dataList, loadDataList, currentChangeHandle, dataForm, dataLoading, sizeChangeHandle, searchHandle, df, onSortChange }
}
const operateEffect = (loadDataList) => { // 封装增、删、改操作
  // 数据
  const dataListSelections=ref([]) // 数组里保存的是整个对象
  const addWin = ref(null) //新增弹层
  const updateWin = ref(null) //修改层
  // 方法
  const addHandle = () => { // 弹出新增层
    addWin.value.init() // 调用子组件中的init方法，对弹出层进行初始化
  }
  const updateHandle = (id) => { // 弹出修改层
    updateWin.value.init(id)
  }
  const selectionChangeHandle = (val) => { // 处理选中事件
    dataListSelections.value = val
    console.log(dataListSelections.value)
  }
  const deleteHandle = (id) => { // 删除事件
    let ids = id ? [id] : dataListSelections.value.map(item => { return item.id; });
    if (ids.length == 0) {
      ElMessage({ message: '没有选中记录', type: 'warning', offset: 200, duration: 1200 });
    } else {
      ElMessageBox.confirm(`确定要删除选中的记录？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).then( async () => {
        await post('course/deleteCourseByIds', { ids }).then((resp) => {
          handleResponse({resp,'msg':'删除失败'},
            () => {
              loadDataList();
              ElMessage({message: '操作成功',type: 'success',offset: 200,duration: 1200})
            }
          )
        })
      })
    }
  }
  return { selectionChangeHandle, deleteHandle, addHandle, addWin, updateHandle, updateWin }
}
const validatorRule = () => { //封装客户端数据验证规则
  const dataRule = ref('');
  dataRule.value = {
      name: [
        { required: false, message: "课程名格式错误", trigger: "blur" },
        { whitespace: true, message: "课程名格式错误", trigger: "blur" }
      ],
  }
  return { dataRule }
}
const { pageIndex, pageSize, totalCount, dataList, loadDataList, currentChangeHandle, dataForm, dataLoading, sizeChangeHandle, searchHandle, df, onSortChange } = showEffect()
const { selectionChangeHandle, deleteHandle, addHandle, addWin, updateHandle, updateWin } = operateEffect( loadDataList )
const { dataRule } = validatorRule()
loadDataList();
</script>
<style scoped="scoped"></style>