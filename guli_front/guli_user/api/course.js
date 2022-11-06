import request from '@/utils/request'

export default{
    /**
     * 
     * @returns id倒数的2条banner
     */
    getCourseList(){
        return request({
            url: `/user/eduservice/course/get`,
            method: 'get'
        })
    }
}