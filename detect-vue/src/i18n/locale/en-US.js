export default {
    locale: 'en-US',
    language: 'English',
    copyright: 'Copyright © 2019 China Railway Signal & Communication Engineering Bureau Group Beijing Center Of Research, Design & Test Co., Ltd',
    menu: {
        i18n: 'Internationalization',
        personal: {
            self: 'My home',
            info:'Info',
            password:'Password'
        },
        system: {
            self: 'System',
            organization:'Organization',
            user:'User',
            role:'Role'
        }
    },
    page: {
        common:{
            index: 'No.',
            createTime: 'Create Time',
            add: 'New',
            modify: 'Modify',
            delete: 'Delete',
            action: 'Action',
            searchHolder: 'Please enter search keyword',
            columnSetting: 'Column Settings',
            deleteConfirm: 'Are you sure you want to delete {entityDescription}\"{entityName}\" ?'
        },
        login: {
            title: 'Enterprise clock monitoring solution',
            remember: 'Remember me',
            forgot: 'Forgot your password?',
            submit: 'Login',
            other: 'Sign in with',
            signup: 'Sign up',
            errorNotice: 'Incorrect username or password'
        },
        register: {
            title: 'Register',
            submit: 'Register',
            other: 'Already have an account?'
        },
        exception: {
            e403: 'Sorry, you don\'t have access to this page.',
            e404: 'Sorry, the page you visited does not exist.',
            e500: 'Sorry, the server is reporting an error.',
            e7001: 'Sorry, your account has been login on other devices, please login again',
            e7002: 'Sorry, your login has expired, please login again',
            e7003: 'Sorry, you need to be login to do this',
            returnHome: 'Return to home',
            returnLogin: 'Return to login'
        },
        i18n: {
            content: 'Hello, nice to meet you!'
        },
        password: {
            lblOldPassword: 'Old password',
            lblNewPassword: 'New password',
            lblNewPasswordRepeat: 'Repeat password',
            btnModify: 'Modify',
            lblSuccessTitle: 'Success',
            lblSuccessDesc: 'After successfully changing the password, you need to login again to take effect',
            lblSuccessBtnTitle: 'Return to login',
            lblErrorTitle: 'Fail',
            lblErrorDesc: 'Incorrect password',
            lblErrorBtnTitle: 'Try again'
        },
        organization: {
            btnAddRoot: 'Add root organization',
            menuAddSub: 'Add sub organization',
            menuDelete: 'Delete organization',
            lblNotice: 'Add, modify, and delete organizations from the right-click menu',
            lblName: 'Name',
            lblIntiMainTitle: 'Organization management',
            lblAddRootMainTitle: 'Add root organization',
            lblAddSubMainTitle: 'Add sub organization',
            lblModifyMainTitle: 'Modify organization',
            btnAdd: 'Add',
            btnModify: 'Modify',
            validateMessageName: 'The organization name cannot be empty',
            deleteModalTitle: 'Delete organization',
            deleteModalContentHead: 'Are you sure you want to delete \'',
            deleteModalContentTail: '\' organization?',
        },
        role:{
            entityDescription: 'Role ',
            name: 'Name',
            access: 'Access',
            validateMessageName: 'The role name cannot be empty',
            validateMessageAccess: 'The role access cannot be empty'
        }
    }
}
