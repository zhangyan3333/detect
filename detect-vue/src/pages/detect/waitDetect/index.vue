<template>
	<Card dis-hover>
		<div>
			<Row style="margin-bottom: 5px;">
				<Col flex="100px">
					<Button type="success" icon="md-add" @click="modalShow(-1)" long>{{ '申请' }}</Button>
				</Col>
				<Col flex="auto">
				</Col>
				<Col style="margin-left: 20px" flex="500px">
					<Input search placeholder='可搜索劳务公司名称或申报单位名称' v-model="entityQuery.fullSearch" @on-search="query" />
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
				<template slot-scope="{ row }" slot="companyLevel">
					<Tag v-if="row.level === 'A'" color="blue">A</Tag>
					<Tag v-if="row.level === 'B'" color="blue">B</Tag>
					<Tag v-if="row.level === 'C'" color="blue">C</Tag>
				</template>
				<!-- state 为企业申请状态 1001申请中 1002申请失败返回申请人 1003申请通过 1004撤销申请 -->
				<template slot-scope="{ row }" slot="state">
					<Tag v-if="row.state === 1001" color="blue">审批中</Tag>
					<Tag v-if="row.state === 1002" color="red">驳回</Tag>
					<Tag v-if="row.state === 1003" color="green">通过</Tag>
					<Tag v-if="row.state === 1004" color="gray">撤回</Tag>
				</template>
				<template slot-scope="{ row }" slot="changeState">
					<Tag v-if="row.changeState === 0" color="green">正常</Tag>
					<Tag v-if="row.changeState === 1" color="blue">审批中</Tag>
					<Tag v-if="row.changeState === 2" color="red">驳回</Tag>
					<Tag v-if="row.changeState === 3" color="gray">撤销</Tag>
				</template>
				<template slot-scope="{ row }" slot="status">
					<Tag v-if="row.status === 0" color="green">正常</Tag>
					<Tag v-if="row.status === 1" color="gray">冻结</Tag>
				</template>
				<template slot-scope ="{ row, index }" slot="action">
					<Button v-if="row.status != '1'" type="primary" size="small" style="margin-right: 5px" @click="modalShow(index)">{{ '编辑' }}</Button>
					<Button v-if="row.status != '1' && row.state == 1001" type="info" size="small" style="margin-right: 10px" @click="reState(index)">{{ '撤回' }}</Button>
					<Button v-if="row.status != '1' && row.changeState == 1" type="info" size="small" style="margin-right: 10px" @click="reChange(index)">{{ '撤回' }}</Button>
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
				   width="65%"
				   :loading="modalLoading"
				   :mask-closable="false"
				   :closable="false"
				   @on-ok="modalOk"
				   @on-cancel="modalCancel">

				<Form ref="formValidate" :model="entity" :label-width="120" :rules="ruleValidate">
					<FormItem label="分包企业名称" prop="name">
						<Input v-model="entity.name" placeholder="请输入分包企业名称"></Input>
					</FormItem>

					<FormItem label="社会信用代码">
						<Row>
							<Col span="9">
								<FormItem prop="code">
									<Input v-model="entity.code" placeholder="请输入统一社会信用代码"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
