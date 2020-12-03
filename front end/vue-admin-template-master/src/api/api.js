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
  }
}