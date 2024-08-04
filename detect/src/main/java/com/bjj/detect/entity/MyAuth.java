package com.bjj.detect.entity;

import com.syzx.framework.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyAuth extends AbstractEntity {

	private String name;
	private String access;
	private int type;
}
