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
    
        <div style="height: 30px;"></div>

        <div class="cou-menu">
            <div class="cou-assort">
                <div class="co-assort1">分类:</div>
                <div class="co-assort2" v-for="o in assort" :key="o"> 
                    <el-link type="primary" :underline="false">{{o.name}}</el-link>
                </div>
            </div>
            <div class="cou-assort">
                <div class="co-assort1">讲师:</div>
                <div class="co-assort2" v-for="o in teacher" :key="o"> 
                    <el-link type="primary" :underline="false">{{o.name}}</el-link>
                </div>
            </div>
        </div>

        <el-divider border-style="dashed"/>

        <div class="cou-middle">
            <div class="cou-1">
                  <div class="cou-li-1" v-for="o in dataList" :key="o">
                    <div class="co-li-1">
                        <el-image :src="o.image"/>
                    </div>
                    <div>
                        <router-link :to="'/details/'+o.name">{{ o.name }}</router-link>
                    </div>
                </div>               
            </div>    
        </div>

        <div class="co-page">
            <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          :total="totalCount"
          layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>	
        </div>


        <div class="home-foot">

        </div>
        <el-backtop :right="100" :bottom="100">
            <el-icon><ArrowUpBold /></el-icon>
        </el-backtop>
    </div>
</template>

<script setup>
  import { ref, reactive } from 'vue'
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
  const showEffect = () => { //封装显示操作, 包含数据显示功能: 加载数据列表、查询、分页显示功能
    // 数据
    const pageIndex = ref(1) //页码
    const pageSize = ref(10) //每页条数
    const totalCount = ref(0) //总条数
    const dataLoading = ref(false) //加载列表
    const dataList = ref([]) //数据列表
    const assort = ref([])
    const teacher = ref([])
    const df = ref(null) // 获得页面的查询表单，对应页面的<el-form ...... ref="df">
    // 方法
    const dataForm = reactive({ //查询表单
      name: null,
      orderType:null, //排序类型: descending|ascending
      orderColumn:null //排序字段名，对应的是prop属性
    })
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
        await post('assort/listAssortByPage', data).then((resp) => {
          handleResponse({resp, dataLoading},
            () => {
              const page = resp.data.page;
              assort.value = page.list;
            }
          )
        })
        await post('teacher/listTeacherByPage', data).then((resp) => {
          handleResponse({resp, dataLoading},
            () => {
              const page = resp.data.page;
              teacher.value = page.list;
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
    return { pageIndex, pageSize, totalCount, dataList, loadDataList, currentChangeHandle, dataForm, dataLoading, sizeChangeHandle, searchHandle, df,assort,teacher }
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
  
  const { pageIndex, pageSize, totalCount, dataList, loadDataList, currentChangeHandle, dataForm, dataLoading, sizeChangeHandle, searchHandle, df, assort,teacher } = showEffect()
  const { dataRule } = validatorRule()
  const {	name,image,getInitData,nickname } = pageInitEffect()
  const {logout,showUpdatePassword,updateWin} = logEffect()
  getInitData();
  loadDataList();
</script>