package com.bjj.detect.entity;

import com.syzx.framework.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @description: 检定证书
 * @author: zhangyan
 * @date: 2024/7/10 1:01
**/
@Getter
@Setter
public class PgCertificate extends AbstractEntity {

	private Long recordId; // 归属检测记录

	private String meterCustomer;  // 送检单位
	private String meterName;  // 器具名称
	private String meterType;  // 规格型号 | 待定
	private String meterCode;  // 出厂编号
	private String meterFactory;   // 制造单位
	private String sBasis;    // 检定依据  | JJG52-2013《弹性元件式一般压力表、压力真空表和真空表》
	private int detectResult; // 检定结果：  0 合格  1 不合格

	private String approver; // 批准人
	private String inspector; // 检定员
	private String verifier; // 核验员

	private Date detectDate; // 检定日期

	private String certCode; // 证书编号

	private String standardLoc;   // 检定地点
	//	private String sBasis;    // 检定依据  | JJG52-2013《弹性元件式一般压力表、压力真空表和真空表》
	private float sTemperature;  // 检测温度
	private float sHumidity;     // 检测湿度

	//检定使用计量标准
	private String sname;  // 标准器名称 | 数字压力计
	private String sRangeL;    // 量程  测量范围 低 | 0
	private String sRangeH;    // 量程  测量范围 高 | 60
	private String sResolution;  // 准确度等级  | 0.05级
	private String sRegulateCode;    // 计量器证书编号
	private Date sEdate;	   // 标准器有效期

	//检定使用主标准器
	private String mname;  // 标准器名称 | 数字压力计
	private String mRangeL;    // 量程  测量范围 低 | 0
	private String mRangeH;    // 量程  测量范围 高 | 60
	private String mResolution;  // 准确度等级  | 0.05级
	private String sFactory;  // 溯源机构
	private String sRegulateBcode;   // 溯源机构编号
	private Date sBdate;     // 溯源有效期

	private float zeroErrorMax;  // 最大零位误差
	private float indicationErrorMax;  // 最大示值误差
	private float returnErrorMax;  // 最大回程误差
	private float positionMax;  // 最大位移误差

	private String UnqualifiedItem;  // 不合格项目
}
