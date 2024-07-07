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
                    <Input search :placeholder="$t('page.user.searchHolder')" v-model="entityQuery.fullSearch" @on-search="query" />
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
                   :data="queryResult.entities"
                   @on-sort-change="onSortChange">
                <template slot-scope ="{ row, index }" slot="index">
                    {{ (entityQuery.pageIndex - 1) * entityQuery.pageSize + index + 1 }}
                </template>

                <!-- 这里添加订制列 -->
                <template slot-scope="{ row }" slot="username">
                    <Avatar shape="square" :src="getProjectUrl(row.avatar)" v-if="row.avatar" />
                    <Avatar shape="square" icon="md-person" v-else />
                    {{ row.username }}
                </template>
                <template slot-scope ="{ row, index }" slot="action">
                    <Button type="warning" size="small" style="margin-right: 5px" @click="modalShow(index)">{{ $t('page.common.modify') }}</Button>
                    <Button type="error" size="small" @click="onDeleteEntityClick(index)">{{ $t('page.common.delete') }}</Button>
                </template>
            </Table>
            <Page style="margin-top: 10px" :total="queryResult.count" :page-size="entityQuery.pageSize" :current="entityQuery.pageIndex"
                  show-total
                  show-elevator
                  show-sizer
                  @on-change="onPageChange"
                  @on-page-size-change="onPageSizeChange" />

            <Modal :title="selectIndex < 0 ? $t('page.common.add') : $t('page.common.modify')"
                   v-model="isShowModel"
                   :loading="modalLoading"
                   :mask-closable="false"
                   :closable="false"
                   @on-ok="modalOk"
                   @on-cancel="modalCancel">

                <Form ref="formValidate" :model="entity" :label-width="100" :rules="ruleValidate">
                    <FormItem :label="$t('page.user.username')" prop="username">
                        <Input v-model="entity.username" :placeholder="$t('page.user.username')"></Input>
                    </FormItem>
                    <FormItem :label="$t('page.user.name')" prop="name">
                        <Input v-model="entity.name" :placeholder=" $t('page.user.name')"></Input>
                    </FormItem>
                    <FormItem :label="$t('page.user.password')" prop="password">
                        <Input v-model="entity.password" :placeholder=" $t('page.user.password')"></Input>
                    </FormItem>
                    <FormItem :label="$t('page.user.roles')" prop="roleIds">
                        <Select v-model="entity.roleIds" multiple clearable>
                            <Option v-for="item in roles" :value="item.id" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </FormItem>
                    <FormItem :label="$t('page.common.ownerOrganization')" prop="organizationId">
                        <TreeSelect v-model="entity.organizationId" :data="organizations"></TreeSelect>
                    </FormItem>
                </Form>

            </Modal>
        </div>
    </Card>
</template>

<script>
    import { mapState, mapActions } from 'vuex';
    import dayjs from 'dayjs';
    import { deepClone, format, getProjectUrl } from '@/libs/system/commonUtil';
    import { entityRequest } from '@/libs/system/requestUtil';
    import { organizationToNodeMapping, entityTreeToNodeTree } from '@/libs/system/treeUtil';

    export default {
        name: 'system-user',
        data() {
            return {
                apiBasePath: 'userInfoes',
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
                        title: $t('page.user.username'),
                        slot: 'username',
                        minWidth: 150,
                        isShow: true
                    }, {
                        title: $t('page.user.name'),
                        key: 'name',
                        minWidth: 150,
                        isShow: true
                    }, {
                        title: $t('page.common.createTime'),
                        key: 'createTime',
                        minWidth: 200,
                        sortable: 'custom',
                        isShow: true,
                        render: function (h) {
                            return h('div',
                                dayjs(this.row.createTime).format('YYYY/MM/DD HH:mm:ss'));  /*这里的this.row能够获取当前行的数据*/
                        }
                    }, {
                        title: $t('page.user.roles'),
                        key: 'roles',
                        minWidth: 150,
                        isShow: true
                    }, {
                        title: $t('page.common.ownerOrganization'),
                        key: 'organizationName',
                        minWidth: 150,
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
                entityQuery: {
                    fullSearch: '',
                    pageIndex: 1,
                    pageSize: 10,
                    sortKey: '',
                    sortOrder: ''
                },
                queryResult: {
                    count: 0,
                    entities: []
                },
                organizations: [],
                roles: [],
                ruleValidate: {
                    username: [
                        { required: true, message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.user.username') }), trigger: 'blur' }
                    ],
                    name: [
                        {required: true, message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.user.name') }), trigger: 'blur'}
                    ],
                    password: [
                        { required: true, message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.user.password') }), trigger: 'blur' }
                    ],
                    roleIds: [
                        { required: true, type: 'array', min: 1, message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.user.roles')  }), trigger: 'change' }
                    ],
                    organizationId: [
                        {
                            required: true,
                            message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.common.ownerOrganization')}),
                            trigger: 'change'
                        }
                    ]
                }
            }
        },
        mounted() {
            this.entityQuery.organizationId = this.info.organizationId;
            this.query();
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
            onPageChange(pageIndex) {
                this.entityQuery.pageIndex = pageIndex;
                this.query();
            },
            onPageSizeChange(pageSize) {
                this.entityQuery.pageIndex = 1;
                this.entityQuery.pageSize = pageSize;
                this.query();
            },
            onSortChange({key, order}) {
                this.current = 1;
                this.entityQuery.sortKey = key;
                this.entityQuery.sortOrder = order;
                this.query();
            },
            modalShow(index) {
                this.$refs.formValidate.resetFields();
                this.selectIndex = index;
                this.entity = index < 0 ? {} : deepClone(this.queryResult.entities[index]);
                this.isShowModel = true;
                this.loadOrganizations();
                this.loadRoles();
            },
            modalOk() {
                this.$refs.formValidate.validate((valid) => {
                    if (valid) {
                        entityRequest(this.selectIndex < 0 ? 'insert':'update', this.apiBasePath, this.entity,
                            // 第四个参数, onSuccess
                            this.query,
                            // 第五个参数, onFinish
                            () => {
                                this.isShowModel = false;
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
                        entityName: this.queryResult.entities[index].name,
                        entityDescription: $t('page.user.entityDescription')
                    }),
                    onOk: () => {
                        // 第四个参数, onSuccess
                        entityRequest('delete', this.apiBasePath, this.queryResult.entities[index].id, this.query);
                    }
                });
            },
            query() {
                this.tableLoading = true;
                entityRequest('page', this.apiBasePath, this.entityQuery,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.queryResult.count = response.data.count;
                        this.queryResult.entities = response.data.entities;
                    },
                    // 第五个参数, onFinish
                    () => {
                        this.tableLoading = false;
                    });
            },
            loadOrganizations(){
                entityRequest('get', 'organizations', this.info.organizationId,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.organizations = entityTreeToNodeTree(response.data, organizationToNodeMapping);
                    });
            },
            loadRoles(){
                entityRequest('get', 'roles', undefined,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.roles = response.data;
                    });
            },
            getProjectUrl
        }
    }
</script>