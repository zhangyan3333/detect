<template xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
	<Modal
			ref="detectResult"
			title= " "
			v-model="isShowView"
			draggable sticky scrollable :mask="false"
			:loading="addLoading"
			:closable=true
			width="1500px"
			class-name="result-modal"
			:styles="{top: '20px'}"
			transfer
	>
		<div style="max-height:700px;overflow-y:auto;overflow-x:hidden;">
			<p style="text-align: center;font-size: 40px;" >中国铁路北京局集团有限公司计量管理所</p>
			<p style="text-align: center;font-size: 50px;margin-top: 30px" v-if="entity.detectResult == 0">检 定 证 书</p>
			<p style="text-align: center;font-size: 50px;margin-top: 30px" v-if="entity.detectResult == 1">检定结果通知书</p>
			<div class="divList">
				<p class="pElement" style="font-size: 25px">证书编号：</p>
				<Input class="pInput" style="font-size: 25px;width: 10%" v-model="entity.detectCode"/>
				<p class="pElement" style="font-size: 25px;margin-left: 20px">号</p>
			</div>
			<div class="divList" >
				<p class="pElement" style="font-size: 25px">送  检  单  位 ：</p>
				<Input class="pInput" style="font-size: 25px;width: 40%" v-model="entity.meterCustomer"/>
			</div>
			<div class="divList" >
				<p class="pElement" style="font-size: 25px">器 具 名 称 ：</p>
				<Input class="pInput" style="font-size: 25px;width: 40%" v-model="entity.meterName"/>
			</div>
			<div class="divList" >
				<p class="pElement" style="font-size: 25px">型 号/规 格 ：</p>
				<Input class="pInput" style="font-size: 25px;width: 40%" v-model="entity.meterType"/>
			</div>
			<div class="divList" >
				<p class="pElement" style="font-size: 25px">出  厂  编  号 ： </p>
				<Input class="pInput" style="font-size: 25px;width: 40%" v-model="entity.meterCode"/>
			</div>
			<div class="divList" >
				<p class="pElement" style="font-size: 25px">制  造  单  位 ： </p>
				<Input class="pInput" style="font-size: 25px;width: 40%" v-model="entity.meterFactory"/>
			</div>
			<div class="divList" >
				<p class="pElement" style="font-size: 25px">检  定  依  据 ： </p>
				<Input class="pInput" style="font-size: 25px;width: 40%" v-model="entity.sbasis"/>
			</div>
			<div class="divList" >
				<p class="pElement" style="font-size: 25px">检  定  结  论 ：  </p>
				<Input class="pInput" disabled style="font-size: 25px;width: 40%" v-if="entity.detectResult ==0 " value="合 格"/>
				<Input class="pInput" disabled style="font-size: 25px;width: 40%" v-if="entity.detectResult ==1 " value="不 合 格"/>
			</div>


			<div class="divList" >
				<p style="font-size: 25px;margin-left: 480px">批  准  人 ： </p>
				<Input style="font-size: 25px;width: 20%" v-model="entity.approver"/>
			</div>
			<div class="divList" >
				<p  style="font-size: 25px">（检定专用章）  </p>
				<p  style="font-size: 25px;margin-left: 300px">核  验  员 ： </p>
				<Input  style="font-size: 25px;width: 20%" v-model="entity.verifier"/>
			</div>
			<div class="divList" >
				<p  style="font-size: 25px;margin-left: 480px">检  定  员 ： </p>
				<Input  style="font-size: 25px;width: 20%" v-model="entity.inspector"/>
			</div>

			<div class="divList" >
				<p class="pElement" style="font-size: 25px">检  定  日  期 ： </p>
				<DatePicker v-model="detectTime" format="yyyy年MM月dd日" type="date"  style="width: 30%;margin-top: 20px" />
