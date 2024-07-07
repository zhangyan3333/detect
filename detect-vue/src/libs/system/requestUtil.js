import request from '@/plugins/request';
import { Message } from 'view-design';

// export function getEntity (apiBasePath, id) {
//     if (id) {
//         return request({
//             url: '/api/' + apiBasePath + '/' + id,
//             method: 'get'
//         });
//     } else {
//         return request({
//             url: '/api/' + apiBasePath,
//             method: 'get'
//         });
//     }
// }
//
// export function nopageEntity (apiBasePath, data) {
//     return request({
//         url: '/api/' + apiBasePath + '/nopage',
//         method: 'post',
//         data
//     });
// }
//
// export function pageEntity (apiBasePath, data) {
//     return request({
//         url: '/api/' + apiBasePath + '/page',
//         method: 'post',
//         data
//     });
// }
//
// export function insertEntity (apiBasePath, data) {
//     return request({
//         url: '/api/' + apiBasePath,
//         method: 'post',
//         data
//     });
// }
//
// export function updateEntity (apiBasePath, data) {
//     return request({
//         url: '/api/' + apiBasePath,
//         method: 'put',
//         data
//     });
// }
//
// export function deleteEntity (apiBasePath, id) {
//     return request({
//         url: '/api/' + apiBasePath + '/' + id,
//         method: 'delete'
//     });
// }
//
export function deleteFile(fileUrl) {
    request({
        url: '/api/files',
        method: 'delete',
        data: {
            fileUrl: fileUrl
        }
    })
        .then(async response => {
            if (response.code > 0) {
                this.$Message.error({
                    background: true,
                    content: '文件删除失败，' + fileUrl,
                    duration: 5
                });
            }
        })
        .catch(err => {
            console.log(err)
        })
}

/**
 * 请求处理类，固化通用流程，提取回调函数
 * @param request 发送的请求
 * @param onSuccess 请求成功后的回调函数
 * @param onFinish 请求结束的回调函数，会在onSuccess和onError之后调用
 * @param onError 请求失败后的回调函数
 * @param isShowSuccessMessage 是否显示成功提示
 */
export function requestHandle(request,onSuccess,onFinish,onError,isShowSuccessMessage) {
    request
        .then(async response => {
            if(response === undefined){
                return;
            }
            if (response.code === 0) {
                if (isShowSuccessMessage) {
                    Message.success({
                        background: true,
                        content: '操作成功'
                    });
                }

                if (onSuccess) {
                    onSuccess(response);
                }
            } else {
                Message.error({
                    background: true,
                    content: '操作失败, ' + response.message,
                    duration: 5
                });

                if (onError) {
                    onError(response);
                }
            }

            if (onFinish) {
                onFinish(response);
            }
        })
        .catch(err => {
            console.log(err)
        })
}

export function entityRequest(operation,apiBasePath,data,onSuccess,onFinish,onError,isShowSuccessMessage) {
    let methodString;
    let pathPostfix = '';

    switch (operation) {
        case 'get':
            methodString = 'get';
            if (data) {
                pathPostfix = '/' + data;
            }
            break;
        case 'page':
            methodString = 'post';
            pathPostfix = '/page';
            break;
        case 'noPage':
            methodString = 'post';
            pathPostfix = '/noPage';
            break;
        case 'insert':
            methodString = 'post';
            break;
        case 'update':
            methodString = 'put';
            break;
        case 'delete':
            methodString = 'delete';
            pathPostfix = '/' + data;
            break;
        default:
    }

    if (methodString === 'get' || methodString === 'delete') {
        requestHandle(request({
            url: '/api/' + apiBasePath + pathPostfix,
            method: methodString
        }), onSuccess, onFinish, onError, isShowSuccessMessage === undefined ? operation === 'delete' : isShowSuccessMessage);
    } else {
        requestHandle(request({
            url: '/api/' + apiBasePath + pathPostfix,
            method: methodString,
            data
        }), onSuccess, onFinish, onError, isShowSuccessMessage === undefined ? operation === 'insert' || operation === 'update' : isShowSuccessMessage);
    }
}