<!--							<Col span="3" style="text-align: right ;padding-right: 10px">营业执照有效期</Col>-->
<!--							<Col span="11">-->
<!--								<DatePicker v-model="entity.codeTerm" format="yyyy-MM-dd" type="date" placeholder="请选择经营许可证有效期截止日期，如为长期选择2099-01-01" style="width: 100%" />-->
<!--							</Col>-->
						</Row>
					</FormItem>
					<FormItem label="企业法人">
						<Row>
							<Col span="9">
								<FormItem prop="ownerName">
									<Input v-model="entity.ownerName" placeholder="请输入分包企业法人姓名"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">法人身份证</Col>
							<Col span="11">
								<FormItem prop="companyTel">
									<Input v-model="entity.companyTel" placeholder="请输入法人身份证号码"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="企业联系人">
						<Row>
							<Col span="9">
								<FormItem prop="leaderName">
									<Input v-model="entity.leaderName" placeholder="请输入分包企业联系人姓名"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">联系人电话</Col>
							<Col span="11">
								<FormItem prop="leaderTel">
									<Input v-model="entity.leaderTel" placeholder="请输入分包企业联系人电话"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="资质类别" prop="qualificationType">
						<Input v-model="entity.qualificationType" placeholder="请输入资质类别"></Input>
					</FormItem>
					<FormItem label="资质证书编号">
						<Row>
							<Col span="9">
								<FormItem prop="certCode">
									<Input v-model="entity.certCode" placeholder="请输入资质证书编号，如多个以，隔开"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">有效期至</Col>
							<Col span="11">
								<DatePicker v-model="entity.certTerm" multiple  format="yyyy-MM-dd" type="date" placeholder="请选择资质证书有效期截止日期,多个资质证书应对应选择多个日期" style="width: 100%" />
							</Col>
						</Row>
					</FormItem>
					<FormItem label="安全生产许可证">
						<Row>
							<Col span="9">
								<FormItem prop="safetyCode">
									<Input v-model="entity.safetyCode" placeholder="请输入安全生产许可证编号"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">有效期至</Col>
							<Col span="11">
								<DatePicker v-model="entity.safetyTerm" format="yyyy-MM-dd" type="date" placeholder="请选择经营许可证有效期截止日期" style="width: 100%" />
							</Col>
						</Row>
					</FormItem>
					<FormItem label="准入申报材料">
						<Row>
							<Col span="9">
								<FormItem prop="reportFile">
									<Upload  ref="upload"
											 :headers="headers"
											 :show-upload-list="false"
											 :format="['xls','xlsx','doc','docx','pdf','png','jpg']"
											 :on-success="onReportSuccess"
											 :on-format-error="onFormatError"
											 :on-exceeded-size="onExceededSize"
											 :on-progress="onProgress"
											 :action="uploadUrl">
										<Button>点击上传</Button><span style="padding-left: 20px">{{this.reportFile}}</span>
									</Upload>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">准入评审表</Col>
							<Col span="11">
								<FormItem prop="reviewFile">
									<Upload  ref="upload"
											 :headers="headers"
											 :show-upload-list="false"
											 :format="['xls','xlsx','doc','docx','pdf','png','jpg']"
											 :on-success="onReviewSuccess"
											 :on-format-error="onFormatError"
											 :on-exceeded-size="onExceededSize"
											 :on-progress="onProgress"
											 :action="uploadUrl">
										<Button>点击上传</Button><span style="padding-left: 20px">{{this.reviewFile}}</span>
									</Upload>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem v-if="entity.id != null" label="补充资料">
						<Row>
							<Col span = "3">
								<FormItem prop="reportFile">
									<Upload  multiple
											 ref="upload"
											 :headers="headers"
											 :show-upload-list="false"
											 :format="['xls','xlsx','doc','docx','pdf','png','jpg']"
											 :on-success="onAddSuccess"
											 :on-format-error="onFormatError"
											 :on-exceeded-size="onExceededSize"
											 :on-progress="onProgress"
											 :action="uploadUrl">
										<Button>点击上传</Button>
									</Upload>
								</FormItem>
							</Col>
							<Col span="3">
								<Button style="margin-left: 20px" @click="fileListShow(entity)">查看列表</Button>
							</Col>
							<Col span="18"/>
						</Row>
					</FormItem>
					<FormItem label="推荐人">
						<Row>
							<Col span="9">
								<FormItem prop="createUsername">
									<Input disabled v-model="entity.createUsername" placeholder="请输入推荐人姓名"></Input>
								</FormItem>
							</Col>
							<Col span="1"></Col>
							<Col span="3" style="text-align: right ;padding-right: 10px">推荐单位</Col>
							<Col span="11">
								<FormItem prop="createOrgName">
									<Input disabled v-model="entity.createOrgName" placeholder="请输入推荐人单位"></Input>
								</FormItem>
							</Col>
						</Row>
					</FormItem>
					<FormItem label="备注" prop="remark">
						<Input v-model="entity.remark" ></Input>
					</FormItem>
					<FormItem v-if="entity.permitAdvice != '' && entity.permitAdvice != undefined" label="审批意见">
						<Input disabled v-model="entity.permitInfo"></Input>
					</FormItem>
				</Form>

			</Modal>
		</div>

		<file-list ref="fileList"/>
	</Card>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import dayjs from 'dayjs';
import { deepClone, format, getProjectUrl } from '@/libs/system/commonUtil';
import {deleteFile, entityRequest, requestHandle} from '@/libs/system/requestUtil';
import { organizationToNodeMapping, entityTreeToNodeTree } from '@/libs/system/treeUtil';
import util from "@/libs/util";
import {UpdateToken} from "@api/account";
// import fileList from '../../project/projectLaborCompany/file-list'

