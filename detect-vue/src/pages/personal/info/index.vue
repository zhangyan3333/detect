<template>
    <div class="i-layout-page-header">
        <PageHeader hidden-breadcrumb>
            <div slot="content">
                <div v-if="!isModify">
                    <Avatar class="header-avatar" :src="getProjectUrl(info.avatar)" size="64" v-if="info.avatar" />
                    <Avatar class="header-avatar" icon="md-person" size="64" v-else />
                    <div class="header-tip">
                        <p class="welcome-title">{{$t('page.info.welcome') + info.name }}！<Button size="small" icon="md-create" @click="modify()">{{$t('page.info.btnModifyInfo')}}</Button></p>
                        <p class="welcome-desc">
                            <Icon type="md-calendar" /> {{$t('page.info.today')+ today}}
                        </p>
                    </div>
                </div>
                <div v-if="isModify">
                    <h2>{{$t('page.info.header')}}</h2>
                    <Form ref="formValidate" :model="entity" label-position="top" :rules="ruleValidate">
                        <Row style="margin-top: 20px">
                            <Col flex="1">
                                <FormItem :label="$t('page.info.lblAvatar')" prop="avatar">
                                    <Avatar shape="square" :src="getProjectUrl(entity.avatar)" size="90" v-if="entity.avatar"  />
                                    <Avatar shape="square" icon="md-person" size="90" v-else />
                                    <div style="line-height: 24px;">
                                        <div v-if="uploadInfo.status==='uploading'">
                                            正在上传：{{formatSize(uploadInfo.loaded)}}/{{formatSize(uploadInfo.total)}}，速度：{{formatSize(uploadInfo.speed)}}/s
                                        </div>
                                        <div v-if="uploadInfo.status==='merging'">
                                            正在合并文件...
                                        </div>
                                        <Progress style="width: 200px"
                                                  :percent="uploadInfo.percent"
                                                  :stroke-width="5"
                                                  v-if="uploadInfo.status==='uploading' || uploadInfo.status==='merging'"></Progress>
                                    </div>
                                    <Upload ref="upload"
                                            :headers="headers"
                                            :show-upload-list="false"
                                            :format="['jpg','jpeg','png']"
                                            :on-success="onSuccess"
                                            :on-format-error="onFormatError"
                                            :on-exceeded-size="onExceededSize"
                                            :on-progress="onProgress"
                                            :action="uploadUrl">
                                        <Button icon="md-camera">{{$t('page.info.btnModifyAvatar')}}</Button>
                                    </Upload>
                                </FormItem>
                                <FormItem :label="$t('page.info.lblName')" prop="name">
                                    <Input v-model="entity.name" :placeholder="$t('page.info.lblName')" />
                                </FormItem>
                                <FormItem>
                                    <Button type="primary" @click="ok">{{$t('page.info.btnUpdate')}}</Button>
                                    <Button @click="cancel" style="margin-left: 20px">{{$t('page.info.btnCancel')}}</Button>
                                </FormItem>
                            </Col>
                            <Col flex="3">

                            </Col>
                        </Row>
                    </Form>
                </div>
            </div>
        </PageHeader>
    </div>
</template>

