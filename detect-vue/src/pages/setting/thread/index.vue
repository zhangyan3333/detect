<template>
    <Card dis-hover>
        <div>
            <Row style="margin-bottom: 5px;">
                <Col flex="auto">
                </Col>
                <Col flex="400px">
                    <Input search :placeholder="$t('page.thread.searchHolder')" v-model="search" @on-search="filter" />
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
                <template slot-scope ="{ row, index }" slot="cycleInterval">
                    {{ row.cycleInterval+units[row.unit] }}
                </template>
            </Table>
        </div>
    </Card>
</template>

<script>
    import { mapState, mapActions } from 'vuex';
    import dayjs from 'dayjs';
    import { entityRequest } from '@/libs/system/requestUtil';

    export default {
        name: 'setting-thread',
        data() {
            return {
                apiBasePath: 'threads',
                tableLoading: false,
                columns: [
                    {
                        title: $t('page.common.index'),
                        slot: 'index',
                        width: 65,
                        fixed: 'left',
                        align: 'center',
                        isShow: true
                    }, {
                        title: $t('page.thread.entityDescription') + $t('page.common.id'),
                        key: 'id',
                        minWidth: 200,
                        isShow: true
                    }, {
                        title: $t('page.thread.entityDescription') + $t('page.common.name'),
                        key: 'name',
                        minWidth: 200,
                        isShow: true
                    }, {
                        title: $t('page.thread.cycleInterval'),
                        slot: 'cycleInterval',
                        minWidth: 150,
                        isShow: true
                    }
                ],
                units: [ $t('page.time.ns'),$t('page.time.us'),$t('page.time.ms'),$t('page.time.s'),$t('page.time.m'),$t('page.time.h'),$t('page.time.d')],
                entities: [],
                filteredEntities: [],
                search: ''
            }
        },
        mounted() {
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
            createFullSearch(entities) {
                for (let i = 0; i < entities.length; i++) {
                    entities[i].fullsearch = (entities[i].name).toLowerCase();
                }
            },
            filter() {
                this.search = this.search.trim().toLowerCase();
                this.filteredEntities = this.search === '' ? this.entities : this.entities.filter(entity => entity.fullsearch.indexOf(this.search) > -1);
            }
        }
    }
</script>