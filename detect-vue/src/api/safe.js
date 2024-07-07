import request from '@/plugins/request';

export function changeMonitorOrganization (data) {
    return request({
        url: '/api/taskMonitorInfos/changeOrganization',
        method: 'post',
        data
    });
}

export function changeAltitudeAmend(data) {
    return request({
        url: '/api/tasks/altitude',
        method: 'put',
        data
    });
}

