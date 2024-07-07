import request from '@/plugins/request';

export function getLocationInOrganizationAndChildren (organizationId) {
    return request({
        url: '/api/locations/inOrganizationAndChildren/'+organizationId,
        method: 'get'
    });
}

export function updateTaskRegions(data) {
    return request({
        url: '/api/tasks/regions',
        method: 'put',
        data
    });
}

export function getFenceDevices (organizationId) {
    return request({
        url: '/api/infraredDevices/fence/'+organizationId,
        method: 'get'
    });
}

export function getTrainDevices (organizationId) {
    return request({
        url: '/api/infraredDevices/train/'+organizationId,
        method: 'get'
    });
}
