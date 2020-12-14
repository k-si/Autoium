<template>
  <div class="dashboard-container">
    <el-row :gutter="200">
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <div><h5 style="color:rgb(26,179,148)">用例库</h5></div>
          <div>
            <h1 style="color:rgb(26,179,148)">{{totalNum}}条</h1>
            <strong style="color:rgb(26,179,148)">7天内更新{{updateNum}}条</strong>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content">
          <div><h5 style="color:rgb(248,172,89)">每天节省</h5></div>
          <div>
            <h1 style="color:rgb(248,172,89)">{{saveNum}}人</h1>
            <strong style="color:rgb(248,172,89)">节约成本{{saveMoney}}元</strong>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <div><h5 style="color:rgb(40,200,192)">用例执行</h5></div>
          <div>
            <h1 style="color:rgb(40,200,192)">{{ecNum}}条</h1>
            <strong style="color:rgb(40,200,192)">成功率{{passRate}}%</strong>
          </div>
        </div>
      </el-col>
    </el-row>
    <div class="line"></div>
    <div id = "main" style="width:1249px; height:300px; margin-top:100px"></div>    
  </div>
</template>

<script>
  import testindex from '@/api/dashboard'
  import { mapGetters } from 'vuex'
  export default {
    data(){
      return {
        updateNum:0,//七天更新的用例总数
        totalNum:0,//总用例数
        unperformNum:0,//未执行用例数
        successNum:0,
        date:[],//折线图时间下标
        tNum:[],//折线图总用例数
        pNum:[],//折线图通过用例数
        fNum:[],//折线图失败用例数
        uNum:[],//折线图未执行用例数
      }
    },
    created(){
      this.getData();
    },
    mounted() {
      this.drawLine();
      console.log(this.totalNum)
      console.log(this.date)
    },
    methods: {
      //获取所有测试用例
      getData(){
      testindex.
      getData().
      then(response =>{ 
        for(let i = 0 ; i < 7 ; i++){
          this.date.push(response.data.sevenDays[6-i].date)//横轴日期
          this.pNum.push(response.data.sevenDays[6-i].successCount)//成功用例数折线图
          this.fNum.push(response.data.sevenDays[6-i].failCount)//失败用例数折线图
          this.uNum.push(response.data.sevenDays[6-i].notPerformed)//未执行用例数折线图
          this.tNum.push(response.data.sevenDays[6-i].total)//总用例折线图数据
        }
        this.totalNum = response.data.total  //用例总数赋值
        this.tNum.forEach((item)=>{this.updateNum+=item}) //七天更新用例数赋值
        this.unperformNum = response.data.notPerformed //未执行用例数赋值
        this.successNum = response.data.successCount//成功执行用例数赋值
        this.chartColumn.setOption({
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: this.date
          },
          series: [
          {
            name: '总用例',
            data:this.tNum
          },
          {
            name: '成功用例',
            data: this.pNum
          },
          {
            name: '失败用例',
            data: this.fNum
          },
          {
            name: '未执行用例',
            data: this.uNum
          }]
        })
      })
      .catch(error => {
        console.log(error)
      })
    },
    //折线图数据
    drawLine(){
        const echarts = require('echarts');
        this.chartColumn = echarts.init(document.getElementById('main'));
        this.chartColumn.setOption({
          title: {
            text: '用例执行情况图'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['总用例', '成功用例', '失败用例', '未执行用例']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: this.date
          },
          yAxis: {
            type: 'value'
          },
          series: [
          {
            name: '总用例',
            type: 'line',
          },
          {
            name: '成功用例',
            type: 'line',

          },
          {
            name: '失败用例',
            type: 'line',
          },
          {
            name: '未执行用例',
            type: 'line',
          }]
        });
      },

    },
    
    name: 'Dashboard',
    computed: {
      //执行用例数
      ecNum(){
        return this.totalNum - this.unperformNum
      },
      //成功率
      passRate(){
        let rate = parseFloat(this.successNum) / parseFloat(this.ecNum) * 100
        return rate.toFixed(2)
      },
      //节省人数
      saveNum(){
        let i = parseFloat(this.ecNum) / 5.00
        return i.toFixed(2)
      },
      //节约成本
      saveMoney(){
        return this.saveNum *200
      }
    }
  }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
    
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
    border-top: 4px solid rgb(231, 234, 236);
    background-color: #f9fafc;
    padding-left: 15px;
    padding-bottom: 15px;
  }
  .line{
    width: 100%;
    border: solid 1px #e5e9f2;
  }
  #main{
    background-color: #f9fafc;
  }

</style>
