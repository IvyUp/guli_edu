import request from '@/utils/request'

//定义访问的接口地址
export default {

    /**
     * 
     * @returns 学科列表（树形结构）
     */
    addCourseInfo(CourseInfo){
        return request({
            url: `/eduservice/course/add`,
            method: 'post',
            data: CourseInfo
          })
    }

}