const monitorInfo1 = [
    {
        'taskId' : 1000000000000001 ,
        'workers': [
            {
                'id'   : 1001,
                'name' : '张一',
                'location' : [121.333908,31.225823]
            },
            {
                'id'   : 2001,
                'name' : '张二',
                'location' : [121.333808,31.225823]
            },
            {
                'id'   : 3001,
                'name' : '张三',
                'location' : [121.333708,31.225823]
            }],
        'alarms': []
    }
];

const monitorInfo2 = [
    {
        'taskId' : 1100000000000001 ,
        'workers': [
            {
                'id'   : 1001,
                'name' : '张一',
                'location' : [121.334908,31.225833]
            },
            {
                'id'   : 2001,
                'name' : '张二',
                'location' : [121.334908,31.226843]
            },
            {
                'id'   : 3001,
                'name' : '张三',
                'location' : [121.334908,31.227853]
            }],
        'alarms': [{
            'taskId' : 1100000000000001,
            'alarmType' : 'outState'
        }]
    }
];
const monitorInfo3 = [
    {
        'taskId' : 1000000000000001 ,
        'workers': [
            {
                'id'   : 1001,
                'name' : '张一',
                'location' : [121.333908,31.225823]
            },
            {
                'id'   : 2001,
                'name' : '张二',
                'location' : [121.333808,31.225823]
            },
            {
                'id'   : 3001,
                'name' : '张三',
                'location' : [121.333708,31.225823]
            }],
        'alarms': []
    },
    {
        'taskId' : 1100000000000001 ,
        'workers': [
            {
                'id'   : 1001,
                'name' : '张一',
                'location' : [121.334908,31.225833]
            },
            {
                'id'   : 2001,
                'name' : '张二',
                'location' : [121.334908,31.226843]
            },
            {
                'id'   : 3001,
                'name' : '张三',
                'location' : [121.334908,31.227853]
            }],
        'alarms': [{
            'taskId' : 1100000000000001,
            'alarmType' : 'outState'
        }]
    }
];

const taskInfo = [
    {
        'id' : 1000000000000001,
        'name' : '大鱼',
        'description' : 'task1',
        'status': 'Running',  // Running Finished
        'organizationId' : 'leaf1',
        'planStart' : '2020-08-02 12:44:19',
        'planEnd' : '2020-08-02 12:54:19',
        'actualStart' : '2020-08-02 12:48:19',
        'actualEnd' : '',
        'areaPoints' : [
                121.328127,31.229893,
                121.326407,31.228384,
                121.328299,31.226809,
                121.330421,31.227249,
                121.331174,31.230232,
                121.330572,31.226162
        ],
        'areaCounts' : [3,3],
        'organizationName' : 'leaf 1-1-1',
        'manageUserIds' : [3001],
        'workUserIds' : [1001,2001]
    },
    {
        'id' : 1100000000000001,
        'name' : '海棠',
        'description' : 'task2',
        'status': 'Running',  // Running Finished
        'organizationId' : 'leaf1',
        'planStart' : '2021-08-03 12:44:19',
        'planEnd' : '2021-08-03 12:50:19',
        'actualStart' : '2021-08-03 12:48:19',
        'actualEnd' : '',
        'areaPoints' : [
            121.333908,31.225823,
            121.334776,31.228331,
            121.334658,31.230212,
            121.333393,31.228648
        ],
        'areaCounts' : [4],
        'organizationName' : 'leaf 1-1-1',
        'manageUserIds' : [3001],
        'workUserIds' : [1001,2001]
    },
    {
        'id' : 1110000000000001,
        'name' : '蓝天',
        'description' : 'task3',
        'status': 'Finished',  // Running Finished
        'organizationId' : 'leaf2',
        'planStart' : '2020-08-02 12:44:19',
        'planEnd' : '2020-08-02 12:54:19',
        'actualStart' : '2020-08-02 12:48:19',
        'actualEnd' : '2020-08-02 12:54:19',
        'areaPoints' : [
            121.330421,31.227249,
            121.331174,31.230232,
            121.330572,31.226162
        ],
        'areaCounts' : [3],
        'organizationName' : 'leaf 1-1-2',
        'manageUserIds' : [3001],
        'workUserIds' : [1001,2001]
    },
    {
        'id' : 1111000000000001,
        'name' : '白云',
        'description' : 'task4',
        'status': 'New',  // Running Finished
        'organizationId' : 'leaf2',
        'planStart' : '2020-08-02 12:44:19',
        'planEnd' : '2020-08-02 12:54:19',
        'actualStart' : '2020-08-02 12:48:19',
        'actualEnd' : '2020-08-02 12:54:19',
        'areaPoints' : [
            121.330421,31.227249,
            121.331174,31.230232,
            121.330572,31.226162
        ],
        'areaCounts' : [3],
        'organizationName' : 'leaf 1-1-2',
        'manageUserIds' : [3001],
        'workUserIds' : [1001,2001]
    }
    
]

export default [
    {
        path: '/tasks/leaf1/monitorInfo',
        method: 'get',
        handle ({ body }) {
                return {
                    code: 0,
                    msg: '登录成功',
                    data: monitorInfo1
                }
        }
    },
    {
        path: '/tasks/leaf2/monitorInfo',
        method: 'get',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: monitorInfo2
            }
        }
    },
    {
        path: '/tasks/parent1-1/monitorInfo',
        method: 'get',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: monitorInfo3
            }
        }
    },
    {
        path: '/api/tasks/1000000000000001',
        method: 'get',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: taskInfo[0]
            }
        }
    },
    {
        path: '/api/tasks/1100000000000001',
        method: 'get',
        handle ({ body }) {
            return {
                code: 0,
                msg: '登录成功',
                data: taskInfo[1]
            }
        }
    },
    {
        path: '/api/tasks',
        method: 'post',
        handle ({ body }) {
            return {
                code: 0,
                msg: '获取成功',
                data: taskInfo
            }
        }
    },
    {
        path: '/api/tasks',
        method: 'put',
        handle ({ body }) {
            return {
                code: 0,
                msg: '更新成功',
                data: {}
            }
        }
    },
    {
        path: '/api/tasks',
        method: 'delete',
        handle ({ body }) {
            return {
                code: 0,
                msg: '删除成功',
                data: {}
            }
        }
    }
]