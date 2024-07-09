import BasicLayout from '@/layouts/basic-layout';

const pre = 'detect-';

export default {
    path: '/detect',
    name: 'detect',
    redirect: {
        name: `${pre}waitDetect`
    },
    auth : ['superAdmin','waitDetect-view', 'detectLedger-view'],
    component: BasicLayout,
    children: [
        {
            path: 'waitDetect',
            name: `${pre}waitDetect`,
            meta: {
                auth : ['superAdmin', 'waitDetect-view'],
                title: '$t:menu.detect.waitDetect',
                closable: true
            },
            component: () => import('@/pages/detect/waitDetect')
        },
        {
            path: 'detectLedger',
            name: `${pre}detectLedger`,
            meta: {
                auth : ['superAdmin', 'detectLedger-view'],
                title: '$t:menu.detect.detectLedger',
                closable: true
            },
            component: () => import('@/pages/detect/detectLedger')
        }
    ]
};
