const pre = '/detect/';

export default {
    path: '/detect',
    title: '$t:menu.detect.self',
    header: 'home',
    icon: 'iconfont icon-personal',
    auth : ['superAdmin','waitDetect-view', 'detectLedger-view'],
    children: [
        {
            auth : ['superAdmin', 'waitDetect-view'],
            path: `${pre}waitDetect`,
            title: '$t:menu.detect.waitDetect',
            icon: 'iconfont icon-info'
        },
        {
            auth : ['superAdmin', 'detectLedger-view'],
            path: `${pre}detectLedger`,
            title: '$t:menu.detect.detectLedger',
            icon: 'iconfont icon-password'
        }
    ]
}
