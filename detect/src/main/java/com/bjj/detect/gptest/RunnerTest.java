package com.bjj.detect.gptest;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerTest implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("-----------------------");
		System.out.println("--------最后执行的-------");
		System.out.println("-----------------------");
	}
}
