import BasicLayout from '@/layouts/basic-layout';

const auth = ['personal'];

const pre = 'personal-';

export default {
    path: '/personal',
    name: 'personal',
    redirect: {
        name: `${pre}info`
    },
    auth,
    component: BasicLayout,
    children: [
        {
            path: 'info',
            name: `${pre}info`,
            meta: {
                auth,
                title: '$t:menu.personal.info',
                closable: true
            },
            component: () => import('@/pages/personal/info')
        },
        {
            path: 'password',
            name: `${pre}password`,
            meta: {
                auth,
                title: '$t:menu.personal.password',
                closable: true
            },
            component: () => import('@/pages/personal/password')
        }
    ]
};