<!--				<Input class="pInput" style="font-size: 25px;width: 30%" v-model="startDate" @click="showDateSelect(index)"/>-->
			</div>

			<div class="divList" v-if="entity.detectResult == 0">
				<p class="pElement" style="font-size: 25px">有  效  期  至 ： </p>
				<DatePicker v-model="overTime" format="yyyy年MM月dd日" type="date" style="width: 30%;margin-top: 20px" />
				<!--				<Input class="pInput" style="font-size: 25px;width: 30%" v-model="endDate" />-->
			</div>

			<Divider dashed />
			<Row  style="justify-content: center">
				<Col class="bottomInfo" span="4"></Col>
				<Col class="bottomInfo" span="2">单位地址：</Col>
				<Col class="bottomInfo" span="8">北京市丰台区金家村290号</Col>
				<Col class="bottomInfo" span="2">邮政编码：</Col>
				<Col class="bottomInfo" span="4">100036</Col>
				<Col class="bottomInfo" span="4"></Col>
			</Row>

			<Row style="justify-content: center">
				<Col class="bottomInfo" span="4"></Col>
				<Col class="bottomInfo" span="2">电话/传真：</Col>
				<Col class="bottomInfo" span="8">(010) 51825804；(021)25804(路电)</Col>
				<Col class="bottomInfo" span="2">电子邮箱：</Col>
				<Col class="bottomInfo" span="4">bjjjls@163.com</Col>
				<Col class="bottomInfo" span="4"></Col>
			</Row>

			<p style="text-align: center;font-size: 40px;margin-top: 60px" >中国铁路北京局集团有限公司计量管理所</p>
			<p style="text-align: center;font-size: 40px;margin-top: -40px">_____________________________________________________</p>

			<div class="divList">
				<p class="pElement" style="font-size: 25px">证书编号：</p>
				<Input class="pInput" style="font-size: 25px;width: 10%" v-model="entity.detectCode"/>
				<p class="pElement" style="font-size: 25px;margin-left: 20px">号</p>
				<p class="pElement" style="font-size: 25px;margin-left: 500px">第 2 页共 2 页</p>
			</div>
			<div style="width: 80%;margin-left: 10%;margin-top: 20px">
				<Row class="rowElement">
					<Col span="24" class="ivu-b ivu-text-left"><p class="pElement" style="font-size: 20px;margin-left: -17%">溯源性说明：本证书中的检定结果均可溯源至国际单位制（SI）单位或社会公用计量标准</p></Col>
				</Row>

				<Row class="rowElement">
					<Col span="6" class="ivu-b ivu-text-center"><p class="pElement">检 定 依 据</p></Col>
					<Col span="18" class="ivu-b ivu-text-center"><Input class="pInputx" v-model="entity.sbasis" /></Col>
				</Row>

				<Row class="rowElement">
					<Col span="6" class="ivu-b ivu-text-center"><p class="pElement">检 定 地 点</p></Col>
					<Col span="18" class="ivu-b ivu-text-center"><Input class="pInputx" v-model="entity.standardLoc" /></Col>
				</Row>

				<Row class="rowElement">
					<Col span="6" class="ivu-b ivu-text-center"><p class="pElement">环 境 条 件</p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement">温度</p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInputx" v-model="entity.stemperature"><template #append><span>℃</span></template></Input></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement">湿度</p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><Input class="pInputx" v-model="entity.shumidity"><template #append><span>%RH</span></template></Input></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement">其他</p></Col>
					<Col span="3" class="ivu-b ivu-text-center"><p class="pElement">/</p></Col>
				</Row>

				<Row style="height: 200px">
					<Col span="4" class="ivu-b ivu-text-center"><p style="margin-top: 40px;font-size: 20px">
						检定<br>
						使用<br>
						计量<br>
						标准</p></Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">名  称</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 10%;font-size: 20px">{{standardTool.sname}} </p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">测量范围</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="font-size: 18px">{{standardTool.srange}} </p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20px;font-size: 20px">不确定度/准确度等级/最大允许误差</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">{{standardTool.sresolution}}级</p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">证书编号</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 10%;font-size: 18px">{{standardTool.sregulateCode}}</p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">有效期至</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 18px">{{standardTool.sedate}}</p></Col>
						</Row>
					</Col>
				</Row>

				<Row style="height: 200px">
					<Col span="4" class="ivu-b ivu-text-center"><p style="margin-top: 40px;font-size: 20px">
						检定<br>
						使用<br>
						主标<br>
						准器</p></Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">名  称</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 10%;font-size: 20px">{{standardTool.mname}} </p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">测量范围</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">{{standardTool.mrange}}</p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20px;font-size: 20px">不确定度/准确度等级/最大允许误差</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">{{standardTool.sresolution}} 级</p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">溯源机构及证书编号</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 10%;font-size: 18px">{{standardTool.sfactory + ' ' +standardTool.sregulateBcode}}</p></Col>
						</Row>
					</Col>
					<Col span="4" class="ivu-b ivu-text-center">
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 20px">有效期至</p></Col>
						</Row>
						<Row style="height: 100px">
							<Col span="24" class="ivu-b ivu-text-center"><p style="margin-top: 20%;font-size: 18px">{{standardTool.sbdate}}</p></Col>
						</Row>
					</Col>
				</Row>


				<p style="text-align: center;font-size: 30px;margin-top:10px" >检    定    结    果</p>
				<Row v-if="entity.detectResult == 0" style="height: 465px">
					<Col span="24" class="ivu-b ivu-text-center">
						<Row class="rowElement">
							<Col span="3" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">序号</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">检定项目</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">最大允许误差</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">检定结果</p></Col>
						</Row>
						<Row class="rowElement">
							<Col span="3" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">1</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">外观</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">／</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">合格</p></Col>
						</Row>
						<Row class="rowElement">
							<Col span="3" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">2</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">零位误差</p></Col>
							<Col span="7" class="ivu-b ivu-text-center">
								<Input class="pInput" v-model="entity.zeroErrorMax">
									<template #append><span>﹪FS</span></template>
								</Input>
							</Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">紧靠止销</p></Col>
						</Row>
						<Row class="rowElement">
							<Col span="3" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">3</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">示值误差</p></Col>
							<Col span="7" class="ivu-b ivu-text-center">
								<Input class="pInput" v-model="entity.indicationErrorMax">
									<template #append><span>﹪FS</span></template>
								</Input>
							</Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">合格</p></Col>
						</Row>
						<Row class="rowElement">
							<Col span="3" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">4</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">回程误差</p></Col>
							<Col span="7" class="ivu-b ivu-text-center">
								<Input class="pInput" v-model="entity.returnErrorMax">
									<template #append><span>﹪FS</span></template>
								</Input>
							</Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">合格</p></Col>
						</Row>
						<Row class="rowElement">
							<Col span="3" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">5</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">轻敲位移</p></Col>
							<Col span="7" class="ivu-b ivu-text-center">
								<Input class="pInput" v-model="entity.positionMax">
									<template #append><span>﹪FS</span></template>
								</Input>
							</Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">合格</p></Col>
						</Row>
						<Row class="rowElement">
							<Col span="3" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">6</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">指针偏转平稳性</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">／</p></Col>
							<Col span="7" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">合格</p></Col>
						</Row>
					</Col>
				</Row>
				<Row v-if="entity.detectResult == 1" style="height: 430px">
					<Col span="17" class="ivu-b ivu-text-center">
						<Row class="rowElement">
							<Col span="4" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">序号</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">检定项目</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">最大允许误差</p></Col>
						</Row>
						<Row class="rowElement">
							<Col span="4" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">1</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">外观</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">／</p></Col>
						</Row>
						<Row class="rowElement">
							<Col span="4" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">2</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">零位误差</p></Col>
							<Col span="10" class="ivu-b ivu-text-center">
								<Input class="pInput" v-model="entity.zeroErrorMax">
									<template #append><span>﹪FS</span></template>
								</Input>
							</Col>
						</Row>
						<Row class="rowElement">
							<Col span="4" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">3</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">示值误差</p></Col>
							<Col span="10" class="ivu-b ivu-text-center">
								<Input class="pInput" v-model="entity.indicationErrorMax">
									<template #append><span>﹪FS</span></template>
								</Input>
							</Col>
						</Row>
						<Row class="rowElement">
							<Col span="4" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">4</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">回程误差</p></Col>
							<Col span="10" class="ivu-b ivu-text-center">
								<Input class="pInput" v-model="entity.returnErrorMax">
									<template #append><span>﹪FS</span></template>
								</Input>
							</Col>
						</Row>
						<Row class="rowElement">
							<Col span="4" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">5</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">轻敲位移</p></Col>
							<Col span="10" class="ivu-b ivu-text-center">
								<Input class="pInput" v-model="entity.positionMax">
									<template #append><span>﹪FS</span></template>
								</Input>
							</Col>
						</Row>
						<Row class="rowElement">
							<Col span="4" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">6</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">指针偏转平稳性</p></Col>
							<Col span="10" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">／</p></Col>
						</Row>
					</Col>
					<Col span="7" class="ivu-b ivu-text-center">
						<Row class="rowElement">
							<Col span="24" class="ivu-b ivu-text-center"><p style="font-size: 20px;margin-top: 20px">不合格项目</p></Col>
						</Row>
						<Col span="24" class="ivu-b ivu-text-center"><Input :autosize="{minRows: 13, maxRows: 13}" type="textarea" v-model="entity.UnqualifiedItem"/></Col>
					</Col>
				</Row>
				<Row class="rowElement">
					<Col span="24" class="ivu-b ivu-text-center"><p class="pElement">-----以下空白-----</p></Col>
				</Row>
				<p style="text-align: center;font-size: 20px;margin-top: 20px" >注：1. 本证书出具的检定结果仅对本次检定过程有效；</p>
				<p style="text-align: center;font-size: 20px;margin-top: 10px" >    2. 未经本单位授权，部分使用本证书无效。</p>
			</div>
		</div>
		<template #footer>
			<Button type="primary"  @click="modalExport()">导出</Button>
			<Button type="primary" v-if="entity.status == 'ledger'" @click="modalExport()">打印</Button>
			<Button type="primary" v-if="entity.status != 'ledger'" @click="modalOk()">保存</Button>
			<Button type="default" @click="modalCancel()">取消</Button>
		</template>

	</Modal>
