<template>
    <Card dis-hover>
        <div>
            <Row style="margin-bottom: 5px;">
                <Col flex="auto">
                </Col>
                <Col flex="400px">
                    <Input search :placeholder="$t('page.session.searchHolder')" v-model="search" @on-search="filter" />
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
                <template slot-scope ="{ row, index }" slot="sendByteLength">
                    <Trend flag="up" text-color>{{ formatSize(row.sendByteLength) }}</Trend>
                </template>
                <template slot-scope ="{ row, index }" slot="receiveByteLength">
                    <Trend flag="down" text-color>{{ formatSize(row.receiveByteLength) }}</Trend>
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
        name: 'setting-session',
        data() {
            return {
                apiBasePath: 'sessions',
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
                        title: $t('page.session.deviceImei'),
                        key: 'deviceImei',
                        minWidth: 150,
                        isShow: true
                    }, {
                        title: $t('page.net.port'),
                        key: 'serverPort',
                        minWidth: 120,
                        isShow: true
                    }, {
                        title: $t('page.net.messageProtocol'),
                        slot: 'messageProtocol',
                        minWidth: 120,
                        isShow: true
                    }, {
                        title: $t('page.net.netProtocol'),
                        slot: 'netProtocol',
                        minWidth: 100,
                        isShow: true
                    }, {
                        title: $t('page.session.sendCount'),
                        key: 'sendCount',
                        minWidth: 100,
                        isShow: true
                    }, {
                        title: $t('page.session.receiveCount'),
                        key: 'receiveCount',
                        minWidth: 100,
                        isShow: true
                    }, {
                        title: $t('page.session.sendByteLength'),
                        slot: 'sendByteLength',
                        minWidth: 120,
                        isShow: true
                    }, {
                        title: $t('page.session.receiveByteLength'),
                        slot: 'receiveByteLength',
                        minWidth: 120,
                        isShow: true
                    }
                ],
                messageProtocolTypes: [$t('page.net.protocolTypes_none'),$t('page.net.protocolTypes_jt808'),$t('page.net.protocolTypes_watchg1')],
                netProtocolTypes: [$t('page.net.protocolTypes_tcp'),$t('page.net.protocolTypes_udp')],
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
                    entities[i].fullsearch = (entities[i].deviceImei + ',' + entities[i].port + ',' + this.messageProtocolTypes[entities[i].messageProtocol] + ',' + this.netProtocolTypes[entities[i].netProtocol]).toLowerCase();
                }
            },
            filter() {
                this.search = this.search.trim().toLowerCase();
                this.filteredEntities = this.search === '' ? this.entities : this.entities.filter(entity => entity.fullsearch.indexOf(this.search) > -1);
            },
            formatSize(size) {
                if (size / 1073741824 > 1) {
                    return (size / 1073741824).toFixed(2) + " GB";
                } else if (size / 1048576 > 1) {
                    return (size / 1048576).toFixed(2) + " MB";
                } else if (size / 1024 > 1) {
                    return (size / 1024).toFixed(2) + " KB";
                } else {
                    return size + " B";
                }
            }
        }
    }
</script>