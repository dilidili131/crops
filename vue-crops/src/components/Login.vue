<template>
  <div class="login-container">
      <div class="login-box">
            <!-- logo -->
            <div class="avator_box">
                <img src="../assets/logo.png" alt="logo">
            </div>
            <!-- login表单 -->
            <el-form class="login-form" :model="loginForm" :rules="loginRules" ref="loginRef" label-width="10px">
                <!-- 用户名 -->
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username"  prefix-icon="iconfont icon-user"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" prefix-icon="iconfont icon-3702mima" type="password" @keyup.enter.native="login"></el-input>
                </el-form-item>
                <!-- 按钮 -->
                <el-form-item class="btns">
                    <el-button @click="login" type="primary">登录</el-button>
                    <el-button @click="register" type="success">注册</el-button>
                    <el-button @click="reset" type="info">重置</el-button>
                </el-form-item>
            </el-form>
      </div>
  </div>
</template>

<script>
import qs from 'qs'
export default {
    name:'Login',
    data() {
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
            loginForm: {
                username:'',
                password:''
            },
            loginRules:{
                username:[{validator: validatePhone,trigger:'change'}],
                password:[{required:'true',message:"请输入密码",trigger:'blur'}]
            }
        };
    },
    methods:{
        login(){
            this.$refs.loginRef.validate(valid => {
               if(!valid){
                   return this.$message.error("请完整填写账号密码");
                }else{
                   this.$http.post('/api/login',qs.stringify({
                       username : this.loginForm.username,
                       password : this.loginForm.password
                   })).then(res=>{
                       console.log(res);
                        if(res.data.code === 200){
                            // window.sessionStorage.setItem('token',res.data.token)
                            console.log(res);
                            window.sessionStorage.setItem('address',res.data.data.address)
                            window.sessionStorage.setItem('type',res.data.data.type)
                            window.sessionStorage.setItem('username',res.data.data.username)
                            this.$router.push('/home')
                        }else{
                            return this.$message.error("用户名或密码错误");
                        }
                   }).catch(err=>{
                       console.log(err);
                   });
                }
            })
        },
        reset(){
            this.$refs.loginRef.resetFields();
        },
        register(){
            this.$router.push("/register");
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
        height: 300px;
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