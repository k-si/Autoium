<template>
  <div>
    <div style="width:1000px;margin-left:220px;margin-top:30px;margin-bottom:30px">
      <template style="display:inline-block">
        <el-input v-model="searchValue" clearable style="display:inline-block;width:500px" placeholder="请输入关键字"/>
        <el-button type="primary" size="mini" style="height:40px" @click="doFilter">搜索</el-button>
      </template>
    </div>
    <el-table :data="resData" style="width: 100%,margin-top:200px;text-align:center" >
      <el-table-column label="id" width="80" align="center" type="index">
        <template slot-scope="scope">
          <el-popover trigger="hover" placemident="top">
            <p> {{ scope.row.id }}</p>
            <div slot="reference" class="id-wrapper">
              {{ scope.row.id }}
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="用例名称" width="180" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p> {{ scope.row.name }}</p>
            <div slot="reference" class="name-wrapper">
              {{ scope.row.name }}
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="用例描述" width="350" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.description }}</p>
            <div slot="reference" class="description-wrapper">
              {{ scope.row.description }}
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="日期" width="180" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope.row.date }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click.native.prevent="deleteRow(scope.$index, tableData)"
          >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 编辑窗口 -->
    <el-dialog
      :visible.sync="editFormVisible"
      title="编辑"
    >
      <el-form
        ref="editForm"
        :model="editForm"
      >
        <el-form-item
          :label-width="formLabelWidth"
          label="用例名称"
        >
          <el-input
            v-model="editForm.name"
            auto-complete="off"
          />
        </el-form-item>
        <el-form-item
          :label-width="formLabelWidth"
          label="用例描述"
        >
          <el-input
            v-model="editForm.description"
            auto-complete="off"
          />
        </el-form-item>
        <el-form-item
          :label-width="formLabelWidth"
          label="日期"
        >
          <el-date-picker
            v-model="editForm.date"
            type="date"
            placeholder="选择日期"
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
      </el-form>
      <div>
        <el-button @click="cancel()">取消</el-button>
        <el-button
          type="primary"
          @click="sumbitEditRow()"
        >确定</el-button>
      </div>
    </el-dialog>
    <el-pagination
      :current-page="currentPage"
      :page-sizes="[8]"
      :page-size="pageSize"
      :total="totalItems"
      layout="total, sizes, prev, pager, next, jumper"
      style="text-align:center"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
var _index // 定义一个全局变量，以获取行数据的行号
export default {
  data() {
    return {
      tableData: [
        {
          id: '1',
          name: '测试1',
          description: '这是一个测试用例1',
          date: '2020-11-30'
        },
        {
          id: '2',
          name: '测试2',
          description: '这是一个测试用例2',
          date: '2020-11-30'
        },
        {
          id: '3',
          name: '测试3',
          description: '这是一个测试用例3',
          date: '2020-11-30'
        },
        {
          id: '4',
          name: '测试4',
          description: '这是一个测试用例4',
          date: '2020-11-30'
        },
        {
          id: '5',
          name: '测试5',
          description: '这是一个测试用例5',
          date: '2020-11-30'
        },
        {
          id: '6',
          name: '测试6',
          description: '这是一个测试用例6',
          date: '2020-11-30'
        },
        {
          id: '7',
          name: '测试7',
          description: '这是一个测试用例7',
          date: '2020-11-30'
        },
        {
          id: '8',
          name: '测试8',
          description: '这是一个测试用例8',
          date: '2020-11-30'
        },
        {
          id: '9',
          name: '测试9',
          description: '这是一个测试用例9',
          date: '2020-11-30'
        }
      ],
      editFormVisible: false, // 是否显示编辑窗口
      editForm: [],
      formLabelWidth: '120px',
      searchValue: '',
      resData: [],
      currentPage: 1,
      pageSize: 8,
      totalItems: 0,
      filterTableData: [],
      flag: false
    }
  },
  methods: {
    handleEdit(index, row) {
      this.editFormVisible = true
      this.editForm = Object.assign({}, row)
      if (this.currentPage !== 1) {
        index = this.pageSize * (this.currentPage - 1) + index
        console.log(index)
      }
      _index = index
    },
    // 保存编辑
    sumbitEditRow() {
      var editData = _index
      this.tableData[editData].name = this.editForm.name
      this.tableData[editData].description = this.editForm.description
      this.tableData[editData].date = this.editForm.date
      this.editFormVisible = false
    },
    cancel() {
      this.editFormVisible = false
    },
    deleteRow(index, rows) {
      // rows.splice(index, 1)
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // rows.splice(index, 1)
        console.log(this.currentPage)
        if (this.currentPage !== 1) {
          index = this.pageSize * (this.currentPage - 1) + index
          console.log(index)
        }
        const totalPage = Math.ceil((this.totalItems - 1) / this.pageSize) // 总页数
        this.currentPage = this.currentPage > totalPage ? totalPage : this.currentPage
        this.currentPage = this.currentPage < 1 ? 1 : this.currentPage
        this.tableData.splice(index, 1)
        this.totalItems = this.totalItems - 1
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch((err) => {
        this.$message({
          type: 'error',
          message: err
        })
      })
      this.resData = this.tableData
      // this.tableData
    },
    mockRequset() {
      this.totalItems = this.tableData.length
      if (this.totalItems > this.pageSize) {
        for (let index = 0; index < this.pageSize; index++) {
          this.resData.push(this.tableData[index])
        }
      } else {
        this.resData = this.tableData
      }
    },
    doFilter() {
      this.resData = []
      this.filterTableData = []
      this.tableData.filter((item) => {
        if ('name' in item || 'description' in item) {
          // 按用例名称或用例描述查询
          if (item.name.indexOf(this.searchValue.toUpperCase()) > -1 ||
            item.description.indexOf(this.searchValue) > -1) {
            this.filterTableData.push(item)
          }
        }
      })
      // 页面数据改变重新统计数据数量和当前页
      this.currentPage = 1
      this.totalItems = this.filterTableData.length
      // 渲染表格,根据值
      this.currentChangePage(this.filterTableData)
      // 页面初始化数据需要判断是否检索过
      this.flag = true
    },
    // 每页显示条数改变触发
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.handleCurrentChange(1)
    },
    // 当前页改变触发
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
      // 判断是否为搜索的数据,传入对应值
      if (!this.flag) {
        this.currentChangePage(this.tableData)
      } else {
        this.currentChangePage(this.filterTableData)
      }
    },
    // 根据分页，确定当前显示的数据
    currentChangePage(list) {
      let fromNum = (this.currentPage - 1) * this.pageSize
      const toNum = this.currentPage * this.pageSize
      this.resData = []
      for (; fromNum < toNum; fromNum++) {
        if (list[fromNum]) {
          this.resData.push(list[fromNum])
        }
      }
    },
    beforeMount() {
      this.mockRequset()
    }
  }
}

</script>
