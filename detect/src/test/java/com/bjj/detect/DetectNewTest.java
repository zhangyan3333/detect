package com.bjj.detect;

import com.bjj.detect.dao.DetectMeterDao;
import com.bjj.detect.dao.DetectRecordDao;
import com.bjj.detect.dao.PgInfoDao;
import com.bjj.detect.dao.PgRecordDao;
import com.bjj.detect.entity.DetectMeter;
import com.bjj.detect.entity.DetectRecord;
import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.service.PgRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DetectNewTest {

	@Autowired
	private DetectMeterDao detectMeterDao;

	@Autowired
	private PgRecordDao pgRecordDao;

	@Autowired
	private PgRecordService pgRecordService;

	@Autowired
	private DetectRecordDao detectRecordDao;

	@Autowired
	private PgInfoDao pgInfoDao;


	@Test
	public void testChangeRecord(){
		PgRecord pgRecord = pgRecordService.getById(479260675965714432L);
		DetectRecord record = new DetectRecord();
		List<PgInfo> infos = pgInfoDao.getByRecordId(479260675965714432L);
		String[] sPressure = new String[5];  // 标准器压力值
		String[] strikeUp = new String[5]; // 轻敲表壳后被检仪表示值  升压
		String[] strikeDown = new String[5]; // 轻敲表壳后被检仪表示值  降压
		String[] positionUp = new String[5]; // 轻敲位移  升压
		String[] positionDown = new String[5]; // 轻敲位移  降压
		String[] indicationError = new String[5];  // 示值误差
		String[] returnError = new String[5];  // 回程误差
		for (int i= 0;i<infos.size();i++){
			PgInfo info = infos.get(i);
			sPressure[i] = String.valueOf(info.getSPressure());
			strikeUp[i] = String.valueOf(info.getStrikeUp());
			strikeDown[i] = String.valueOf(info.getStrikeDown());
			positionUp[i] = String.valueOf(info.getPositionUp());
			positionDown[i] = String.valueOf(info.getPositionDown());
			indicationError[i] = String.valueOf(info.getIndicationError());
			returnError[i] = String.valueOf(info.getReturnError());
		}

		record.setSPressure(sPressure);
		record.setStrikeUp(strikeUp);
		record.setStrikeDown(strikeDown);
		record.setPositionUp(positionUp);
		record.setPositionDown(positionDown);
		record.setIndicationError(indicationError);
		record.setReturnError(returnError);

		record.setMeterId(pgRecord.getId());

		record.setAppearance(pgRecord.getAppearance());
		record.setPointer(pgRecord.getPointer());
		record.setIndicationErrorMax(pgRecord.getIndicationErrorMax());
		record.setReturnErrorMax(pgRecord.getReturnErrorMax());
		record.setPositionMax(pgRecord.getPositionMax());
		record.setIndicationErrorPermit(pgRecord.getIndicationErrorPermit());
		record.setReturnErrorPermit(pgRecord.getReturnErrorPermit());
		record.setPositionPermit(pgRecord.getPositionPermit());

		record.setIndicationErrorUpper(pgRecord.getIndicationErrorUpper());
		record.setReturnErrorUpper(pgRecord.getReturnErrorUpper());
		record.setPositionUpper(pgRecord.getPositionUpper());
		record.setIndicationErrorUpperPermit(pgRecord.getIndicationErrorUpperPermit());
		record.setReturnErrorUpperPermit(pgRecord.getReturnErrorUpperPermit());
		record.setPositionUpperPermit(pgRecord.getPositionUpperPermit());

		record.setDetectResult(pgRecord.getDetectResult());
		record.setDetectLevel(pgRecord.getDetectLevel());

		record.setCheckStep(pgRecord.getCheckStep());
		record.setInspector(pgRecord.getInspector());
		record.setVerifier(pgRecord.getVerifier());
		record.setApprover(pgRecord.getApprover());

		record.setResultId(pgRecord.getResultId());
		record.setDetectCode(pgRecord.getDetectCode());

		record.setStandardToolId(pgRecord.getStandardToolId());

		detectRecordDao.insert(record);

	}

	@Test
	public void testChange(){
		List<PgRecord> pgRecordList = pgRecordDao.get();
		for (PgRecord pgRecord : pgRecordList){
			DetectMeter meter = new DetectMeter();
			meter.setMeterName(pgRecord.getMeterName());
			meter.setMeterType(pgRecord.getMeterType());
			meter.setMeterCustomer(pgRecord.getMeterCustomer());
			meter.setMeterCode(pgRecord.getMeterCode());
			meter.setMeterRangeL(pgRecord.getMeterRangeL());
			meter.setMeterRangeH(pgRecord.getMeterRangeH());
			meter.setMeterResolution(pgRecord.getMeterResolution());
			meter.setMeterDivisionNo(pgRecord.getMeterDivisionNo());
			meter.setMeterFactory(pgRecord.getMeterFactory());
			meter.setStandardToolId(pgRecord.getStandardToolId());
			detectMeterDao.insert(meter);
		}
	}


}
