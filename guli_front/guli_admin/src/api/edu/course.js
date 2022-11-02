import request from '@/utils/request'

//定义访问的接口地址
export default {

    /**
     * 
     * @returns 课程id
     */
    addCourseInfo(CourseInfo){
        return request({
            url: `/eduservice/course/add`,
            method: 'post',
            data: CourseInfo
          })
    },

    /**
     * 根据课程id，获取课程信息
     * @param {课程id} courseId 
     * @returns 课程信息
     */
    getCourseInfoById(courseId){
        return request({
            url: `/eduservice/course/get/${courseId}`,
            method: 'get'
        })
    },

    /**
     * 修改课程信息
     * @param {课程信息} courseInfo 
     * @returns 
     */
    updateCourseInfo(courseInfo){
        return request({
            url: `/eduservice/course/update`,
            method: 'post',
            data: courseInfo
        })
    }


}