<template>
    <Card dis-hover>
        <Row style="margin-top: 30px;">
            <Col flex="1">
                <Form ref="formValidate" :model="entity" :label-width="120" v-show="!isSubmit" :rules="ruleValidate">
                    <FormItem :label="$t('page.password.lblOldPassword')" prop="oldPassword">
                        <Input v-model="entity.oldPassword" :placeholder="$t('page.password.lblOldPassword')" type="password" password></Input>
                    </FormItem>
                    <FormItem :label="$t('page.password.lblNewPassword')" prop="newPassword">
                        <Input v-model="entity.newPassword" :placeholder="$t('page.password.lblNewPassword')" type="password" password></Input>
                    </FormItem>
                    <FormItem :label="$t('page.password.lblNewPasswordRepeat')" prop="newPasswordRepeat">
                        <Input v-model="entity.newPasswordRepeat" :placeholder="$t('page.password.lblNewPasswordRepeat')" type="password" password></Input>
                    </FormItem>
                    <FormItem>
                        <Button type="primary" @click="ok">{{$t('page.password.btnModify')}}</Button>
                    </FormItem>
                </Form>
            </Col>
            <Col flex="2">
            </Col>
        </Row>

        <Result :type="sumbitResult" :title="$t(sumbitResultTitle)" v-show="isSubmit">
            <div slot="desc">
                {{$t(sumbitResultDesc)}}
            </div>
            <div slot="actions">
                <Button type="primary" to="/login" v-if="sumbitResult==='success'">{{$t(sumbitResultBtnTitle)}}</Button>
                <Button type="primary" @click="back" v-if="sumbitResult==='error'">{{$t(sumbitResultBtnTitle)}}</Button>
            </div>
        </Result>

    </Card>
</template>

<script>
    import { mapState, mapActions } from 'vuex';
    import util from '@/libs/util';
    import { ChangePassword } from '@api/personal';
    import { UpdateToken } from '@api/account';
    import { requestHandle } from '@/libs/system/requestUtil';

    export default {
        name: 'personal-password',
        data() {
            const validateNewPassword = (rule, value, callback) => {
                if (!value || value === '') {
                    callback(new Error('密码不能为空'));
                } else if (value === this.entity.oldPassword) {
                    callback(new Error('新密码和旧密码一致'));
                } else {
                    callback();
                }
            };
            const validateNewPasswordRepeat = (rule, value, callback) => {
                if (value === this.entity.newPassword) {
                    callback();
                } else {
                    callback(new Error('两次密码不一致'));
                }
            };
            return {
                isSubmit: false,
                sumbitResult: 'error',
                sumbitResultTitle: '',
                sumbitResultDesc: '',
                sumbitResultBtnTitle: '',
                entity: {},
                ruleValidate: {
                    oldPassword: [
                        {required: true, message: '密码不能为空', trigger: 'blur'}
                    ],
                    newPassword: [
                        {required: true, validator: validateNewPassword, trigger: 'blur'}
                    ],
                    newPasswordRepeat: [
                        {required: true, validator: validateNewPasswordRepeat, trigger: 'blur'}
                    ]
                }
            }
        },
        mounted() {
            requestHandle(UpdateToken());
        },
        computed: {
            ...mapState('admin/user', [
                'info'
            ])
        },
        methods: {
            ok() {
                this.$refs.formValidate.validate((valid) => {
                    if (valid) {
                        this.entity.userId = util.cookies.get('uuid');
                        ChangePassword(this.entity)
                            .then(async response => {
                                if (response.code === 0) {
                                    this.sumbitResult = 'success';
                                    this.sumbitResultTitle = 'page.password.lblSuccessTitle';
                                    this.sumbitResultDesc = 'page.password.lblSuccessDesc';
                                    this.sumbitResultBtnTitle = 'page.password.lblSuccessBtnTitle';
                                } else {
                                    this.sumbitResult = 'error';
                                    this.sumbitResultTitle = 'page.password.lblErrorTitle';
                                    this.sumbitResultDesc = 'page.password.lblErrorDesc';
                                    this.sumbitResultBtnTitle = 'page.password.lblErrorBtnTitle';
                                }
                                this.isSubmit = true;
                            })
                            .catch(err => {
                                console.log(err)
                            })
                    }
                });
            },
            back(){
                this.isSubmit = false;
            }
        }
    }
</script>
