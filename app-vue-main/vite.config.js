import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  base:'/',
  server:{
    host:"0.0.0.0", //启动时，可以以Ip访问
    port:3000,      // 是否弹出浏览器
    open:false,
    cors:true
  },
  plugins: [vue()],
})
