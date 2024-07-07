<template>
    <Card dis-hover>
        <div>
            <Row style="margin-bottom: 5px;">
                <Col flex="100px">
                    <Button type="success" icon="md-add" @click="modalShow(-1)" long>{{ $t('page.common.add') }}</Button>
                </Col>
                <Col flex="auto">
                </Col>
                <Col flex="400px">
                    <Input search :placeholder="$t('page.server.searchHolder')" v-model="search" @on-search="filter" />
                </Col>
                <Col flex="20px">
                    <Dropdown trigger="click" style="margin-top: 5px">
                        <Tooltip class="ivu-ml" :content="$t('page.common.columnSetting')" placement="top">
                            <i-link>
                                <Icon type="md-options" />
                            </i-link>
                        </Tooltip>
                        <DropdownMenu slot="list">
                            <li class="ivu-dropdown-item" v-for="column, index in columns" :key="column.title" v-if="index>0 && !column.fixed" @click="column.isShow = !column.isShow">
                                <Checkbox v-model="column.isShow"></Checkbox>
                                <span>{{ column.title }}</span>
                            </li>
                        </DropdownMenu>
                    </Dropdown>
                </Col>
            </Row>
            <Table :loading="tableLoading"
                   :columns="displayColumns"
                   :data="filteredEntities" height="600">
                <template slot-scope ="{ row, index }" slot="index">
                    {{ index + 1 }}
                </template>
                <template slot-scope ="{ row, index }" slot="messageProtocol">
                    {{ messageProtocolTypes[row.messageProtocol] }}
                </template>
                <template slot-scope ="{ row, index }" slot="netProtocol">
                    {{ netProtocolTypes[row.netProtocol] }}
                </template>
                <template slot-scope ="{ row, index }" slot="status">
                    <Tag :color="statusTags[row.status].color">{{ statusTags[row.status].text }}</Tag>
                </template>
                <template slot-scope ="{ row, index }" slot="action">
                    <Button :disabled="row.status!=1" type="warning" size="small" style="margin-right: 5px" @click="modalShow(index)">{{ $t('page.common.modify') }}</Button>
                    <Button :disabled="row.status!=1" type="error" size="small" @click="onDeleteEntityClick(index)">{{ $t('page.common.delete') }}</Button>
                </template>
            </Table>
            <Modal :title="selectIndex < 0 ? $t('page.common.add') : $t('page.common.modify')"
                   v-model="isShowModel"
                   :loading="modalLoading"
                   :mask-closable="false"
                   :closable="false"
                   @on-ok="modalOk"
                   @on-cancel="modalCancel">

                <Form ref="formValidate" :model="entity" :label-width="100" :rules="ruleValidate">
                    <FormItem :label="$t('page.server.entityDescription') + $t('page.server.template')" prop="templateId">
                        <Select v-model="entity.templateId" :disabled="selectIndex>=0">
                            <Option v-for="serverTemplate in serverTemplates" :value="serverTemplate.id" :key="serverTemplate.id">{{ serverTemplate.name }}</Option>
                        </Select>
                    </FormItem>
                    <FormItem :label="$t('page.device.entityDescription') + $t('page.common.name')" prop="name">
                        <Input v-model="entity.name" :disabled="selectIndex>=0" :placeholder="$t('page.device.entityDescription') + $t('page.common.name')"></Input>
                    </FormItem>
                    <FormItem :label="$t('page.net.port')" prop="port">
                        <InputNumber :max="65535" :min="1" v-model="entity.port"></InputNumber>
                    </FormItem>
                </Form>

            </Modal>
        </div>
    </Card>
</template>

