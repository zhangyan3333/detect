package com.bjj.detect.entity;

import com.syzx.framework.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 测量详情
 * @author: zhangyan
 * @date: 2024/7/10 1:42
**/
@Getter
@Setter
public class PgInfo extends AbstractEntity {

	private Long pgRecordId;  // 归属检测记录

	private int index;

	private float sPressure;  // 标准器压力值
	private float strikeUp; // 轻敲表壳后被检仪表示值  升压
	private float strikeDown; // 轻敲表壳后被检仪表示值  降压
	private float positionUp; // 轻敲位移  升压
	private float positionDown; // 轻敲位移  降压
	private float indicationError;  // 示值误差
	private float returnError;  // 回程误差

	private String upImage;    // 升压图
	private String downImage;  // 降压图

	public PgInfo(){
		this.positionUp = 0;
		this.positionDown = 0;
	}
}
