package com.bjj.detect.util;

import com.bjj.detect.service.PgRecordService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RunnerSelf implements ApplicationRunner {

	@Resource
	private PgRecordService pgRecordService;



	/**
	 * @param args:
	 * @return: void
	 * @description:  程序开始后执行定时任务。
	 * @author: zhangyan
	 * @date: 2024/7/14 0:52
	**/
	@Override
	public void run(ApplicationArguments args) throws Exception {
		pgRecordService.detectRecordSync();
		pgRecordService.standardMeterSync();
	}
}
