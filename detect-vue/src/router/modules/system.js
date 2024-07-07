import BasicLayout from '@/layouts/basic-layout';

const auth = ['superAdmin', 'system'];

const pre = 'system-';

export default {
    path: '/system',
    name: 'system',
    redirect: {
        name: `${pre}organization`
    },
    auth,
    component: BasicLayout,
    children: [
        {
            path: 'organization',
            name: `${pre}organization`,
            meta: {
                auth,
                title: '$t:menu.system.organization',
                closable: true
            },
            component: () => import('@/pages/system/organization')
        },
        {
            path: 'user',
            name: `${pre}user`,
            meta: {
                auth,
                title: '$t:menu.system.user',
                closable: true
            },
            component: () => import('@/pages/system/user')
        },
        {
            path: 'role',
            name: `${pre}role`,
            meta: {
                auth,
                title: '$t:menu.system.role',
                closable: true
            },
            component: () => import('@/pages/system/role')
        }
    ]
};
