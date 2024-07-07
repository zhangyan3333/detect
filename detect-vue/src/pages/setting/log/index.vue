<template>
    <Card dis-hover>
        <Row style="margin-bottom: 5px;">
            <Button type="dashed" icon="iconfont icon-setting" @click="modalShow" long>{{ $t('page.log.modify') }}</Button>
        </Row>
        <Row>
            <div class="scroll-log" ref="logs">
                <p v-for="log in logs" :class="log.logClass">{{log.content}}</p>
            </div>
        </Row>
        <Modal :title="$t('page.log.modify')"
               v-model="isShowModel"
               :loading="modalLoading"
               :mask-closable="false"
               :closable="false"
               @on-ok="modalOk"
               @on-cancel="modalCancel">

            <Form ref="formValidate" :model="entity" :label-width="150">
                <Divider orientation="left">{{ $t('page.log.targets') }}</Divider>
                <FormItem :label="$t('page.log.memory')">
                    <i-switch v-model="entity.isLogMemory" size="large">
                        <span slot="open">{{$t('page.common.open')}}</span>
                        <span slot="close">{{$t('page.common.close')}}</span>
                    </i-switch>
                </FormItem>
                <FormItem :label="$t('page.log.console')">
                    <i-switch v-model="entity.isLogConsole" size="large">
                        <span slot="open">{{$t('page.common.open')}}</span>
                        <span slot="close">{{$t('page.common.close')}}</span>
                    </i-switch>
                </FormItem>
                <Divider orientation="left">{{ $t('page.log.rules') }}</Divider>
                <FormItem :label="$t('page.log.error')">
                    <i-switch v-model="entity.isLogError" size="large">
                        <span slot="open">{{$t('page.common.open')}}</span>
                        <span slot="close">{{$t('page.common.close')}}</span>
                    </i-switch>
                </FormItem>
                <FormItem :label="$t('page.log.important')">
                    <i-switch v-model="entity.isLogImportant" size="large">
                        <span slot="open">{{$t('page.common.open')}}</span>
                        <span slot="close">{{$t('page.common.close')}}</span>
                    </i-switch>
                </FormItem>
                <FormItem :label="$t('page.log.info')">
                    <i-switch v-model="entity.isLogInfo" size="large">
                        <span slot="open">{{$t('page.common.open')}}</span>
                        <span slot="close">{{$t('page.common.close')}}</span>
                    </i-switch>
                </FormItem>
                <FormItem :label="$t('page.log.detail')">
                    <i-switch v-model="entity.isLogDetail" size="large">
                        <span slot="open">{{$t('page.common.open')}}</span>
                        <span slot="close">{{$t('page.common.close')}}</span>
                    </i-switch>
                </FormItem>
            </Form>

        </Modal>
    </Card>
</template>

<script>
    import { entityRequest } from '@/libs/system/requestUtil';

    export default {
        name: "setting-log",
        data() {
            return {
                modalLoading: true,
                isShowModel: false,
                entity: {},
                logs: [],
                totalIndex: 0,
                logTypeClasses:['log-error','log-important','log-info','log-detail'],
                timer: {},
                scrollTop: 0
            }
        },
        mounted() {
            this.timer = setInterval(() => {
                this.loadLog();
            }, 2000);
            console.log("创建timer")
        },
        beforeDestroy() {
            clearInterval(this.timer);
            console.log("销毁timer")
        },
        methods: {
            modalShow() {
                entityRequest('get', "logs/config", undefined,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.entity = response.data;
                        this.splitConfig();
                        this.isShowModel = true;
                    });
            },
            modalOk() {
                this.mergeConfig();
                entityRequest('update', "logs/config", this.entity,
                    // 第四个参数, onSuccess
                    () => {
                        this.isShowModel = false;
                    });
            },
            modalCancel() {
                this.isShowModel = false;
            },
            getLogClass(logType) {
                return this.logTypeClasses[Math.log(logType)/Math.log(2)];
            },
            loadLog() {
                entityRequest('get', "logs", this.totalIndex,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.scrollTop = this.$refs.logs.scrollTop;
                        let lines;
                        for (let i = 0; i < response.data.logs.length; i++) {
                            lines = response.data.logs[i].split("\r\n");
                            for (let j = 0; j < lines.length; j++) {
                                this.logs.push({
                                    content: lines[j],
                                    logClass: this.getLogClass(response.data.logTypes[i])
                                });
                            }
                        }
                        this.totalIndex = response.data.totalIndex;

                        if ((this.$refs.logs.scrollHeight - this.scrollTop) > 555) {
                            this.$nextTick(() => {
                                this.$refs.logs.scrollTop = this.scrollTop;
                            });
                        } else {
                            this.$nextTick(() => {
                                this.$refs.logs.scrollTop = this.$refs.logs.scrollHeight;
                            });
                        }
                    });
            },
            splitConfig() {
                let temp = this.entity.targets;
                this.entity.isLogConsole = temp >= 2;
                if (this.entity.isLogConsole) {
                    temp -= 2;
                }
                this.entity.isLogMemory = temp >= 1;

                temp = this.entity.rules;
                this.entity.isLogDetail = temp >= 8;
                if (this.entity.isLogDetail) {
                    temp -= 8;
                }
                this.entity.isLogInfo = temp >= 4;
                if (this.entity.isLogInfo) {
                    temp -= 4;
                }
                this.entity.isLogImportant = temp >= 2;
                if (this.entity.isLogImportant) {
                    temp -= 2;
                }
                this.entity.isLogError = temp >= 1;
            },
            mergeConfig() {
                this.entity.targets = 0
                if (this.entity.isLogMemory) {
                    this.entity.targets += 1;
                }
                if (this.entity.isLogConsole) {
                    this.entity.targets += 2;
                }

                this.entity.rules = 0
                if (this.entity.isLogError) {
                    this.entity.rules += 1;
                }
                if (this.entity.isLogImportant) {
                    this.entity.rules += 2;
                }
                if (this.entity.isLogInfo) {
                    this.entity.rules += 4;
                }
                if (this.entity.isLogDetail) {
                    this.entity.rules += 8;
                }
            }
        }
    }
</script>

<style scoped>
    .scroll-log{
        height: 550px;
        width: 100%;
        overflow: auto;
        /*border: 1px solid #999;*/
    }
    .log-error{
        color: #ff0000;
        font-weight: bold;
    }
    .log-important{
        color: #008dff;
        font-weight: bold;
    }
    .log-info{
        color: #000000;
    }
    .log-detail{
        color: #000000;
    }
</style>