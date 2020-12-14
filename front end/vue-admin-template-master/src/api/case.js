// 表示引入utlis中的requset
import request from '@/utils/request'
export default{
  // 1.用例列表（条件查询分页）
  // current分页中的当前页`
  // limit每页记录数
  // caseQuery 条件对象
  getCaseListPage(current, limit, caseQuery) {
    return request({
      url: `/api/manage/PageCaseCondition/${current}/${limit}`, // 接口地址
      method: 'post', // 提交方式
      // caseQurey条件对象，后端使用RequsetBody获取数据
      // data表示把对象转换为json进行传递到接口里面
      data: caseQuery
    })
  },
  // 删除用例
  deleteCaseId(id) {
    return request({
      url: `/api/manage/delete/${id}`, // 接口地址
      method: 'delete' // 提交方式
    })
  },
  // 添加用例
  addCase(testcase) {
    return request({
      url: `/api/manage/addCase`, // 接口地址
      method: 'post', // 提交方式
      data: testcase
    })
  },
  // 根据用例id查询用例方法
  getCaseInfo(id) {
    return request({
      url: `/api/manage/getCase/${id}`, // 接口地址
      method: 'get' // 提交方式
    })
  },
  updateCase(testcase) {
    return request({
      url: `/api/manage/updateCase`, // 接口地址
      method: 'post', // 提交方式
      data: testcase
    })
  },
  // 获取所有用例
  getAll() {
    return request({
      url: `/api/manage/getAll`, // 接口地址
      method: 'get' // 提交方式
    })
  },
  // 获取所有项目名称
  getAllProject() {
    return request({
      url: `/api/manage/getAllProject`, // 接口地址
      method: 'get' // 提交方式
    })
  },
  // 获取所有模块名称
  getAllSuite() {
    return request({
      url: `/api/manage/getAllSuite`, // 接口地址
      method: 'get' // 提交方式
    })
  },
  // 批量删除
  deleteArray(delArr) {
    return request({
      url: `/api/manage/delete`, // 接口地址
      method: 'post', // 提交方式
      data: { 'delArr': delArr }
    })
  },
  // 文件上传
  upload(file) {
    return request({
      url: `/api/manage/upload`, // 接口地址
      method: 'post', // 提交方式
      data: file
    })
  }
}

