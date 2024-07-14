package com.bjj.detect.entity;

import com.syzx.framework.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StandardMeter extends AbstractEntity {

	private int sid;

	private String sname;   // 名称
	private String scode;   // 出厂编号
	private String sResolution;   // 准确度等级
	private String sRangeL;
	private String sRangeH;
	private Date sEdate;

	private String sRegulateCode;    // 计量编号
	private String sFactory;  // 厂家
	private String sRegulateBcode;   // 计量证书编号
	private Date sBdate;

	private String sAccuracy;
	private String sDivisionNo;    // 分度号
	private String sRangeUnit;
	private String sunit;   // 规格型号

	private int sModule;
	private String rtp;
	private String ra;
	private String rb;
	private String rc;
	private String a4;
	private String b4;
	private String w0;
	private String w100;
	private String t800;
	private String t900;
	private String t1000;
	private String t1100;



}
