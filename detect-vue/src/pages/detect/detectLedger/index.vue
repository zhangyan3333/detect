<template>
	<Card dis-hover>
		<div>
			<Row style="margin-bottom: 5px;">
<!--				<Col flex="100px">-->
<!--					<Button type="success" icon="md-add" @click="modalShow(-1)" long>{{ '同步检定数据' }}</Button>-->
<!--				</Col>-->
				<Col style="" flex="500px">
					<Input search placeholder='可搜索送检单位及仪表名称' v-model="entityQuery.fullSearch" @on-search="query" />
				</Col>
				<Col flex="auto">
				</Col>
<!--				<Col style="margin-left: 20px" flex="500px">-->
<!--					<Input search placeholder='可搜索送检单位及仪表名称' v-model="entityQuery.fullSearch" @on-search="query" />-->
<!--				</Col>-->
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
				<template slot-scope="{ row }" slot="resultType">
					<Tag v-if="row.detectResult === 0" color="blue">合格</Tag>
					<Tag v-if="row.detectResult === 1" color="red">不合格</Tag>
				</template>
				<template slot-scope="{ row }" slot="checkStep">
					<Tag v-if="row.checkStep === 0" color="green">检定完成</Tag>
					<Tag v-if="row.checkStep === 1" color="blue">待检定</Tag>
					<Tag v-if="row.checkStep === 2" color="blue">待核验</Tag>
					<Tag v-if="row.checkStep === 3" color="blue">待批准</Tag>
					<Tag v-if="row.checkStep === 4" color="blue">待确认</Tag>
				</template>

				<template slot-scope ="{ row, index }" slot="action">
					<Button  type="primary" size="small" style="margin-right: 5px" @click="modalShow(index)">{{ '编辑' }}</Button>
					<Button  type="info" size="small" style="margin-right: 10px" @click="reState(index)">{{ '提交' }}</Button>
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
				   width="40%"
				   :loading="modalLoading"
				   :mask-closable="false"
				   :closable="false"
				   @on-ok="modalOk"
				   @on-cancel="modalCancel">

				<Form ref="formValidate" :model="entity" :label-width="120" :rules="ruleValidate">
					<FormItem label="检定员：" prop="inspector">
						<Input v-model="entity.inspector" placeholder="请输入检定员"></Input>
					</FormItem>
					<FormItem label="核验员：" prop="verifier">
						<Input v-model="entity.verifier" placeholder="请输入核验员">></Input>
					</FormItem>
					<FormItem label="批准人：">
						<Input v-model="entity.approver" placeholder="请输入批准人">></Input>
					</FormItem>
					<FormItem label="备注：">
						<Input v-model="entity.remark" placeholder="请输入备注">></Input>
					</FormItem>
				</Form>

			</Modal>
		</div>

		<!--		<file-list ref="fileList"/>-->
		<detect-info ref="detectInfo"/>
	</Card>
</template>

