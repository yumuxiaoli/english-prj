import dayjs from 'dayjs'

export const formatTime = (value) => { 
  if (value) {
      return dayjs(value).format('YYYY-MM-DD HH:mm:ss')
  } else {
      return ''
  }
}

export const formatDate = (value) => { 
  if (value) {
      return dayjs(value).format('YYYY-MM-DD')
  } else {
      return ''
  }
}

export const formatFileSize = (value) => { 
  value = value || 0;
  let result;
  if (value > 100 * 1024) {
      result = Math.round((value / 1024 / 1024) * 100) / 100 + "MB";
  } else {
      result = Math.round((value / 1024) * 100) / 100 + "KB";
  }
  return result;
}

/**
 * @param list 例如：[{key:"C", value:"收费"},{key:"F", value:"免费"}]
 * @param key 例如：C
 * @returns {string} 例如：收费
 */
export const formatOpt = (list, key) =>  { //格式化下拉选项在主页面的显示内容
    if (!list || !key) {
        return "";
    } else {
        let result = "";
        for (let i = 0; i < list.length; i++) {
            if (key === list[i]["key"]) {
                result = list[i]["value"];
            }
        }
        return result;
    }
};
