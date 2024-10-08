package com.bjj.detect.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.syzx.framework.entity.AbstractEntity;
import com.syzx.framework.orm.annotation.FullSearch;
import com.syzx.framework.orm.annotation.ManyToOne;
import com.syzx.framework.orm.annotation.Transient;
import lombok.*;
import org.apache.ibatis.annotations.Many;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description: 检测记录
 * @author: zhangyan
 * @date: 2024/7/10 1:42
**/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FullSearch(propertyNames = {"meterName","meterCustomer"})
public class PgRecord extends AbstractEntity {

	private int mid; //记录已经同步的检定结果，因为sql库中主键id自增，可以选择比mid大的读取即可。

//	@ExcelProperty(value = "检定证书编号")
	private String detectCode; // 检定证书编号

	private String meterCustomer;  // 送检单位
	private String meterCode;  // 出厂编号
	private String meterRangeL;    // 量程  测量范围 低
	private String meterRangeH;    // 量程  测量范围 高
	private String meterResolution;  // 准确度等级
	private String meterDivisionNo;  // 分度值
	private String meterFactory;   // 制造单位

	@ExcelProperty(value = "名称")
	private String sname;  // 标准器名称 | 数字压力计
	private String scode;  // 标准器编号
	private String sRangeL;    // 量程  测量范围 低 | 0
	private String sRangeH;    // 量程  测量范围 高 | 60
	private String sResolution;  // 准确度等级  | 0.05级
	private String standardMedium;  // 工作介质  | 空气
	private String standardLoc;   // 检定地点
	private Date sEdate;	   // 标准器有效期
	private String sBasis;    // 检定依据  | JJG52-2013《弹性元件式一般压力表、压力真空表和真空表》
	private float sTemperature;  // 检测温度
	private float sHumidity;     // 检测湿度
	private String sRegulateCode;    // 计量器证书编号
	private String sFactory;  // 溯源机构
	private String sRegulateBcode;   // 溯源机构编号
	private Date sBdate;     // 溯源有效期

	private String appearance; // 外观检查
	private String pointer; // 指针偏转平稳性
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
	private String imgPath3;  // 图片文件地址

	private Long standardToolId;   // 检测标准id
	@ManyToOne(foreignKeyName = "standardToolId",joinClass = StandardTool.class,joinPropertyName = "sname")
	private String standardName;  // 检测标准名称
	@ManyToOne(foreignKeyName = "standardToolId",joinClass = StandardTool.class,joinPropertyName = "mname")
	private String standardToolName;  // 检测标准器名称

	@Transient
	private List<PgInfo> infos; // 加载检测信息
}
