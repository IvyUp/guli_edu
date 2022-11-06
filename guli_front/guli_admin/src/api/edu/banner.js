import request from '@/utils/request'
//定义访问的接口地址
export default {

    getBannerPage(current, size){
        return request({
            url: `/admin/educms/banner/page/${current}/${size}`,
            method: 'get'
          })
    }


}