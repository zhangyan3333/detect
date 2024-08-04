<template xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
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
							<Input v-model="entity.detectCode"></Input>
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
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.meterCustomer" /></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">出厂编号</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.meterCode"/></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">测量范围</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">{{entity.meterRangeL}}～{{entity.meterRangeH}}</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">准确度等级</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.meterResolution"><template #append> 级 </template></Input></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">分度值</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.meterDivisionNo"/></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">制造单位</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input :autosize="{maxRows: 2}" type="textarea" style="margin-top: 3px;font-size: 16px;" v-model="entity.meterFactory"/></Col>
		</Row>
		<Row class-name="rowElement">
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">标准器名称</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.sname"/></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">标准器编号</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.scode"/></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">测量范围</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">{{entity.srangeL}}～{{entity.srangeH}}</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">准确度等级</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.sresolution"><template #append> 级 </template></Input></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">工作介质</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.standardMedium"/></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">检定地点</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input :autosize="{maxRows: 2}" type="textarea" v-model="entity.standardLoc"/></Col>
		</Row>
		<Row class-name="rowElement">
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">标准器有效期</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"> <p class="pElement" v-text="entity.se"/></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">检定依据</p></Col>
			<Col span="10" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.sbasis"/></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">温度</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.stemperature"><template #append>&nbsp;℃ </template> </Input></Col>
			<Col span="2" class="ivu-b ivu-text-center"><p class="pElement">湿度</p></Col>
			<Col span="2" class="ivu-b ivu-text-center"><Input class="pInput" v-model="entity.shumidity"><template #append> ％RH </template> </Input></Col>
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
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info1.spressure"/></Col>
					<Col span="3" class="ivu-b ivu-text-center">
						<Input class="pInput" v-model="info1.strikeUp">
							<template #append>
								<Button icon="ios-search" @click="imageUpShow(info1)"></Button>
							</template>
						</Input>
					</Col>
					<Col span="3" class="ivu-b ivu-text-center">
						<Input class="pInput" v-model="info1.strikeDown">
							<template #append>
								<Button icon="ios-search" @click="imageDownShow(info1)"></Button>
							</template>
						</Input>
					</Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info1.positionUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info1.positionDown"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info1.indicationError"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info1.returnError"/></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info2.spressure"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info2.strikeUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info2.strikeDown"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info2.positionUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info2.positionDown"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info2.indicationError"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info2.returnError"/></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info3.spressure"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info3.strikeUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info3.strikeDown"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info3.positionUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info3.positionDown"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info3.indicationError"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info3.returnError"/></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info4.spressure"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info4.strikeUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info4.strikeDown"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info4.positionUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info4.positionDown"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info4.indicationError"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info4.returnError"/></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info5.spressure"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info5.strikeUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info5.strikeDown"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info5.positionUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info5.positionDown"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info5.indicationError"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info5.returnError"/></Col>
				</Row>
				<Row class-name="rowElement">
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info6.spressure"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info6.strikeUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info6.strikeDown"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info6.positionUp"/></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info6.positionDown"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info6.indicationError"/></Col>
					<Col span="4" class="ivu-b ivu-text-center"><Input class="pInput" v-model="info6.returnError"/></Col>
				</Row>
			</Col>
			<Col span="10" class="ivu-b" style="font-size: 16px">
				<Row class-name="resultRow" style="margin-top: 30px"><p style="font-size: 20px;">检定结果：</p></Row>
				<Row class-name="resultRow"><p>1 外观检查：</p> <Input class="result" v-model="entity.appearance"/></Row>
				<Row class-name="resultRow"><p>2 指针偏转平稳性：</p><Input class="result" v-model="entity.pointer"/></Row>
				<Row class-name="resultRow"><p>3 最大示值误差：</p><Input class="result" v-model="entity.indicationErrorMax"/><p style="margin-left: 80px">允许值：</p>
					<Input class="result" v-model="entity.indicationErrorPermit"><template #prepend><span>±</span></template></Input></Row>
				<Row class-name="resultRow"><p>4 最大回程误差：</p><Input class="result" v-model="entity.returnErrorMax"/>
					<p style="margin-left: 80px">允许值： </p><Input class="result" v-model="entity.returnErrorPermit"/></Row>
				<Row class-name="resultRow"><p>5 最大轻敲位移：</p><Input class="result" v-model="entity.positionMax"/>
					<p style="margin-left: 80px">允许值： </p><Input class="result" v-model="entity.positionPermit"/></Row>
				<Row class-name="resultRow"><p>6 测量上限处：</p></Row>
				<Row class-name="resultRow">
					<p style="margin-left: 20px">示值误差：</p><Input class="result" v-model="entity.indicationErrorUpper"/>
					<p style="margin-left: 110px">允许值：</p><Input class="result" v-model="entity.indicationErrorUpperPermit"><template #prepend><span>±</span></template></Input>
				</Row>
				<Row class-name="resultRow">
					<p style="margin-left: 20px">回程误差：</p><Input class="result" v-model="entity.returnErrorUpper"/>
					<p style="margin-left: 110px">允许值：</p><Input class="result" v-model="entity.returnErrorUpperPermit"/>
				</Row>
				<Row class-name="resultRow">
					<p style="margin-left: 20px">轻敲位移：</p><Input class="result" v-model="entity.positionUpper"/>
					<p style="margin-left: 110px">允许值： </p><Input class="result" v-model="entity.positionUpperPermit"/>
				</Row>
				<Row  style="margin-top: 20px;margin-left: 50px">
					<p style="margin-top: 4px">7 检定结论：</p>
					<RadioGroup v-model="entity.detectResult" type="button">
						<Radio :label="0">合  格</Radio>
						<Radio :label="1">不合格</Radio>
					</RadioGroup>
					<Input style="padding-left: 40px;width: 30%" v-model="entity.detectLevel">
						<template #prepend><span>符合</span></template>
						<template #append><span>级</span></template>
					</Input>
				</Row>
			</Col>
		</Row>

		<Modal
				:title="imageTitl"
				v-model="imageShow"
				:styles="{top: '20px'}"
				footer-hide
		>
			<img width="100%" height="200px" :src="getProjectUrl(imagePath)" />
		</Modal>


		<template #footer>
			<Button type="primary"  @click="modalExport()">导出</Button>
