package com.bjj.detect.entity;

import com.syzx.framework.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRec extends AbstractEntity {
	private String message;
	private String userName;
	private String userId;

}
