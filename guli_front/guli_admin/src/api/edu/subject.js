import request from '@/utils/request'

//定义访问的接口地址
export default {

    /**
     * 
     * @returns 学科列表（树形结构）
     */
    getSubjectList(){
        return request({
            url: `/eduservice/subject/list`,
            method: 'get'
          })
    }

}