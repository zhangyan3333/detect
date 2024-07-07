<template>
    <div class="page-account">
        <div v-if="showI18n" class="page-account-header">
            <i-header-i18n />
        </div>
        <div class="page-account-container">
            <div class="page-account-top">
<!--                <div class="page-account-top-logo">-->
<!--                    <img src="@/assets/images/logo.png" alt="logo">-->
<!--                </div>-->
				<div style="font-family: Hiragino Sans GB;color: #464c5b;font-size: 45px">计 量 检 定 管 理 系 统</div>
            </div>
            <Login @on-submit="handleSubmit">

                <!--<editor-fold desc="开发配置区，此为开发配置，打包时会自动删除，打包完成后会自动修复">-->
                <UserName name="username" value="admin" enter-to-submit />
                <Password name="password" value="testcentre1119" enter-to-submit />
                <!--</editor-fold>-->

                <!--<editor-fold desc="打包配置区，此为打包配置，开发时会自动注释">-->
                <!--<UserName name="username" enter-to-submit />-->
                <!--<Password name="password" enter-to-submit />-->
                <!--</editor-fold>-->

                <!--<div class="page-account-auto-login">-->
                    <!--<Checkbox v-model="autoLogin" size="large">{{ $t('page.login.remember') }}</Checkbox>-->
                    <!--<a href="">{{ $t('page.login.forgot') }}</a>-->
                <!--</div>-->
                <Submit>{{ $t('page.login.submit') }}</Submit>
            </Login>
            <div v-show="!isSuccess" style="margin: 20px; color: #ed4014">{{ $t('page.login.errorNotice') }}</div>
            <!--<div class="page-account-other">-->
                <!--<span>{{ $t('page.login.other') }}</span>-->
                <!--<img src="@/assets/svg/icon-social-wechat.svg" alt="wechat">-->
                <!--<img src="@/assets/svg/icon-social-qq.svg" alt="qq">-->
                <!--<img src="@/assets/svg/icon-social-weibo.svg" alt="weibo">-->
                <!--<router-link class="page-account-register" :to="{ name: 'register' }">{{ $t('page.login.signup') }}</router-link>-->
            <!--</div>-->
        </div>
        <i-copyright />
    </div>
</template>
<script>
    import iCopyright from '@/components/copyright';
    import { mapActions } from 'vuex';
    import mixins from '../mixins';

    export default {
        mixins: [ mixins ],
        components: { iCopyright },
        data () {
            return {
                isSuccess: true
            }
        },
        methods: {
            ...mapActions('admin/account', [
                'login'
            ]),
            /**
             * @description 登录
             * 表单校验已有 iView Pro 自动完成，如有需要修改，请阅读 iView Pro 文档
             */
            handleSubmit (valid, values) {
                if (valid) {
                    const { username, password } = values;
                    this.login({
                        username,
                        password
                    })
                        .then((isSuccess) => {
                            this.isSuccess = isSuccess;
                            if (isSuccess) {
                                // 重定向对象不存在则返回顶层路径
                                this.$router.replace(this.$route.query.redirect || '/');
                            }
                    });
                }
            }
        }
    };
</script>
