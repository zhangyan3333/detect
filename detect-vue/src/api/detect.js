import request from '@/plugins/request';

export function printWord (data) {
    return request({
        url: '/api/pgRecords/print',
        method: 'post',
        data
    });
}

export function exportDetectRecord (data) {
    return request({
        url: '/api/basicfile/exportDetectRecord',
        method: 'post',
        data
    });
}

export function exportDetectResult (data) {
    return request({
        url: '/api/basicfile/exportDetectResult',
        method: 'post',
        data
    });
}
