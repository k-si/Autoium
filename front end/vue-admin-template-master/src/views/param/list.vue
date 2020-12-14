<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
        <el-input v-model="searchObj.name" placeholder="讲师名"/>
    </el-form-item>

    <el-form-item>
        <el-select v-model="searchObj.level" clearable placeholder="参数类型">
          <el-option :value="1" label="key-value类型"/>
          <el-option :value="2" label="SQL类型"/>
        </el-select>
    </el-form-item>

      <el-form-item>
        <el-date-picker
          v-model="searchObj.begin"
          type="datetime"
          placeholder="选择添加时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.end"
          type="datetime"
          placeholder="选择修改时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

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
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="参数类型" width="120">
        <template slot-scope="scope">
          {{ scope.row.level===1?'SQL类型':'key-value类型' }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="参数名称" width="80" />

      <el-table-column prop="content" label="参数值" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/teacher/edit/'+scope.row.id">
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
      @current-change="fetchData"
    />
  </div>
</template>

<script>
import param from '@/api/param'
export default {
    data() {
        return {
            listLoading: true, // 是否显示loading信息
            list: null, // 数据列表
            total: 0, // 总记录数
            page: 1, // 页码
            limit: 10, // 每页记录数
            searchObj: {}// 查询条件
        }
  },
    
  created() { // 当页面加载时获取数据
    this.fetchData()
  },
    
  methods: {
    fetchData(page = 1) { // 调用api层获取数据库中的数据
        console.log('加载列表')
        this.page = page
        this.listLoading = true
        param.getPageList(this.page, this.limit, this.searchObj).then(response => {

            // debugger 设置断点调试
            if (response.success === true) {
                this.list = response.data.records
                this.total = response.data.total
            }
            this.listLoading = false
        })
    },
    resetData() {
        this.searchObj = {}
        this.fetchData()
    }
  }
}
</script>