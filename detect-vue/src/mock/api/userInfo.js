const userInfo =  [
        {
            'id'   : 1001,
            'name' : '张一',
            'organizationId' : 'leaf1',
            'organizationName' : 'leaf 1-1-1',
            'location' : [121.333908,31.225823]
        },
        {
            'id'   : 2001,
            'name' : '张二',
            'organizationId' : 'leaf1',
            'organizationName' : 'leaf 1-1-1',
            'location' : [121.333808,31.225823]
        },
        {
            'id'   : 3001,
            'name' : '张三',
            'organizationId' : 'leaf1',
            'organizationName' : 'leaf 1-1-1',
            'location' : [121.333708,31.225823]
        },
        {
            'id'   : 4001,
            'name' : '张四',
            'organizationId' : 'leaf1',
            'organizationName' : 'leaf 1-1-1',
            'location' : [121.333708,31.225823]
        },
        {
            'id'   : 5001,
            'name' : '张五',
            'organizationId' : 'leaf1',
            'organizationName' : 'leaf 1-1-1',
            'location' : [121.333708,31.225823]
        },
        {
            'id'   : 6001,
            'name' : '张六',
            'organizationId' : 'leaf1',
            'organizationName' : 'leaf 1-1-1',
            'location' : [121.333708,31.225823]
        },
        {
            'id'   : 7001,
            'name' : '张七',
            'organizationId' : 'leaf2',
            'organizationName' : 'leaf 1-1-2',
            'location' : [121.333708,31.225823]
        }
];


export default [
    {
        path: '/api/userInfos',
        method: 'post',
        handle ({ body }) {
            console.log('/api/userInfos',body)
                return {
                    code: 0,
                    msg: '登录成功',
                    data: userInfo
                }
        }
    },
    {
        path: '/api/userInfos/1001',
        method: 'get',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: userInfo[0]
            }
        }
    },
    {
        path: '/api/userInfos/2001',
        method: 'get',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: userInfo[1]
            }
        }
    },
    {
        path: '/api/userInfos/3001',
        method: 'get',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: userInfo[2]
            }
        }
    }
]