import request from '@/plugins/request';

export function ChangePassword (data) {
    return request({
        url: '/api/userInfoes/password',
        method: 'put',
        data
    });
}

export function ChangeInfo (data) {
    return request({
        url: '/api/userInfoes/info',
        method: 'put',
        data
    });
}

export function DeleteFile (data) {
    return request({
        url: '/api/files',
        method: 'delete',
        data
    });
}
