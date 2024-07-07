const pre = '/system/';

const auth = ['superAdmin', 'system'];

export default {
    path: '/system',
    title:  '$t:menu.system.self',
    header: 'home',
    icon: 'iconfont icon-system',
    auth,
    children: [
        {
            auth,
            path: `${pre}organization`,
            title: '$t:menu.system.organization',
            icon: 'iconfont icon-organization'
        },
        {
            auth,
            path: `${pre}user`,
            title: '$t:menu.system.user',
            icon: 'iconfont icon-user'
        },
        {
            auth,
            path: `${pre}role`,
            title: '$t:menu.system.role',
            icon: 'iconfont icon-role'
        }
    ]
}
