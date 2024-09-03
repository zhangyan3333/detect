package com.bjj.detect.controller;

import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.query.DetectQuery;
import com.bjj.detect.query.PgCertificateQuery;
import com.bjj.detect.service.DetectService;
import com.syzx.framework.controller.ApiResult;
import com.syzx.framework.controller.ApiResultCode;
import com.syzx.framework.query.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/detect")
public class DetectController {

	@Autowired
	private DetectService detectService;

	/**
	 * 分页获取符合查询条件的实体集合.
	 * @param detectQuery 查询条件
	 * @return 服务器统一api回复
	 */
	@PostMapping(value = "/page")
	public ApiResult<QueryResult<PgRecord>> pageByQuery(@RequestBody DetectQuery detectQuery) {
		QueryResult<PgRecord> list = detectService.getAll(detectQuery);
		return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
	}


}
