import request from '@/utils/request'

export default {
    getAllApiCase() {
      return request({
        url: '/api/getAll',
        method: 'get'
      })
    },
    getApiInfoById(id) {
      return request({
        url: `/api/getApi/${id}`,
        method: 'get',
    })
  },
    saveApiSuite(apisuite){
      return request({
        url:'/apiSuite/save',
        method:'post',
        data:apisuite
      })
    },
    saveApi(api){
      return request({
        url:'/api/save',
        method:'post',
        data:api
      })
    },
    deleteApiSuite(id){
      return request({
        url:`/apiSuite/delete/${id}`,
        method:'get'
      })
    },
    deleteApi(id){
      return request({
        url:`/api/delete/${id}`,
        method:'get'
      })
    }
}