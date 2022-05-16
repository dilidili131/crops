<template>
  <div>
    <!-- 搜索 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input v-model="searchData.worm_name" placeholder="病虫名"></el-input> 
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchData.category" placeholder="病虫类属">
            <el-option v-for="item in allCate" :key="item" :label="item" :value="item"></el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" icon="el-icon-search" @click="search" style="width:100%">搜索</el-button>
        </el-col>
        <el-col :span="2">
          <el-button v-if="isAdmin==='admin'" type="primary" icon="el-icon-circle-plus-outline" @click="addWorm" style="width:100%">添加</el-button>
        </el-col>
        <el-col :span="2">
          <el-button @click="reset" type="info" icon="el-icon-close" style="width:100%">重置</el-button>
        </el-col>
      </el-row>
    </el-card>
    <!-- 数据列表 -->
    <el-card>
      <el-table
        :data="allWorm"
        border>
        <el-table-column type="expand">
          <template slot-scope="scope">
            <div class="detailBox">
              <span style="font-size:25px;margin-bottom:20px;">详细信息</span>
              <el-row :gutter="20" >
                <el-col :span="8">
                  <el-card class="imageBox">
                    <el-image :src="'http://zhibao.yuanlin.com/'+scope.row.img_link"></el-image>
                  </el-card>
                </el-col>
                <el-col :span="16">
                  <el-card class="textBox">
                    <p>名称：{{scope.row.worm_name}}</p>
                    <p>学名：{{scope.row.worm_sname}}</p>
                    <p>类属：{{scope.row.category}}</p>
                  </el-card>
                </el-col>
              </el-row>

              <el-card class="location">
                <div slot="header" class="clearfix">
                  <span>分布与危害</span>
                </div>
                <p>{{scope.row.location}}</p>
              </el-card>
            <el-card class="character">
                <div slot="header" class="clearfix">
                  <span>形态特征</span>
                </div>
                <p>{{scope.row.character}}</p>
              </el-card>
              <el-card class="discipline">
                <div slot="header" class="clearfix">
                  <span>发布规律</span>
                </div>
                <p>{{scope.row.discipline}}</p>
              </el-card>
              <el-card class="prevent">
                <div slot="header" class="clearfix">
                  <span>防治方法</span>
                </div>
                <p>{{scope.row.prevent}}</p>
              </el-card>
            </div>   
          </template>
        </el-table-column>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="worm_name" label="名称"></el-table-column>
        <el-table-column prop="worm_sname" label="学名"></el-table-column>
        <el-table-column prop="category" label="类属"></el-table-column>
        <el-table-column label="图片(查看大图)">
          <template slot-scope="scope">
            <el-image class="table-td-img" :src="'http://zhibao.yuanlin.com/'+scope.row.img_link" :preview-src-list="['http://zhibao.yuanlin.com/'+scope.row.img_link]"></el-image>
          </template>
        </el-table-column>
        <el-table-column v-if="isAdmin === 'admin'" label="操作">
          <template slot-scope="scope">
            <el-button icon="el-icon-edit-outline" type="primary" @click="edit(scope.row)" size="mini">修改</el-button>
            <el-button icon="el-icon-document-delete" type="danger" @click="deleteWorm(scope.row)" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="searchData.pagenum"
        :page-sizes="[5 ,10, 50, 100]"
        :page-size="searchData.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <!-- 修改 -->
    <el-dialog
      title="信息修改"
      :visible.sync="editDialogVisible"
      width="60%">
      <el-form :model="editForm" ref="editFormRef" label-width="100px" class="demo-ruleForm">
        <el-form-item label="名称">
          <el-input v-model="editForm.worm_name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="学名">
          <el-input v-model="editForm.worm_sname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="类属">
          <el-input v-model="editForm.category" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="分布与危害">
          <el-input v-model="editForm.location" type="textarea" autosize></el-input>
        </el-form-item>
        <el-form-item label="形态特征">
          <el-input v-model="editForm.character" type="textarea" autosize></el-input>
        </el-form-item>
        <el-form-item label="发布规律">
          <el-input v-model="editForm.discipline" type="textarea" autosize></el-input>
        </el-form-item>
        <el-form-item label="防治方法">
          <el-input v-model="editForm.prevent" type="textarea" autosize></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogCancel">取 消</el-button>
        <el-button type="primary" @click="editFormSubmit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 添加 -->
    <el-dialog
      title="信息添加"
      :visible.sync="addDialogVisible"
      width="60%">
      <el-form :model="addForm" :rules="addRule" ref="addFormRef" label-width="100px" class="demo-ruleForm">
        <el-form-item label="名称" prop="worm_name">
          <el-input v-model="addForm.worm_name"></el-input>
        </el-form-item>
         <el-form-item label="图片链接" prop="img_link">
          <el-input v-model="addForm.img_link"></el-input>
        </el-form-item>
        <el-form-item label="分布与危害" prop="location">
          <el-input v-model="addForm.location" type="textarea" autosize></el-input>
        </el-form-item>
        <el-form-item label="形态特征" prop="character">
          <el-input v-model="addForm.character" type="textarea" autosize></el-input>
        </el-form-item>
        <el-form-item label="发布规律" prop="discipline">
          <el-input v-model="addForm.discipline" type="textarea" autosize></el-input>
        </el-form-item>
        <el-form-item label="防治方法" prop="prevent">
          <el-input v-model="addForm.prevent" type="textarea" autosize></el-input>
        </el-form-item>
        <el-form-item label="学名" prop="worm_sname">
          <el-input v-model="addForm.worm_sname"></el-input>
        </el-form-item>
        <el-form-item label="类属" prop="category">
          <el-input v-model="addForm.category"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogCancel">取 消</el-button>
        <el-button type="primary" @click="addFormSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import qs from 'qs'
