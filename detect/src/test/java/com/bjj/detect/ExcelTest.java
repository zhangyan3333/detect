package com.bjj.detect;

import com.alibaba.excel.EasyExcel;
import com.bjj.detect.dao.PgRecordDao;
import com.bjj.detect.entity.PgRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ExcelTest {


	@Autowired
	private PgRecordDao pgRecordDao;

	@Test
	public void testWriteExcel() {

		String filename = "D:\\user1.xlsx";

		// 向Excel中写入数据 也可以通过 head(Class<?>) 指定数据模板
		EasyExcel.write(filename, PgRecord.class)
				.sheet("用户信息")
				.doWrite(pgRecordDao.get());
	}


	// 根据user模板构建数据
	private List<PgRecord> getUserData() {
		List<PgRecord> records = pgRecordDao.get();
		List<PgRecord> result = new ArrayList<>();
		for (int i = 1; i < records.size(); i++) {
			PgRecord record = PgRecord.builder()
					.sname(records.get(i).getSname())
					.meterType(records.get(i).getMeterType())
					.build();
			result.add(record);
		}
		return result;
	}

}
