import request from '@/utils/request'


export default {

    /**
     * 
     * @param {视频源id} videoId 
     * @returns 视频播放凭证
     */
    getPlayAuth(videoId) {
        return request({
        url: `/eduvod/video/playauth/${videoId}`,
        method: 'get'
        })
    }

}