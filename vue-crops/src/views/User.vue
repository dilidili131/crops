<template>
    <div>
        <el-card>
            <el-row :gutter="20">
                <el-col :span="8">
                    <el-input v-model="searchInfo.address" placeholder="地址" clearable @clear="clear"></el-input> 
                </el-col>
                <el-col :span="2">
                    <el-button type="primary" icon="el-icon-search" @click="search" style="width:100%">搜索</el-button>
                </el-col>
                <el-col :span="3">
                    <el-button type="info" icon="el-icon-search" @click="addAdmin" style="width:100%">添加管理员</el-button>
                </el-col>
            </el-row>
        </el-card>
        <el-card class="userList">
            <el-tabs type="border-card" v-model="activeName">
                <el-tab-pane label="用户列表" name="normal">
                    <el-table :data="userList" border>
                        <el-table-column type="index"></el-table-column>
                        <el-table-column prop="username" label="用户电话号"></el-table-column>
                        <el-table-column prop="address" label="用户地址"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button icon="el-icon-edit-outline" type="primary" @click="edit(scope.row)" size="mini">修改</el-button>
                                <el-button icon="el-icon-document-delete" type="danger" @click="deleteUser(scope.row)" size="mini">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane label="管理员列表" name="admin">
                    <el-table :data="adminList" border>
                        <el-table-column type="index"></el-table-column>
                        <el-table-column prop="username" label="用户电话号"></el-table-column>
                        <el-table-column prop="address" label="用户地址"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button icon="el-icon-edit-outline" type="primary" @click="edit(scope.row)" size="mini">修改</el-button>
                                <el-button icon="el-icon-document-delete" type="danger" @click="deleteUser(scope.row)" size="mini">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
        </el-card>
        <el-dialog
            title="修改信息"
            :visible.sync="editDialogVisible"
            width="40%">
            <el-form ref="editFormRef" :model="editForm" label-width="100px">
                <el-form-item label="用户手机号">
                    <el-input v-model="editForm.username"></el-input>
                </el-form-item>
                <el-form-item label="用户地址">
                    <el-input v-model="editForm.address"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editdialogCancel">取 消</el-button>
                <el-button type="primary" @click="editDialogVisibleSubmit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog
            title="添加管理员"
            :visible.sync="addDialogVisible"
            width="40%">
            <el-form :model="addAdminForm" :rules="addRules" ref="addRef" label-width="90px">
                <!-- 用户名 -->
                <el-form-item prop="username" label="手机号">
                    <el-input v-model="addAdminForm.username"  prefix-icon="iconfont icon-user"></el-input>
                </el-form-item>
                <!-- 省市区县 -->
                <el-form-item label="省市区/县" prop="address">
                    <el-cascader
                        v-model="addAdminForm.address"
                        :options="cityData"
                        :props="{ expandTrigger: 'hover' }"
                        style="width:100%">
                    </el-cascader>
                </el-form-item>
                
                <!-- 密码 -->
                <el-form-item prop="password" label="密码">
                    <el-input v-model="addAdminForm.password" prefix-icon="iconfont icon-3702mima" type="password"></el-input>
                </el-form-item>
                <!-- 确认密码 -->
                <el-form-item prop="repassword" label="重复密码">
                    <el-input v-model="addAdminForm.repassword" prefix-icon="iconfont icon-3702mima" type="password"></el-input>
                </el-form-item>
                
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="adddialogCancel">取 消</el-button>
                <el-button type="primary" @click="addDialogVisibleSubmit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import cityData from '../assets/js/citydata.js'
