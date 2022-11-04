<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-button type="text" @click="openChapterDialog()">添加章节</el-button>

    <!-- 章节 -->
    <ul class="chapterList">
        <li
            v-for="chapter in chapterList"
            :key="chapter.id">
            <p>
                {{ chapter.title }}
                <span class="acts">
                    <el-button style="" type="text" @click="openVideo(chapter.id)">添加小节</el-button>
                    <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑章节</el-button>
                    <el-button type="text" @click="removeChapter(chapter.id)">删除章节</el-button>
                </span>
            </p>

            <!-- 小节 -->
            <ul class="chapterList videoList">
                <li
                    v-for="video in chapter.children"
                    :key="video.id">
                    
                    <p>
                      {{ video.title }}
                      <span class="acts">                  
                          <el-button style="" type="text" @click="openEditVideo(video.id)">编辑小节</el-button>
                          <el-button type="text" @click="removeVideo(video.id)">删除小节</el-button>
                      </span>
                    </p>
                </li>
            </ul>
        </li>
    </ul>

    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button  type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 添加和修改小节表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">

      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
              :on-success="handleVodUploadSuccess"
              :on-remove="handleVodRemove"
              :before-remove="beforeVodRemove"
              :on-exceed="handleUploadExceed"
              :file-list="fileList"
              :action="BASE_API+'/eduvod/video/upload'"
              :limit="1"
              class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
                <div slot="content">最大支持1G，<br>
                    支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                    GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                    MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                    SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'
import vod from '@/api/edu/vod'
  
export default {

    data(){
        return{
            saveBtnDisabled: false,
            saveVideoBtnDisabled: false,
            chapterList: [],
            courseId: '',
            dialogChapterFormVisible:false,//章节弹框
            dialogVideoFormVisible: false, //小节弹框
            chapter: {
              id: '', //章节id
              title: '',  //章节名称
              sort: 0 //章节排序
            },
            video: {
              id: '', //小节id
              chapterId: '', //小节所属章节id
              courseId: '', //小节所属课程id
              title: '', //小节名称
              sort: 0,  //小节排序
              free: false, //是否免费
              videoSourceId: '', //视频id
              videoOriginalName:''//视频名称
            },
            fileList: [], //上传文件列表
            BASE_API: process.env.BASE_API //接口API地址
        }
    },
    created(){
      //获取路由的id值
        if(this.$route.params && this.$route.params.id){  //坑：此处为id不是courseId
          this.courseId = this.$route.params.id
          this.getChapterList()
        }   
    },
    methods:{
//*********************************视频操作******************************************/
        //点击删除前
        beforeVodRemove(file, fileList){
          return this.$confirm(`确定移除 ${file.name}?`)
        },
        //点击删除
        handleVodRemove(file, fileList){
          vod.deleteVodById(this.video.videoSourceId)
            .then(response => {
              this.$message({
                  type: 'success',
                  message: '删除视频成功!'
              });
              //清空视频相关数据
              this.fileList = []
              this.video.videoSourceId = ''
              this.video.videoOriginalName = ''
            })
        },
        //视频上传成功
        handleVodUploadSuccess(response, file, fileList){
          this.video.videoSourceId = response.data.item
          this.video.videoOriginalName = file.name
        },
        //上传视频数量大于1
        handleUploadExceed(files, fileList){
          this.$message.warning('想要重新上传视频，请先删除已上传视频')  
        },

//**********************************小节操作***************************************/
        //删除小节
        removeVideo(videoId){
          this.$confirm('此操作将删除小节, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {  //点击确定，执行then方法
                //调用删除的方法
                video.deleteVideoById(videoId).then(response =>{//删除成功
                    //提示信息
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //刷新页面
                    this.getChapterList()
                })
            }) //点击取消，执行catch方法
        },
   
        //添加或修改小节
        saveOrUpdateVideo(){
          this.saveVideoBtnDisabled = true
          if(!this.video.id){
            this.saveVideo()
          }else{
            this.updateVideo()   
          }
        },

        helpSaveVideo(){
          //关闭弹框
          this.dialogVideoFormVisible = false
          //刷新页面
          this.getChapterList()
          //重置内容
          this.video.title = ''
          this.video.sort = 0
          this.fileList = []
          this.video.videoSourceId = ''
          this.video.videoOriginalName = ''
          this.saveVideoBtnDisabled = false
        },

        saveVideo(){
          video.addVideo(this.video).then(response => {
            //提示信息
            this.$message({
                type: 'success',
                message: '保存成功！'
            })
            this.helpSaveVideo()
          })
        },

        updateVideo(){
          video.updateVideo(this.video).then(response => {
            //提示信息
            this.$message({
                type: 'success',
                message: '更新成功！'
            })
            this.helpSaveVideo()
          })
        },

        //编辑小节
        openEditVideo(videoId){
          //1 打开弹窗
          this.dialogVideoFormVisible = true
          //2 回显小节数据
          video.getVideoById(videoId).then(response => {
            //回显数据
            this.video = response.data.items
            //this.video.free = response.data.items.isFree
          })
        },

        //打开小节弹框
        openVideo(chapterId){
            //1 弹窗
            this.dialogVideoFormVisible = true
            //2 设置chapterId
            this.video.chapterId = chapterId
            this.video.courseId = this.courseId
        },

//**********************************章节操作***************************************/
        removeChapter(chapterId){
          this.$confirm('此操作将删除章节, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {  //点击确定，执行then方法
                //调用删除的方法
                chapter.deleteChapterById(chapterId).then(response =>{//删除成功
                    //提示信息
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //刷新页面
                    this.getChapterList()
                })
            }) //点击取消，执行catch方法
        },

        //点击<编辑>按钮
        openEditChapter(chapterId){
          //1 弹出编辑窗口
          this.dialogChapterFormVisible = true
          //2 根据chapterId回显数据
          chapter.getChapterById(chapterId).then(response => {
            this.chapter = response.data.items
          })
        },

        saveOrUpdate(){
          if(!this.chapter.id){
            this.saveChapter()
          }else{
            this.updateChapter()
          }
        },

        saveChapter(){
          this.chapter.courseId = this.courseId
          chapter.addChapter(this.chapter).then(response => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              })
              this.dialogChapterFormVisible = false
              this.chapter = {}
              this.getChapterList()
          })
        },

        updateChapter(){
          chapter.updateChapter(this.chapter).then(response => {
            this.$message({
              type: 'success',
              message: '更新成功！'
            })
            this.dialogChapterFormVisible = false
            this.chapter = {}
            this.getChapterList()
          })      
        },

        //点击<添加章节>按钮
        openChapterDialog(){
          //1 弹框
          this.dialogChapterFormVisible = true
          //2 表单数据清空
          this.chapter.title = ''
          this.chapter.sort = 0
        },

        getChapterList(){
            chapter.getChapterList(this.courseId).then(response => {
                this.chapterList = response.data.items
            })
        },

        previous(){
            this.$router.push({ path: '/course/info/' + this.courseId})        
        },

        next(){
            this.$router.push({ path: '/course/publish/' + this.courseId})    
        }
    }
    
}
</script>



