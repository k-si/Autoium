<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="用例名称">
        <el-input v-model="testcase.name"/>
      </el-form-item>
      <el-form-item label="所属项目">
        <el-select v-model="testcase.projectName" filterable placeholder="请选择" @change="selectProjectId">
          <el-option
            v-for="item in projects"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            <span>{{ item.label }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属模块">
        <el-select v-model="testcase.apiCaseSuiteName" filterable placeholder="请选择" @change="selectApiCaseSuiteId">
          <el-option
            v-for="item in apiCaseSuites"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            <span>{{ item.label }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用例描述">
        <el-input v-model="testcase.description" :rows="10" type="textarea"/>
      </el-form-item>
      <el-form-item label="执行状态">
        <el-select v-model="testcase.status" clearable placeholder="请选择">
          <el-option :value="0" label="未执行"/>
          <el-option :value="1" label="执行成功"/>
          <el-option :value="2" label="执行失败"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <!-- :disabled防止重复点击添加,也就是点击添加之后不能再点击第二次 -->
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
// 添加用例这里需要排序,让它每次新添加的用例都显示在第一个(可以根据创建时间排序)
import testcaseApi from '@/api/case'
export default {
  data() {
    return {
      testcase: {},
      saveBtnDisabled: false, // 表示保存按钮是否禁用
      projects: [],
      apiCaseSuites: [],
      item: {}
    }
  },
  watch: { // 监听 监控到路由变化,每次变化都会执行
    $route(to, from) { // 路由变化方式,只要路由发生变化方法就会执行
      this.init()
    }
  },
  created() { // 页面渲染之前执行
    this.init()
    this.getProjects()
    this.getApiCaseSuites()
  },
  methods: {
    init() {
    // 判断如果有id参数就调用,没有id参数就不调用根据id查询方法,也就不是修改
      if (this.$route.params && this.$route.params.id) {
        // 从路径获取id值
        const id = this.$route.params.id // 表示得到路由里面的参数值
        // 调用根据id查询方法
        this.getInfo(id) // 做数据回显
      } else { // 没有id的话不是修改,那么就表单中应该没有数据,所以应该清除表单数据
        this.testcase = {}
      }
    },
    // 根据用例id查询的方法
    getInfo(id) { // 添加的时候不需要调用,修改的时候才需要调用
      testcaseApi.getCaseInfo(id)
        .then(response => {
          // 查看这个方法中接口名字叫什么
          this.testcase = response.data.case
        })
    },
    // 这个方法每次点击就会被调用,所以不需要在created()里面再调用
    saveOrUpdate() {
    // 判断是修改还是添加
    // 根据testcase是否有id
      console.log(this.options)
      if (!this.testcase.id) {
        // 添加
        this.saveCase()
      } else {
        // 修改
        this.updateCaseInfo()
      }
    },
    // 修改用例方法
    updateCaseInfo() {
      testcaseApi.updateCase(this.testcase)
        .then(response => {
        // 消息提示
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          // 回到用例列表页面中去 路由跳转
          this.$router.push({ path: '/case/table' })
        })
    },
    // 添加用例方法
    saveCase() {
      testcaseApi.addCase(this.testcase)
        .then(response => {
          // 消息提示
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 回到用例列表页面中去 路由跳转
          this.$router.push({ path: '/case/table' })
        })
    },
    getProjects() {
      const obj = this
      let item = {}
      testcaseApi.getAllProject().then((response) => {
        for (let i = 0; i < response.data.list.length; i++) {
          item = response.data.list[i]
          item.value = '选项' + (i + 1)
          item.label = item.name
          obj.projects.push(item)
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    getApiCaseSuites() {
      const obj = this
      let item = {}
      testcaseApi.getAllSuite().then((response) => {
        for (let i = 0; i < response.data.list.length; i++) {
          item = response.data.list[i]
          item.value = '选项' + (i + 1)
          item.label = item.name
          obj.apiCaseSuites.push(item)
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    selectProjectId(value) {
      let selectItem = {}
      selectItem = this.projects.find((item) => {
        return item.value === value
      })
      this.testcase.projectId = selectItem.id
    },
    selectApiCaseSuiteId(value) {
      let selectItem = {}
      selectItem = this.apiCaseSuites.find((item) => {
        return item.value === value
      })
      this.testcase.apiCaseSuiteId = selectItem.id
    }
  }
}
</script>
