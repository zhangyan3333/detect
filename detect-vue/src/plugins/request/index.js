import store from '@/store';
import axios from 'axios';
import util from '@/libs/util';
import Setting from '@/setting';
import router from '@/router';

import { Message, Notice } from 'view-design';

// 创建一个错误
function errorCreate (msg) {
    const err = new Error(msg);
    errorLog(err);
    throw err;
}

// 记录和显示错误
function errorLog (err) {
    // 添加到日志
    store.dispatch('admin/log/push', {
        message: '数据请求异常',
        type: 'error',
        meta: {
            error: err
        }
    });
    // 打印到控制台
    if (process.env.NODE_ENV === 'development') {
        util.log.error('>>>>>> Error >>>>>>');
        console.log(err);
    }
    // 显示提示，可配置使用 iView 的 $Message 还是 $Notice 组件来显示
    if (Setting.errorModalType === 'Message') {
        Message.error({
            content: err.message,
            duration: Setting.modalDuration
        });
    } else if (Setting.errorModalType === 'Notice') {
        Notice.error({
            title: '提示',
            desc: err.message,
            duration: Setting.modalDuration
        });
    }
}

// 创建一个 axios 实例
const service = axios.create({
    baseURL: Setting.apiBaseURL,
    timeout: 5000 // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
    config => {
        // 在请求发送之前做一些处理
        const token = util.cookies.get('token');
        console.log('请求拦截器');
        console.log(token);
        if(token != undefined) {
            // 在消息头中插入Authorization字段
            config.headers.Authorization = 'Bearer ' + token;
        }

        return config;
    },
    error => {
        // 发送失败
        console.log(error);
        Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    response => {
        if (response.data.code === 7001) {
            store.dispatch('admin/account/clearInfo');
            router.replace({
                name: '7001'
            });
        } else if (response.data.code === 7002) {
            store.dispatch('admin/account/clearInfo');
            router.replace({
                name: '7002'
            });
        } else if (response.data.code === 7003) {
            store.dispatch('admin/account/clearInfo');
            router.replace({
                name: '7003'
            });
        } else if (response.data.code === 7004) {
            router.replace({
                name: '403'
            });
        } else if (response.data.code === 500) {
            router.replace({
                name: '500'
            });
        } else {
            return response.data;
        }
    },
    error => {
        if (error && error.response) {
            switch (error.response.status) {
            case 400: error.message = '请求错误'; break;
            case 401: error.message = '未授权，请登录'; break;
            case 403: error.message = '拒绝访问'; break;
            case 404: error.message = `请求地址出错: ${error.response.config.url}`; break;
            case 408: error.message = '请求超时'; break;
            case 500: error.message = '服务器内部错误'; break;
            case 501: error.message = '服务未实现'; break;
            case 502: error.message = '网关错误'; break;
            case 503: error.message = '服务不可用'; break;
            case 504: error.message = '网关超时'; break;
            case 505: error.message = 'HTTP版本不受支持'; break;
            default: break;
            }
        }
        errorLog(error);
        return Promise.reject(error);
    }
);

export default service;
