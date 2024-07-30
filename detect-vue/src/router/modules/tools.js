import BasicLayout from '@/layouts/basic-layout';

const pre = 'tools-';

export default {
    path: '/tools',
    name: 'tools',
    redirect: {
        name: `${pre}standardTool`
    },
    auth : ['superAdmin','standardTool-view', 'detectTool-view'],
    component: BasicLayout,
    children: [
        {
            path: 'standardTool',
            name: `${pre}standardTool`,
            meta: {
                auth : ['superAdmin', 'standardTool-view'],
                title: '$t:menu.tools.standardTool',
                closable: true
            },
            component: () => import('@/pages/tools/standardTool')
        },
        {
            path: 'detectTool',
            name: `${pre}detectTool`,
            meta: {
                auth : ['superAdmin', 'detectTool-view'],
                title: '$t:menu.tools.detectTool',
                closable: true
            },
            component: () => import('@/pages/tools/detectTool')
        }
    ]
};
