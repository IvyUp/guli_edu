<template>
    <div class="app-container">
        
        <!-- Banner列表 -->
        <el-table
            v-loading="listLoading"
            :data="list"
            element-loading-text="数据加载中"
            border
            fit
            highlight-current-row>

            <el-table-column
                label="序号"
                width="70"
                align="center">
                <template slot-scope="scope">
                    {{ (page - 1) * limit + scope.$index + 1}}
                </template>
            </el-table-column>

            <el-table-column
                prop="name"
                label="名称"
                width="80">
            </el-table-column>

            <el-table-column
                width="80"
                label="头衔">
                <template slot-scope="scope">
                    {{ scope.row.level===1?'高级讲师':'首席讲师' }}
                </template>
            </el-table-column>

            <el-table-column prop="intro" label="资历" />

            <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

            <el-table-column prop="sort" label="排序" width="60" />

            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <router-link :to="'/teacher/save/'+scope.row.id">
                        <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
                    </router-link>
                    <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>

        </el-table>

        <!-- 分页功能 -->
        <el-pagination
        :current-page="page"
        :page-size="limit"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper"
        @current-change="getList">
        </el-pagination>

    </div>
</template>

<script>
//引入调用teacher.js文件
import banner from '@/api/edu/banner'


//核心代码
export default {

    //方式2
    data(){ //定义变量和初始值
        return{
            list:null,  //查询之后接口返回的集合
            page:1, //当前页
            limit:3,   //每页记录数
            total:0,    //总记录数
            teacherQuery:{} //条件封装对象
        }
    },

    created(){ //页面渲染之前执行，一般调用methods定义的方法
        //调用
        this.getList()
    },

    methods: { //常见具体的方法，调用teacher.js定义的方法
        getBannerPage(page = 1){
            this.page = page
            banner.getBannerPage(current, size).then(response => { 
                    this.list = response.data.records
                    this.total = response.data.total     
            })
        },
        //清空查询条件
        resetData(){
            //1 清空查询条件
            this.teacherQuery = {}
            //2 查询所有
            this.getList()
        },
        /**
         * 通过讲师Id，删除讲师
         */
        removeDataById(id){
            this.$confirm('此操作将永久删除该讲师, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { //点击'确认'，执行删除
                //删除讲师
                teacher.removeTeacherById(id)     
            }).then(() => {
                //刷新列表
                this.getList()
                //提示信息
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                })   
            })
                     
        }
    }

}
</script>
