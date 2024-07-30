import system from './modules/system';
import setting from './modules/setting';
import personal from './modules/personal';
import BasicLayout from '@/layouts/basic-layout';
import detect from '@/router/modules/detect';
import tools from '@/router/modules/tools';

/**
 * 在主框架内显示
 */

const frameIn = [
    {
        path: '/',
        redirect: {
            name: 'personal-info'
        },
        component: BasicLayout,
        children: [
            {
                path: 'index',
                name: 'index',
                redirect: {
                    name: 'personal-info'
                }
            },
            // 刷新页面 必须保留
            {
                path: 'refresh',
                name: 'refresh',
                hidden: true,
                component: {
                    beforeRouteEnter (to, from, next) {
                        next(instance => instance.$router.replace(from.fullPath));
                    },
                    render: h => h()
                }
            },
            // 页面重定向 必须保留
            {
                path: 'redirect/:route*',
                name: 'redirect',
                hidden: true,
                component: {
                    beforeRouteEnter (to, from, next) {
                        next(instance => instance.$router.replace(JSON.parse(from.params.route)));
                    },
                    render: h => h()
                }
            }
        ]
    },
    system,
    tools,
    personal,
    setting,
    detect
];

/**
 * 在主框架之外显示
 */

const frameOut = [
    // 登录
    {
        path: '/login',
        name: 'login',
        meta: {
            title: '$t:page.login.title'
        },
        component: () => import('@/pages/account/login')
    }
    // // 注册
    // {
    //     path: '/register',
    //     name: 'register',
    //     meta: {
    //         title: '$t:page.register.title'
    //     },
    //     component: () => import('@/pages/account/register')
    // },
    // // 注册结果
    // {
    //     path: '/register/result',
    //     name: 'register-result',
    //     meta: {
    //         auth: true,
    //         title: '注册结果'
    //     },
    //     component: () => import('@/pages/account/register/result')
    // }
];

/**
 * 错误页面
 */

const errorPage = [
    {
        path: '/7001',
        name: '7001',
        meta: {
            title: '7001'
        },
        component: () => import('@/pages/system/error/7001')
    },
    {
        path: '/7002',
        name: '7002',
        meta: {
            title: '7002'
        },
        component: () => import('@/pages/system/error/7002')
    },
    {
        path: '/7003',
        name: '7003',
        meta: {
            title: '7003'
        },
        component: () => import('@/pages/system/error/7003')
    },
    {
        path: '/403',
        name: '403',
        meta: {
            title: '403'
        },
        component: () => import('@/pages/system/error/403')
    },
    {
        path: '/500',
        name: '500',
        meta: {
            title: '500'
        },
        component: () => import('@/pages/system/error/500')
    },
    {
        path: '*',
        name: '404',
        meta: {
            title: '404'
        },
        component: () => import('@/pages/system/error/404')
    }
];

// 导出需要显示菜单的
export const frameInRoutes = frameIn;

// 重新组织后导出
export default [
    ...frameIn,
    ...frameOut,
    ...errorPage
];