<script>
    import { mapState, mapActions } from 'vuex';
    import dayjs from 'dayjs';
    import { entityRequest } from '@/libs/system/requestUtil';
    import { deepClone, format } from '@/libs/system/commonUtil';

    export default {
        name: 'setting-server',
        data() {
            const validateSelectNotNull = (rule, value, callback) => {
                if (value < 0) {
                    callback(new Error(rule.message));
                } else {
                    callback();
                }
            };
            const validateNotNull = (rule, value, callback) => {
                callback();
            };
            return {
                apiBasePath: 'servers',
                tableLoading: false,
                modalLoading: true,
                isShowModel: false,
                selectIndex: -1,
                entity: {},
                columns: [
                    {
                        title: $t('page.common.index'),
                        slot: 'index',
                        width: 65,
                        fixed: 'left',
                        align: 'center',
                        isShow: true
                    }, {
                        title: $t('page.server.entityDescription') + $t('page.common.name'),
                        key: 'name',
                        minWidth: 200,
                        isShow: true
                    }, {
                        title: $t('page.net.port'),
                        key: 'realPort',
                        minWidth: 100,
                        isShow: true
                    }, {
                        title: $t('page.net.messageProtocol'),
                        slot: 'messageProtocol',
                        minWidth: 150,
                        isShow: true
                    }, {
                        title: $t('page.net.netProtocol'),
                        slot: 'netProtocol',
                        minWidth: 100,
                        isShow: true
                    }, {
                        title: $t('page.server.sessionCount'),
                        key: 'sessionCount',
                        minWidth: 100,
                        isShow: true
                    }, {
                        title: $t('page.common.status'),
                        slot: 'status',
                        minWidth: 100,
                        isShow: true
                    }, {
                        title: $t('page.common.action'),
                        slot: 'action',
                        width: 150,
                        fixed: 'right',
                        align: 'center',
                        isShow: true
                    }
                ],
                ruleValidate: {
                    templateId: [
                        {
                            required: true,
                            validator: validateSelectNotNull,
                            message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.device.entityDescription') + $t('page.server.template')}),
                            trigger: 'change'
                        }
                    ],
                    name: [
                        {
                            required: true,
                            message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.device.entityDescription') + $t('page.common.name')}),
                            trigger: 'blur'
                        }
                    ],
                    port: [
                        {
                            required: true,
                            validator: validateNotNull,
                            message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.net.port')}),
                            trigger: 'blur'
                        }
                    ]
                },

                messageProtocolTypes: [$t('page.net.protocolTypes_none'),$t('page.net.protocolTypes_jt808'),$t('page.net.protocolTypes_watchg1')],
                netProtocolTypes: [$t('page.net.protocolTypes_tcp'),$t('page.net.protocolTypes_udp')],
                statusTags: [{
                    color: 'orange',
                    text: $t('page.server.starting')
                }, {
                    color: 'green',
                    text: $t('page.server.running')
                }, {
                    color: 'red',
                    text: $t('page.server.closing')
                }, {
                    color: 'default',
                    text: $t('page.server.closed')
                }],
                entities: [],
                filteredEntities: [],
                search: '',
                serverTemplates: []
            }
        },
        mounted() {
            this.query();
            this.loadServerTemplates();
        },
        computed: {
            ...mapState('admin/user', [
                'info'
            ]),
            displayColumns() {
                return this.columns.filter(column => column.isShow);
            }
        },
        methods: {
            modalShow(index) {
                this.$refs.formValidate.resetFields();
                this.selectIndex = index;
                this.entity = index < 0 ? {templateId:this.serverTemplates[0].id,name:'',port:10000 } : {oldPort:this.entities[index].realPort,name:this.entities[index].name,port:this.entities[index].realPort };
                this.isShowModel = true;
            },
            modalOk() {
                this.$refs.formValidate.validate((valid) => {
                    if (valid) {
                        entityRequest(this.selectIndex < 0 ? 'insert' : 'update', this.apiBasePath, this.entity,
                            // 第四个参数, onSuccess
                            () => {
                                this.query();
                                this.isShowModel = false;

                            },undefined,
                            // 第六个参数, onError
                            () => {
                                this.modalLoading = false;
                                setTimeout(() => {
                                    this.modalLoading = true;
                                }, 0);
                            });

                    } else {
                        // 取消按钮的loading状态
                        // 因为表单的校验，会导致确定之后，按钮会进入loading状态
                        // 所以要在验证不通过的时候取消loading状态
                        this.modalLoading = false;
                        setTimeout(() => {
                            this.modalLoading = true;
                        }, 0);
                    }
                });
            },
            modalCancel() {
                this.isShowModel = false;
            },
            onDeleteEntityClick(index) {
                this.$Modal.confirm({
                    title: $t('page.common.delete'),
                    content: format($t('page.common.deleteConfirmTemplate'), {
                        entityName: this.entities[index].name,
                        entityDescription: $t('page.server.entityDescription')
                    }),
                    onOk: () => {
                        // 第四个参数是onSuccess
                        entityRequest('delete',this.apiBasePath, this.entities[index].realPort, this.query);
                    }
                });
            },
            query() {
                this.tableLoading = true;
                entityRequest('get', this.apiBasePath, undefined,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.entities = response.data;
                        this.createFullSearch(this.entities);
                        this.filter();
                    },
                    // 第五个参数, onFinish
                    () => {
                        this.tableLoading = false;
                    });
            },
            loadServerTemplates() {
                entityRequest('get', this.apiBasePath + '/templates', undefined,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.serverTemplates = response.data;
                    });
            },
            createFullSearch(entities) {
                for (let i = 0; i < entities.length; i++) {
                    entities[i].fullsearch = (entities[i].name + ',' + entities[i].port + ',' + this.messageProtocolTypes[entities[i].messageProtocol] + ',' + this.netProtocolTypes[entities[i].netProtocol]).toLowerCase();
                }
            },
            filter() {
                this.search = this.search.trim().toLowerCase();
                this.filteredEntities = this.search === '' ? this.entities : this.entities.filter(entity => entity.fullsearch.indexOf(this.search) > -1);
            }
        }
    }
</script>