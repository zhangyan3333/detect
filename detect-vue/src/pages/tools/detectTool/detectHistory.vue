<template xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
	<Modal
			ref="detectHistory"
			title= "检定历史记录"
			v-model="isShowView"
			draggable sticky scrollable :mask="false"
			:loading="addLoading"
			:closable=true
			width="1200px"
			:styles="{top: '20px'}"
	>

		<Table ref="dataTable"
			   border
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
		</Table>


		<template #footer>
			<Button type="default" @click="modalCancel()">取消</Button>
		</template>

	</Modal>
</template>

<script>


import util from "@/libs/util";
import { entityRequest } from "@/libs/system/requestUtil";
import { getProjectUrl } from '@/libs/system/commonUtil';
import { exportDetectRecord } from '@api/detect';
import {mapState} from "vuex";
import dayjs from "dayjs";

export default {
	name: "evaluations",
	data(){
		let _this = this;
		return {
			apiBasePath: 'detectRecords',
			isShowView:false,
			addLoading:false,
			tableLoading: false,
			modalLoading: true,
			isShowModel: false,
			queryResult: {
				count: 0,
				entities: []
			},
			ruleValidate: {

			},
			access:[],
			entityQuery: {
				fullSearch: '',
				pageIndex: 1,
				pageSize: 10,
				sortKey: '',
				sortOrder: ''
			},
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
					title: '检测时间',
					key: 'createTime',
					minWidth: 200,
					align: 'center',
					isShow: true,
					render: function (h, {row,index}) {
						return h('div',dayjs(row.createTime).format('YYYY年M月D日'));

					}
				},
				{
					title: '型号规格',
					key: 'meterType',
					minWidth: 200,
					align: 'center',
					isShow: true
				},
				{
					title: '送检单位',
					key: 'meterCustomer',
					minWidth: 300,
					align: 'center',
					isShow: true
				},
				{
					title: '出厂编号',
					key: 'meterCode',
					minWidth: 100,
					align: 'center',
					isShow: true
				}, {
					title: '制造单位',
					key: 'meterFactory',
					align: 'center',
					minWidth: 300,
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
					minWidth: 200,
					align: 'center',
					render: function (h, {row,index}) {
						return [h('a', {
							style:{
								marginRight : '20px'
							},
							on: {
								click: (val) => {
									_this.$refs.detectInfo.initData(row);
									_this.$refs.detectInfo.isShowView = true;
								}
							}
						},'查看')
							// 	, h('a', {
							// 	on: {
							// 		click: (val) => {
							// 			_this.entityQuery.fullSearch = "print";
							// 			printWord(_this.entityQuery).then(()=>{console.log('成功')});
							// 		}
							// 	}
							// },'     打印')
						]
					},
					isShow: true
				}, {
					title: '结果文件',
					key: 'qualificationType',
					minWidth: 200,
					align: 'center',
					render: function (h, {row,index}) {
						return [h('a', {
							style:{
								marginRight : '20px'
							},
							on: {
								click: (val) => {
									if (row.standardToolId != null){
										if (row.resultFile != null && row.resultFile != ''){
											window.open(getProjectUrl(row.resultFile));
										}else {
											_this.$refs.detectResult.initData(row);
										}
									}else {
										_this.$Message.error({
											background: true,
											content: '该仪表还未配置标准器，请在【仪表管理】-【被检管理】中完成配置',
											duration: 5
										});
									}

								}
							}
						},'查看'), h('a', {
							style:{
								marginRight : '20px'
							},
							on: {
								click: (val) => {
									_this.isShowUp = true;
									_this.entity = _this.queryResult.entities[index];
								}
							}
						},'上传')						]
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
				}
				// ,{
				// 	title: $t('page.common.action'),
				// 	slot: 'action',
				// 	width: 150,
				// 	fixed: 'right',
				// 	align: 'center',
				// 	isShow: true
				// }
			],
		}
	},
	computed: {
		...mapState('admin/user', [
			'info'
		]),
		displayColumns() {
			return this.columns.filter(column => column.isShow);
		}
	},
	mounted() {
		this.entityQuery.checkStep = [0,4];
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
		fileListShow(entity){
			this.$refs.fileList.show(4,entity);
		},
		initData(row){
			console.log(row)
			this.entityQuery.meterId = row.id;
			this.query();
		},
		modalCancel(){
			this.isShowView = false;
		},
		query() {
			this.tableLoading = true;
			entityRequest('page', this.apiBasePath, this.entityQuery,
					(response)=>{
						this.queryResult.entities = response.data.entities;
						this.queryResult.count = response.data.count;
					},
					() => {
						this.tableLoading = false;
					})
		},
	},
	watch: {

	}
}
</script>