</template>

<script>


import util from "@/libs/util";
import {entityRequest} from "@/libs/system/requestUtil";
import { getProjectUrl } from '@/libs/system/commonUtil';
import {mapState} from "vuex";
import dayjs from "dayjs";
import { exportDetectResult } from '@api/detect';

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
			startDate:'',
			endDate:'',
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
			projectList:[],
			currentIndex:1,
			standardTool:{},
			detectTime:'',
			overTime:'',
			type:''
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
			this.currentIndex = info.detectResult + 1;

			//查询Pgcertification中是否有，有就返回复制entity，没有就使用info来赋值entity，保存后生成certificate，然后修改可以导出。
			if (info.resultId == 0){
				this.type = 'add';
				this.entity = info;
				this.entity.recordId = info.id;
				this.detectTime = dayjs(info.createTime).format('YYYY 年 M 月 D 日')
				this.overTime = dayjs(info.createTime).add(6, 'month').format('YYYY 年 M 月 D 日')
			}else {
				this.type = 'update';

				entityRequest('get','pgCertificates',info.resultId,
						(response)=>{
							this.entity = response.data;
							this.detectTime = response.data.detectTime==null?null:new Date(response.data.detectTime);
							this.overTime = response.data.overTime==null?null:new Date(response.data.overTime);
						},()=>{
							this.entity.resultType = info.resultType;
						},null,false)
			}



			entityRequest('get','standardTools',info.standardToolId,
					(response)=>{
						this.standardTool =response.data;
					},null,null,false)

			this.isShowView = true;
		},
		modalOk(){
			this.entity.detectTime = this.detectTime;
			this.entity.overTime = this.overTime;
			if (this.type == 'add'){
				entityRequest('insert', 'pgCertificates', this.entity,
						(response)=>{
							this.$parent.$parent.query();
						},
						() => {
							this.isShowView = false;
						})
			}else {
				entityRequest('update', 'pgCertificates', this.entity,
						(response)=>{
							this.$parent.$parent.query();
						},
						() => {
							this.isShowView = false;
						})
			}


			// 判断 PgRecord 的 checkStep 属性，如果是3，则进行结果文件创建
			// 如果是4 则进行结果文件更新
			// let resultType = this.entity.checkStep;
			// if ( resultType == 3){
			// 	this.$Message.success({
			// 		background: true,
			// 		content: '结果文件已保存',
			// 		duration: 5
			// 	});
			// 	// this.entity.checkStep = 4;
			// }else if ( resultType == 4 ){
			//
			// 	this.$Message.success({
			// 		background: true,
			// 		content: '结果文件已更新',
			// 		duration: 5
			// 	});
			// }else {
			// 	this.$Message.error({
			// 		background: true,
			// 		content: '审批还未结束，无法创建结果文件',
			// 		duration: 5
			// 	});
			// }


		},
		modalCancel(){
			this.isShowView = false;
			// this.$refs.form.resetFields();
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
			this.entity.exportState = this.currentIndex;
			this.entity.recordId = this.entity.id;
			this.entity.resultId = this.entity.resultId==null?0:this.entity.resultId;
			this.entity.certCode = this.entity.detectCode;
			this.entity.detectTime = new Date(this.entity.detectTime);
			exportDetectResult(this.entity)
					.then(( response)=>{
						console.log(response)
						if (response.code == 0){
							window.open(getProjectUrl(response.data));
						}else {
							this.$Message.error({
								background: true,
								content: response.message,
								duration: 3
							});
						}
					}).finally(()=>{

					})
		}
	},
	watch: {

	}
}
</script>

<style scoped>
	/deep/  .ivu-input{
		text-align: center;
		font-size: 18px;
	}
	/*/deep/.ivu-modal-content{*/
	/*	height: 800px;*/
	/*	overflow-y: auto;*/
	/*}*/
	.pInputx{
		margin-top: 13px;
		font-size: 16px;
	}
	.pElement{
		text-align: center;
	}
	.divList{
		margin-top: 20px;
		justify-content: center;
		display: flex;
	}
	.bottomInfo{
		font-size: 20px;
	}
	.pElement{
		margin-top: 16px;
		font-size: 20px;
	}
	.rowElement{
		height: 60px;
		margin-top: 1px;
	}
	.rowElement p{
		font-size: 20px;
	}
	.pInput{
		margin-top: 22px;
		font-size: 16px;
	}
	.result-modal{
		height: 500px;
	}
</style>
