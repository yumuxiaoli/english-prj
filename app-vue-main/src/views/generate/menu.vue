              <el-menu-item index="2-1-8"
              	v-if="isAuth(['ROOT', 'WORDS:SELECT'])"
              	@click="$router.push({ name: 'Words' })">
              	单词</el-menu-item>