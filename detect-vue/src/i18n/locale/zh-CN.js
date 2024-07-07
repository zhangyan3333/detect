export default {
    locale: 'zh-CN',
    language: '简体中文',
    copyright: 'Copyright © 2024 中国铁路北京局集团有限公司计量管理所',
    menu: {
        i18n: '多语言',
        personal: {
            self: '个人中心',
            info:'个人信息',
            password:'修改密码'
        },
        system: {
            self: '系统管理',
            organization:'组织机构',
            user:'用户管理',
            role:'角色管理'
        },
        setting: {
            self: '后台管理',
            system: '系统信息',
            online: '在线人员',
            log: '日志',
            server:'服务器',
            session:'会话',
            thread:'线程'
        },
        safe: {
            self: '安全监测',
            monitor:'实时监测',
            alarm:'人员告警',
            notice:'来车提示'
        },
        usual: {
            self: '日常管理',
            device: '定位设备',
            infrared: '红外设备',
            task:'任务管理'
        }
    },
    page: {
        common:{
            id: 'ID',
            index: '序号',
            name: '名称',
            time: '时间',
            createTime: '创建时间',
            ownerOrganization: '所属机构',
            add: '添加',
            modify: '修改',
            delete: '删除',
            detail: ' 详细信息',
            status: '状态',
            action: '操作',
            start: '开始',
            stop: '结束',
            open: '打开',
            close: '关闭',
            warning: '警告',
            searchHolder: '请输入搜索关键字',
            columnSetting: '列设置',
            deleteConfirmTemplate: '您确定要删除{entityDescription} \"{entityName}\" 吗？',
            notNullTemplate: '{propertyName} 不能为空',
            none: '无',
            meter: '米',
        },
        net:{
            ip: 'IP地址',
            port: '端口号',
            netProtocol: '网络协议',
            messageProtocol: '通信协议',
            protocolTypes_none: '无协议',
            protocolTypes_jt808: '交通部Jt808协议',
            protocolTypes_watchg1: '手环一代协议',
            protocolTypes_udp: 'UDP',
            protocolTypes_tcp: 'TCP',
        },
        time:{
            ns: '纳秒',
            us: '微秒',
            ms: '毫秒',
            s: '秒',
            m: '分钟',
            h: '小时',
            d: '天'
        },
        login: {
            title: '企业级安全防护解决方案',
            remember: '自动登录',
            forgot: '忘记密码',
            submit: '登录',
            other: '其它登录方式',
            signup: '注册账户',
            errorNotice: '用户名或密码错误'
        },
        register: {
            title: '注册',
            submit: '注册',
            other: '使用已有账户登录'
        },
        exception: {
            e403: '抱歉，您无权访问该页面',
            e404: '抱歉，您访问的页面不存在',
            e500: '抱歉，服务器出错了',
            e7001: '抱歉，您的账号已在其他设备登录，请重新登录',
            e7002: '抱歉，您的登录已过期，请重新登录',
            e7003: '抱歉，您需要登录才能进行操作',
            returnHome: '返回首页',
            returnLogin: '返回登录页面'
        },
        i18n: {
            content: '你好，很高兴认识你！'
        },
        info: {
            welcome: '欢迎你，',
            today: '今天是，',
            header: '基本信息',
            updateSuccessMessage: '上传成功，用时：',
            pictureFormatErrorMessage: '图片格式错误, 只支持png或jpg格式',
            pictureSizeErrorMessage: '图片过大',
            validateMessageName: '昵称不能为空',
            btnModifyInfo: '修改信息',
            btnModifyAvatar: '修改头像',
            btnUpdate: '更新基本信息',
            btnCancel: '取消',
            lblAvatar: '头像',
            lblName: '昵称',
        },
        password: {
            lblOldPassword: '旧密码',
            lblNewPassword: '新密码',
            lblNewPasswordRepeat: '重复密码',
            btnModify: '修改密码',
            lblSuccessTitle: '修改成功',
            lblSuccessDesc: '修改密码成功之后，需要重新登录才能生效',
            lblSuccessBtnTitle: '返回登录页面',
            lblErrorTitle: '修改失败',
            lblErrorDesc: '您输入的密码错误',
            lblErrorBtnTitle: '再试一次'
        },
        organization: {
            entityDescription: '机构',
            root: '根机构',
            sub: '子机构',
            noticeMessage: '通过右键菜单，来添加、修改和删除组织机构',
            manage: '管理'
        },
        role:{
            entityDescription: '角色',
            searchHolder: '可搜索 角色名称',
            access: '权限'
        },
        user:{
            entityDescription: '用户',
            searchHolder: '可搜索 昵称、角色、所属机构',
            username: '用户名',
            name: '昵称',
            password: '密码',
            roles: '角色'
        },
        device: {
            entityDescription: '设备',
            searchHolder: '可搜索 设备名称、所属机构、绑定人员',
            type: '类型',
            protocol: '通信协议',
            organizationName: '所属机构',
            bindUser: '绑定人员',
            userConflictNotice: '所选人员已经绑定过设备，您确定要强制解除绑定吗？',
            deviceTypes_person: '人员定位设备',
            deviceTypes_locator: '打点设备'
        },
        infrared: {
            searchHolder: '可搜索 设备名称、所属机构',
            deviceTypes_g1receive: '一代围栏-收',
            deviceTypes_g1send: '一代围栏-发',
            deviceTypes_g2: '二代围栏',
            deviceTypes_train: '来车预警'
        },
        task: {
            entityDescription: '任务',
            searchHolder: '可搜索 任务名称、所属机构',
            description: '描述',
            binded: '已绑定',
            manageUsers: '管理人员',
            workerUsers: '施工人员',
            users: '任务人员',
            hour: '时间段',
            entityFence: '实体围栏',
            tryToFindUser: '告警时，尝试确定触发人员',
            tryToFindUserWarning: '由于定位精度原因，该选项可能会产生误告警，请谨慎选择',
            clock: '点',
            train: '来车预警',
            deviceImei: '设备imei',
            trainNotice: '来车提示',
            actualStart: '任务开始时间',
            actualEnd: '任务结束时间',

            statusNew: '未开始',
            statusRunning: '运行中',
            statusFinished: '已结束',

            modifyRegion: '修改区域',
            altitudeAmend: '锁定海拔到指定高度',
            altitudeAmendWarning: '该选项只会影响地图显示，不影响原始数据，任务开始后，可在监控界面修改'
        },
        monitor: {
            userSearchHolder: '搜索人员',
            deviceSearchHolder: '搜索设备',
            taskName: '任务名称',
            userCount: '人员',
            alarmCount: '告警',
            totalAlarmCount: '未处理告警数',
            refresh: '刷新',
            userAndDevicelist: '人员和设备列表',
            user: '人员',
            netStatus: '网络状态',
            alarmStatus: '告警状态',
            managerFlag: '管',
            trainFlag: '车',
            offline: '离线',
            good: '良好',
            bad: '较差',
            inAlarm: '告警',
            device: '设备',
            sendOffline: '发离线',
            receiveOffline: '收离线',
            online: '在线',
            altitudeAmend: '海拔修正',
        },
        alarm: {
            entityDescription: '告警',
            searchHolder: '可搜索 所属任务、告警人员、告警设备、确认人、处理人',
            confirm: '确认',
            handle: '处理',
            statusNew: '待确认',
            statusConfirmed: '待处理',
            manualHandled: '人工处理',
            userReturnSafe: '自主返回',
            entityStopAlarm: '自主停止',
            taskForceClose: '强制结束',

            ownerTask: '所属任务',
            person: '人',
            device: '设备',
            source: '告警源',
            triggerType: '触发方式',

            fenceTypes_virtual: '虚拟围栏',
            fenceTypes_entity: '实体围栏'
        },
        notice: {
            entityDescription: '提示',
            searchHolder: '可搜索 所属任务、设备imei、提示信息',
            ownerTask: '所属任务',
            message: '提示信息',
            deviceImei: '设备imei',
        },
        online: {
            entityDescription: '在线人员',
            searchHolder: '可搜索 用户名、昵称',
            username: '用户名',
            nickname: '昵称',
            lastActiveTime: '最后活动时间'
        },
        log: {
            modify: '修改配置',
            targets: '输出目标',
            rules: '输出规则',
            memory: '网络',
            console: '后台',
            error: '错误信息',
            important: '重要信息',
            info: '普通信息',
            detail: '详细信息'
        },
        server: {
            entityDescription: '服务器',
            template: '模板',
            searchHolder: '可搜索 服务器名称、端口号、网络协议、通信协议',
            sessionCount: '会话数',
            sendByteLength: '已发送流量',
            receiveByteLength: '已接收流量',
            starting: '启动中',
            running: '运行中',
            closing: '关闭中',
            closed: '已关闭'
        },
        session: {
            entityDescription: '终端',
            searchHolder: '可搜索 终端Imei、端口号、网络协议、通信协议',
            deviceImei: '终端Imei',
            serialNumber: '当前流水号',
            sendCount: '发送次数',
            receiveCount: '接收次数',
            sendByteLength: '发送流量',
            receiveByteLength: '接收流量'
        },
        thread: {
            entityDescription: '线程',
            searchHolder: '可搜索 线程名称',
            cycleInterval: '循环周期'
        }
    }

}
