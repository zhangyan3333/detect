package com.bjj.detect.entity.VO;

import com.alibaba.excel.annotation.ExcelProperty;
import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.StandardTool;
import com.syzx.framework.entity.AbstractEntity;
import com.syzx.framework.orm.annotation.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class DetectRecordVO extends AbstractEntity {

	private String detectCode; // 检定证书编号

	private String meterCustomer;  // 送检单位
	private String meterCode;  // 出厂编号
	private String meterRangeL;    // 量程  测量范围 低
	private String meterRangeH;    // 量程  测量范围 高
	private String meterResolution;  // 准确度等级
	private String meterDivisionNo;  // 分度值
	private String meterFactory;   // 制造单位

	private String sname;  // 标准器名称 | 数字压力计
	private String scode;  // 标准器编号
	private String sRangeL;    // 量程  测量范围 低 | 0
	private String sRangeH;    // 量程  测量范围 高 | 60
	private String sResolution;  // 准确度等级  | 0.05级
	private String standardMedium;  // 工作介质  | 空气
	private String standardLoc;   // 检定地点
	private String sEdate;	   // 标准器有效期
	private String sBasis;    // 检定依据  | JJG52-2013《弹性元件式一般压力表、压力真空表和真空表》
	private float sTemperature;  // 检测温度
	private float sHumidity;     // 检测湿度
	private String sRegulateCode;    // 计量器证书编号
	private String sFactory;  // 溯源机构
	private String sRegulateBcode;   // 溯源机构编号
	private String sBdate;     // 溯源有效期

	private String appearance; // 外观检查
	private String pointer; // 指针偏转平稳性
	private float zeroErrorMax;  // 最大示值误差
	private float indicationErrorMax;  // 最大示值误差
	private float returnErrorMax;  // 最大回程误差
	private float positionMax;  // 最大位移误差
	private float indicationErrorPermit;  // 示值误差允许值
	private float returnErrorPermit;  // 回程误差允许值
	private float positionPermit;  // 位移误差允许值

	private float indicationErrorUpper;  // 示值误差-测量上限
	private float returnErrorUpper;  // 回程误差-测量上限
	private float positionUpper;  // 位移误差-测量上限
	private float indicationErrorUpperPermit;  // 示值误差-测量上限-允许值
	private float returnErrorUpperPermit;  // 回程误差-测量上限-允许值
	private float positionUpperPermit;  // 位移误差-测量上限-允许值

	private int detectResult; // 检定结果：  0 合格  1 不合格
	private float detectLevel; // 符合：xx 级

	private String remark;  // 备注

	private String inspector; // 检定员
	private String verifier; // 核验员
	private String approver; // 批准员

	@ManyToOne(foreignKeyName = "resultId",joinClass = PgCertificate.class,joinPropertyName = "detectTime")
	private Date detectTime; // 检定日期
	@ManyToOne(foreignKeyName = "resultId",joinClass = PgCertificate.class,joinPropertyName = "overTime")
	private Date overTime;  // 过期时间

	private int checkStep;  // 状态 0.完成所有审批 1.检定员  2.核验员审核  3.批准人审核

	private long resultId;  // 关联结果文件id

	// 冗余字段
	private String meterName;  // 器具名称
	@ExcelProperty(value = "规格型号")
	private String meterType;  // 规格型号未知

	private String resultFile; // 结果文件地址
	private String recordFile;  // 记录文件地址
	private String imgPath1;  // 图片文件地址
	private String imgPath2;  // 图片文件地址

	private Long meterId; // 被检表ID

	private Long standardToolId;   // 检测标准id
	@ManyToOne(foreignKeyName = "standardToolId",joinClass = StandardTool.class,joinPropertyName = "sname")
	private String standardName;  // 检测标准名称
	@ManyToOne(foreignKeyName = "standardToolId",joinClass = StandardTool.class,joinPropertyName = "mname")
	private String standardToolName;  // 检测标准器名称



	private String[] sPressure;  // 标准器压力值
	private String[] strikeUp; // 轻敲表壳后被检仪表示值  升压
	private String[] strikeDown; // 轻敲表壳后被检仪表示值  降压
	private String[] positionUp; // 轻敲位移  升压
	private String[] positionDown; // 轻敲位移  降压
	private String[] indicationError;  // 示值误差
	private String[] returnError;  // 回程误差

	private String upImage1;    // 升压图1
	private String downImage1;  // 降压图1
	private String upImage2;    // 升压图2
	private String downImage2;  // 降压图2
	private String upImage3;    // 升压图3
	private String downImage3;  // 降压图3
	private String upImage4;    // 升压图4
	private String downImage4;  // 降压图4
	private String upImage5;    // 升压图5
	private String downImage5;  // 降压图5
	private String upImage6;    // 升压图6
	private String downImage6;  // 降压图6
	private String upImage7;    // 升压图7
	private String downImage7;  // 降压图17

}
