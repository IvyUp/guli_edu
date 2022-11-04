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
    },

    /**
     * 根据课程id, 获取课程发布信息
     * @param {课程id} courseId 
     * @returns 课程发布信息封装对象
     */
    getCoursePublishVoById(courseId){
        return request({
            url: `/eduservice/course/publish/${courseId}`,
            method: 'get'
        })
    },

    publishCourseById(courseId){
        return request({
            url: `/eduservice/course/publish/${courseId}`,
            method: 'put'
        })
    },

    getPageList(page, limit, searchObj){
        return request({
            url: `/eduservice/course/page/${page}/${limit}`,
            method: 'post',
            data: searchObj
        })
    },

    /**
     * 根据课程id，删除课程
     * @param {课程id} courseId 
     * @returns 
     */
    deleteCourseById(courseId){
        return request({
            url: `/eduservice/course/delete/${courseId}`,
            method: 'delete'
        })
    }

}