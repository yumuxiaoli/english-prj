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
                <template #title>Workspace</template>
                <el-menu-item index="3-1">item one</el-menu-item>
                <el-menu-item index="3-2">item two</el-menu-item>
                <el-menu-item index="3-3">item three</el-menu-item>
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
                            <p>name:</p>
                            <p>age:</p>
                        <div>
                           
                        </div>
                        </div>
                    </template>
                </el-popover>
                <!-- </div> -->
            </el-menu-item>
        </el-menu>

        <!-- word-card -->
        <div class="recite-middle">
            <div class="recite-card">
                <el-card class="box-card">
                    <template #header>
                    <div class="card-header">
                        <span>Card name</span>
                        <el-button class="button" text>Operation button</el-button>
                    </div>
                    </template>
                        <div v-for="o in 20" :key="o" class="text item">
                            <el-collapse v-model="activeNames" @change="handleChange">
                                <el-collapse-item title="Consistency" name="1">
                                    <div>
                                    Consistent with real life: in line with the process and logic of real
                                    life, and comply with languages and habits that the users are used to;
                                    </div>
                                    <div>
                                    Consistent within interface: all elements should be consistent, such
                                    as: design style, icons and texts, position of elements, etc.
                                    </div>
                                </el-collapse-item>
                            </el-collapse>
                        </div>
                </el-card>
            </div>     
        </div>
            
        <div>
        
        </div>


        <!-- 回到头部 -->
        <el-backtop :right="100" :bottom="100">
            <el-icon><ArrowUpBold /></el-icon>
        </el-backtop>
    </div>

    


</template>

<script setup>   
    import { ref } from 'vue'
    const checkedCities = ref([''])
    const cities = ['Shanghai', 'Beijing', 'Guangzhou', 'Shenzhen']

</script>
