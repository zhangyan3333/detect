<template xmlns="http://www.w3.org/1999/html">
	<Modal
			ref="detectInfo"
			title= " "
			v-model="isShowView"
			draggable sticky scrollable :mask="false"
			:loading="addLoading"
			:closable=true
			width="1500px"
			:styles="{top: '20px'}"
	>

		<Form ref="form" :model="entity"  :label-width="200" :label-position="'right'" :rules="ruleValidate">
			<p style="font-size: 30px;text-align: center"> 一般压力表检定记录 </p>
			<FormItem label="单位：MPa" style="margin-left: -6%">
				<Row>
					<Col span="18">
					</Col>
					<Col span="2" style="text-align: right ;padding-right: 10px">检定证书编号：</Col>
					<Col span="4">
						<FormItem prop="birth">
							<Input v-model="entity.evaluateOrgName"></Input>
						</FormItem>
					</Col>
				</Row>
			</FormItem>
		</Form>
<!--		<Table border aria-readonly="true"-->
<!--			   :loading="tableLoading"-->
<!--			   :columns="columns"-->
<!--			   :data="evaluations"-->
<!--			   :key="tableKey"-->
<!--			   :span-method="handleSpan"-->
<!--				height="500"-->
<!--		>-->

<!--		</Table>-->
		<Row class-name="rowElement" style="margin-top: -20px">
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">送检单位</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement"></p> </Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">出厂编号</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">测量范围</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">～</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">准确度等级</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">分度值</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">制造单位</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
		</Row>
		<Row class-name="rowElement">
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">标准器名称</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">数字压力计</p> </Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">标准器编号</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">测量范围</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">0～60</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">准确度等级</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">0.05级</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">工作介质</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">空气</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">检定地点</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
		</Row>
		<Row class-name="rowElement">
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">标准器有效期</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">  年  月  日</p> </Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">检定依据</p></Col>
			<Col span="10" class="ivu-b ivu-text-center"><p class="pElement">JJG52-2013《弹性元件式一般压力表、压力真空表和真空表》</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">温度</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">℃</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">湿度</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">％RH</p></Col>
		</Row>
		<Row style="height: 465px">
			<Col span="14" class="ivu-b ivu-text-center">
				<Row style="height: 100px">
					<Col span="4" class="ivu-b ivu-text-center"><p style="margin-top: 40px;font-size: 16px">标准器压力值</p></Col>
					<Col span="6" class="ivu-b ivu-text-center">
						<Row style="height: 50px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 13px;font-size: 16px">轻敲表壳后被检仪表示值</p></Col>
						</Row>
						<Row style="height: 50px">
							<Col span="12" class="ivu-b ivu-text-center"><p style="margin-top: 13px;font-size: 16px">升压</p></Col>
							<Col span="12" class="ivu-b ivu-text-center"><p style="margin-top: 13px;font-size: 16px">降压</p></Col>
						</Row>
					</Col>
					<Col span="6" class="ivu-b ivu-text-center">
						<Row style="height: 50px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 13px;font-size: 16px">轻敲位移</p></Col>
						</Row>
						<Row style="height: 50px">
							<Col span="12" class="ivu-b ivu-text-center"><p style="margin-top: 13px;font-size: 16px">升压</p></Col>
							<Col span="12" class="ivu-b ivu-text-center"><p style="margin-top: 13px;font-size: 16px">降压</p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center"><p style="margin-top: 40px;font-size: 16px">示值误差</p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p style="margin-top: 40px;font-size: 16px">回程误差</p></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
					<Col span="4" class="ivu-b ivu-text-center"><p class="pElement"></p></Col>
				</Row>
			</Col>
			<Col span="10" class="ivu-b" style="font-size: 16px">
				<Row class-name="resultRow" style="margin-top: 30px"><p style="font-size: 20px;">检定结果：</p></Row>
				<Row class-name="resultRow"><p>1 外观检查：</p></Row>
				<Row class-name="resultRow"><p>2 指针偏转平稳性：</p></Row>
				<Row class-name="resultRow"><p>3 最大示值误差：</p><p style="margin-left: 80px">允许值：±</p></Row>
				<Row class-name="resultRow"><p>4 最大回程误差：</p><p style="margin-left: 80px">允许值： </p></Row>
				<Row class-name="resultRow"><p>5 最大轻敲位移：</p><p style="margin-left: 80px">允许值： </p></Row>
				<Row class-name="resultRow"><p>6 测量上限处：</p></Row>
				<Row class-name="resultRow"><p style="margin-left: 20px">示值误差：</p><p style="margin-left: 110px">允许值：±</p></Row>
				<Row class-name="resultRow"><p style="margin-left: 20px">回程误差：</p><p style="margin-left: 110px">允许值： </p></Row>
				<Row class-name="resultRow"><p style="margin-left: 20px">轻敲位移：</p><p style="margin-left: 110px">允许值： </p></Row>
				<Row class-name="resultRow"><p>7 检定结论：</p><p style="margin-left: 14px">□ 合格 </p><p style="margin-left: 80px">符合 &nbsp;&nbsp;  &nbsp;&nbsp;  级 </p></Row>
				<Row class-name="resultRow"><p style="margin-left: 113px">□ 不合格</p></Row>
			</Col>
		</Row>


		<template #footer>
			<Button type="primary" v-if="index<0" @click="modalOk()">确定</Button>
			<Button type="default" v-if="index<0"  @click="modalCancel()">取消</Button>
		</template>
	</Modal>
