const locations =  [
        {
            'longitude' : 121.323594,
            'latitude' : 31.231828,
            'organizationId' : 'parent1-1'
        },
        {
            'longitude' : 121.321878,
            'latitude' : 31.230323,
            'organizationId' : 'parent1-1'
        },
        {
            'longitude' : 121.325883,
            'latitude' : 31.229181,
            'organizationId' : 'parent1-1'
        },
        {
            'longitude' : 121.330228,
            'latitude' : 31.230254,
            'organizationId' : 'parent1-1'
        },
        {
            'longitude' : 121.33011,
            'latitude' : 31.232135,
            'organizationId' : 'parent1-1'
        },
        {
            'longitude' : 121.326634,
            'latitude' : 31.232162,
            'organizationId' : 'parent1-1'
        },
        {
            'longitude' : 121.323766,
            'latitude' : 31.228745,
            'organizationId' : 'parent1-1'
        }
];


export default [
    {
        path: '/locations/parent1-1',
        method: 'get',
        handle ({ body }) {
            
                return {
                    code: 0,
                    msg: '登录成功',
                    data: locations
                }
        }
    },
    {
        path: '/api/traces/1001',
        method: 'get',
        handle ({ body }) {
            
            return {
                code: 0,
                msg: '登录成功',
                data: locations
            }
        }
    },
]