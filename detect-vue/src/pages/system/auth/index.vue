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
                    <Input search :placeholder="$t('page.role.searchHolder')" v-model="entityQuery.fullSearch" @on-search="query" />
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

				<template slot-scope="{ row }" slot="type">
					<Tag v-if="row.type === 1" color="blue">审核权限</Tag>
					<Tag v-if="row.type === 2" color="green">浏览权限</Tag>
				</template>

                <!-- 这里添加订制列 -->
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
                    <FormItem label="权限名称" prop="name">
                        <Input v-model="entity.name" placeholder="权限名称"></Input>
                    </FormItem>
					<FormItem label="权限代码" prop="access">
						<Input v-model="entity.access" placeholder="权限名称"></Input>
					</FormItem>
                    <FormItem label="权限类型" prop="type">
						<Select v-model="entity.type" clearable aria-placeholder="请选择权限类型">
							<Option :value="1" :key="1">审核权限</Option>
							<Option :value="2" :key="2">浏览权限</Option>
						</Select>
                    </FormItem>
                </Form>

            </Modal>
        </div>
    </Card>
</template>

<script>
    import dayjs from 'dayjs';
    import { deepClone, format } from '@/libs/system/commonUtil';
    import { entityRequest } from '@/libs/system/requestUtil';

    export default {
        name: 'system-role',
        data() {
            return {
                apiBasePath: 'myAuths',
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
                    },
					{
						title: '名称',
						key: 'name',
						minwidth: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '权限类型',
						key: 'type',
						slot: 'type',
						minwidth: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '权限代码',
						key: 'access',
						minwidth: 200,
						align: 'center',
						isShow: true
					},
					{
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
                ruleValidate: {
                    name: [
                        {required: true, message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.role.entityDescription') + $t('page.common.name') }), trigger: 'blur'}
                    ]
                }
            }
        },
        mounted() {
            this.query();
        },
        computed: {
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
            },
            modalOk() {
                this.$refs.formValidate.validate((valid) => {
                    if (valid) {
						this.entity.authorityIds = ['298959070142922752','298959070142922753'];
                        entityRequest(this.selectIndex < 0 ? 'insert' : 'update', this.apiBasePath, this.entity,
                            //第四个参数, onSuccess
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
                        entityDescription: $t('page.role.entityDescription')
                    }),
                    onOk: () => {
                        // 第四个参数是onSuccess
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
            }
        }
    }
</script>
