const alarmInfo = [
    {
        id: 1001,
        createTime: new Date(),
        taskId: 1000000000000001,
        taskName: '大鱼',
        alarmType: '',
        userId: 1001,
        userName: '张一',
        confirmUserId: '',
        confirmUserName: '',
        confirmTime: '',
        handleUserId: '',
        handleUserName: '',
        handleTime: '',
        status: 'New'
    },
    {
        id: 1002,
        createTime: new Date(),
        taskId: 1000000000000001,
        taskName: '大鱼',
        alarmType: '',
        userId: 1001,
        userName: '张一',
        confirmUserId: '1002',
        confirmUserName: '张二',
        confirmTime: new Date(),
        handleUserId: '',
        handleUserName: '',
        handleTime: '',
        status: 'Confirmed'
    },
    {
        id: 1003,
        createTime: new Date(),
        taskId: 1200000000000001,
        taskName: '海棠',
        alarmType: '',
        userId: 1001,
        userName: '张一',
        confirmUserId: '1002',
        confirmUserName: '张二',
        confirmTime: new Date(),
        handleUserId: '1003',
        handleUserName: '张三',
        handleTime: new Date(),
        status: 'Handled'
    }
]

export default [
    {
        path: '/api/alarms/1001',
        method: 'get',
        handle ({ body }) {
                return {
                    code: 0,
                    msg: '登录成功',
                    data: alarmInfo[0]
                }
        }
    },
    {
        path: '/api/alarms',
        method: 'post',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: alarmInfo
            }
        }
    },
    {
        path: '/api/alarms',
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
        path: '/api/alarms',
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