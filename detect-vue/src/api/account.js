import request from '@/plugins/request';

export function AccountLogin (data) {
    return request({
        url: '/api/tokens',
        method: 'post',
        data
    });
}

export function UpdateToken () {
    return request({
        url: '/api/tokens/updateTime',
        method: 'get'
    });
}

// export function AccountLogout (data) {
//     return request({
//         url: '/api/tokens/' + data,
//         method: 'delete'
//     });
// }

export function AccountRegister(data) {
    return null;
}
