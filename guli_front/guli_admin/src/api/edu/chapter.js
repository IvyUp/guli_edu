import request from '@/utils/request'

//定义访问的接口地址
export default {

    /**
     * 根据课程id获取章节列表
     * @returns 章节列表
     */
    getChapterList(courseId){
        return request({
            url: `/eduservice/chapter/list/${courseId}`,
            method: 'get'
        })
    },

    /**
     * 根据章节id, 获取章节
     * @param {章节id} chapterId 
     * @returns 
     */
    getChapterById(chapterId){
        return request({
            url: `/eduservice/chapter/get/${chapterId}`,
            method: 'get'
        })    
    },

    /**
     * 修改章节
     * @param {章节信息} chapter 
     * @returns 
     */
    updateChapter(chapter){
        return request({
            url: `/eduservice/chapter/update/${chapter.id}`,
            method: 'put',
            data: chapter
        })
    },

     /**
     * 新增章节
     * @param {章节信息} chapter 
     * @returns 
     */
    addChapter(chapter){
        return request({
            url: `/eduservice/chapter/add`,
            method: 'post',
            data: chapter
        })
    },

    /**
     * 根据章节id，删除章节
     * @param {章节id} chapterId 
     * @returns 
     */
    deleteChapterById(chapterId){
        return request({
            url: `/eduservice/chapter/delete/${chapterId}`,
            method: 'delete'
        })
    }

}