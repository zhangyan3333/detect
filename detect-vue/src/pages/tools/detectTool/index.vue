<template>
    <Card dis-hover>
        <div>
            <Row style="margin-bottom: 5px;">
				<Col flex="200px">
					<Select v-model="entityQuery.standardToolId" clearable placeholder="请选择检定标准器">
						<Option v-for="item in standards" :value="item.id" :key="item.id">{{ item.sname }}</Option>
					</Select>
				</Col>
				<Col flex="20px"/>
				<Col flex="400px">
					<Input search placeholder="可搜索被检表名称" v-model="entityQuery.fullSearch" @on-search="queryFirst" />
				</Col>
                <Col flex="auto">
                </Col>
				<Col flex="100px">
					<Button type="success" icon="md-add" @click="modalShow(-1)" long>{{ $t('page.common.add') }}</Button>
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
            <Table border
				   :loading="tableLoading"
                   :columns="displayColumns"
                   :data="queryResult.entities"
                   @on-sort-change="onSortChange">
                <template slot-scope ="{ row, index }" slot="index">
                    {{ (entityQuery.pageIndex - 1) * entityQuery.pageSize + index + 1 }}
                </template>

                <!-- 这里添加订制列 -->
<!--                <template slot-scope="{ row }" slot="username">-->
<!--                    <Avatar shape="square" :src="getProjectUrl(row.avatar)" v-if="row.avatar" />-->
<!--                    <Avatar shape="square" icon="md-person" v-else />-->
<!--                    {{ row.username }}-->
<!--                </template>-->

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
				   draggable
					width="800"
                   :mask-closable="false"
                   :closable="true"
                   @on-ok="modalOk"
                   @on-cancel="modalCancel">

                <Form ref="formValidate" :model="entity" :label-width="120" :rules="ruleValidate">
					<FormItem label="器具名称" prop="meterName">
						<Input v-model="entity.meterName" placeholder=" 请输入器具名称 "></Input>
					</FormItem>
					<FormItem label="规格型号" prop="meterType">
						<Input v-model="entity.meterType" placeholder=" 请输入规格型号 "></Input>
					</FormItem>
					<FormItem label="制造单位" prop="meterFactory">
						<Input v-model="entity.meterFactory" placeholder=" 请输入制造单位 "></Input>
					</FormItem>
					<FormItem label="送检单位" prop="meterCustomer">
						<Input v-model="entity.meterCustomer" placeholder=" 请输入送检单位 "></Input>
					</FormItem>
					<FormItem label="出厂编号" prop="meterCode">
						<Input v-model="entity.meterCode" placeholder=" 请输入出厂编号 "></Input>
					</FormItem>
					<FormItem label="准确度等级" prop="meterResolution">
						<Input v-model="entity.meterResolution" placeholder=" 请输入准确度等级 "></Input>
					</FormItem>
					<FormItem label="量程(低)">
						<Row>
							<Col span="9">
								<FormItem prop="meterRangeL">
									<Input v-model="entity.meterRangeL" placeholder="请输入被检表最低量程"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">量程(高)</Col>
							<Col span="11">
								<FormItem prop="meterRangeH">
									<Input v-model="entity.meterRangeH" placeholder="请输入被检表最高量程"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="分度值" prop="meterDivisionNo">
						<Input v-model="entity.meterDivisionNo" placeholder=" 请输入分度值 "></Input>
					</FormItem>
					<FormItem label="检定标准" prop="roleIds">
						<Select v-model="entity.standardToolId" clearable placeholder="请选择检定标准及标准器">
							<Option v-for="item in standards" :value="item.id" :key="item.id">{{ item.sname + '   -   ' + item.mname }}</Option>
						</Select>
					</FormItem>
                </Form>
            </Modal>
        </div>

		<detect-history ref="detectHistory"/>
    </Card>
</template>