<!--			<Button type="primary" v-if="entity.status == 'ledger'" @click="modalExport()">打印</Button>-->
			<Button type="primary" v-if="entity.status != 'ledger'" @click="modalOk()">保存</Button>
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
		return{
			apiBasePath: 'evaluations',
			isShowView:false,
			imagePath:'',
			addLoading:true,
			imageShow:false,
			imageTitl:'',
			index:-2,
			tableKey:0,
			evaluations:[],
			entity:{
				total:0
			},
			info1: { },
			info2: { },
			info3: { },
			info4: { },
			info5: { },
			info6: { },
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

	},
	methods: {

		chargeScore(){
			let sum = 0
			for (let i = 0; i < this.evaluations.length; i++) {
				sum += parseFloat(this.evaluations[i].score);
				// this.tableKey++;
			}
			this.entity.total = parseFloat(sum).toFixed(1);
		},
		loadData(index,info,type){
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
			this.entity = info;
			this.entity.se = dayjs(info.sEdate).format('YYYY年M月D日');
			let infos = this.entity.infos;
			for (let i = 0; i < infos.length; i++) {
				if (i == 0){ this.info1 = infos[i]}
				if (i == 1){ this.info2 = infos[i]}
				if (i == 2){ this.info3 = infos[i]}
				if (i == 3){ this.info4 = infos[i]}
				if (i == 4){ this.info5 = infos[i]}
				if (i == 5){ this.info6 = infos[i]}
			}
		},
		modalOk(){
			entityRequest('update', 'pgRecords', this.entity,
					(response)=>{
						this.$parent.$parent.query();
					},
					() => {
						this.isShowView = false;
					})

		},
		modalCancel(){
			this.isShowView = false;
			this.$refs.form.resetFields();
		},
		changeData(){
			this.tableKey++;
			console.log('click==========================')
		},
		imageUpShow(info){
			this.imagePath = info.upImage;
			console.log(info.upImage)
			this.imageTitl = '升压点'+ info.index + '图片'
			this.imageShow = true;
		},
		imageDownShow(info){
			this.imagePath = info.downImage;
			this.imageTitl = '降压点'+ info.index + '图片'
			this.imageShow = true;
		},
		getProjectUrl,
		modalExport(){
			exportDetectRecord(this.entity)
					.then(( response)=>{
						window.open(getProjectUrl(response.data));
						this.$Message.success({
							background: true,
							content: '数据导出成功',
							duration: 3
						});
					}).finally(()=>{

					})

		}
	},
	watch: {

	}
}
</script>

<style scoped>
	/deep/ .pInput .ivu-input{
		text-align: center;
	}
	.result{
		width: 20%;
		margin-top: -5px;
	}
	.rowElement{
		height: 60px;
		margin-top: 1px;
	}
	.pElement{
		margin-top: 20px;
		font-size: 16px;
	}
	.pInput{
		margin-top: 15px;
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