</template>

<script>


import util from "@/libs/util";
import {entityRequest} from "@/libs/system/requestUtil";
import {mapState} from "vuex";
import dayjs from "dayjs";

export default {
	name: "evaluations",
	data(){
		let _this = this;
		return{
			apiBasePath: 'evaluations',
			isShowView:false,
			addLoading:true,
			index:-2,
			tableKey:0,
			evaluations:[],
			entity:{
				total:0
			},
			ruleValidate:{
				// evaluateTime: [{ required: true, message: '该项为必填项，请输入内容', trigger: 'blur' }],
			},
			tableLoading: false,
			columns:[
				{
					title: '考核项目',
					key: 'item',
					align:'center',
					width: 250,
					isShow: true
				}, {
					title: '序号',
					key: 'index',
					align:'center',
					width: 60,
					isShow: true
				}, {
					title: '考评内容',
					key: 'content',
					align:'left',
					width: 610,
					isShow: true
				}, {
					title: '标准分',
					key: 'standard',
					align:'center',
					width: 120,
					isShow: true
				}, {
					title: '得分',
					key: 'score',
					align:'center',
					width: 120,
					isShow: true,
					render:(h,params)=>{
						return h('InputNumber', { props: {
															value: _this.evaluations[params.index].score,
															min:0 ,
															max: _this.evaluations[params.index].standard} ,
							on: {'input': (val) => {
								if (parseInt(val) > _this.evaluations[params.index].standard || parseInt(val) < 0){
									_this.evaluations[params.index].score = 0;
									_this.$Message.error({
										background: true,
										content: '输入分值超出范围，请重新输入',
										duration: 5
									});
								}else {
									_this.evaluations[params.index].score = val;
								}
								_this.chargeScore();
							}}})
					}
				}, {
					title: '备注',
					key: 'note',
					align:'center',
					isShow: true,
					render:(h,params)=>{
						return h('Input', { props: {value: _this.evaluations[params.index].note},
							on: {'input': (val) => {
									_this.evaluations[params.index].note = val;
								}}})
					}
				}
			],
			entityQuery: {
				fullSearch: '',
				pageIndex: 1,
				pageSize: 10,
				sortKey: '',
				sortOrder: '',
				purchaseType:-1
			},
			currentProjectId:'',
			projectList:[]
		}
	},
	computed: {
		...mapState('admin/user', [
			'info'
		])
	},
	mounted() {
		this.loadTable();
	},
	methods: {
		loadTable(){
			this.evaluations =[
				{'item':'资源配备（15分）','index': 1, 'content':'现场负责人有较强的管理能力、协调能力和解决问题能力。','standard':'5','score':0,'note':''},
				{'item':'资源配备（15分）','index': 2, 'content':'现场人员结构合理，数量充足，特种作业人员持证上岗。','standard':'5','score':0,'note':''},
				{'item':'资源配备（15分）','index': 3, 'content':'按合同约定合理配置施工机具、提供合格材料。','standard':'5','score':0,'note':''},
				{'item':'工程进度（20分）','index': 4, 'content':'积极配合项目部的各项工作，服从指挥、协调、安排。','standard':'10','score':0,'note':''},
				{'item':'工程进度（20分）','index': 5, 'content':'按时完成关键节点或阶段性计划。','standard':'10','score':0,'note':''},
				{'item':'施工安全（20分）','index': 6, 'content':'人员经过安全培训上岗。','standard':'5','score':0,'note':''},
				{'item':'施工安全（20分）','index': 7, 'content':'按要求配备和使用劳保或安全防护用品。','standard':'5','score':0,'note':''},
				{'item':'施工安全（20分）','index': 8, 'content':'认真执行安全技术规范，落实安全措施，及时整改安全隐患。','standard':'5','score':0,'note':''},
				{'item':'施工安全（20分）','index': 9, 'content':'未发生人员轻伤及以上安全事故或事件。','standard':'5','score':0,'note':''},
				{'item':'工程质量（20分）','index': 10, 'content':'严格按照合同约定的质量标准和技术交底组织施工。','standard':'5','score':0,'note':''},
				{'item':'工程质量（20分）','index': 11, 'content':'配合施工质量随工监督检查，积极整改质量问题。','standard':'5','score':0,'note':''},
				{'item':'工程质量（20分）','index': 12, 'content':'交付工程质量验收合格，无遗留质量问题和返工返修。','standard':'10','score':0,'note':''},
				{'item':'文明施工（10分）','index': 13, 'content':'做好材料、机具的现场保管工作，施工过程中避免损坏、浪费。','standard':'5','score':0,'note':''},
				{'item':'文明施工（10分）','index': 14, 'content':'按照标准文明工地要求做好现场管理。','standard':'5','score':0,'note':''},
				{'item':'综合管理（15分）','index': 15, 'content':'分包企业队伍稳定，与招用人员签订劳动合同，推行实名制管理，按时足额支付农民工工资，无恶意讨薪、聚众闹事。','standard':'5','score':0,'note':''},
				{'item':'综合管理（15分）','index': 16, 'content':'分包补充合同控制在合理范围，无价款结算纠纷。分包企业无坐地起价、超合同约定追索工程款等情况。','standard':'5','score':0,'note':''},
				{'item':'综合管理（15分）','index': 17, 'content':'分包企业诚信自律，无偷工减料、弄虚作假、恶意诉讼和行贿等行为。','standard':'5','score':0,'note':''},
			]
		},
		chargeScore(){
			let sum = 0
			for (let i = 0; i < this.evaluations.length; i++) {
				sum += parseFloat(this.evaluations[i].score);
				// this.tableKey++;
			}
			this.entity.total = parseFloat(sum).toFixed(1);
		},
		handleSpan ({ row, column, rowIndex, columnIndex }) {
			let merge = [1,2,4,6,7,8,10,11,13,15,16];
			if (rowIndex === 0 && columnIndex === 0) {return {rowspan: 3, colspan: 1};}
			else if (rowIndex === 3 && columnIndex === 0) {return {rowspan: 2, colspan: 1};}
			else if (rowIndex === 5 && columnIndex === 0) {return {rowspan: 4, colspan: 1};}
			else if (rowIndex === 9 && columnIndex === 0) {return {rowspan: 3, colspan: 1};}
			else if (rowIndex === 12 && columnIndex === 0) {return {rowspan: 2, colspan: 1};}
			else if (rowIndex === 14 && columnIndex === 0) {return {rowspan: 3, colspan: 1};}
			else if (merge.indexOf(rowIndex) > -1  && columnIndex === 0) {return {rowspan: 0, colspan: 0};}
		},
		loadData(index,info,type){
			this.loadTable();
			this.entity.laborCompanyId= info.laborCompanyName.split(',')[0];
			this.entity.laborCompanyName= info.laborCompanyName.split(',')[1];
			this.entity.projectId = info.projectId;
			this.entity.projectName = info.projectName;
			this.entity.contractName = info.contractCode;
			this.entity.assessor = this.info.name;
			this.entity.createUserId = util.cookies.get('uuid');
			this.entity.createUserName = this.info.name;

			this.entity.name = info.name;
			this.entity.evaluateOrgName = this.info.organizationName;
			this.entity.evaluateOrgId = this.info.organizationId;
		},
		initData(info){
			this.entity={};
			this.entity.laborCompanyName= info.laborCompanyName
			this.entity.projectName = info.projectName;
			this.entity.contractName = info.contractName;
			this.entity.assessor = info.assessor;
			this.entity.evaluateOrgName = info.organizationName;
			this.entity.total = info.total;
			this.entity.evaluateTime = info.evaluateTime;
			this.entity.periodYear = info.periodYear;
			this.entity.periodQuarter = info.periodQuarter;
			this.entity.evaluateOrgName = info.evaluateOrgName;

			let scores = info.scores.split(',');
			let notes = info.notes.split(',');
			for (let i = 0; i < scores.length; i++) {
				this.evaluations[i].score = scores[i];
				this.evaluations[i].note = notes[i]
			}
		},
		modalOk(){
				if (this.entity.evaluateTime != null  && this.entity.evaluateTime != '' ) {
					this.entity.evaluateTime = dayjs(this.entity.evaluateTime).format('YYYY-MM-DD')
					let notes = []; let scores =[]
					for (let i = 0; i < this.evaluations.length; i++) {
						notes[parseInt(this.evaluations[i].index)-1] = this.evaluations[i].note;
						scores[parseInt(this.evaluations[i].index)-1] = this.evaluations[i].score;
					}
					this.entity.scores = scores.join(',');
					this.entity.notes = notes.join(',');
					if (this.entity.total < 60 ) {this.entity.level = '不合格'}
					if (this.entity.total >= 60 && this.entity.total <90) {this.entity.level = '合格'}
					if (this.entity.total > 90) {this.entity.level = '优秀'}


					entityRequest('insert', this.apiBasePath, this.entity,
							// 第四个参数, onSuccess
							this.query,
							// 第五个参数, onFinish
							() => {
								this.isShowView = false;
							});
				} else {
					this.$Message.error({
						background: true,
						content: '请选择考核时间',
						duration: 3
					});
					// 取消按钮的loading状态
					// 因为表单的校验，会导致确定之后，按钮会进入loading状态
					// 所以要在验证不通过的时候取消loading状态
					this.modalLoading = false;
					setTimeout(() => {
						this.modalLoading = true;
					}, 0);
				}



			console.log(this.entity);
		},
		modalCancel(){
			this.isShowView = false;
			this.$refs.form.resetFields();
		},
		changeData(){
			this.tableKey++;
			console.log('click==========================')
		}
	},
	watch: {

	}
}
</script>

<style scoped>
	.rowElement{
		height: 60px;
		margin-top: 1px;
	}
	.pElement{
		margin-top: 20px;
		font-size: 16px;
	}
	.resultRow{
		margin-left: 50px;
		margin-top: 10px;
	}
	.resultRow p{
		font-size: 17px;
	}
</style>
