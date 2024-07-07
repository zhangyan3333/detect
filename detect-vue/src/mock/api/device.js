const deviceInfo = [
    {
        id: 1001,
        createTime: new Date(),
        imei: 10000001,
        sim: 111,
        deviceType: 'HighPrecision',  // LowPrecision
        protocolType: 'Jt808',    // Watch_G1
        organizationId: 'leaf1',
        organizationName: 'leaf 1-1-1',
        userId: 1001,
        username: '张一'
    },
    {
        id: 1002,
        createTime: new Date(),
        imei: 20000001,
        sim: 211,
        deviceType: 'LowPrecision',  // LowPrecision
        protocolType: 'Jt808',    // Watch_G1
        organizationId: 'leaf1',
        organizationName: 'leaf 1-1-1',
        userId: 2001,
        username: '张二'
    },
    {
        id: 1003,
        createTime: new Date(),
        imei: 30000001,
        sim: 311,
        deviceType: 'HighPrecision',  // LowPrecision
        protocolType: 'Watch_G1',    // Watch_G1
        organizationId: 'leaf2',
        organizationName: 'leaf 1-1-2',
        userId: 3001,
        username: '张三'
    }
]

export default [
    {
        path: '/api/devices/1001',
        method: 'get',
        handle ({ body }) {
                return {
                    code: 0,
                    msg: '登录成功',
                    data: deviceInfo[0]
                }
        }
    },
    {
        path: '/api/devices',
        method: 'post',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: deviceInfo
            }
        }
    },
    {
        path: '/api/devices',
        method: 'delete',
        handle ({ body }) {
            return {
                code: 0,
                msg: '删除成功',
                data: {}
            }
        }
    },
    {
        path: '/api/devices',
        method: 'put',
        handle ({ body }) {
            return {
                code: 0,
                msg: '更新成功',
                data: {}
            }
        }
    }
]