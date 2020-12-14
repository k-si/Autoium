<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="参数类型">
        <el-select v-model="param.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="key-value类型"/>
          <el-option :value="2" label="SQL类型"/>
        </el-select>
      </el-form-item>
      <el-form-item label="参数名称">
        <el-input v-model="param.name"/>
      </el-form-item>
      <el-form-item label="参数值">
        <el-input v-model="param.career"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="param.sort" controls-position="right" min="0"/>
      </el-form-item>
      <el-form-item label="参数说明">
        <el-input v-model="param.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import param from '@/api/param'
export default {
  data() {
    return {
      param: {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: ''
      },
      saveBtnDisabled: false // 保存按钮是否禁用,
    }
  },

  methods: {

    saveOrUpdate() {
      this.saveBtnDisabled = true
      this.saveData()
    },

    // 保存
    saveData() {

    }

  }
}
</script>