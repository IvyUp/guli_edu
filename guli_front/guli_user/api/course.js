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
    },

    /**
     * 
     * @param {查询页} page 
     * @param {每页条数} limit 
     * @param {课程条件} courseQueryVo 
     * @returns 
     */
    getCoursePage(page, limit, courseQueryVo){
        return request({
            url: `/user/eduservice/course/page/${page}/${limit}`,
            method: 'post',
            data: courseQueryVo
        })
    },

    /**
     * 
     * @returns 学科分类数据
     */
    getSubjectList(){
        return request({
            url: `/eduservice/subject/list`,
            method: 'get'
        })
    }



}