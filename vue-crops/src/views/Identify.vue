<template>
    <div class="container">
        <el-row :gutter="20" class="top-container">
            <el-col :span="9" class="uploadcard">
                <el-card  shadow="hover" style="height:100%">
                    <div slot="header" class="clearfix">
                        <span>上传图片</span>
                    </div>
                    <el-upload
                        drag
                        action="#"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :http-request="httpRequest"
                        :file-list="fileList"
                        list-type="picture"
                        multiple>
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    </el-upload>
                    
                </el-card>
                
            </el-col>
            <el-col :span="9" class="afterImg">
                <el-card shadow="hover" style="height:100%">
                    <div slot="header" class="clearfix">
                        <span>图片标注</span>
                    </div>
                    <el-empty v-if="!istrue" description="图片标注"></el-empty>
                    <div style="height:100%;width:100%">
                        <img v-if="istrue" :src="imgUrl" style="height:300px;width:300px;object-fit: fill;"/>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6" >
                <el-card shadow="hover" :body-style="{ padding: '0px' }" class="mgb20">
                    <div class="grid-content grid-con-1">
                        <i class="el-icon-menu grid-con-icon"></i>
                        <div class="grid-cont-right">
                            <div class="grid-num">{{return_data.category}}</div>
                            <div>类别</div>
                        </div>
                    </div>
                </el-card>
                <el-card shadow="hover" :body-style="{ padding: '0px' }" class="mgb20">
                    <div class="grid-content grid-con-2">
                        <i class="el-icon-s-claim grid-con-icon"></i>
                        <div class="grid-cont-right">
                            <div class="grid-num">{{return_data.confidence}}</div>
                            <div>置信度</div>
                        </div>
                    </div>
                </el-card>
                 <el-card shadow="hover" :body-style="{ padding: '0px' }" class="mgb20">
                    <div class="grid-content grid-con-3">
                        <i class="el-icon-s-data grid-con-icon"></i>
                        <div class="grid-cont-right">
                            <div class="grid-num">{{return_data.count}}</div>
                            <div>病虫害数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>;
        </el-row>
        <el-row :gutter="20" class="bottom-container">
            <el-col :span="6" class="result">
                <el-card shadow="hover" style="height:100%">
                    <div slot="header" class="clearfix">
                        <span>分布与危害</span>
                    </div>
                    <el-empty v-if="worm_detail.location === ''" description="分布与危害"></el-empty>
                    <p>{{worm_detail.location}}</p>
                </el-card>
            </el-col>
            <el-col :span="6" class="result">
                <el-card shadow="hover" class="messageBox">
                    <div slot="header" class="clearfix">
                        <span>形态特征</span>
                    </div>
                    <el-empty v-if="worm_detail.character === ''" description="形态特征"></el-empty>
                    <p>{{worm_detail.character}}</p>
                </el-card>
            </el-col>
            <el-col :span="6" class="result">
                <el-card shadow="hover" class="messageBox">
                    <div slot="header" class="clearfix">
                        <span>发布规律</span>
                    </div>
                    <el-empty v-if="worm_detail.discipline === ''" description="发布规律"></el-empty>
                    <p>{{worm_detail.discipline}}</p>
                </el-card>
            </el-col>
            <el-col :span="6" class="result">
                <el-card shadow="hover" class="messageBox">
                    <div slot="header" class="clearfix">
                        <span>防治方法</span>
                    </div>
                    <el-empty v-if="worm_detail.prevent===''" description="防治方法"></el-empty>
                    <p>{{worm_detail.prevent}}</p>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import qs from 'qs'
