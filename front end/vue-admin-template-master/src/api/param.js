import request from '@/utils/request'

export default {

  getPageList(page, limit, searchObj) {
    return request({
      url: `param/pageParam/${page}/${limit}`,
      method: 'get',
      data: searchObj
    })
  },
  save(param) {
    return request({
        url: `param/addParam`,
        method: 'post',
        data: param
    })
}
}