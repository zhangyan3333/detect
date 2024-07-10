package com.bjj.detect.gptest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

//	@Pointcut("execution(* com.bjj.detect.util.*.*(..))")
//	public void myPointCut(){
//
//	}

//	@Before(value = "myPointCut()")
//	public void before(JoinPoint joinPoint){
//		System.out.println("[前置通知] 开始日志");
//		System.out.println("[前置通知] " + joinPoint.getTarget());
//		System.out.println("[前置通知] " + joinPoint.getSignature().getName());
//	}
//
//	@After(value = "myPointCut()")
//	public void after(JoinPoint joinPoint){
//		System.out.println("[后置通知] 开始日志");
//		System.out.println("[后置通知] " + joinPoint.getTarget());
//		System.out.println("[后置通知] " + joinPoint.getSignature().getName());
//	}
}
