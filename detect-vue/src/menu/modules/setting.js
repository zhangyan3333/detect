const pre = '/setting/';

const auth = ['superAdmin'];

export default {
    path: '/setting',
    title:  '$t:menu.setting.self',
    header: 'home',
    icon: 'iconfont icon-setting',
    auth,
    children: [
        {
            auth,
            path: `${pre}system`,
            title: '$t:menu.setting.system',
            icon: 'md-speedometer'
        },
        {
            auth,
            path: `${pre}online`,
            title: '$t:menu.setting.online',
            icon: 'iconfont icon-online'
        },
        {
            auth,
            path: `${pre}log`,
            title: '$t:menu.setting.log',
            icon: 'iconfont icon-log'
        },
        // {
        //     auth,
        //     path: `${pre}server`,
        //     title: '$t:menu.setting.server',
        //     icon: 'iconfont icon-server'
        // },
        // {
        //     auth,
        //     path: `${pre}session`,
        //     title: '$t:menu.setting.session',
        //     icon: 'iconfont icon-session'
        // },
        {
            auth,
            path: `${pre}thread`,
            title: '$t:menu.setting.thread',
            icon: 'iconfont icon-thread'
        }
    ]
}
