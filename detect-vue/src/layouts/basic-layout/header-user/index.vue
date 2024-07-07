<template>
    <span class="i-layout-header-trigger i-layout-header-trigger-min">
        <Dropdown :trigger="isMobile ? 'click' : 'hover'" class="i-layout-header-user" :class="{ 'i-layout-header-user-mobile': isMobile }" @on-click="handleClick">
            <Avatar size="small" :src="getProjectUrl(info.avatar)" v-if="info.avatar" />
            <Avatar size="small" icon="md-person" v-else />
            <span class="i-layout-header-user-name" v-if="!isMobile">{{ info.name }}</span>
            <DropdownMenu slot="list">
                <i-link to="/personal">
                    <DropdownItem>
                        <Icon type="ios-contact-outline" />
                        <span>{{ $t('basicLayout.user.center') }}</span>
                    </DropdownItem>
                </i-link>
                <i-link to="/personal/password">
                    <DropdownItem>
                        <Icon type="iconfont icon-password" />
                        <span>{{ $t('basicLayout.user.setting') }}</span>
                    </DropdownItem>
                </i-link>
                <DropdownItem divided name="logout">
                    <Icon type="ios-log-out" />
                    <span>{{ $t('basicLayout.user.logOut') }}</span>
                </DropdownItem>
            </DropdownMenu>
        </Dropdown>
    </span>
</template>
<script>
    import { mapState, mapActions } from 'vuex';
    import { getProjectUrl } from '@/libs/system/commonUtil';

    export default {
        name: 'iHeaderUser',
        computed: {
            ...mapState('admin/user', [
                'info'
            ]),
            ...mapState('admin/layout', [
                'isMobile',
                'logoutConfirm'
            ])
        },
        methods: {
            ...mapActions('admin/account', [
                'logout'
            ]),
            handleClick (name) {
                if (name === 'logout') {
                    this.logout({
                        confirm: this.logoutConfirm,
                        vm: this
                    });
                }
            },
            getProjectUrl
        }
    }
</script>
