package com.bjj.detect.entity;

import com.syzx.framework.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitTest extends AbstractEntity {

	private String message;
	private int status;
}
