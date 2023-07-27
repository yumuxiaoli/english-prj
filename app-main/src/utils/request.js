import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

//axios.defaults.baseURL = 'http://39.108.214.216:8090/abtec-api/'
//axios.defaults.baseURL = 'http://localhost:8090/sakura-api/'
axios.defaults.baseURL = 'http://localhost:8080/'
//axios.defaults.baseURL = 'http://www.sx10086.com:8090/sakura-api/'
//假如访问项目是采用http://192.168.232.1:3000,baseURL需要如下设置
//axios.defaults.baseURL = 'http://192.168.232.1:8090/audiobus-api/'
axios.defaults.withCredentials = true; //让ajax携带cookie,没这个设置，拿不到cookie
export const post = (url, data = {}) => { // post方式请求
  return new Promise((resolve, reject) => {
    axios.post(url, data, {
      headers: {
        'Content-Type': 'application/json;charset=UTF-8'
      }
    }).then((response) => {
      resolve(response)
    }).catch((e) => {
      reject(e)
    })
  })
}

export const get = (url, params = {}) => { // get方式请求
  return new Promise((resolve, reject) => {
    axios.get(url, { params }).then((response) => { // { params }的括号不能少
      resolve(response)
    }).catch((e) => {
      reject(e)
    })
  })
}

export const upload = (url, data) => { // 上传文件
  return new Promise((resolve, reject) => {
    axios.post(url, data, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then((response) => {
      resolve(response)
    }).catch((e) => {
      reject(e)
    })
  })
}

export const multiStrictGet = (getArray) => { // 同时发出多个请求从服务器获取数据,所有数据必须都正确返回才正确
  return Promise.all(getArray)
}
// 和之前唯一的不同就是在promise数组添加了一个回调函数，当数组中有接口reject时，catch住结果直接返回
// 这样失败的结果也可以当做成功处理，所以在promise.all中我们可以监听到所有结果的返回，然后再针对不同的返回值进行处理。
export const multiLooseGet = (getArray) => { // 同时发出多个请求从服务器获取数据，对异常进行了处理，可以返回部分数据
  return Promise.all(
    getArray.map(
      (get) => {
        return get.catch( // 对异常进行处理，再返回一个promise; carch和then都返回一个promise
          (err) => {
            console.log(err)
            return { responseCode: 500, responseMsg: '服务器异常' }
          }
        )
      }
    )
  )
}


//此方法封装对响应的处理
//param.resp: 服务器端返回的响应
//param.msg: 业务失败情况下的自定义提示信息
//param.dataLoading: 正在加载变量
//callback: 成功的回调
const SUCCESS = 6666 //服务器端返回的数据中code:6666表示业务完成成功
const FAIL = 7777 //服务器端返回的数据中code:7777表示业务完成失败
export const handleResponse = (param, callback) => {
  if(!(param.resp == undefined || param.resp == null || param.resp.data == undefined || param.resp.data == null)){ //响应有效
    //无论响应是何种状况，假如有正在加载的动态效果存在，则先关闭此效果
    if(param.dataLoading){ //假如动态加载变量存在
      param.dataLoading.value = false //取消动态加载
    }
    if(param.resp.data.code == SUCCESS) { 
      if(param.resp.data.rows!=null){ //服务器端是增、删、改操作，返回的data中包含rows属性
        if (param.resp.data.rows > 0) {
          callback()
        } else {
          param.msg = (param.msg==null || param.msg=='') ? '操作失败' : param.msg
          ElMessage({ message: param.msg, type: 'error', offset: 200, duration: 1200 })
        }
      }else{
        callback()
      }	
    }else if(param.resp.data.code == FAIL){
      param.msg = param.resp.data.msg //取出服务器端返回的错误信息
      param.msg = (param.msg==null || param.msg=='') ? '业务完成失败' : param.msg
      ElMessage({ message: param.msg, type: 'error', offset: 200, duration: 1200 })
    }  
  }else{ //响应无效
    if(param.dataLoading){ //假如动态加载变量存在
      param.dataLoading.value = false //取消动态加载
    }
    ElMessage({ message: '服务器异常', type: 'error', offset: 200, center: true })
  }
}