<script>
	import { mapState, mapActions } from 'vuex';
	import dayjs from 'dayjs';
	import { deepClone, format, getProjectUrl } from '@/libs/system/commonUtil';
	import {deleteFile, entityRequest, requestHandle} from '@/libs/system/requestUtil';
	import util from "@/libs/util";
	import detectInfo from '@/pages/detect/waitDetect/detectInfo';
	// import fileList from '../../project/projectLaborCompany/file-list'

	export default {
		name: 'laborCompany-laborCompanyInfo',
		components:{ detectInfo },
		data() {
			let _this = this;
			return {
				apiBasePath: 'detectRecords',
				uploadUrl:'',
				headers: {
					'Authorization': 'Bearer ' + util.cookies.get('token')
				},
				uploadInfo: {
					startTime: 0,
					endTime: 0,
					loaded: 0,
					total: 0,
					percent: 0,
					speed: 0,
					status: 'none'
				},
				reportFile:'',
				reviewFile:'',
				addFile:'',
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
					},{
						title: '器具名称',
						key: 'meterName',
						minWidth: 200,
						align: 'center',
						isShow: true
					},
					{
						title: '送检单位',
						key: 'meterCustomer',
						minWidth: 250,
						align: 'center',
						isShow: true
					},
					{
						title: '检测日期',
						key: 'detectTime',
						minWidth: 140,
						align: 'center',
						isShow: true,
						render: function (h, {row,index}) {
							if (row.detectTime == null){
								return h('div','');
							}else {
								return h('div',dayjs(row.detectTime).format('YYYY年M月D日'));
							}
						}
					},
					{
						title: '有效期至',
						key: 'overTime',
						minWidth: 140,
						align: 'center',
						isShow: true,
						render: function (h, {row,index}) {
							if (row.detectTime == null){
								return h('div','');
							}else {
								return h('div',dayjs(row.overTime).format('YYYY年M月D日'));
							}
						}
					},
					// {
					// 	title: '型号规格',
					// 	key: 'sname',
					// 	minWidth: 200,
					// 	align: 'center',
					// 	isShow: true
					// },
					// {
					// 	title: '出厂编号',
					// 	key: 'meterCode',
					// 	minWidth: 100,
					// 	align: 'center',
					// 	isShow: true
					// }, {
					// 	title: '制造单位',
					// 	key: 'meterFactory',
					// 	align: 'center',
					// 	minWidth: 300,
					// 	isShow: true
					// },
					{
						title: '检定状态',
						key: 'checkStep',
						minWidth: 100,
						align: 'center',
						slot: 'checkStep',
						isShow: true
					},
					{
						title: '检定结论',
						key: 'leaderName',
						minWidth: 100,
						align: 'center',
						slot: 'resultType',
						isShow: true
					}, {
						title: '检定原始记录',
						key: 'leaderTel',
						minWidth: 140,
						align: 'center',
						render: function (h, {row,index}) {
							return [h('a', {
								style:{
									marginRight : '20px'
								},
								on: {
									click: (val) => {
										row.status = 'ledger';
										_this.$refs.detectInfo.initData(row);
										_this.$refs.detectInfo.isShowView = true;
									}
								}
							},'查看')]
						},
						isShow: true
					}, {
						title: '结果文件',
						key: 'resultFile',
						minWidth: 120,
						align: 'center',
						render: function (h, {row,index}) {
							return [h('a', {
								on: {
									click: (val) => {
										if (row.resultFile != null){
											window.open(getProjectUrl(row.resultFile));
										}else {
											_this.$Message.error({
												background: true,
												content: '当前仪表未上传结果证书',
												duration: 5
											});
										}
									}
								}
							},'查看')]
						},
						isShow: true
					}, {
						title: '批准人',
						key: 'approver',
						minWidth: 150,
						align: 'center',
						isShow: true
					},  {
						title: '核验员',
						key: 'verifier',
						minWidth: 150,
						align: 'center',
						isShow: true
					},  {
						title: '检定员',
						key: 'inspector',
						minWidth: 150,
						align: 'center',
						isShow: true
					},{
						title: '备注',
						key: 'remark',
						align: 'center',
						minWidth: 200,
						isShow: true
					},
					// {
					// 	title: $t('page.common.action'),
					// 	slot: 'action',
					// 	width: 150,
					// 	fixed: 'right',
					// 	align: 'center',
					// 	isShow: true
					// }
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

				}
			}
		},
		mounted() {
			this.entityQuery.roles = this.info.roles;
			this.entityQuery.checkStep = [0,4];
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
			downLoad(){
				// console.log('download:'+getProjectUrl('2023/03/302383654863110144.jpg'));
				window.open(getProjectUrl(this.reportFile));
			},
			onReportSuccess(res, file) {
				this.entity.reportFile = res.data;
				this.reportFile = file.name;
				this.uploadInfo.percent = 0;
				this.uploadInfo.status = 'finished';
				this.$refs.upload.clearFiles();
				this.$Message.success({
					background: true,
					content: $t('page.info.updateSuccessMessage') + (this.uploadInfo.endTime - this.uploadInfo.startTime) / 1000 + 's',
					duration: 3
				});
			},
			onReviewSuccess(res, file) {
				this.entity.reviewFile = res.data;
				this.reviewFile = file.name;
				this.uploadInfo.percent = 0;
				this.uploadInfo.status = 'finished';
				this.$refs.upload.clearFiles();
				this.$Message.success({
					background: true,
					content: $t('page.info.updateSuccessMessage') + (this.uploadInfo.endTime - this.uploadInfo.startTime) / 1000 + 's',
					duration: 3
				});
			},
			onAddSuccess(res, file) {
				this.uploadInfo.percent = 0;
				this.uploadInfo.status = 'finished';
				this.$refs.upload.clearFiles();
				let conFile = {};
				let cp = this.queryResult.entities[this.selectIndex];
				conFile.type = 4; //劳务分包补充资料
				conFile.fileName = file.name;
				conFile.filePath = res.data;
				conFile.lineId= cp.id;
				conFile.lineName = cp.name;
				conFile.loadUserId = util.cookies.get('uuid');
				conFile.loadUserName = this.info.name;
				// conFile.lineProjectId = util.cookies.get('projectId');

				// entityRequest('insert', 'contractFiles', conFile,
				// 		// 第四个参数, onSuccess
				// 		this.query,
				// 		// 第五个参数, onFinish
				// 		() => {
				// 			this.isShowFileModel = false;
				// 		});
			},
			onFormatError(file) {
				this.$Message.error({
					background: true,
					content: '上传的文件格式不符，仅支持上传doc/docx格式文件',
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
			fileListShow(entity){
				this.$refs.fileList.show(4,entity);
			},
			modalShow(index) {
				this.entity = index < 0 ? {} : deepClone(this.queryResult.entities[index]);
				this.isShowModel = true;
			},
			reState(index){
				this.entity = index < 0 ? {} : deepClone(this.queryResult.entities[index]);//获取更新的企业
				this.entity.state = 1002;         //修改更新状态为正常
				// entityRequest('update', 'laborCompanies', this.entity,
				// 		// 第四个参数, onSuccess
				// 		this.query,null,null,false)
			},
			modalOk() {
				entityRequest('update', 'pgRecords', this.entity,
						(response)=>{
							this.query();
						},
						() => {
							this.isShowModel = false;
						})
			},
			modalCancel() {
				this.isShowModel = false;
			},
			query() {
				this.tableLoading = true;
				// 使用 mysql查询
				entityRequest('page', this.apiBasePath, this.entityQuery,
						(response)=>{
							this.queryResult.entities = response.data.entities;
							this.queryResult.count = response.data.count;
						},
						() => {
							this.tableLoading = false;
						})
				// 按照sql查询
				// entityRequest('page', this.apiBasePath, this.entityQuery,
				// 		// 第四个参数, onSuccess
				// 		(response) => {
				// 			this.queryResult.count = response.data.count;
				// 			console.log(this.entityQuery.pageIndex , this.entityQuery.pageSize)
				// 			let entities = response.data.entities;
				//
				// 			let result = [];
				// 			let index = this.entityQuery.pageIndex;
				// 			let size = this.entityQuery.pageSize;
				//
				// 			for (let i = (index-1) *size; i < (index-1) *size + size && i < entities.length; i++) {
				// 				result.push(entities[i]);
				// 			}
				//
				// 			this.queryResult.entities = result;
				// 		},
				// 		// 第五个参数, onFinish
				// 		() => {
				// 			this.tableLoading = false;
				// 		});
			},
			getProjectUrl
		}
	}
</script>
