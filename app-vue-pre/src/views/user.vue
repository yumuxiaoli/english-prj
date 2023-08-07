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
                <el-menu-item index="3-2">文章</el-menu-item>
                <el-menu-item index="3-3">老师</el-menu-item>
            </el-sub-menu>
            <el-menu-item class="img-head">
                <!-- <div> -->
                <el-popover
                    :width="300"
                    popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
                    >
                    <template #reference>
                        <el-avatar :size="55" @click="drawer = true"> user </el-avatar>
                    </template>
                    <template #default>
                        <div
                            class="demo-rich-conent"
                            style="display: flex; gap: 16px; justify-content: center; flex-direction: row"
                            >
                                <el-avatar :size="55"> user </el-avatar>
                                <div style="display: flex; flex-direction: column">
                                    <p>name:</p>
                                    <p>age:</p> 
                                </div>
                        </div>
                        <el-menu-item style="justify-content: center;" @click="$router.push({name:'User'})">
                            个人中心
                        </el-menu-item>
                    </template>
                </el-popover>
                <!-- </div> -->
            </el-menu-item>
        </el-menu>

        <div style="height: 30px;"></div>
        <div class="user-con">
            <div class="user-aside">
                <el-card class="user-box-card">
                    <div>
                        <el-menu
                            active-text-color="#ffd04b"
                            background-color="#545c64"
                            class="el-menu-vertical-demo"
                            default-active="2"
                            text-color="#fff"
                            @open="handleOpen"
                            @close="handleClose"
                            style="height: 550px">
                            <el-menu-item index="1" style="display: flex; justify-content: center;align-self: center; height: 150px;">
                                <div>
                                    <el-avatar :size="60" src="https://empty" @error="errorHandler">
                                    <img
                                        src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                                    />
                                    </el-avatar>
                                </div>
                            </el-menu-item>
                            <el-menu-item style="justify-content: center;font-size: 16px;">
                                个人中心
                            </el-menu-item>
                            <el-menu-item style="justify-content: center;font-size: 16px;">
                                修改密码
                            </el-menu-item>
                            <el-menu-item style="justify-content: center;font-size: 16px;">
                                进度查看
                            </el-menu-item>
                        </el-menu>
                    </div> 
                </el-card>
                
            </div>
            <div class="user-main">
            <el-card class="box-card">
            <div class="user-contain">
               <el-menu mode="horizontal">
                    <p>基本信息</p>
               </el-menu>
               <div class="user-main-1">
                    <div class="us-1">
                        <div class="us-2"><p>用户名:</p></div>
                        <div>username{{  }}</div>
                    </div>
                    <div class="us-1">
                        <div class="us-2"><p>用户名:</p></div>
                        <div>username{{  }}</div>
                    </div><div class="us-1">
                        <div class="us-2"><p>用户名:</p></div>
                        <div>username{{  }}</div>
                    </div><div class="us-1">
                        <div class="us-2"><p>用户名:</p></div>
                        <div>username{{  }}</div>
                    </div><div class="us-1">
                        <div class="us-2"><p>用户名:</p></div>
                        <div>username{{  }}</div>
                    </div>
               </div>
               
            </div>
            </el-card> 
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
const errorHandler = () => true
</script>