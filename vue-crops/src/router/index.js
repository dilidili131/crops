import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../components/Home.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import Identify from '../views/Identify.vue'
import Search from '../views/Search.vue'
import MsgManage from '../views/MsgManage.vue'
import User from '../views/User.vue'
Vue.use(VueRouter)

const routes = [{
        path: '/',
        redirect: '/login'
    }, {
        path: '/login',
        component: Login
    }, {
        path: '/register',
        component: Register
    }, {
        path: '/home',
        redirect: '/identify'
    }, {
        path: '/home',
        component: Home,
        children: [{
                path: '/identify',
                component: Identify
            },
            {
                path: '/search',
                component: Search
            },
            {
                path: '/message',
                component: MsgManage
            },
            {
                path: '/user',
                component: User
            }
        ]
    },


]

const router = new VueRouter({
    routes
})

export default router