import {nanoid} from 'nanoid'
export default {
    name:"Identify",
    data() {
        return {
            fileList: [
            ],
            istrue:false ,
            return_data:{
                category:'无',
                confidence:'0.00',
                count:0,
                detail:[]
            },
            category:'',
            worm_detail:{
                location:'',
                character:'',
                discipline:'',
                prevent:''
            },
            imgUrl:'',
        }
    },
    created(){
        if(window.sessionStorage.getItem('username')===null){
            this.$router.push('/login')
        }
    },
    methods:{
        handlePreview(file){
            this.previewPath = file.response.data.url
            console.log(file);
        },
        handleRemove(file){
            console.log(file);
        },
        cate(cate){
            if(cate==='coleoptera'){
                this.return_data.category = "鞘翅目"
            }else if(cate==='Linnaeus'){
                this.return_data.category = "半翅目"
            }else if(cate==='Boerner'){
                this.return_data.category = "鞘翅目小蠹科"
            }else if(cate==='linnaeus'){
                this.return_data.category = "半翅目"
            }else if(cate==='acuminatus'){
                this.return_data.category = "鞘翅目小蠹科"
            }else if(cate==='armandi'){
                this.return_data.category = "鞘翅目小蠹科"
            }else if(cate==='Leconte'){
                this.return_data.category = "鞘翅目长蠹科"
            }
        },
        async httpRequest(file){
            this.imgUrl=""
            var name = nanoid()
            var formData = new FormData()
            formData.append('photo',file.file);
            await this.$http.post('/api/identification',formData).then(res=>{
                if(res.status === 200){
                    this.return_data.count = res.data.data.length
                    this.return_data.confidence = res.data.data[0].confidences
                    this.cate(res.data.data[0].label)

                    res.data.data.forEach(item => {
                        var objs={};
                        objs.x = item.location[0][0];
                        objs.y = item.location[0][1];
                        objs.w = item.location[0][2];
                        objs.h = item.location[0][3];
                        objs.name = name;
                        this.return_data.detail.push(objs)
                    });
                    this.$http.post('/api/getAllWormData',qs.stringify({
                        worm_name:'',
                        category:this.return_data.category,
                        pagenum:1,
                        pagesize:1
                    })).then(res=>{
                        if(res.data.code === 200){
                            this.worm_detail.character = res.data.data.list[0].character;
                            this.worm_detail.location = res.data.data.list[0].location;
                            this.worm_detail.discipline = res.data.data.list[0].discipline;
                            this.worm_detail.prevent = res.data.data.list[0].prevent;
                        }else{
                            this.$message.error("获取病虫详细信息失败")
                        }
                    }).catch(err=>{
                        console.log(err);
                    })
                }else{
                    this.$message.error('无法识别')
                }
            }).catch(err=>{
                console.log(err);
            })
            //画框
            await this.imgUpload(file,name);
            console.log("imgload  hou");
            console.log(this.return_data.detail);
            await this.imagePaint(name);
            // 报警
            if(this.return_data.count > 10){
                this.$http.post('/api/alert',qs.stringify({
                    username:window.sessionStorage.getItem('username'),
                    worm_name:this.return_data.category,
                    number:this.return_data.count,
                    address:window.sessionStorage.getItem('address'),
                    done:'否'
                }))
            }
        },
        async imgUpload(file,name){
            var form = new FormData()    
            form.append('photo',file.file);
            form.append('name',name);
            
            await this.$http.post("/api/imageUpload",form).then(res=>{
                if(res.data.code === 200){
                    console.log("图片上传成功");
                }
            }).catch(err=>{
                console.log(err);
            })
        },
        async imagePaint(name){
            await this.$http.post('/api/imagePaint',this.return_data.detail,{headers: {'Content-Type': 'application/json'}}).then(ress=>{
                if(ress.data.code === 200){
                    this.istrue = true
                    this.imgUrl= 'https://lch-crops.oss-cn-zhangjiakou.aliyuncs.com/crops'+name+'2.jpg'
                }
            }).catch(err=>{
                console.log(err);
            })
            this.return_data.detail = []
        }

    }
}
</script>

<style scoped>
    .box-card{
        margin-bottom: 20px;
    }
    /deep/ .el-upload{
        width: 100%; 
    }
    /deep/ .el-upload .el-upload-dragger{
        width: 100%;
    }
    .mgb20{
        margin-bottom: 20px;
    }
    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }
    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(100, 213, 114);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }
    .messageBox{
        height: 100%;
    }
    .previewimg{
        height: 80%;
    }
    .container{
        height: 100%;
    }
    .top-container{
        height: 48%;
    }
    .bottom-container{
        height: 48%;
        box-sizing: border-box;
        padding-bottom: 20px;
    }
    .uploadcard{
        height: 100%;
        padding-bottom: 20px;
    }
    .afterImg{
        height: 100%;
        padding-bottom: 20px;
    }
    .result{
        height: 100%;
        padding-bottom: 20px;
    }
</style>