import qs from 'qs'
import {nanoid} from 'nanoid'
export default {
    name:'User',
    created(){
        this.getAllUser();
    },
    mounted(){
        this.getAllAdmin()
    },
    data() {
        // 密码验证
        var validatePass = (rule,value,callback)=>{
            if(value === ''){
                callback(new Error('请输入密码'))
            }else{
                if(this.addAdminForm.repassword !== ''){
                    this.$refs.addRef.validateField('repassword')
                }
                callback()
            }
        };
        // 再次确认密码验证
        var validateCheckPass = (rule,value,callback)=>{
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.addAdminForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        // 手机号码验证
        var validatePhone = (rule,value,callback)=>{
            var reg=11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/;
            if (value === '') {
                callback(new Error('请输入手机号'));
            } else if (!reg.test(value)) {
                callback(new Error('手机号码格式不正确!'));
            } else {
                callback();
            }
        };
        return {
            searchInfo:{
                address:'',
                pagenum:1,
                pagesize:10
            },
            cityData:cityData,
            userList:[],
            userTotal:0,
            adminList:[],
            adminTotal:0,
            activeName:'normal',
            editDialogVisible:false,
            addDialogVisible:false,
            editForm:{
                username:'',
                address:''
            },
            id:'',
            addAdminForm:{
                username:'',
                password:'',
                repassword:'',
                address:[],
            },
            addRules:{
                username:[{validator: validatePhone,trigger:'change'}],
                password:[{validator: validatePass,trigger:'blur'}],
                repassword:[{validator: validateCheckPass,trigger:'change'}],
            }
        };
    },
    methods:{
        getAllUser(){
            this.$http.post("/api/getAllUser",qs.stringify({
                type:'normal',
                address:this.searchInfo.address,
                pagenum:this.searchInfo.pagenum,
                pagesize:this.searchInfo.pagesize
            })).then(res=>{
                console.log(res);
                if(res.data.code === 200){
                    this.userList = res.data.data.list
                    this.userTotal = res.data.data.num
                }else{
                    this.$message.error("获取失败")
                }
            }).catch(err=>{
                console.log(err);
            })
        },
        getAllAdmin(){
            this.$http.post("/api/getAllUser",qs.stringify({
                type:'admin',
                address:this.searchInfo.address,
                pagenum:this.searchInfo.pagenum,
                pagesize:this.searchInfo.pagesize
            })).then(res=>{
                console.log(res);
                if(res.data.code === 200){
                    this.adminList = res.data.data.list
                    this.adminTotal = res.data.data.num
                }else{
                    this.$message.error("获取失败")
                }
            }).catch(err=>{
                console.log(err);
            })
        },
        search(){
            if(this.activeName === 'admin'){
                this.getAllAdmin();
            }else{
                this.getAllUser();
            }
        },
        addAdmin(){
            this.addDialogVisible = true
        },
        clear(){
             if(this.activeName === 'admin'){
                this.getAllAdmin();
            }else{
                this.getAllUser();
            }
        },
        edit(row){
            this.editDialogVisible = true
            this.editForm.username = row.username
            this.editForm.address = row.address
            this.id = row.id
            console.log(row);
        },
        async deleteUser(row){
            const confirmResult =await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).catch(err=>err)

            if(confirmResult === 'cancel'){
                return this.$message.info('已经取消该操作')
            }else{
                this.$http.post('/api/deleteUser',qs.stringify({
                    id:row.id
                })).then(res=>{
                    if(res.data.code === 200){
                        if(this.activeName === 'admin'){
                            this.getAllAdmin();
                        }else{
                            this.getAllUser();
                        }
                        this.$message.success("成功删除")
                    }else{
                        this.$meaasge.error('删除失败')
                    }
                }).catch(err=>{
                    console.log(err);
                })

            }
        },
        editdialogCancel(){
            this.$ref.editFormRef.resetFields();
        },
        editDialogVisibleSubmit(){
            this.$http.post('/api/updateUser',qs.stringify({
                id:this.id,
                username:this.editForm.username,
                address:this.editForm.address
            })).then(res=>{
                if(res.data.code === 200){
                    this.editDialogVisible = false;
                    if(this.activeName === 'admin'){
                        this.getAllAdmin();
                    }else{
                        this.getAllUser();
                    }
                    this.$message.success('更新成功')
                }else{
                    this.editDialogVisible = false;
                    this.$message.error('更新失败')
                }
            })
        },
        adddialogCancel(){
            this.$refs.addRef.resetFields();
        },
        addDialogVisibleSubmit(){
            this.$refs.addRef.validate(valid=>{
                if(!valid){
                    return this.$message.error("请正确填写信息")
                }else{
                    this.$http.post("/api/addAdmin",qs.stringify({
                        id:nanoid(),
                        username:this.addAdminForm.username,
                        password:this.addAdminForm.password,
                        address:this.addAdminForm.address.join('/'),
                        type:'admin'
                    })).then(res=>{
                        if(res.data.code === 200){
                            this.addDialogVisible = false;
                            this.getAllAdmin();
                            this.$message.success('添加成功')
                        }else{
                            this.addDialogVisible = false;
                            this.$message.error('添加失败')
                        }
                    })
                }
            })
        }


    }
}
</script>

<style>
    .userList{
        margin-top: 20px;
    }
</style>