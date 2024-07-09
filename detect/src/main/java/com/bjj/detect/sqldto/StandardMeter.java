package com.bjj.detect.sqldto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class StandardMeter {
	private int sid;
//	private String sname;   // 名称
//	private String scode;   // 出厂编号
//	private String sResolution;   // 准确度等级
//	private String sRangeL;
//	private String sRangeH;
//	private Date sEdate;

//	private String sRegulateCode;    // 计量编号
//	private String sFactory;  // 厂家
//	private String sRegulateBcode;   // 计量证书编号
//  private Date sBdate;

	private String sAccuracy;
	private String sDivisionNo;    // 分度号
	private String sRangeUnit;
	private String sunit;   // 规格型号

	private int sModule;
	private BigDecimal rtp;
	private BigDecimal ra;
	private BigDecimal rb;
	private BigDecimal rc;
	private BigDecimal a4;
	private BigDecimal b4;
	private BigDecimal w0;
	private BigDecimal w100;
	private BigDecimal t800;
	private BigDecimal t900;
	private BigDecimal t1000;
	private BigDecimal t1100;

}
