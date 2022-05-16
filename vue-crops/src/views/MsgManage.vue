<template>
  <div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input v-model="searchInfo.worm_name" placeholder="请输入病虫" clearable @clear="clear"></el-input>
        </el-col>
        <el-col :span="2">
          <el-button @click="search" type="primary" style="width:100%">筛选</el-button>
        </el-col>
        <el-col :span="12">
        </el-col>
        <el-col :span="1">
          <el-badge :value="noDoneTotal" class="item">
            <el-button size="small" type="danger">未处理</el-button>
          </el-badge>
        </el-col>
        
      </el-row>
    </el-card>
    <el-card style="margin-top:20px">
      <el-tabs type="border-card" v-model="activeName">
        <el-tab-pane label="已处理" name="yes">
          <el-table :data="doneList" border style="width: 100%">
            <el-table-column prop="username" label="报告人联系方式"> </el-table-column>
            <el-table-column prop="worm_name" label="病虫名"> </el-table-column>
            <el-table-column prop="number" label="病虫数量"> </el-table-column>
            <el-table-column prop="address" label="地址"> </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="未处理" name="no">
          <el-table :data="noDoneList" border style="width: 100%">
            <el-table-column prop="username" label="报告人联系方式"> </el-table-column>
            <el-table-column prop="worm_name" label="病虫名"> </el-table-column>
            <el-table-column prop="number" label="病虫数量"> </el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <el-button icon="el-icon-edit-outline" type="primary" @click="done(scope.row)" size="mini">已处理</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        
        
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import qs from 'qs'
export default {
  name:"MsgManage",
  data() {
    return {
      activeName:'yes',
      searchInfo:{
        worm_name:'',
        address:'',
        pagenum:'',
        pagesize:''
      },
      allList:[],
      total:0,
      doneList:[],
      noDoneList:[],
      doneTotal:0,
      noDoneTotal:0
    };
  },
  mounted(){
    // this.getAllMsgNoDone();
  },
  created(){
    this.searchInfo.address = window.sessionStorage.getItem('address')
    this.getAllList();
  },
  methods:{
    getAllList(){
      this.$http.post('/api/getAllMessage',qs.stringify({
        worm_name:this.searchInfo.worm_name,
        address:this.searchInfo.address,
        done:''
      })).then(res=>{
        // console.log(res);
        if(res.data.code === 200){
          res.data.data.list.forEach(element => {
            if(element.done === "是"){
              this.doneList.push(element);
              this.doneTotal = this.doneTotal + 1;
            }else{
              this.noDoneList.push(element);
              this.noDoneTotal = this.noDoneTotal + 1;
            }
          });
        }else{
          this.$message.error("获取信息失败")
        }
      }).catch(err=>{
        console.log(err);
      })
    },
    search(){
      if(this.activeName === 'yes'){
        this.getAllMsgDone();
      }else{
        this.getAllMsgNoDone();
      }
    },
    clear(){
      if(this.activeName === 'yes'){
        this.getAllMsgDone();
      }else{
        this.getAllMsgNoDone();
      }
    },
    async done(row){
      const confirmResult =await this.$confirm('此操作将标记该事项为已处理, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).catch(err=>err)

      if(confirmResult === 'cancel'){
          return this.$message.info('已经取消该操作')
      }else{
        this.$http.post('/api/updateDone',qs.stringify({
          username:row.username,
          address:row.address,
          worm_name:row.worm_name
        })).then(res=>{
          if(res.data.code === 200){
            this.getAllMsgDone();
            setTimeout(() => {
              this.getAllMsgNoDone();
            }, 500);
            this.$message.success('标记成功')
          }else{
            this.$message.error('标记失败')
          }
        }).catch(err=>{
          console.log(err);
        })
      }
    }
  }
}
</script>

<style>
.el-col{
	min-height:1px;
}
</style>