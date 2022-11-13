import request from '@/utils/request'

export default {

    /**
     * 创建订单
     * @param {课程id} courseId 
     * @returns 
     */
    createOrder(courseId) {
      return request({
        url: `/orderservice/order/create/${courseId}`,
        method: 'post'
      })
    },

    /**
     * 获取订单信息
     * @param {订单号} orderNo 
     * @returns 
     */
    getOrder(orderNo){
      return request({
        url: `/orderservice/order/select/${orderNo}`,
        method: 'get'
      })
    },

    /**
     * 生成微信支付二维码
     * @param {订单号} orderNo 
     * @returns 
     */
    createNative(orderNo){
      return request({
        url: `/orderservice/paylog/create/qr/${orderNo}`,
        method: 'get'
      })
    },
    
    /**
     * 查询微信支付状态
     * @param {订单号} orderNo 
     * @returns 
     */
    queryPayStatus(orderNo){
      return request({
        url: `/orderservice/paylog/status/${orderNo}`,
        method: 'get'
      })
    }


}