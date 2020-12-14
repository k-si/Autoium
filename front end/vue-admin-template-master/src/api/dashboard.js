import request from '@/utils/request'

export default{
  //获取失败测试用例
  getData(){
    return request({
      url: `/index`, // 接口地址
      method: 'get' // 提交方式
    })
  }
}