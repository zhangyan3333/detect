const organization = [
    {
        title: 'parent1',
        expand: true,
        value: 'parent1',
        selected: false,
        checked: false,
        children: [
            {
                title: 'parent 1-1',
                expand: true,
                value: 'parent1-1',
                selected: false,
                checked: false,
                children: [
                    {
                        title: 'leaf 1-1-1',
                        value: 'leaf1',
                        selected: false,
                        checked: false,
                    },
                    {
                        title: 'leaf 1-1-2',
                        value: 'leaf2',
                        selected: false,
                        checked: false,
                    }
                ]
            }
        ]
    }
];

export default [
    {
        path: '/organization/12',
        method: 'get',
        handle ({ body }) {
            const data = organization
            return {
                code: 0,
                msg: '请求成功',
                data: data
            }
        }
    },
    {
        path: '/organization/parent1-1/allChildren',
        method: 'get',
        handle ({ body }) {
            const data = organization ;
            return {
                code: 0,
                msg: '请求成功',
                data: data
            }
        }
    }
]