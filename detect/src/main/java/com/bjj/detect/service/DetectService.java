package com.bjj.detect.service;

import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.query.DetectQuery;
import com.syzx.framework.query.QueryResult;

public interface DetectService {

	QueryResult<PgRecord> getAll(DetectQuery detectQuery);
}
