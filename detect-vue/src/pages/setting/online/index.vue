<template>
    <Card dis-hover>
        <div>
            <Row style="margin-bottom: 5px;">
                <Col flex="auto">
                </Col>
                <Col flex="400px">
                    <Input search :placeholder="$t('page.online.searchHolder')" v-model="search" @on-search="filter" />
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
                <template slot-scope ="{ row, index }" slot="nickname">
                    {{ row.info.name }}
                </template>
                <template slot-scope ="{ row, index }" slot="lastActiveTime">
                    {{ formatTime(row.lastActiveTime) }}
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
        name: 'setting-online',
        data() {
            return {
                apiBasePath: 'tokens',
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
                        title: $t('page.online.username'),
                        key: 'username',
                        minWidth: 200,
                        isShow: true
                    }, {
                        title: $t('page.online.nickname'),
                        slot: 'nickname',
                        minWidth: 200,
                        isShow: true
                    }, {
                        title: $t('page.net.ip'),
                        key: 'ip',
                        minWidth: 200,
                        isShow: true
                    }, {
                        title: $t('page.online.lastActiveTime'),
                        slot: 'lastActiveTime',
                        minWidth: 200,
                        isShow: true
                    }
                ],
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
                    entities[i].fullsearch = (entities[i].username + ',' + entities[i].info.name).toLowerCase();
                }
            },
            filter() {
                this.search = this.search.trim().toLowerCase();
                this.filteredEntities = this.search === '' ? this.entities : this.entities.filter(entity => entity.fullsearch.indexOf(this.search) > -1);
            },
            formatTime(time) {
                let second = ((new Date().getTime() - time) / 1000).toFixed(0);
                if (second < 2) {
                    return '刚才';
                } else if (second < 60) {
                    return second + '秒前';
                } else if (second < 3600) {
                    return (second / 60).toFixed(0) + '分钟前';
                } else if (second < 86400) {
                    return (second / 3600).toFixed(0) + '小时前';
                } else {
                    return '1天前';
                }
            }
        }
    }
</script>