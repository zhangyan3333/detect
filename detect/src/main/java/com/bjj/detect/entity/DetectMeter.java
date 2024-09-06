package com.bjj.detect.entity;

import com.bjj.detect.entity.VO.DetectRecordVO;
import com.syzx.framework.entity.AbstractEntity;
import com.syzx.framework.orm.annotation.FullSearch;
import com.syzx.framework.orm.annotation.ManyToOne;
import com.syzx.framework.orm.annotation.Transient;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@FullSearch(propertyNames = {"meterName","meterType","meterCustomer"})
public class DetectMeter extends AbstractEntity {

	private String meterName;  // 器具名称
	private String meterType;  // 规格型号
	private String meterCustomer;  // 送检单位
	private String meterCode;  // 出厂编号
	private String meterRangeL;    // 量程  测量范围 低
	private String meterRangeH;    // 量程  测量范围 高
	private String meterResolution;  // 准确度等级
	private String meterDivisionNo;  // 分度值
	private String meterFactory;   // 制造单位

	private int checkStep;  // 状态 0.完成所有审批 1.检定员  2.核验员审核  3.批准人审核
	private int detectResult; // 检定结果：  0 合格  1 不合格
	private String detectCode; // 检定证书编号
	private String remark;  // 备注

	private long resultId;  // 关联结果文件id
	@ManyToOne(foreignKeyName = "resultId",joinClass = PgCertificate.class,joinPropertyName = "detectTime")
	private Date detectTime; // 检定日期
	@ManyToOne(foreignKeyName = "resultId",joinClass = PgCertificate.class,joinPropertyName = "overTime")
	private Date overTime;  // 过期时间

	private Long standardToolId;   // 检测标准id
	@ManyToOne(foreignKeyName = "standardToolId",joinClass = StandardTool.class,joinPropertyName = "sname")
	private String standardName;  // 检测标准名称
	@ManyToOne(foreignKeyName = "standardToolId",joinClass = StandardTool.class,joinPropertyName = "mname")
	private String standardToolName;  // 检测标准器名称

	private String resultFile; // 结果文件地址
	private String recordFile;  // 记录文件地址
	private String imgPath1;  // 图片文件地址
	private String imgPath2;  // 图片文件地址

	@Transient
	private List<DetectRecordVO> infos; // 加载历次检测信息
}
