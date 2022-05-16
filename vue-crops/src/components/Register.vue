<template>
    <div class="login-container">
      <div class="login-box">
            <!-- logo -->
            <div class="avator_box">
                <img src="../assets/logo.png" alt="logo">
            </div>
            <!-- login表单 -->
            <el-form class="login-form" :model="registerForm" :rules="registerRules" ref="registerRef" label-width="90px">
                <!-- 用户名 -->
                <el-form-item prop="username" label="手机号">
                    <el-input v-model="registerForm.username"  prefix-icon="iconfont icon-user"></el-input>
                </el-form-item>
                <!-- 省市区县 -->
                <el-form-item label="省市区/县" prop="address">
                    <el-cascader
                        v-model="registerForm.address"
                        :options="cityData"
                        :props="{ expandTrigger: 'hover' }"
                        @change="handleChange"
                        style="width:100%">
                    </el-cascader>
                </el-form-item>
                
                <!-- 密码 -->
                <el-form-item prop="password" label="密码">
                    <el-input v-model="registerForm.password" prefix-icon="iconfont icon-3702mima" type="password"></el-input>
                </el-form-item>
                <!-- 确认密码 -->
                <el-form-item prop="repassword" label="重复密码">
                    <el-input v-model="registerForm.repassword" prefix-icon="iconfont icon-3702mima" type="password"></el-input>
                </el-form-item>
                <!-- 按钮 -->
                <el-form-item class="btns">
                    <el-button @click="register" type="primary">注册</el-button>
                    <el-button @click="backToLogin" type="success">返回登录</el-button>
                    <el-button @click="reset" type="info">重置</el-button>
                </el-form-item>
            </el-form>
      </div>
  </div>
</template>

<script>
import qs from 'qs'
import cityData from '../assets/js/citydata.js'
import {nanoid} from 'nanoid'
export default {
    name: "Register",
    data() {
        // 密码验证
        var validatePass = (rule,value,callback)=>{
            if(value === ''){
                callback(new Error('请输入密码'))
            }else{
                if(this.registerForm.repassword !== ''){
                    this.$refs.registerRef.validateField('repassword')
                }
                callback()
            }
        };
        // 再次确认密码验证
        var validateCheckPass = (rule,value,callback)=>{
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.registerForm.password) {
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
            registerForm:{
                username:'',
                password:'',
                repassword:'',
                address:[]
            },
            registerRules:{
                username:[{validator: validatePhone,trigger:'change'}],
                password:[{validator: validatePass,trigger:'blur'}],
                repassword:[{validator: validateCheckPass,trigger:'change'}],
            },
            cityData:cityData,
        };
    },
    methods:{
        register(){
            this.$refs.registerRef.validate(valid=>{
                if(!valid){
                    return this.$message.error("请正确输入注册信息");
                }
                this.$http.post("/api/checkUser",qs.stringify({
                    username:this.registerForm.username
                })).then(res1=>{
                    console.log("res1");
                    console.log(res1);
                    if(res1.data.code === 200){
                        return this.$message.error("用户已存在，请直接登录");
                    }else{
                        this.$http.post('/api/addUser',qs.stringify({
                            id:nanoid(),
                            username:this.registerForm.username,
                            password:this.registerForm.password,
                            address:this.registerForm.address.join('/'),
                            type:'normal'
                        })).then(res2=>{
                            console.log("res2");
                            console.log(res2);
                            if(res2.data.code === 200){
                                this.$message.success('注册成功,3秒钟后跳转到登录界面');
                                this.$refs.registerRef.resetFields();
                                setTimeout(() => {
                                    this.$router.push('/login')
                                }, 3000);
                            }else{
                                this.$message.error("注册失败");
                            }
                        }).catch(err=>{
                            console.log(err);
                        });
                    }
                }).catch(err=>{
                    console.log(err);
                });
            })
        },
        reset(){
            this.$refs.registerRef.reserFields();
        },
        handleChange(){
            console.log(this.registerForm.address);
        },
        backToLogin(){
            this.$router.push('/login')
        }
    }
}
</script>

<style scoped>
    .login-container{
        background-color: #2b4b6b;
        height: 100%;
        position: relative;
    }
    .login-box{
        width: 450px;
        height: 400px;
        background-color: #fff;
        border-radius: 3px;
        position: absolute;
        left: 50%;
        top: 50%;
        /* margin-left: -225px;
        margin-top: -150px; */
        transform: translate(-50%,-50%);
    }
    .avator_box{
        width: 130px;
        height: 130px;
        border: 1px solid #ddd;
        border-radius: 50%;
        padding: 10px;
        box-shadow: 0 0 10px #ddd;
        position: absolute;
        left: 50%;
        transform: translate(-50%,-50%);
        background-color: #fff;
    }
    .avator_box img{
        width: 100%;
        height: 100%;
        border-radius: 50%;
        background-color: #fff;
    }
    .login-form{
        position: absolute;
        bottom: 0;
        width: 100%;
        padding: 0 20px;
        box-sizing: border-box;
    }
    .btns{
        display: flex;
        justify-content: flex-end;
    }
</style>