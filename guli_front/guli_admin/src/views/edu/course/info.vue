<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">

        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <el-form-item label="课程分类">
            <el-select
                v-model="courseInfo.subjectParentId"
                placeholder="一级分类" @change="oneSubjectChanged">

                <el-option
                    v-for="subject in subjectOneList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>

            </el-select>

            <!-- 二级分类 -->
            <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
                <el-option
                    v-for="subject in subjectTwoList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>
            </el-select>
        </el-form-item>

        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
        <el-select
            v-model="courseInfo.teacherId"
            placeholder="请选择">

            <el-option
                v-for="teacher in teacherList"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id"/>

        </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <el-form-item label="课程简介">
            <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>

        <!-- 课程封面-->
        <el-form-item label="课程封面">
            <el-upload
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :action="BASE_API+'/eduoss/file/upload/avatar'"
                class="avatar-uploader">
                <img :src="courseInfo.cover">
            </el-upload>
        </el-form-item>

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
        </el-form>
  </div>
</template>

<script>
import course from '@/api/edu/course'
import teacher from '@/api/edu/teacher'
import subject from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'

export default {
    components: { Tinymce },    //引入文本编辑器组件

    data(){
        return{
            saveBtnDisabled: false,  //保存按钮是否禁用
            courseInfo:{
                title: '',
                subjectId: '',//二级分类id
                subjectParentId:'',//一级分类id
                teacherId: '',
                lessonNum: 0,
                description: '',
                cover: '/static/cover.jpg',
                price: 0
            },
            teacherList:[],     //封装所有的讲师
            subjectOneList:[],  //封装所有的一级学科
            subjectTwoList:[],   //封装所有的二级学科
            BASE_API: process.env.BASE_API, // 接口API地址
            courseId: '' //课程id
        }
    },
    created(){

        if(this.$route.params && this.$route.params.id){
            this.courseId = this.$route.params.id
            this.getCourseInfo()
        }else{
            //初始化所有讲师
            this.getTeacherList()
            //初始化一级学科
            this.getOneSubjectList()
        }

    },
    methods: {

        //回显课程信息
        getCourseInfo(){
            course.getCourseInfoById(this.courseId).then(response => {
                this.courseInfo = response.data.items
                subject.getSubjectList().then(response => {
                    this.subjectOneList = response.data.list  
                    for(var i=0; i<this.subjectOneList.length; i++){
                        if(this.courseInfo.subjectParentId == this.subjectOneList[i].id){
                            this.subjectTwoList = this.subjectOneList[i].children
                        }
                    }
                })
                this.getTeacherList()                           
            })
        },

        //上传课程封面成功后，执行
        handleAvatarSuccess(res, file){
            this.courseInfo.cover = res.data.url
        },

         //上传之前调用的方法
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg'
            const isLt2M = file.size / 1024 / 1024 < 2
            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!')
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!')
            }
            return isJPG && isLt2M
        },

        //点击一级学科分类，触发change事件，显示对应的二级学科分类    
        oneSubjectChanged(value){   //value就是一级学科的id值
            //遍历所有的一级学科
            for(var i=0; i<this.subjectOneList.length; i++){
                //检索的一级学科id与选择的一级学科id是否一致
                if(this.subjectOneList[i].id === value){
                    //从一级学科里面获取二级学科信息
                    this.subjectTwoList = this.subjectOneList[i].children
                }
            }
            //把二级学科id值清空
            this.courseInfo.subjectId = ''
        },

        getOneSubjectList(){
            subject.getSubjectList().then(response => {
                this.subjectOneList = response.data.list
            })
        },

        getTeacherList(){
            teacher.getTeacherList().then(response => {
                this.teacherList = response.data.items
            })
        },

        saveOrUpdate(){
            if(this.courseInfo.id){
                this.updateCourse()
            }else{
                this.saveCourse()
            } 
        },

        saveCourse(){
            course.addCourseInfo(this.courseInfo).then(response => {
                //提示
                this.$message({
                    type: 'success',
                    message: '添加课程信息成功!'
                });   
                //跳转到第二步
                 this.$router.push({path:'/course/chapter/'+response.data.courseId}) 
            })
        },

        updateCourse(){
            course.updateCourseInfo(this.courseInfo).then(response => {
                //提示
                this.$message({
                    type: 'success',
                    message: '修改课程信息成功!'
                });   
                //跳转到第二步
                this.$router.push({path:'/course/chapter/'+this.courseId}) 
            })
        }
    }
}
</script>

<style scoped>
.tinymce-container {
line-height: 29px;
}
</style>