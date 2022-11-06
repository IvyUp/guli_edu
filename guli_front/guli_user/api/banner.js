import request from '@/utils/request'

export default {
    //查询前两条banner数据
  getListBanner() {
    return request({
      url: '/user/educms/banner/get',
      method: 'get'
    })
  } 
}