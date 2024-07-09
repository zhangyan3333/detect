package com.bjj.detect.sqldto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetectedMeter {

	private int mid;
//	private String meterFactory;   // 生产厂家
//	private String meterResolution;  // 准确度等级
//	private String meterCustomer;  // 送检单位
//	private String meterRangeL;    // 量程
//	private String meterRangeH;
//	private String meterCode;  // 出厂编号
//	private String meterDivisionNo;  // 分度号
	private String meterType;  // 仪表类型
	private int meterTypeCode;
	private String meterState;  // 仪表状态
	private int meterPoint;
	private String meterName;  // 规格型号
	private String meterUnit;
	private String meterRate;  // 频率
	private String meterAccuracy;  // 分辨力/修约间隔
	private String meterRangeUnit;
	private int meterPeriod;      // 检定周期
	private String meterCMC;
	private int moduleType;       // 4二次仪表
	private int meterDiameter;

}
