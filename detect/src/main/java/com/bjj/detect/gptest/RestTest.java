package com.bjj.detect.gptest;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.Resource;

public class RestTest implements Condition {

//	@Resource
//	private OtherTest otherTest;

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (1==1){
			return true;
		}else {
			return false;
		}
	}
}