export default {
  name:'Search',
  data() {
    return {
      searchData:{
        worm_name:'',
        category:'',
        pagenum:1,
        pagesize:5
      },
      allWorm:[],
      allCate:[],
      total:0,
      isAdmin:'',
      editDialogVisible:false,
      addDialogVisible:false,
      editForm:{
        worm_name:'',
        worm_sname:'',
        category:'',
        location:'',
        character:'',
        discipline:'',
        prevent:''
      },
      addForm:{
        worm_name:'',
        img_link:'',
        location:'',
        character:'',
        discipline:'',
        prevent:'',
        worm_sname:'',
        category:'',
      },
      addRule:{
        worm_name:[{required:'true',message:'请输入病虫名称',trigger:'blur'}],
        img_link:[{required:'true',message:'请输入图片链接',trigger:'blur'}],
        location:[{required:'true',message:'请输入分布与危害',trigger:'blur'}],
        character:[{required:'true',message:'请输入形态特征',trigger:'blur'}],
        discipline:[{required:'true',message:'请输入发布规律',trigger:'blur'}],
        prevent:[{required:'true',message:'请输入防治方法',trigger:'blur'}],
        worm_sname:[{required:'true',message:'请输入学名',trigger:'blur'}],
        category:[{required:'true',message:'请输入类属',trigger:'blur'}],
      }
    };
  },
  mounted(){
    this.isAdmin = window.sessionStorage.getItem('type')
  },
  created(){
    this.getAllCate();
    this.getAllWorm();
  },
  methods:{
    getAllCate(){
      this.allCate=[
        '膜翅目',
        '同翅目',
        '鳞翅目',
        '鞘翅目',
        '半翅目',
        '蜱螨目',
        '直翅目',
        '双翅目',
        '柄眼目',
        '啮齿目',
        '等翅目',
        '缨翅目',
        '翅目',
        '螨目',
        '真螨目',
        '等足目',
        '脉翅目',
        '竹节虫目',
        '蝉螨目',
        '蜱满目',
        '垫刃目',
        '甲壳纲潮虫亚目']
    },
    getAllWorm(){
      this.$http.post('/api/getAllWormData',qs.stringify({
        worm_name:this.searchData.worm_name,
        category:this.searchData.category,
        pagenum:this.searchData.pagenum,
        pagesize:this.searchData.pagesize
      })).then(res=>{
        if(res.data.code === 200){
          console.log(res);
          this.allWorm = res.data.data.list
          this.total = res.data.data.num
        }else{
          return this.$message.eror('获取失败')
        }
      }).catch(err=>{
        console.log(err);
      })
    },
    search(){
      this.getAllWorm();
    },
    handleSizeChange(val){
      this.searchData.pagesize = val
      this.getAllWorm();
    },
    handleCurrentChange(val){
      this.searchData.pagenum = val
      this.getAllWorm();
    },
    reset(){
      this.searchData.worm_name = '';
      this.searchData.category = '';
      this.getAllWorm();
    },
    edit(row){
      this.editForm.worm_name = row.worm_name
      this.editForm.worm_sname = row.worm_sname
      this.editForm.category = row.category
      this.editForm.location = row.location
      this.editForm.character = row.character
      this.editForm.discipline = row.discipline
      this.editForm.prevent = row.prevent
      this.editDialogVisible = true
      console.log(row);
    },
    async deleteWorm(row){
     
      const confirmResult =await this.$confirm('此操作将永久删除该病虫, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).catch(err=>err)

      if(confirmResult === 'cancel'){
        return this.$message.info('已经取消该操作')
      }else{
        this.$http.post('/api/deleteWorm',qs.stringify({
          worm_name:row.worm_name
        })).then(res=>{
          if(res.data.code === 200){
            this.$message.success("删除成功")
            this.getAllWorm();
          }else{
            this.$message.error('删除失败')
          }
        }).catch(err=>{
          console.log(err);
        })
      }
    },
    editDialogCancel(){
      this.$refs.editFormRef.resetFields();
      this.editDialogVisible = false
    },
    editFormSubmit(){
      this.$http.post("/api/updateWormInfo",qs.stringify(this.editForm)).then(res=>{
        if(res.data.code ===200){
          this.$refs.editFormRef.resetFields();
          this.editDialogVisible = false;
          this.getAllWorm();
        }else{
          this.$message.error("更新失败")
        }
      }).catch(err=>{
        console.log(err);
      })
    },
    addWorm(){
      this.addDialogVisible = true
    },
    addDialogCancel(){
      this.$refs.addFormRef.resetFields();
      this.addDialogVisible=false
    },
    addFormSubmit(){
      this.$refs.addFormRef.validate(valid=>{
        if(!valid){
          this.$message.error("请完整输入并重新洗")
        }else{
          this.$http.post("/api/insertWorm",qs.stringify(this.addForm)).then(res=>{
            if(res.data.code === 200){
              this.$message.success("增加成功")
              this.getAllWorm();
              this.$refs.addFormRef.resetFields();
              this.addDialogVisible = false
            }else{
              this.addDialogVisible = false
              return this.$message.erorr("增加失败")
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
  .el-card{
    margin-top: 20px;
  }
  .table-td-img {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
  }
  .el-pagination{
    margin-top: 15px;
  }
  .el-select{
    width: 100%;
  }
  .detailBox{
    /* border: 1px solid black; */
    /* width: 100%; */
    margin-left: 50px;
    margin-right: 50px;
  }
  .imageBox{
    width: 100%;
    height: 200px;
  }
  .textBox{
    margin-right: 20px;
    width: 100%;
    height: 200px;
  }
  .el-tag{
    margin-right: 5px;
  }
</style>