export default {
	name: 'laborCompany-laborCompanyInfo',
	components:{ fileList },
	data() {
		let _this = this;
		return {
			apiBasePath: 'laborCompanies',
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
				}, {
					title: '分包企业名称',
					key: 'name',
					minWidth: 300,
					isShow: true
				},{
					title: '申报单位',
					key: 'createOrgName',
					minWidth: 200,
					isShow: true
				},{
					title: '统一社会信用代码',
					key: 'code',
					minWidth: 200,
					isShow: true
				},
				// {
				// 	title: '营业执照有效期',
				// 	key: 'codeTerm',
				// 	minWidth: 140,
				// 	isShow: true
				// },
				{
					title: '公司法人',
					key: 'ownerName',
					minWidth: 100,
					isShow: true
				}, {
					title: '法人身份证',
					key: 'companyTel',
					minWidth: 160,
					isShow: true
				},
				{
					title: '联系人',
					key: 'leaderName',
					minWidth: 100,
					isShow: true
				}, {
					title: '联系方式',
					key: 'leaderTel',
					minWidth: 160,
					isShow: true
				}, {
					title: '资质类别',
					key: 'qualificationType',
					minWidth: 400,
					isShow: true
				}, {
					title: '资质证书编号',
					key: 'certCode',
					minWidth: 150,
					isShow: true
				},  {
					title: '资质证书有效期',
					key: 'certTerm',
					minWidth: 150,
					isShow: true
				},  {
					title: '安全许可证编号',
					key: 'safetyCode',
					minWidth: 150,
					isShow: true
				},  {
					title: '安全许可证有效期',
					key: 'safetyTerm',
					minWidth: 150,
					isShow: true
				},{
					title: '准入申报材料',
					key: 'reportFile',
					align: 'center',
					minWidth: 140,
					isShow: true,
					render: function (h, {row,index}) {
						// console.log(row.reportFile)
						return [h('a', {
							on: {
								click: (val) => {
									if (row.reportFile != null){
										window.open(getProjectUrl(row.reportFile));
									}else {
										_this.$Message.error({
											background: true,
											content: '当前公司未上传准入申报材料',
											duration: 5
										});
									}

								}
							}
						},'查看')]
					}
				}, {
					title: '准入评审材料',
					key: 'reviewFile',
					align: 'center',
					minWidth: 140,
					isShow: true,
					render: function (h, {row,index}) {
						return [h('a', {
							on: {
								click: (val) => {
									if (row.reviewFile != null){
										window.open(getProjectUrl(row.reviewFile));
									}else {
										_this.$Message.error({
											background: true,
											content: '当前公司未上传准入评审材料',
											duration: 5
										});
									}
								}
							}
						},'查看')]
					}
				}, {
					title: '补充资料',
					key: 'addFile',
					align: 'center',
					minWidth: 140,
					isShow: true,
					render: function (h, {row,index}) {
						return [h('a', {
							on: {
								click: (val) => {
									_this.$refs.fileList.show(4,row);
								}
							}
						},'查看')]
					}
				},{
					title: '公司评级',
					align: 'center',
					slot: 'companyLevel',
					minWidth: 100,
					isShow: true
				},{
					title: '企业状态',
					key: 'status',
					slot: 'status',
					minWidth: 100,
					isShow: true
				},{
					title: '准入状态',
					key: 'state',
					slot: 'state',
					minWidth: 100,
					isShow: true
				},{
					title: '更新状态',
					key: 'changeState',
					slot: 'changeState',
					minWidth: 100,
					isShow: true
				},{
					title: '待审批人员',
					key: 'handleUserName',
					align: 'center',
					minWidth: 200,
					isShow: true
				},{
					title: '审批意见',
					key: 'permitAdvice',
					align: 'center',
					minWidth: 200,
					isShow: true
				},{
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
				name: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				createOrgName: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				code: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				codeTerm: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				ownerName: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				qualificationType: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				companyTel: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				leaderName: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				leaderTel: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				certCode: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				safetyCode: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				certTerm: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
				safetyTerm: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
			},
			data: {
				name: '',
				status1: '',
				count: null,
				date: '',
				status2: '',
				status3: ''
			},
		}
	},
	mounted() {
		this.entityQuery.roles = this.info.roles;
		this.entityQuery.showAll = 1;
		this.entityQuery.type = 0 ;
		this.entityQuery.status = -1 ;
		this.entityQuery.state = -1 ;
		this.entityQuery.organizationId = this.info.organizationId;
		this.entityQuery.sortOrder = 'desc';
		this.entityQuery.sortKey = 'state';
		this.query();
		requestHandle(UpdateToken(), () => {
			this.uploadUrl = getProjectUrl('api/basicfile');
		});
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

			entityRequest('insert', 'contractFiles', conFile,
					// 第四个参数, onSuccess
					this.query,
					// 第五个参数, onFinish
					() => {
						this.isShowFileModel = false;
					});
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
			this.reportFile = '';
			this.reviewFile = '';
			this.addFile = '';
			this.selectIndex = index;
			this.entity = index < 0 ? {} : deepClone(this.queryResult.entities[index]);
			this.entity.permitInfo = this.entity.permitUserName +':' + this.entity.permitAdvice;
			if( this.entity.state == 1001 ){
				this.$Message.error({
					background: true,
					content: '当前公司正在进行准入审批，请等待审批完成后再进行操作',
					duration: 5
				});
			}else {
				if (index < 0){  // 初始化添加填报信息
					this.entity.createUserid = util.cookies.get('uuid');
					this.entity.createUsername = this.info.name;
					this.entity.createOrgName = this.info.organizationName;
					this.entity.createOrgId = this.info.organizationId;
					this.entity.type = 0;  //劳务分包企业
					this.entity.status = 0;  //劳务分包企业现状  0正常 1冻结
					//state 为企业申请状态 1001申请中 1002申请失败返回申请人 1003申请通过 1004撤销申请
					this.entity.state = 1001;  //进入提交状态
				}
				this.isShowModel = true;
				// setTimeout(() => {
				// 	this.$refs.formValidate.resetFields();
				// }, 10);

			}
		},
		reState(index){
			this.entity = index < 0 ? {} : deepClone(this.queryResult.entities[index]);//获取更新的企业
			this.entity.state = 1002;         //修改更新状态为正常
			entityRequest('update', 'laborCompanies', this.entity,
					// 第四个参数, onSuccess
					this.query,null,null,false)
		},
		reChange(index){
			this.entity = index < 0 ? {} : deepClone(this.queryResult.entities[index]);
			this.entity.changeState = 3;
			entityRequest('update', 'laborCompanies', this.entity,
					// 第四个参数, onSuccess
					this.query,null,null,false)
		},
		modalOk() {
			entityRequest('get','flowUsers',null,
					(response)=>{
						if (response.data.length > 0){
							let flowUsers = response.data;
							for (let i = 0; i < flowUsers.length; i++) {
								if (flowUsers[i].type == '1'){
									this.entity.handleUserId = flowUsers[i].handleUserId;
									this.entity.handleUserName = flowUsers[i].handleUserName;
								}
							}
						}
					},
					()=>{
						this.$refs.formValidate.validate((valid) => {
							if (valid) {
								this.$Modal.confirm({
									title: '提交审批',
									content: '点击确定后将提交审批',
									onOk: () => {
										this.entity.changeAdvice = '';
										// 营业执照有效期不需要输入，改为默认
										// this.entity.codeTerm = dayjs(this.entity.codeTerm).format('YYYY-MM-DD');
										this.entity.codeTerm = '2099-01-01';
										let certTimes = '';
										if (this.entity.certTerm.length > 0){
											for (let i = 0; i < this.entity.certTerm.length; i++) {
												if (i == this.entity.certTerm.length - 1){
													certTimes += dayjs(this.entity.certTerm[i]).format('YYYY-MM-DD');
												}else {
													certTimes += dayjs(this.entity.certTerm[i]).format('YYYY-MM-DD')+',';
												}
											}
										}
										this.entity.certTerm = certTimes;
										this.entity.safetyTerm = dayjs(this.entity.safetyTerm).format('YYYY-MM-DD');
										// this.entity.ownerName = '0'; //删除信息
										// this.entity.companyTel = '0';//删除信息


										let id = this.entity.id;

										entityRequest('get','flowUsers',null,)

										if (this.selectIndex < 0 || this.entity.state == 1002 ){
											if (this.entity.state == 1002){ // 如果之前被驳回，现在重新审批
												this.entity.state = 1001;
												// this.entity.handleUserId = this.entity.handleUserId;
												// this.entity.handleUserName = $t('config.companyCheck.checkUserName');
												entityRequest('update', this.apiBasePath, this.entity,
														// 第四个参数, onSuccess
														this.query,
														// 第五个参数, onFinish
														() => {
															this.isShowModel = false;
														});
											}else {
												this.entity.state = 1001;
												// this.entity.handleUserId = $t('config.companyCheck.checkUserId');
												// this.entity.handleUserName = $t('config.companyCheck.checkUserName');
												entityRequest('insert', this.apiBasePath, this.entity,
														// 第四个参数, onSuccess
														this.query,
														// 第五个参数, onFinish
														() => {
															this.isShowModel = false;
														},()=>{

														});
											}
										}else {
											this.entity.state = 1001;
											this.entity.lineId = id;
											this.entity.id = new Date().getTime();
											this.entity.modifyUsername = this.info.name;
											this.entity.modifyDate = dayjs(new Date()).format('YYYY-MM-DD');

											entityRequest('insert', 'laborCompanyChanges', this.entity,
													// 第四个参数, onSuccess
													this.query,
													// 第五个参数, onFinish
													() => {
														this.isShowModel = false;
													});
										}
									},
									onCancel:()=>{
										// 取消按钮的loading状态
										// 因为表单的校验，会导致确定之后，按钮会进入loading状态
										// 所以要在验证不通过的时候取消loading状态
										this.modalLoading = false;
										setTimeout(() => {
											this.modalLoading = true;
										}, 0);
									}
								})
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
					}
			)
		},
		modalCancel() {
			this.isShowModel = false;
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