<script>
    import { mapState, mapActions } from 'vuex';
    import dayjs from 'dayjs';
    import { deepClone, format, getProjectUrl } from '@/libs/system/commonUtil';
    import { entityRequest } from '@/libs/system/requestUtil';
    import { organizationToNodeMapping, entityTreeToNodeTree } from '@/libs/system/treeUtil';
	import util from '@/libs/util';
	import detectHistory from '@/pages/tools/detectTool/detectHistory';

    export default {
        name: 'tools-detectTool',
		components:{ detectHistory },
        data() {
			let _this = this;
            return {
                apiBasePath: 'detectMeters',
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
						key: 'meterName',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '规格型号',
						key: 'meterType',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '检定证书编号',
						key: 'detectCode',
						width: 150,
						align: 'center',
						isShow: true
					},
					{
						title: '送检单位',
						key: 'meterCustomer',
						minWidth: 150,
						align: 'center',
						isShow: true
					},
					{
                        title: '出厂编号',
						key: 'meterCode',
						width: 200,
						align: 'center',
						isShow: true
                    },
					{
						title: '测量范围',
						key: 'srange',
						width: 200,
						align: 'center',
						isShow: true,
						render: function (h, {row,index}) {
							return h('div','(' + row.meterRangeL + '-' + row.meterRangeH+')MPa')
						}
					},
					{
						title: '准确度等级',
						key: 'meterResolution',
						width: 120,
						align: 'center',
						isShow: true
					},
					{
						title: '分度值',
						key: 'meterDivisionNo',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '制造单位',
						key: 'meterFactory',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '检定日期',
						key: 'detectTime',
						width: 200,
						align: 'center',
						isShow: true,
						render: function (h, {row,index}) {
							if(row.detectTime === null){
								return h('div','未检定');
							}
							return h('div',dayjs(row.detectTime).format('YYYY年M月D日'));
						}
					},
					{
						title: '检定标准',
						key: 'standardName',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '检定标准器',
						key: 'standardToolName',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '检定历史记录',
						key: 'leaderTel',
						minWidth: 200,
						align: 'center',
						render: function (h, {row,index}) {
							return [h('a', {
								style:{
									marginRight : '20px'
								},
								on: {
									click: (val) => {
										_this.$refs.detectHistory.initData(row);
										_this.$refs.detectHistory.isShowView = true;
									}
								}
							},'查看')]
						},
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
                    sortOrder: '',
					standardToolId:''
                },
                queryResult: {
                    count: 0,
                    entities: []
                },
                organizations: [],
                roles: [],
                ruleValidate: {

                },
				standards:[]
            }
        },
        mounted() {
            this.entityQuery.checkStep = [0,4];
            this.initStandards();
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

            },
            modalOk() {
            	this.entity.organizationId = 0;
            	this.entity.modifyUserId = util.cookies.get('uuid');
            	this.entity.modifyUserName = this.info.name;
            	this.entity.modifyTime = new Date();

                this.$refs.formValidate.validate((valid) => {
                    if (valid) {
                    	console.log(this.entity)
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
					title: '被检表删除',
					content: '删除后将无法恢复，确认删除<'+ this.queryResult.entities[index].meterName+ '>吗？',
                    onOk: () => {
                    	let deleteEntity ={};
						deleteEntity.message = this.queryResult.entities[index].meterName;
						deleteEntity.userName = this.info.name;
						deleteEntity.userId = util.cookies.get('uuid');
                        // 第四个参数, onSuccess
                        entityRequest(
                        		'delete',
								this.apiBasePath,
								this.queryResult.entities[index].id,
								()=>{
									entityRequest('insert', 'deleteRecs', deleteEntity, this.query)
								},null,null,false
						);
                    }
                });
            },
			queryFirst(){
            	this.entityQuery.pageIndex = 1;
            	this.query();
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
            getProjectUrl,
			initStandards(){
				entityRequest('noPage', 'standardTools', {},
						// 第四个参数, onSuccess
						(response) => {
							this.standards = response.data;
						},null,null,false);
			},
			getStandardById(id,standards){
            	let result = null;
				for (let i = 0; i < standards.length; i++) {
					if (id == standards[i].id){
						result = standards[i];
					}
				}
				return result;
			}
        },
		watch: {
			'entity.standardToolId':{
				handler (val) {
					if (val != null && typeof val != 'undefined' && val > 0){
						let standard = this.getStandardById(val,this.standards);
						if (standard != null ){
							this.entity.sname = standard.sname;
							this.entity.scode = standard.sregulateBcode;
							console.log(new Date(standard.sbdate))
							this.entity.sresolution = standard.mresolution;
							let range = (standard.mrange).split("~");
							this.entity.srangeL = range[0].substr(1,range[0].length -1);
							this.entity.srangeH = range[1].substr(0,range[1].indexOf(")"));
							this.entity.standardLoc = standard.location;
							this.entity.sedate = new Date(standard.sbdate);
							this.entity.sfactory = standard.sfactory;
							this.entity.location = standard.location;
						}
					}
				},
				immediate: true
			},
			'entityQuery.standardToolId':{
				handler (val) {
					console.log(val)
					this.entityQuery.standardToolId = val;
					this.query();
				},
				immediate: true
			}
		}
    }
</script>
