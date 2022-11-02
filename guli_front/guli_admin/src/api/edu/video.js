import request from '@/utils/request'

//定义访问的接口地址
export default {

     /**
     * 新增小节
     * @param {小节信息} video
     * @returns 小节id
     */
    addVideo(video){
        return request({
            url: `/eduservice/video/add`,
            method: 'post',
            data: video
        })
    },

    /**
     * 修改小节
     * @param {小节信息} video
     * @returns 
     */
    updateVideo(video){
        return request({
            url: `/eduservice/video/update/${video.id}`,
            method: 'put',
            data: video
        })
    },


    /**
     * 根据小节id，删除小节
     * @param {小节id} videoId
     * @returns 
     */
    deleteVideoById(videoId){
        return request({
            url: `/eduservice/video/delete/${videoId}`,
            method: 'delete'
        })
    },

    /**
     * 根据小节id，查询小节
     * @param {小节id} videoId
     * @returns 
     */
    getVideoById(videoId){
        return request({
            url: `/eduservice/video/get/${videoId}`,
            method: 'get'
        })
    }

}