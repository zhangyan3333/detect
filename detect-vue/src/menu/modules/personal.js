const pre = '/personal/';

const auth = ['personal'];

export default {
    path: '/personal',
    title: '$t:menu.personal.self',
    header: 'home',
    icon: 'iconfont icon-personal',
    auth,
    children: [
        {
            auth,
            path: `${pre}info`,
            title: '$t:menu.personal.info',
            icon: 'iconfont icon-info'
        },
        {
            auth,
            path: `${pre}password`,
            title: '$t:menu.personal.password',
            icon: 'iconfont icon-password'
        }
    ]
}
