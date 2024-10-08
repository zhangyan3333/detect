/**
 * 注册、登录、注销
 * */
import util from '@/libs/util';
import router from '@/router';
import { AccountLogin, AccountRegister } from '@api/account';
import { entityRequest } from '@/libs/system/requestUtil';

import { Modal } from 'view-design';

export default {
    namespaced: true,
    actions: {
        /**
         * @description 登录
         * @param {Object} param context
         * @param {Object} param username {String} 用户账号
         * @param {Object} param password {String} 密码
         * @param {Object} param route {Object} 登录成功后定向的路由对象 任何 vue-router 支持的格式
         */
        login ({ dispatch }, { username = '', password = '' } = {}) {
            return new Promise((resolve, reject) => {
                // 开始请求登录接口
                AccountLogin({
                    username,
                    password
                })
                    .then(async res => {
                        // 设置 cookie 一定要存 uuid 和 token 两个 cookie
                        // 整个系统依赖这两个数据进行校验和存储
                        // uuid 是用户身份唯一标识 用户注册的时候确定 并且不可改变 不可重复
                        // token 代表用户当前登录状态 建议在网络请求中携带 token
                        // 如有必要 token 需要定时更新，默认保存一天，可在 setting.js 中修改
                        // 如果你的 token 不是通过 cookie 携带，而是普通字段，也可视情况存储在 localStorage
                        if(res && res.data && res.data !== 'undefined'){
                            // console.log('------------------')
                            // console.log(res.data)
                            // console.log('------------------')
                            res.data.info.access.push('personal');
                            util.cookies.set('uuid', res.data.uuid);
                            util.cookies.set('token', res.data.token);
                            await dispatch('admin/user/set', res.data.info, { root: true });
                            // 用户登录后从持久化数据加载一系列的设置
                            await dispatch('load');
                            // 结束
                            resolve(true);
                        }else{
                            resolve(false);
                        }
                    })
                    .catch(err => {
                        reject(err);
                    })
            })
        },
        /**
         * @description 退出登录
         * */
        logout ({ commit, dispatch }, { confirm = false, vm } = {}) {
            async function logout () {
                // 删除cookie
                util.cookies.remove('token');
                util.cookies.remove('uuid');
                // 清空 vuex 用户信息
                await dispatch('admin/user/set', {}, { root: true });
                // 跳转路由
                router.push({
                    name: 'login'
                });
            }

            if (confirm) {
                Modal.confirm({
                    title: vm.$t('basicLayout.logout.confirmTitle'),
                    content: vm.$t('basicLayout.logout.confirmContent'),
                    onOk () {
                        entityRequest('delete', 'tokens', util.cookies.get('uuid'), logout);
                    }
                });
            } else {
                entityRequest('delete', 'tokens', util.cookies.get('uuid'), logout);
            }
        },
        /**
         * @description 清除登录信息
         * */
        clearInfo ({ dispatch }, { isClearCookie = true } = {}) {
            async function clearInfo () {
                if(isClearCookie){
                    // 删除cookie
                    util.cookies.remove('token');
                    util.cookies.remove('uuid');
                }
                // 清空 vuex 用户信息
                await dispatch('admin/user/set', {}, { root: true });
            }

            clearInfo();
        },
        /**
         * @description 注册
         * @param {Object} param context
         * @param {Object} param mail {String} 邮箱
         * @param {Object} param password {String} 密码
         * @param {Object} param mobile {String} 手机号码
         * @param {Object} param captcha {String} 验证码
         */
        register ({ dispatch }, {
            mail = '',
            password = '',
            mobile = '',
            captcha = ''
        } = {}) {
            return new Promise((resolve, reject) => {
                // 开始请求登录接口
                AccountRegister({
                    mail,
                    password,
                    mobile,
                    captcha
                })
                    .then(async res => {
                        // 注册成功后，完成与登录一致的操作
                        // 注册也可视情况不返还 uuid、token 等数据，在注册完成后，由前端自动执行一次登录逻辑
                        util.cookies.set('uuid', res.uuid);
                        util.cookies.set('token', res.token);
                        // 设置 vuex 用户信息
                        await dispatch('admin/user/set', res.info, { root: true });
                        // 用户登录后从持久化数据加载一系列的设置
                        await dispatch('load');
                        // 结束
                        resolve();
                    })
                    .catch(err => {
                        // console.log('err: ', err);
                        reject(err);
                    })
            })
        },
        /**
         * @description 用户登录后从持久化数据加载一系列的设置
         * @param {Object} state vuex state
         * @param {Object} dispatch vuex dispatch
         */
        load ({ state, dispatch }) {
            return new Promise(async resolve => {
                // 加载用户登录信息
                await dispatch('admin/user/load', null, { root: true });
                // 持久化数据加载上次退出时的多页列表
                await dispatch('admin/page/openedLoad', null, { root: true });
                // end
                resolve();
            })
        }
    }
};
