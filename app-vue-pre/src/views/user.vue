<template>
  <div class="user-con">
       <div class="user-aside">
            <!-- <el-card class="user-box-card"> -->
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
                        <el-menu-item>
                            个人中心
                        </el-menu-item>
                        <el-menu-item>
                            修改密码
                        </el-menu-item>
                    </el-menu>
                </div> 
            <!-- </el-card> -->
            
        </div>
        <div class="user-main">
           <el-card class="box-card">
           <div class="user-contain">
                <div style="text-align: right; font-size: 12px">
                    <div class="toolbar">
                    <el-dropdown>
                        <el-icon style="margin-right: 8px; margin-top: 1px"
                        ><setting
                        /></el-icon>
                        <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>View</el-dropdown-item>
                            <el-dropdown-item>Add</el-dropdown-item>
                            <el-dropdown-item>Delete</el-dropdown-item>
                        </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                    <span>Tom</span>
                    </div>
                </div>      

            <div>
                <div>
                    <el-table :data="tableData">
                        <el-table-column prop="date" label="Date" width="140" />
                        <el-table-column prop="name" label="Name" width="120" />
                        <el-table-column prop="address" label="Address" />
                    </el-table>
                </div>
            </div>
        </div>
        </el-card> 
        </div>
  </div>>
</template>

<script lang="ts" setup>
const errorHandler = () => true
</script>