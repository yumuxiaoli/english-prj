              <el-menu-item index="2-1-8"
              	v-if="isAuth(['ROOT', 'VIDEO:SELECT'])"
              	@click="$router.push({ name: 'Video' })">
              	视频</el-menu-item>