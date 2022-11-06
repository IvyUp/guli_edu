<template>
    <div class="app-container">

        <el-form label-width="120px">
            <el-form-item label="讲师姓名">
                <el-input v-model="teacher.name"></el-input>
            </el-form-item>
            <el-form-item label="讲师排序">
                <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
            </el-form-item>
            <el-form-item label="讲师头衔">
                <el-select v-model="teacher.level" clearable placeholder="请选择">
                    <!-- 数据类型一定要和取出的json中的一致，否则没法回填
                         因此，这里value使用动态绑定的值，保证其数据类型是number
                    -->
                    <el-option label="高级讲师" :value="1"></el-option>
                    <el-option label="首席讲师" :value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="讲师资历">
                <el-input v-model="teacher.career"></el-input>
            </el-form-item>
            <el-form-item label="讲师简介">
                <el-input v-model="teacher.intro" :rows="10" type="textarea"></el-input>
            </el-form-item>

            <!-- 讲师头像 -->
            <el-form-item label="讲师头像">
                <!-- 头衔缩略图 -->
                <pan-thumb :image="teacher.avatar"/>
                <!-- 文件上传按钮 -->
                <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">
                    更换头像
                </el-button>
                <!--
                v-show：是否显示上传组件
                :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                :url：后台上传的url地址
                @close：关闭上传组件
                @crop-upload-success：上传成功后的回调 
                -->
                <image-cropper
                    v-show="imagecropperShow"
                    :width="300"
                    :height="300"
                    :key="imagecropperKey"
                    :url="BASE_API+'/eduoss/file/upload/avatar'"
                    field="file"
                    @close="close"
                    @crop-upload-success="cropSuccess"/>
            </el-form-item>

            <el-form-item>
                <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
            </el-form-item>
        </el-form>

    </div>
</template>


<script>

import teacher from '@/api/edu/teacher'
// 引入头像上传组件模块
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'


export default {
    components: { ImageCropper, PanThumb },

    data(){
        return{
            teacher:{
                name: '',
                sort: 0,
                level: 1,
                career: '',
                intro: '',
                avatar: ''
            },

            BASE_API: process.env.BASE_API, //接口API地址
            imagecropperShow: false, //是否显示上传组件
            imagecropperKey: 0, //上传组件id    

            saveBtnDisabled: false //保存按钮是否禁用
        }
    },
    created(){  //页面渲染之前执行
       this.init()
    },
    watch: {    //监听
        $route(to, from){
            this.init()
        }
    },
    methods:{
        //上传成功后的回调函数
        cropSuccess(data){
            this.imagecropperShow = false
            this.teacher.avatar = data.url
            this.imagecropperKey = this.imagecropperKey + 1    
        },    

        //关闭组件方法
        close(){
            this.imagecropperShow = false
            //上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
            this.imagecropperKey =  this.imagecropperKey + 1   
        },    

        //根据讲师id是否存在，判断是查询操作，还是添加操作
        init(){
            if(this.$route.params && this.$route.params.id){
                //从路径获取id值
                const id = this.$route.params.id
                //调用根据id查询的方法
                this.getInfo(id)
            }else{
                //清空表单
                this.teacher = {}   
            }
        },

        //根据讲师id是否存在，判断调用save()还是update()
        saveOrUpdate(){
            if(this.teacher.id){
                this.update()
            }else{
                this.save()
            }
        },

        save(){
            teacher.saveTeacher(this.teacher)
            .then(response => { //添加成功
                //提示信息
                return this.$message({
                    type: 'success',
                    message: '保存成功！'
                })
            })
            .then(response => { //通过路由跳转，回到列表页面
                this.$router.push({
                    path: '/teacher/table'
                })
            })
        },

        update(){
            const id = this.$route.params.id
            teacher.updateTeacherById(id, this.teacher)
            .then(response => { //修改成功
                 //提示信息
                return this.$message({
                    type: 'success',
                    message: '修改成功！'
                })
            })
            .then(response => {
                this.$router.push({
                    path: '/teacher/table'
                })
            })
        },

        getInfo(id){
            teacher.selectTeacherById(id)
            .then(response => {
                this.teacher = response.data.item
            })
        }
    }
}
</script>