import request from '@/utils/request'

export default {

  //用户登录
  submitLogin(user) {
    return request({
      url: '/educenter/member/login',
      method: 'post',
      data: user
    })
  },
  
  //获取用户登录信息
  getLoginInfo(){
    return request({
      url: `/educenter/member/login/info`,
      method: 'get'
    })
  }


}