<script>
    import { mapState, mapActions } from 'vuex';
    import util from '@/libs/util';
    import { UpdateToken } from '@api/account';
    import { ChangeInfo } from '@api/personal';
    import { deleteFile, requestHandle } from '@/libs/system/requestUtil';
    import { getProjectUrl } from '@/libs/system/commonUtil';
    import dayjs from 'dayjs';

    export default {
        name: 'personal-info',
        data() {
            return {
                uploadUrl:'',
                today: dayjs(new Date()).format('YYYY年M月D日') + " 星期" + "日一二三四五六".charAt(new Date().getDay()),
                isModify: false,
                headers: {
                    'Authorization': 'Bearer ' + util.cookies.get('token')
                },
                entity: {},
                uploadInfo: {
                    startTime: 0,
                    endTime: 0,
                    loaded: 0,
                    total: 0,
                    percent: 0,
                    speed: 0,
                    status: 'none'
                },
                ruleValidate: {
                    name: [
                        {required: true, message: $t('page.info.validateMessageName'), trigger: 'blur'}
                    ]
                }
            }
        },
        mounted() {
            requestHandle(UpdateToken(), () => {
                this.uploadUrl = getProjectUrl('api/files');
            });
        },
        computed: {
            ...mapState('admin/user', [
                'info'
            ])
        },
        methods: {
            onSuccess(res, file) {
                if(this.entity.avatar != this.info.avatar) {
                    deleteFile(this.entity.avatar);
                }

                this.entity.avatar = res.data;
                this.uploadInfo.percent = 0;
                this.uploadInfo.status = 'finished';
                this.$refs.upload.clearFiles();
                this.$Message.success({
                    background: true,
                    content: $t('page.info.updateSuccessMessage') + (this.uploadInfo.endTime - this.uploadInfo.startTime) / 1000 + 's',
                    duration: 3
                });
            },
            onFormatError(file) {
                this.$Message.error({
                    background: true,
                    content: $t('page.info.pictureFormatErrorMessage'),
                    duration: 5
                });
            },
            onExceededSize(file) {
                this.$refs.upload.clearFiles();
                this.$Message.error({
                    background: true,
                    content: $t('page.info.pictureSizeErrorMessage'),
                    duration: 3
                });
            },
            onProgress(event, file, fileList) {

                this.uploadInfo.status = 'uploading';
                this.uploadInfo.startTime = new Date();
                this.uploadInfo.endTime = this.uploadInfo.startTime;
                this.uploadInfo.total = event.total;

                let lastTime = this.uploadInfo.startTime;
                let lastLoad = 0;

                // 调用监听 上传进度 的事件
                event.target.onprogress = (event) => {
                    this.uploadInfo.endTime = new Date();

                    this.uploadInfo.loaded = event.loaded;
                    this.uploadInfo.percent = parseInt(((event.loaded / event.total) * 99).toFixed(0));
                    this.uploadInfo.speed = (this.uploadInfo.loaded - lastLoad) / (this.uploadInfo.endTime - lastTime) * 1000;

                    lastLoad = this.uploadInfo.loaded;
                    lastTime = this.uploadInfo.endTime;
                    if (event.loaded === event.total) {
                        this.uploadInfo.status = 'merging';
                    }
                }
            },
            formatSize(size) {
                if (size > 1073741824) {
                    return (size / 1073741824.0).toFixed(2) + 'GB';
                } else if (size > 1048576) {
                    return (size / 1048576.0).toFixed(1) + 'MB';
                } else if (size > 1024) {
                    return (size / 1024.0).toFixed(0) + 'KB';
                } else {
                    return size + 'B';
                }
            },
            modify() {
                this.entity = {
                    avatar: this.info.avatar,
                    name: this.info.name
                };
                this.isModify = true;
            },
            ok() {
                this.$refs.formValidate.validate((valid) => {
                    if (valid) {
                        this.entity.id = util.cookies.get('uuid');
                        ChangeInfo(this.entity)
                            .then(async response => {
                                if (response.code === 0) {
                                    this.$Message.success({
                                        background: true,
                                        content: '操作成功'
                                    });
                                    this.info.avatar = this.entity.avatar;
                                    this.info.name = this.entity.name;
                                    this.isModify = false;
                                } else {
                                    this.$Message.success({
                                        background: true,
                                        content: '操作失败'
                                    });
                                }
                            })
                            .catch(err => {
                                console.log(err)
                            })
                    }
                });
            },
            cancel() {
                if(this.entity.avatar != this.info.avatar) {
                    deleteFile(this.entity.avatar);
                }

                this.isModify = false;
            },
            getProjectUrl
        }
    }
</script>
<style>
    .header-avatar{
        border-radius: 50%;
        margin-right: 16px;
    }
    .header-tip{
        display: inline-block;
        vertical-align: middle;
    }
    .welcome-title{
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 12px;
    }
    .welcome-desc{
        color: #808695;
    }
</style>