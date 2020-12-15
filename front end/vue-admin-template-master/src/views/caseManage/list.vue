<template>
  <div class="app-container">
    <!--查询表单 v-model数据双向绑定-->
    <el-form inline>
      <el-form-item style="width:150px">
        <el-input v-model="caseQuery.name" placeholder="用例名称" style="width:150px"/>
      </el-form-item>
      <el-form-item style="width:150px" >
        <el-input v-model="caseQuery.description" placeholder="用例描述" style="width:150px"/>
      </el-form-item>
      <el-form-item label="创建时间" >
        <el-date-picker
          v-model="caseQuery.gmtCreateStart"
          type="datetime"
          placeholder="选择创建时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="caseQuery.gmtCreateEnd"
          type="datetime"
          placeholder="选择修改时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <div style="margin-bottom:10px">
      <el-button type="danger" @click="delArray()">批量删除</el-button>
      <el-button @click="toggleSelection()">取消选择</el-button>
      <!-- action 必选参数，上传的地址
      :limit 最大允许上传个数
      :http-request 覆盖默认的上传行为，自定义上传
      :on-preview 文件上传时的钩子
      :on-success 文件上传成功的钩子
      :on-error 文件上传失败的钩子
      :on-remove 文件列表移除文件时的钩子
      :file-list 上传的文件列表
      :auto-upload 是否在选取文件后立即进行上传 -->
      <el-upload
        ref="upload"
        :on-success="successUpload"
        :on-error="errorUpload"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :file-list="fileList"
        :auto-upload="false"
        :before-upload="beforeAvatarUpload"
        :limit="1"
        accept=".xls,.xlsx"
        class="upload-demo"
        action="http://localhost:8888/api/manage/upload"
        style="float:right;margin-right:50px">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        <el-tag>
          <i class="el-icon-download" />
          <a :href="'static/template.xlsx'">点击下载模版</a>
        </el-tag>
      </el-upload>
    </div>

    <!-- ：data的值也就是取到值对应的数据，只需要将数组传递过去就可以进行遍历，因为底层进行了封装
        el-table-column表示列，prop表示属性名字，也就是要显示的属性key的值 -->
    <!-- 表格 -->
    <!-- 跨页对表格选中状态进行保存：
    1.el-table中加入:row-key="getRowKeys" 在methods里面实现方法，返回列的id值
    2.el-table-column多选框属性加入:reserve-selection="true" -->
    <el-table
      ref="multipleTable"
      :data="list"
      :header-cell-style="{background:'#eef1f6',color:'#606266'}"
      :row-key="getRowKeys"
      element-loading-text="数据加载中"
      border
      stripe
      fit
      highlight-current-row
      @selection-change="handleSelectionChange">
      <el-table-column
        :reserve-selection="true"
        type="selection"
        style="width: 5%;"/>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="用例 Id">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="用例名称">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="所属项目">
              <span>{{ props.row.projectName }}</span>
            </el-form-item>
            <el-form-item label="所属模块">
              <span>{{ props.row.apiCaseSuiteName }}</span>
            </el-form-item>
            <el-form-item label="用例描述">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            <el-form-item label="执行状态">
              <span v-if="props.row.status===0">未执行</span>
              <span v-if="props.row.status===1">执行成功</span>
              <span v-if="props.row.status===2">执行失败</span>
            </el-form-item>
            <el-form-item label="创建时间">
              <span>{{ props.row.gmtCreate }}</span>
            </el-form-item>
            <el-form-item label="修改时间">
              <span>{{ props.row.gmtModified }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <!-- scope表示一个表格,scope.row得到每行的内容 -->
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="用例名称" width="150" align="center"/>
      <el-table-column prop="description" label="用例描述" align="center" />
      <el-table-column prop="gmtCreate" label="创建时间" width="160" align="center"/>
      <el-table-column label="执行状态" width="80" align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.status===0">未执行</p>
          <p v-if="scope.row.status===1">执行成功</p>
          <p v-if="scope.row.status===2">执行失败</p>
        </template>
      </el-table-column>
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
import testcase from '@/api/case'

export default {
  // 核心代码写到export default里面
  // export default表示可以被别人调用，因为new vue已经被封装
  data() {
    // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      page: 1, // 当前页，默认第一页
      limit: 5, // 每页显示记录数
      total: 0, // 总记录数
      caseQuery: {}, // 条件封装对象
      fileList: [],
      delArr: [], // 存放删除的数据
      multipleSelection: [], // 多选的数据
      confirmCurrentSelectFileList: []
    }
  },
  created() {
    // 页面渲染之前执行，一般调用methods定义的方法
    // 调用 (当前页面当中的方法)
    this.getList()
    this.confirmCurrentSelectFileList
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
          this.tableData = response.data.list
          console.log(this.list)
        }) // 请求成功
        .catch(error => {
          console.log(error)
        }) // 请求失败
    },
    // 清空的方法
    resetData() {
      // 表单输入项数据清空
      this.caseQuery = {} // 因为是双向绑定,所以当对象中数据没了,那么也就意味着表单中输入数据没了
      // 查询所有讲师的数据
      this.getList()
    },
    // 单个删除用例的方法
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
    },
    getRowKeys(row) {
      return row.id
    },
    // 选择事件，获得选中的数据
    handleSelectionChange(data) {
      this.multipleSelection = data
    },
    // 批量删除
    delArray() {
      var that = this
      const length = that.multipleSelection.length
      if (length === 0) {
        that.$message.error('请先至少选择一项')
      } else {
        for (let i = 0; i < length; i++) {
          that.delArr.push(that.multipleSelection[i].id)
          console.log(that.multipleSelection[i].id)
        }
        // 在页面中调用方法
        this.$confirm('此操作将永久删除这些用例记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { // 点击确定，执行then方法
        // 调用删除的方法
          testcase.deleteArray(that.delArr)
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
    },
    // 清除选中状态
    toggleSelection() {
      this.$refs.multipleTable.clearSelection()
    },
    // 上传前验证
    beforeAvatarUpload(file) {
      // 如果当前没有用例不对文件名字进行判断，直接将加入的文件名字置为空
      if (this.total === 0) {
        this.confirmCurrentSelectFileList = []
      }
      // 文件存在标识
      let existsFileFlag = false
      for (let i = 0; i < this.fileList.length; i++) {
        if ((file.name === this.fileList[i].name)) {
          // 如果存在了，赋值true
          existsFileFlag = true
          break
        }
      }

      // 遍历的这个文件存在当前列表直接结束
      if (existsFileFlag) {
        // 否则不加入
        this.fileList.pop()
        this.$message.error('文件已经选择过了')
        // console.log(`文件已经存在：${file.name}`)
        this.handleMsg(`文件已经选择过了：${file.name})`)
        Notification.warning({
          title: '文件重复提示：',
          message: `文件已经选择过了：${file.name})`,
          duration: 5000
        })
        return
      }
      // 判断当前选择的文件，和当次已经 确定所选文件 是否重复
      let existsAllFileFlag = false
      for (let i = 0; i < this.confirmCurrentSelectFileList.length; i++) {
        if ((file.name === this.confirmCurrentSelectFileList[i].name)) {
          // 如果存在了，赋值true
          existsAllFileFlag = true
          break
        }
      }
      if (!existsAllFileFlag) {
        this.fileList.push(file)
      } else {
      // 否则不加入
        this.fileList.pop()
        this.$message.error('当前选择的文件已经上传过啦，请选择新的文件')
        Notification.warning({
          title: '文件重复提示：',
          message: `当前选择的文件已经上传过了，请查看文件列表去确认：${file.name}`
          // duration: 5000
        })
        return
      }
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed() {
      this.$message.error('上传数量超过限制')
    },
    beforeRemove(file, fileList) {
      return this.$message({
        message: '删除成功',
        type: 'success'
      })
    },
    // 上传
    submitUpload() {
      // // 如果没有选择上传文件进行提示
      // if (this.fileList.length === 0) {
      //   this.$message.error('当前还没有选取文件啊！')
      // }
      // 上传操作
      this.$refs.upload.submit()
    },
    handleMsg(message, type = 'info') {
      const duration = 1500
      this.msgPromise = this.msgPromise.then(() => {
        this.$message({ message, type, duration })
      })
    },

    // 上传成功后执行的方法
    successUpload() {
      // 提示上传成功
      this.$message({
        type: 'success',
        message: '上传成功!'
      })
      // 设置延时，将fileList清空
      const obj = this
      setTimeout(function() {
        obj.fileList = []
      }, 1500)
      // 重新获取数据
      this.getList()
      // 把当前已经确认导入成功的图纸保存起来
      for (const file of this.fileList) {
        this.confirmCurrentSelectFileList.push(file)
      }
      console.log(this.confirmCurrentSelectFileList)
    },
    // 上传失败后执行的方法
    errorUpload() {
      // 提示上传失败
      this.$message({
        type: 'error',
        message: '上传失败!'
      })
    }
  }
}
</script>
<style scoped>
/*  */
 .el-form--inline /deep/ .el-form-item__content{
   width:220px
 }
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
    /* 鼠标滑过高亮显示 */
.el-table tbody tr:hover>td {
   background-color:rgb(255, 255, 204)!important
   }

    /*  斑马纹表格背景 */
.el-table--striped .el-table__body tr.el-table__row--striped td {
  background:rgb(236, 245, 255)
  }

</style>
