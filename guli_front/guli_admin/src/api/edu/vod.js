import request from '@/utils/request'

//定义访问的接口地址
export default {

    /**
     * 根据视频id，删除视频
     * @param {视频id} videoSourceId
     * @returns 
     */
    deleteVodById(videoSourceId){
        return request({
            url: `/eduvod/video/delete/${videoSourceId}`,
            method: 'delete'
        })
    }


}