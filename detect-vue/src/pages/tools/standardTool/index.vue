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
				   width="1200"
				   :mask-closable="false"
				   :closable="true"
				   @on-ok="modalOk"
				   @on-cancel="modalCancel">

				<Form ref="formValidate" :model="entity" :label-width="120" :rules="ruleValidate">
					<FormItem label="所属单位">
						<Row>
							<Col span="9">
								<FormItem prop="organizationName">
									<Input v-model="entity.organizationName" placeholder="请输入标准器归属单位"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">标准器所在地</Col>
							<Col span="11">
								<FormItem prop="location">
									<Input v-model="entity.location" placeholder="请输入标准器所在地"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="检定使用标准名">
						<Row>
							<Col span="9">
								<FormItem prop="sname">
									<Input v-model="entity.sname" placeholder="请输入检定使用标准计量名称"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">标准器名称</Col>
							<Col span="11">
								<FormItem prop="mname">
									<Input v-model="entity.mname" placeholder="请输入标准器名称"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="标准编号">
						<Row>
							<Col span="9">
								<FormItem prop="sregulateCode">
									<Input v-model="entity.sregulateCode" placeholder="请输入检定标准的标准编号"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">标准器编号</Col>
							<Col span="11">
								<FormItem prop="sregulateBcode">
									<Input v-model="entity.sregulateBcode" placeholder="请输入标准器编号"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="标准准确度">
						<Row>
							<Col span="9">
								<FormItem prop="sresolution">
									<Input v-model="entity.sresolution" placeholder="请规范输入检定标准的准确度等级"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">标准器准确度</Col>
							<Col span="11">
								<FormItem prop="mResolution">
									<Input v-model="entity.mresolution" placeholder="请输入标准器准确度"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="标准测量范围">
						<Row>
							<Col span="9">
								<FormItem prop="srange">
									<Input v-model="entity.srange" placeholder="请规范输入检定标准的测量范围"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">标准器测量范围</Col>
							<Col span="11">
								<FormItem prop="mRange">
									<Input v-model="entity.mrange" placeholder="请输入标准器测量范围"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>


					<FormItem label="标准有效期">
						<Row>
							<Col span="9">
								<FormItem prop="sEdate">
									<Input v-model="entity.sedate" placeholder="请输入检定标准的有效期"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">标准器有效期</Col>
							<Col span="11">
								<FormItem prop="sBdate">
									<Input v-model="entity.sbdate" placeholder="请输入标准器有效期"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="">
						<Row>
							<Col span="10"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">溯源机构</Col>
							<Col span="11">
								<FormItem prop="sFactory">
									<Input v-model="entity.sfactory" placeholder="请输入标准器溯源机构"></Input>
								</FormItem>
							</Col>
						</Row>
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
	import util from '@/libs/util';

	export default {
		name: 'tools-standardTool',
		data() {
			return {
				apiBasePath: 'standardTools',
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
						title: '标准名称',
						key: 'sname',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '证书编号',
						key: 'sregulateCode',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '标准器名',
						key: 'mname',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '标准器证书编号',
						key: 'sregulateBcode',
						width: 150,
						align: 'center',
						isShow: true
					},
					{
						title: '归属单位',
						key: 'organizationName',
						minWidth: 150,
						align: 'center',
						isShow: true
					},
					{
						title: '地点',
						key: 'location',
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
							if(row.srange != null &&row.srange.length > 0){
								let result = row.srange.split(",");
								switch (result.length){
									case 0 : return h('div','');break;
									case 1 : return h('div',result[0]);break;
									case 2 : return [h('p',result[0]),h('p',result[1])];break;
									case 3 : return [h('p',result[0]),h('p',result[1]),h('p',result[2])];break;
									case 4 : return [h('p',result[0]),h('p',result[1]),h('p',result[2]),h('p',result[3])];break;
									case 5 : return [h('p',result[0]),h('p',result[1]),h('p',result[2]),h('p',result[3]),h('p',result[4])];break;
								}
							}else {
								return h('div','')
							}
						}
					},
					{
						title: '准确度等级',
						key: 'sresolution',
						width: 120,
						align: 'center',
						isShow: true
					},
					{
						title: '有效期至',
						key: 'sedate',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '标准器测量范围',
						key: 'mrange',
						width: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '标准器准确度等级',
						key: 'mresolution',
						width: 120,
						align: 'center',
						isShow: true					},
					{
						title: '溯源机构',
						key: 'sfactory',
						width: 200,
						align: 'center',
						isShow: true
					},

					{
						title: '标准器有效期至',
						key: 'sbdate',
						width: 200,
						align: 'center',
						isShow: true
					},
					// {
					//     title: $t('page.common.createTime'),
					//     key: 'createTime',
					//     minWidth: 200,
					//     sortable: 'custom',
					//     isShow: true,
					//     render: function (h) {
					//         return h('div',
					//             dayjs(this.row.createTime).format('YYYY/MM/DD HH:mm:ss'));  /*这里的this.row能够获取当前行的数据*/
					//     }
					// },
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
				organizations: [],
				roles: [],
				ruleValidate: {

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
					title: '标准删除',
					content: '确认删除<'+ this.queryResult.entities[index].sname+ '>吗？',
					onOk: () => {
						let deleteEntity ={};
						deleteEntity.name = this.queryResult.entities[index].sname;
						deleteEntity.message = this.queryResult.entities[index].mname;
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
			getProjectUrl
		}
	}
</script>
