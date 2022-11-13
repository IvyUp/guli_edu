<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item label="日期">
        <el-date-picker
          v-model="date"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>


      <el-button
        :disabled="btnDisabled"
        type="primary"
        @click="create()">生成</el-button>
    </el-form>

  </div>
</template>

<script>
import statisticsApi from '@/api/statistics'

export default {

    data(){
        return{
            date: ''   
        }
    },
    created(){

    },
    methods:{
        create(){
            statisticsApi.createDaliyData(this.date).then(response => {
                //提示信息
                    this.$message({
                        type: 'success',
                        message: '数据生成成功!'
                    });
                //跳转页面
                this.$router.push({
                    path: '/statistics/show'
                })
            })
        }
    }
    
}
</script>