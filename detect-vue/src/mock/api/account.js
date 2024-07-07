const userDB = [
    {
        username: 'admin',
        password: 'admin',
        uuid: 'admin-uuid',
        info: {
            name: 'Aresn',
            avatar: 'https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar',
            access: ['system']
        }
    }
];

export default [
    {
        path: '/api/login',
        method: 'post',
        handle ({ body }) {
            const user = userDB.find(e => e.username === body.username && e.password === body.password);
            if (user) {
                return {
                    code: 0,
                    msg: '登录成功',
                    data: {
                        ...user,
                        token: 'A68NUPaXVBJYRStwvd9frcUn8rlf30h6'
                    }
                }
            } else {
                return {
                    code: 401,
                    msg: '用户名或密码错误',
                    data: {}
                }
            }
        }
    }
]