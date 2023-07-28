<template>
  <ckeditor :editor="state.editor" v-model="state.editorData" :config="state.editorConfig"></ckeditor>
</template>
<script setup>
import { ref, reactive, getCurrentInstance } from 'vue'
import '../../ckeditor5-36.0.1/build/ckeditor' //此处引入ClassicEditor
const ckUploadUrl = getCurrentInstance()?.appContext.config.globalProperties.$ckuploadUrl //获取再main.js中定义的全局变量$ckuploadUrl
const state = reactive({
  editor: ClassicEditor,
  editorData: '',
  editorConfig: {
    simpleUpload: {
        // The URL that the images are uploaded to.
        uploadUrl: ckUploadUrl,
        // Enable the XMLHttpRequest.withCredentials property.
        withCredentials: true
    },
    toolbar: {
      items: [
        'heading',
        '|',
        'bold',
        'italic',
        'fontFamily',
        'fontSize',
        'undo',
        'redo',
        'link',
        'bulletedList',
        'numberedList',
        'findAndReplace',
        'horizontalLine',
        '|',
        'outdent',
        'indent',
        'alignment',
        '|',
        'imageUpload',
        'insertTable',
        'blockQuote',
        'mediaEmbed',
        '|',
        'fontBackgroundColor',
        'fontColor',
        'codeBlock',
        'sourceEditing'
      ]
    },
    language: 'zh-cn',
    image: {
      toolbar: [
        'imageTextAlternative',
        'toggleImageCaption',
        'imageStyle:inline',
        'imageStyle:block',
        'imageStyle:side'
      ]
    },
    table: {
      contentToolbar: [
        'tableColumn',
        'tableRow',
        'mergeTableCells'
      ]
    },
    fontFamily: {
      options: [
        'default',
        '宋体',
        '新宋体',
        '仿宋',
        '楷体',
        '微软雅黑',
        '黑体',
        '华文仿宋',
        '华文楷体',
        '华文隶书',
        '华文宋体',
        '华文细黑',
        '华文新魏',
        '华文行楷',
        '华文中宋',
        '隶书',
        '苹方 常规',
        '幼圆'
      ],
      supportAllValues: true
    },
    fontSize: {
      options: [10, 12, 14, 'default', 18, 20, 22],
      supportAllValues: true
    }
  }
})
const getData = () => { //获取数据
  return state.editorData
}
const setData = (data) => { //设置数据
  state.editorData = data
}
const clearData = () => { //清除数据
  state.editorData = ""
}
defineExpose({ //对外暴露方法 
  getData, setData, clearData
})
</script>

<!--此处的style要采用全局的才会生效-->
<style>
.ck-editor {
    min-width: 910px !important;
}
.ck-content {
    min-height: 300px;
}
</style>
