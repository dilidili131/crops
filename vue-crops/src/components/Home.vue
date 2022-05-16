<template>
    <el-container>
        <el-header>
            <!-- 折叠按钮 -->
            <div class="collapse-btn" @click="collapseChage" >
                <i v-if="!collapse" class="cbtn el-icon-s-fold" ></i>
                <i v-else class="cbtn el-icon-s-unfold"></i>
            </div>
            <!-- title -->
            <span class="title">农业病虫害识别系统</span>
            <div class="back">
                <!-- <span style="color: white;margin-right: 36px">当前登录人：{{currentName}}</span> -->
                <el-button type="danger" size="small" @click="logout">退出</el-button>
            </div>
            <!-- <el-button class="back" @click="logout" type="error" size="mini">退出</el-button> -->
        </el-header>
        <el-container>
            <el-aside :width="width">
                <el-menu
                    :collapse="collapse"
                    :collapse-transition="false"
                    background-color="#324157"
                    text-color="#bfcbd9" 
                    active-text-color="#20a0ff"
                    router
                    unique-opened>
                    <el-menu-item index="identify">
                        <i class="el-icon-camera-solid"></i>
                        <span slot="title">病虫害识别</span>
                    </el-menu-item>
                    <el-menu-item index="search">
                        <i class="el-icon-grape"></i>
                        <span slot="title">病虫害预防与检索</span>
                    </el-menu-item>
                    <el-menu-item v-if="type === 'admin'" index="/message">
                        <i class="el-icon-message-solid"></i>
                        <span slot="title">消息通知</span>
                    </el-menu-item>
                    <el-menu-item v-if="type === 'admin'" index="/user">
                        <i class="el-icon-user-solid"></i>
                        <span slot="title">用户列表</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-container>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </el-container>
</template>

<script>
    export default {
        name:'Home',
        data() {
            return {
                collapse: false,
                width:'200px',
                type:''
            };
        },
        created(){
            // console.log(window.sessionStorage.getItem('username'));
            if(window.sessionStorage.getItem('username')===null){
                this.$router.push('/login')
            }
        },
        mounted(){
            this.type = window.sessionStorage.getItem('type')
        },
        methods:{
            collapseChage(){
                this.collapse = !this.collapse;
                this.width = this.collapse?"65px":"200px"
            },
            logout(){
                window.sessionStorage.clear();
                this.$router.push('/login')
            },
        }
    }
</script>

<style>
    .el-header{
        background-color: #242f42;
        width: 100%;
        /* height: 60px !important; */
        padding: 0 !important;
        /* display: flex;
        justify-content: space-between;
        align-items: center; */
        color: white;
        position: relative;
    }
    .el-aside{
        background-color: #324157;
        height: 100%;
    }
    .el-main{
        background-color: #242f42;
    }
    .collapse-btn{
        float: left;
        width: 70px;
        line-height: 60px;
        font-size: 30px;
        cursor: pointer;
    }
    .collapse-btn:hover{
        background-color: #2d3644;
    }
    .collapse-btn .cbtn{
       margin-left: 20px;
    }
    .title{
        display: inline-block;
        line-height: 60px;
    }
    .back{
        /* float: right; */
        display: inline-block;
        /* line-height: 60px; */
        position: absolute;
        right: 20px;
        top: 15px;
    }

    .el-container{
        height: 100%;
    }
    .el-main{
        background-color: rgb(240, 240, 240);
    }
    .el-menu{
        border: none;
    }
    /* .tags{
        position: relative;
        height: 30px;
        overflow: hidden;
        background-color: #fff;
        padding-right: 20px;
        margin-bottom: 20px;
        box-shadow: 0 5px 10px #dddd;
    } */

</style>