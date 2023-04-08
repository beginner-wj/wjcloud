import Vue from "vue"
import Router from "vue-router"
import Login from "../views/login"
import Admin from "../views/admin"
import Welcome from "../views/admin/welcome"
import User from "@/views/admin/user"
import Resource from "@/views/admin/resource";
import Role from "@/views/admin/role";
import RoleResource from "@/views/admin/roleresource";





Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes:[{
        path:"*",
        redirect: "/login"
    },{
        path:"/login",
        component: Login
   },{
        path:"",
        component: Login
    },{
        path:"/",
        name:"admin",
        component: Admin,
        meta:{
            loginRequire: true
        },
        children:[
            {
                path:"welcome",//子路由不需要加斜杠
                name:"welcome",
                component: Welcome
            },
            {
                path:"system/user",//子路由不需要加斜杠
                name:"system/user",
                component: User
            },
            {
                path:"system/resource",//子路由不需要加斜杠
                name:"system/resource",
                component: Resource
            },
            {
                path:"system/roleresource",//子路由不需要加斜杠
                name:"system/roleresource",
                component: RoleResource
            },
            {
                path:"system/role",//子路由不需要加斜杠
                name:"system/role",
                component: Role
            }

        ]
    },]
})
