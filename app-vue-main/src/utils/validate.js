/**
 * 验证邮箱
 * @param {*} s
 */
export function isEmail(s) {
	return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

/**
 * 验证手机号码
 * @param {*} s
 */
export function isMobile(s) {
	return /^1[0-9]{10}$/.test(s)
}

/**
 * 验证电话号码
 * @param {*} s
 */
export function isPhone(s) {
	return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * 验证URL地址
 * @param {*} s
 */
export function isURL(s) {
	return /^http[s]?:\/\/.*/.test(s)
}

/**
 * 验证用户名
 * @param {Object} s
 */
export function isUsername(s) {
	return /^[a-zA-Z0-9]{2,50}$/.test(s)
}


/**
 * 验证密码
 * @param {Object} s
 */
export function isPassword(s) {
	return /^[a-zA-Z0-9]{6,20}$/.test(s)
}

// 验证方法:传入验证数据,正则 ,返回true或者false
export const validateEle = (val, reg) => {
  if (reg.test(val)) {
    return true
  } else {
    return false
  }
}

/*验证框架的自定义验证方法*/
export const validateFileSize = (rule, value, callback) => { //验证上传文件大小
  if (value===null) {
    callback(new Error('文件大小无效'));
  } else if(!/^[1-9]\d*$/.test(value)){
    callback(new Error('文件大小无效'));
  }else {
    callback();
  }
}

export const validateSort = (rule, value, callback) => { //验证排序字段
  if (value===null) {
    callback(new Error('显示顺序无效'));
  } else {
    callback();
  }
}