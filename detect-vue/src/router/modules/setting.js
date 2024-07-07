import BasicLayout from '@/layouts/basic-layout';

const auth = ['superAdmin'];

const pre = 'setting-';

export default {
    path: '/setting',
    name: 'setting',
    redirect: {
        name: `${pre}system`
    },
    auth,
    component: BasicLayout,
    children: [
        {
            path: 'system',
            name: `${pre}system`,
            meta: {
                auth,
                title: '$t:menu.setting.system',
                closable: true
            },
            component: () => import('@/pages/setting/system')
        },
        {
            path: 'online',
            name: `${pre}online`,
            meta: {
                auth,
                title: '$t:menu.setting.online',
                closable: true
            },
            component: () => import('@/pages/setting/online')
        },
        {
            path: 'log',
            name: `${pre}log`,
            meta: {
                auth,
                title: '$t:menu.setting.log',
                closable: true
            },
            component: () => import('@/pages/setting/log')
        },
        // {
        //     path: 'server',
        //     name: `${pre}server`,
        //     meta: {
        //         auth,
        //         title: '$t:menu.setting.server',
        //         closable: true
        //     },
        //     component: () => import('@/pages/setting/server')
        // },
        // {
        //     path: 'session',
        //     name: `${pre}session`,
        //     meta: {
        //         auth,
        //         title: '$t:menu.setting.session',
        //         closable: true
        //     },
        //     component: () => import('@/pages/setting/session')
        // },
        {
            path: 'thread',
            name: `${pre}thread`,
            meta: {
                auth,
                title: '$t:menu.setting.thread',
                closable: true
            },
            component: () => import('@/pages/setting/thread')
        }
    ]
};
