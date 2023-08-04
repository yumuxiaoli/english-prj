<template>
    <div class="body">
        <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
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
                <el-menu-item index="3-1">视频</el-menu-item>
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
                        style="display: flex; gap: 16px; flex-direction: row"
                        >
                            <el-avatar :size="55"> user </el-avatar>
                            <div style="display: flex; flex-direction: column;">
                                <p>name:</p>
                                <p>age:</p> 
                            </div>
                            
                        <div>
                           
                        </div>
                        </div>
                    </template>
                </el-popover>
                <!-- </div> -->
            </el-menu-item>
        </el-menu>
                
        <!-- middle -->
        <div class="home-middle-1">
            <el-row>
                <el-col :span="16">
                    <div class="cx-1">
                        <el-carousel indicator-position="outside"  height="auto" autoplay>
                            <el-carousel-item style="height: auto;">
                                <el-empty :image-size="300" image=""/>
                            </el-carousel-item>
                        </el-carousel>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="cx-2">
                        <el-table :data="tableData" style="width: 100%,height: 300px;">
                        <el-table-column prop="date" label="Date" width="180" />
                    </el-table>
                    </div>
                </el-col>
            </el-row>
        </div>  

        <div class="home-middle-space">

        </div>

        <div class="home-middle-2">

        </div>

        <div class="home-middle-3">

        </div>

        <div class="home-foot">

        </div>

        <el-backtop :right="100" :bottom="100">
            <el-icon><ArrowUpBold /></el-icon>
        </el-backtop>
    </div>
</template>

<script setup>
    import router from '../router/index.js';
    import { ref } from 'vue'
    const drawer = ref(false)
    // const carouseData = [
    //     {url: require("../assets/css/img/1.jpg")}
    // ]
    const activeIndex = ref('1')
    // const handleSelect = (key: string, keyPath: string[]) => {
    // console.log(key, keyPath)
    // }

    import {
        Document,
        Menu as IconMenu,
        Location,
        Setting,
    } from '@element-plus/icons-vue'
    const handleOpen = () => {
    console.log(key, keyPath)
    }
    const handleClose = () => {
    console.log(key, keyPath)
    }

</script>


