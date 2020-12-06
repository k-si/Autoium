<template>
  <div class="app-container">
    <!--查询表单 v-model数据双向绑定-->
    <el-form :inline="true" class="demo-form-inline" >
      <el-form-item style="width:150px">
        <el-input v-model="caseQuery.name" placeholder="用例名称"/>
      </el-form-item>
      <el-form-item style="width:150px">
        <el-input v-model="caseQuery.description" placeholder="用例描述"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="caseQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="caseQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- ：data的值也就是取到值对应的数据，只需要将数组传递过去就可以进行遍历，因为底层进行了封装
        el-table-column表示列，prop表示属性名字，也就是要显示的属性key的值 -->
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <!-- scope表示一个表格,scope.row得到每行的内容 -->
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="用例名称" width="80" />
      <el-table-column prop="description" label="用例描述" />
      <el-table-column prop="date" label="创建时间" width="160"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!-- 通过路由跳转进入数据回显界面,在路由index添加路由,但是不显示,也就是在添加用例页面就可以完成 -->
          <router-link :to="'/case/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
// 引入case.js文件
import testcase from '@/api/project/case'

export default {
  // 核心代码写到export default里面
  // export default表示可以被别人调用，因为new vue已经被封装
  data() {
    // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      page: 1, // 当前页，默认第一页
      limit: 10, // 每页显示记录数
      total: 0, // 总记录数
      caseQuery: {} // 条件封装对象
    }
  },
  created() {
    // 页面渲染之前执行，一般调用methods定义的方法
    // 调用 (当前页面当中的方法)
    this.getList()
  },
  methods: {
    // 创建具体的方法，调用case.js定义的方法
    // 用例列表的方法
    getList(page = 1) { // 这里的page=1,表明如果不传参数的话,这里page就默认为1,传参数的话,参数是多少,page就是多少
      this.page = page
      testcase
        .getCaseListPage(this.page, this.limit, this.caseQuery)
        .then(response => {
          // response得到接口返回数据
          // 获得数据集合
          this.list = response.data.list
          // 获得总记录数
          this.total = response.data.total
          console.log(this.list)
        }) // 请求成功
        .catch(error => {
          console.log(error)
        }) // 请求失败
    },
    // 清空的方法
    resData() {
      // 表单输入项数据清空
      this.caseQuery = {} // 因为是双向绑定,所以当对象中数据没了,那么也就意味着表单中输入数据没了
      // 查询所有讲师的数据
      this.getList()
    },
    // 删除用例的方法
    removeDataById(id) {
      // 在页面中调用方法
      this.$confirm('此操作将永久删除用例记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // 点击确定，执行then方法
        // 调用删除的方法
        testcase.deleteCaseId(id)
          .then(response => { // 删除成功
            // 提示信息
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            // 回到列表页面
            this.getList()
          })
      }) // 点击取消，执行catch方法,但是因为取消不需要提示,所以catch去掉了
    }
  }
}
</script>
