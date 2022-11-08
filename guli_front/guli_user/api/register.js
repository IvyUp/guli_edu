import request from '@/utils/request'
import cookie from 'js-cookie'

export default {

  //用户注册
  submitRegister(formItem) {
    return request({
      url: '/educenter/member/register',
      method: 'post',
      data: formItem
    })
  },
  
  //发送验证码
  sendCode(phone){
    return request({
      url: `/edumessage/code/send/${phone}`,
      method: 'get'
    })
  }


}