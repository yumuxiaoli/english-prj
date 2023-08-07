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
        <div class="detail-title">
            <h3>title</h3>
        </div>

        <div >
            <el-row style="display: flex; flex-wrap: nowrap">
                <el-col span="16">
                    <div class="dta-middle">     
                        <div class="dta-video">
                            <Video   
                                :src= "videourl"
                                controls
                                autoplay
                                class="video"
                                ref="dialogVideo"
                                width="650" 
                                height="450"
                                ><source type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'></Video>
                        </div>  
                    </div>  
                </el-col>
                <el-col :span="8">
                    <div class="dta-middle-1">
                        <div class="dta-list">
                            <el-card>
                            </el-card>  
                        </div>
                    </div>
                </el-col>
                
            </el-row>
        </div>
            
       

        <div class="detail-space">

        </div>


        <div class="detail-middle-1">
            <el-row style="display: flex;flex-wrap: nowrap">
                <el-col :span="12">
                    <div >
                        <div>
                            <el-empty :image-size="300" style="width:" >

                            </el-empty> 
                        </div>
                       
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="tea-right">
                        <div>
                            <div class="tea-name">
                                title
                            </div>
                            <div class="tea-txt">
                                内容
                            </div>
                        </div>
                        
                    </div>
                </el-col>
            </el-row>
            
        </div> 

        

        <div class="detail-space">

        </div>      

        <div class="home-foot">

        </div>          

        <el-backtop :right="100" :bottom="100">
            <el-icon><ArrowUpBold /></el-icon>
        </el-backtop>
    </div>
</template>

<script setup>
    import { ref } from 'vue'
    const videourl = ref('http://ryalk03vf.hn-bkt.clouddn.com/QQ录屏20230724142807.mp4')
</script>