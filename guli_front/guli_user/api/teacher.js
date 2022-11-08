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
    },
    
    /**
     * 
     * @param {当前页} page 
     * @param {每页条数} limit 
     * @returns 完整的page信息
     */
    getTeacherPage(page,limit){
      return request({
        url: `/user/eduservice/teacher/page/${page}/${limit}`,
        method: 'get'
      })
    },

    /**
     * 
     * @param {讲师id} teacherId 
     * @returns 讲师信息 + 课程信息
     */
    getTeacherInfo(teacherId){
      return request({
        url: `/user/eduservice/teacher/get/${teacherId}`,
        method: 'get'
      })
    }


}