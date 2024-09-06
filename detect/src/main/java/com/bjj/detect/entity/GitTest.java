package com.bjj.detect.entity;

import com.syzx.framework.entity.AbstractEntity;
import com.syzx.framework.orm.annotation.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GitTest extends AbstractEntity {

	private String message;
	private int status;

	private String[] count;
}
