// 表示引入utlis中的requset
import request from '@/utils/request'
export default{
  // 1.用例列表（条件查询分页）
  // current分页中的当前页`
  // limit每页记录数
  // caseQuery 条件对象
  getCaseListPage(current, limit, caseQuery) {
    return request({
      url: `/autoium/api-case-suite/PageCaseCondition/${current}/${limit}`, // 接口地址
      method: 'post', // 提交方式
      // caseQurey条件对象，后端使用RequsetBody获取数据
      // data表示把对象转换为json进行传递到接口里面
      data: caseQuery
    })
  },
  // 删除用例
  deleteCaseId(id) {
    return request({
      url: `/autoium/api-case-suite/${id}`, // 接口地址
      method: 'delete' // 提交方式
    })
  },
  // 添加用例
  addCase(testcase) {
    return request({
      url: `/autoium/api-case-suite/addCase`, // 接口地址
      method: 'post', // 提交方式
      data: testcase
    })
  },
  // 根据用例id查询用例方法
  getCaseInfo(id) {
    return request({
      url: `/autoium/api-case-suite/getCase/${id}`, // 接口地址
      method: 'get' // 提交方式
    })
  },
  updateCase(testcase) {
    return request({
      url: `/autoium/api-case-suite/updateCase`, // 接口地址
      method: 'post', // 提交方式
      data: testcase
    })
  }

}

