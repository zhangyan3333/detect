import request from '@/plugins/request';

export function getUserInOrganization (organizationId) {
    return request({
        url: '/api/userInfoes/inOrganization/'+organizationId,
        method: 'get'
    });
}

export function getUserInOrganizationAndChildren (organizationId) {
    return request({
        url: '/api/userInfoes/inOrganizationAndChildren/'+organizationId,
        method: 'get'
    });
}

export function getUserInTask (taskId) {
    return request({
        url: '/api/userInfoes/inTask/'+taskId,
        method: 'get'
    });
}
