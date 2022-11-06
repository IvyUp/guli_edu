import request from '@/utils/request'

export default{
    /**
     * 
     * @returns id倒数的2条banner
     */
     getTeacherList() {
        return request({
          url: '/user/eduservice/teacher/get',
          method: 'get'
        })
    } 
}