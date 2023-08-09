              <el-menu-item index="2-1-8"
              	v-if="isAuth(['ROOT', 'EXCISE:SELECT'])"
              	@click="$router.push({ name: 'Excise' })">
              	练习</el-menu-item>