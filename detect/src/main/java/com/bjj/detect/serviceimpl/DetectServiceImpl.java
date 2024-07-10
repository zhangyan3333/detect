package com.bjj.detect.serviceimpl;

import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.query.DetectQuery;
import com.bjj.detect.service.DetectService;
import com.bjj.detect.sqldto.DetectedDetail;
import com.bjj.detect.util.SqlConnect;
import com.syzx.framework.query.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetectServiceImpl implements DetectService {

	@Autowired
	private SqlConnect sqlConnect;

	@Override
	public QueryResult<PgRecord> getAll(DetectQuery detectQuery) {

		QueryResult<PgRecord> result = new QueryResult<PgRecord>();

		try {
			List<PgRecord> records = new ArrayList<PgRecord>();

			Connection conn = sqlConnect.connect();
			String sql = "select * from Tbl_DetectedMeter";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				PgRecord record = new PgRecord();
				record.setMeterCustomer(rs.getString("meterCustomer"));
				record.setMeterCode(rs.getString("meterCode"));
				record.setMeterRangeL(rs.getString("meterRangeL"));
				record.setMeterRangeH(rs.getString("meterRangeH"));
				record.setMeterResolution(rs.getString("meterResolution"));
				record.setMeterDivisionNo(rs.getString("meterDivisionNo"));
				record.setMeterFactory(rs.getString("meterFactory"));
				record.setMeterName(rs.getString("meterName"));
				record.setMeterType(rs.getString("meterType"));

				records.add(record);
			}

			result.setEntities(records);
			result.setCount(records.size());
			sqlConnect.release(conn,ps,rs);

		}catch (Exception e){
			e.printStackTrace();
		}

		return result;
	}

}
