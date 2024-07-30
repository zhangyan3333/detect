const pre = '/tools/';

export default {
    path: '/tools',
    title: '$t:menu.tools.self',
    header: 'home',
    icon: 'iconfont icon-personal',
    auth : ['superAdmin','standardTool-view', 'detectTool-view'],
    children: [
        {
            auth : ['superAdmin', 'standardTool-view'],
            path: `${pre}standardTool`,
            title: '$t:menu.tools.standardTool',
            icon: 'iconfont icon-info'
        },
        {
            auth : ['superAdmin', 'detectTool-view'],
            path: `${pre}detectTool`,
            title: '$t:menu.tools.detectTool',
            icon: 'iconfont icon-password'
        }
    ]
}
