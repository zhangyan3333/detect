package com.bjj.detect.entity;

import com.syzx.framework.entity.AbstractEntity;
import com.syzx.framework.orm.annotation.FullSearch;
import com.syzx.framework.orm.annotation.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@FullSearch(propertyNames = {"sname","mname","sFactory","sResolution","sRegulateBcode","modifyUserName","organizationName","location"})
public class StandardTool extends AbstractEntity {

	//检定使用计量标准
	private String sname;  // 标准名称 | 数字压力计
	private String sRange;  // 量程  测量范围
//	private String sRangeL;    // 量程  测量范围 低 | 0
//	private String sRangeH;    // 量程  测量范围 高 | 60
	private String sResolution;  // 准确度等级  | 0.05级
	private String sRegulateCode;    // 证书编号
	private String sEdate;	   // 有效期至

	//检定使用主标准器
	private String mname;  // 标准器名称 | 数字压力计
	private String mRange; //量程  测量范围
//	private String mRangeL;    // 量程  测量范围 低 | 0
//	private String mRangeH;    // 量程  测量范围 高 | 60
	private String mResolution;  // 准确度等级  | 0.05级
	private String sFactory;  // 溯源机构
	private String sRegulateBcode;   // 证书编号
	private String sBdate;     // 有效期至

	private String location;  //标准器位置
	private Long organizationId;
	private String organizationName;

	private Long modifyUserId;
	private String modifyUserName;
	private Date modifyTime;

	private String sBasis;    // 检定依据  | JJG52-2013《弹性元件式一般压力表、压力真空表和真空表》
}
