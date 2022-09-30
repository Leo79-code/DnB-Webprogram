import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: "/login"
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue'),
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue'),
    },
    {
        path: '/manage',
        name: 'Manage',
        component: () => import('../views/Manage.vue'),
        children: [
            {
                path: 'warehouseman',
                name: 'WarehouseMan',
                component: () => import('../views/WarehouseMan.vue')
            },
            {
                path: 'workerman',
                name: 'WorkerMan',
                component: () => import('../views/WorkerMan.vue')
            },
            {
                path: 'warehouseinf',
                name: 'WarehouseInf',
                component: () => import('../views/WarehouseInf.vue')
            },
            {
                path: 'goodsinf',
                name: 'GoodsInf',
                component: () => import('../views/GoodsInf.vue')
            },
            {
              path: 'analysis',
              name: "Analysis",
              component: () => import('../views/Analysis.vue')
            },
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
