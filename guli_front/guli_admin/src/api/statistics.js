import request from '@/utils/request'
//定义访问的接口地址
export default {

    createDaliyData(date){
        return request({
            url: `/statisticsservice/daily/${date}`,
            method: 'put'
        })
    },

    getEchartsData(searchObj){
        return request({
            url: `/statisticsservice/daily/echarts/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get'
        })
    }


}