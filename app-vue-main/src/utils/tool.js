/**
 * 将[1, 2, 3, 4, 5, 6, 7, 8, 28, 36, 17]转成数组
 */
export function strToArray(str) {
	if(str!==undefined&&str!==null&&str.length>0){
		return str.replaceAll(/[\s+|\[|\]]/g,"").split(",") // 去除两边的[]和空格
	}
	return []
}

//客户端提交的数据有些可以是null,但不能是空字符串
//此方法将空字符串转成null值,非空则返回原来的值
export function ifEmpty(str) {
	if(str!=null && str.replace(/\s+/g, "") == ""){
		return null 
	}
	return str
}