import request from '@/utils/request'
//定义访问的接口地址
export default {

    //讲师列表（条件查询分页）
    /**
     * 
     * @param {条件对象} condition 
     * @param {当前页} current 
     * @param {每页记录数} recode 
     * @returns 讲师列表
     */
    getTeacherListPage(current, recode, condition){
        return request({
            // url: '/eduservice/teacher/condition/'+current+'/'+recode,
            url: `/eduservice/teacher/condition/${current}/${recode}`,
            method: 'post',
            //condition条件对象，后端使用RequestBody获取数据
            //data表示把对象转换成json，进行传递到接口里面
            data: condition
          })
    },

    /**
     * 获取所有讲师信息
     * @returns 所有讲师信息
     */
    getTeacherList(){
        return request({    
            url: `/eduservice/teacher/get/all`,
            method: 'get'
          })
    },

    /**
     * 根据讲师id，删除讲师
     * @param {讲师id} id 
     */
    removeTeacherById(id){
        return request({
            url: `/eduservice/teacher/delete/${id}`,
            method: 'delete'
        })
    },

    /**
     * 添加讲师
     * @param {讲师} teacher 
     */
    saveTeacher(teacher){
        return request({
            url: `/eduservice/teacher/add`,
            method: 'post',
            data: teacher
        })
    },

    /**
     * 根据讲师id，获取讲师信息
     * @param {讲师id} id 
     */
    selectTeacherById(id){
        return request({
            url: `/eduservice/teacher/get/${id}`,
            method: 'get'
        })
    },

    /**
     * 根据讲师id，更新讲师信息
     * @param {讲师id} id 
     * @param {更新后的讲师信息} teacher 
     */
    updateTeacherById(id, teacher){
        return request({
            url: `/eduservice/teacher/update/${id}`,
            method: 'put',
            data: teacher
        })
    }

}