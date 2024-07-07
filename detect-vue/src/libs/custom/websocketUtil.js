import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)


export default new Vuex.Store({
    state: {
        ws: null, //建立的连接
        lockReconnect: false, //是否真正建立连接
        timeout: 15000, //15秒一次心跳
        timeoutObj: null, //心跳心跳倒计时
        serverTimeoutObj: null, //心跳倒计时
        timeoutnum: null, //断开 重连倒计时
        msg: null //接收到的信息
    },
    getters: {
        // 获取接收的信息
        socketMsgs: state => {
            return state.msg
        }
    },
    mutations: {
        //初始化ws 用户登录后调用
        webSocketInit(state) {
            let that = this
            //this 创建一个state.ws对象【发送、接收、关闭socket都由这个对象操作】
            state.ws = new WebSocket(process.env.VUE_APP_WEBSOCKET_URL);
            state.ws.onopen = function(res){
                console.log("Connection success...");
                /**
                 * 启动心跳检测
                 */
                that.commit("start");
            }
            state.ws.onmessage = function(res){
                if (res.data === "heartCheck") {
                    // 收到服务器信息，心跳重置
                    that.commit("reset");
                    console.log("socket-heartCheck");
                }else{
                    state.msg = res;
                }
            }
            state.ws.onclose = function(res){
                console.log("Connection closed...");
                //重连
                that.commit('reconnect');
            }
            state.ws.onerror = function(res){
                console.log("Connection error...");
                //重连
                that.commit('reconnect');
            }
        },
        reconnect(state) {
            //重新连接
            let that = this;
            if (state.lockReconnect) {
                return;
            }
            state.lockReconnect = true;
            //没连接上会一直重连,30秒重试请求重连，设置延迟避免请求过多
            state.timeoutnum &&
            clearTimeout(state.timeoutnum);
            state.timeoutnum = setTimeout(() => {
                //新连接
                that.commit('webSocketInit')
                state.lockReconnect = false;
            }, 5000);
        },
        reset(state) {
            //重置心跳
            let that = this;
            //清除时间
            clearTimeout(state.timeoutObj);
            clearTimeout(state.serverTimeoutObj);
            //重启心跳
            that.commit('start')
        },
        start(state) {
            //开启心跳
            var self = this;
            state.timeoutObj &&
            clearTimeout(state.timeoutObj);
            state.serverTimeoutObj &&
            clearTimeout(state.serverTimeoutObj);
            state.timeoutObj = setTimeout(() => {
                //这里发送一个心跳，后端收到后，返回一个心跳消息，
                if (state.ws.readyState === 1) {
                    //如果连接正常
                    state.ws.send("heartCheck");
                } else {
                    //否则重连
                    self.commit('reconnect');
                }
                state.serverTimeoutObj = setTimeout(function () {
                    //超时关闭
                    state.ws.close();
                }, state.timeout);
            }, state.timeout);
        },
    },
    actions: {
        webSocketInit({
                          commit
                      }, url) {
            commit('webSocketInit', url)
        },
        webSocketSend({
                          commit
                      }, p) {
            commit('webSocketSend', p)
        }
    }
})