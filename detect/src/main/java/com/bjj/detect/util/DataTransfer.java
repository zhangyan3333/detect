package com.bjj.detect.util;

import org.springframework.core.env.Environment;
import com.bjj.detect.dao.PgCertificateDao;
import com.bjj.detect.dao.PgInfoDao;
import com.bjj.detect.dao.PgRecordDao;
import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.entity.StandardMeter;
import com.bjj.detect.query.PgRecordQuery;
import com.bjj.detect.service.PgInfoService;
import com.bjj.detect.service.PgRecordService;
import com.bjj.detect.service.StandardMeterService;
import com.bjj.detect.sqldto.DetectedDetail;
import com.bjj.detect.sqldto.DetectedMeter;
import com.syzx.framework.config.FrameworkConfig;
import com.syzx.framework.exceptions.BusinessException;
import com.syzx.framework.uid.CentralizedUidGenerator;
import com.syzx.framework.utils.PrintUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class DataTransfer {

	@Autowired
	private SqlConnect sqlConnect;

	@Autowired
	private PgInfoService pgInfoService;

	@Autowired
	private PgRecordService pgRecordService;

	@Autowired
	private StandardMeterService standardMeterService;

	@Autowired
	private PgCertificateDao certificateDao;

	@Autowired
	private PgInfoDao pgInfoDao;

	@Autowired
	private PgRecordDao pgRecordDao;

	@Autowired
	private Environment environment;

	private static final DateTimeFormatter datePathFormat = DateTimeFormatter.ofPattern("yyyy/MM");

	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	@Transactional(rollbackFor = Exception.class)
	public void detectRecordSqlToMysql(){
		List<Integer> records = new ArrayList<>();

		//读取 sql数据库中所有状态不为0的数据
		List<PgRecord> recordList = readRecord(records,sqlConnect);

		if (recordList.size()>0){
			List<PgRecord> haveList = pgRecordService.get();
			for (int i = recordList.size()-1; i >-1; i--) {
				boolean flag = true;
				for (int j = 0; j < haveList.size() && flag; j++) {
					if (recordList.get(i).getMid() == haveList.get(j).getMid()){
						if (haveList.get(j).getCheckStep() == 0){
							PgRecord record = haveList.get(j);
							pgInfoDao.deleteByProperty("pgRecordId",record.getId());
							certificateDao.deleteByRecordId(record.getId());
							record.setInfos(recordList.get(i).getInfos());

							pgRecordDao.update(cacheRecord(record));

							List<PgInfo> pgInfos =  recordList.get(i).getInfos();
							for (PgInfo info : pgInfos){
								info.setPgRecordId(record.getId());
								pgInfoDao.insert(info);
							}

							haveReadRecord(recordList.get(i).getMid(),sqlConnect);

						}
						recordList.remove(recordList.get(i));
						flag =false;
					}
				}
			}
			if (recordList.size() > 0){
				for (int i = 0; i < recordList.size(); i++) {
					PgRecord record = recordList.get(i);
					record.setCheckStep(1);
					pgRecordDao.insert(record);
					pgInfoDao.insertAll(record.getInfos());
				}
			}
		}
	}

	public PgRecord cacheRecord(PgRecord record){
		record.setCheckStep(1);
		record.setCreateTime(new Date());
		record.setInspector(null);
		record.setVerifier(null);
		record.setApprover(null);
		record.setRecordFile(null);
		record.setResultFile(null);
		record.setResultId(0);
		record.setRemark(null);
		record.setImgPath1(null);
		record.setImgPath2(null);
		record.setImgPath3(null);
		return record;
	}

	public void haveReadRecord(int recordId, SqlConnect sqlConnect){
		Connection conn = sqlConnect.connect();

		String tableName = environment.getProperty("tables.detectTable");
		String sql = "update " + tableName + " set meterstate = ? where mid = ?";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, recordId);
			ps.executeUpdate();

			ps.close();
			conn.close();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public List<PgRecord> readRecord(List<Integer> pgs, SqlConnect sqlConnect){
		List<PgRecord> records = new ArrayList<>();
		try{
			Connection conn = sqlConnect.connect();
			String tableName = environment.getProperty("tables.detectTable");
			String sql = "select * from " + tableName + " where meterstate = 1";
			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1,70);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				PgRecord record =new PgRecord();
				record.setMid(rs.getInt("mid"));
				record.setMeterFactory(rs.getString("meterFactory")==null?"":rs.getString("meterFactory"));
				record.setMeterResolution(rs.getString("meterResolution")==null?"":rs.getString("meterResolution"));
				record.setMeterCustomer(rs.getString("meterCustomer")==null?"":rs.getString("meterCustomer"));
				record.setMeterRangeL(rs.getString("meterRangeL")==null?"":rs.getString("meterRangeL"));
				record.setMeterRangeH(rs.getString("meterRangeH")==null?"":rs.getString("meterRangeH"));
				record.setMeterCode(rs.getString("meterCode")==null?"":rs.getString("meterCode"));
				record.setMeterDivisionNo(rs.getString("meterDivisionNo")==null?"":rs.getString("meterDivisionNo"));
				record.setMeterName(rs.getString("meterType")==null?"":rs.getString("meterType"));

				// 加载检测信息
				getsqlInfos(record,rs);

				records.add(record);
			}
			sqlConnect.release(conn,ps,rs);
		}catch (Exception e){
			PrintUtil.info("读取检测仪表数据库出错"
					+ sdf.format(new Date())
					+ e.toString());
		}
		return records;
	}

	public void getsqlInfos(PgRecord record,ResultSet rs) throws Exception{
		// 获取 所有检测信息 格式为【up,down,imagepath】
		List<String[]> infos = changeToPgInfo(rs);
		// 组装 info 首先根据被检表的两成确定数据
		List<Float> pa = pressArray(record.getMeterRangeL(),record.getMeterRangeH(),record); // 获取被测表量程 并加载一些初始数据

		List<PgInfo> pgInfos = new ArrayList<>();
		// 加载检测数据
		for (int i = 0; i < pa.size(); i++) {
			PgInfo info = new PgInfo();
			String[] meta = infos.get(i);
			Float up = Float.valueOf(meta[0]);
			Float down = Float.valueOf(meta[1]);

			info.setPgRecordId(record.getId());
			info.setIndex(i+1);
			info.setSPressure(pa.get(i));
			info.setStrikeUp(up);
			info.setStrikeDown(down);
			info.setUpImage(meta[2]);
			info.setDownImage(meta[3]);
			info.setIndicationError(Float.valueOf((String.format("%.2f", Math.abs(up-down)))));
			info.setReturnError(Float.valueOf((String.format("%.2f", Math.abs(up-down)))));

			pgInfos.add(info);
		}

		record.setInfos(pgInfos);
	}


	/**
	 * @param meterRangeL:
	 * @param meterRangeH:
	 * @return: float
	 * @description: 根据仪表测量范围，获取标准压力器值 等 经验值
	 * @author: zhangyan
	 * @date: 2024/7/14 1:56
	 **/
	private List<Float> pressArray(	String meterRangeL, String meterRangeH, PgRecord record){
		List<Float> pressArray = new ArrayList<Float>();
//		List<StandardMeter> meters = standardMeterService.get();
		if (meterRangeL.equals("0")&&meterRangeH.equals("1.6")){
			// 配置检定仪表信息，待修改
//			for (StandardMeter meter : meters){
//				if (meter.getSid() == 27){ loadStandardMetetInfo(record,meter);}
//			}

			record.setSBasis("JJG52-2013《弹性元件式一般压力表、压力真空表和真空表》");  // 依据
			record.setSTemperature(23f);  // 温度
			record.setSHumidity(49f);    // 湿度
			record.setStandardMedium("空气");  //介质
			record.setStandardLoc("中国铁路北京局集团有限公司计量管理所");  // 检测地点

			// 录入检定结果
			record.setAppearance("合格");
			record.setPointer("合格");
			record.setIndicationErrorMax(0.1f);
			record.setReturnErrorMax(0.1f);
			record.setPositionMax(0.0f);
			record.setIndicationErrorPermit(0.0256f);
			record.setReturnErrorPermit(0.0256f);
			record.setPositionPermit(0.0128f);
			record.setIndicationErrorUpper(0.02f);
			record.setReturnErrorUpper(0.02f);
			record.setPositionUpper(0.00f);
			record.setIndicationErrorUpperPermit(0.04f);
			record.setReturnErrorUpperPermit(0.04f);
			record.setPositionUpperPermit(0.02f);
			record.setDetectLevel(1.6f);

			// 制定压力器值范围
			pressArray.add(0f);
			pressArray.add(0.4f);
			pressArray.add(0.8f);
			pressArray.add(1.2f);
			pressArray.add(1.6f);
		}else {
			pressArray.add(0f);
			pressArray.add(0f);
			pressArray.add(0f);
			pressArray.add(0f);
			pressArray.add(0f);
			pressArray.add(0f);
		}
		return pressArray;
	}



	private List<String[]> changeToPgInfo(ResultSet rs){
		List<String[]> result = new ArrayList<>();
		try {
			String meterUpiont1 = rs.getString("meterUpiont1")==null?"0":rs.getString("meterUpiont1");
			String meterUpiont2 = rs.getString("meterUpiont2")==null?"0":rs.getString("meterUpiont2");
			String meterUpiont3 = rs.getString("meterUpiont3")==null?"0":rs.getString("meterUpiont3");
			String meterUpiont4 = rs.getString("meterUpiont4")==null?"0":rs.getString("meterUpiont4");
			String meterUpiont5 = rs.getString("meterUpiont5")==null?"0":rs.getString("meterUpiont5");
			String meterUpiont6 = rs.getString("meterUpiont6")==null?"0":rs.getString("meterUpiont6");

			String meterDpiont1 = rs.getString("meterDpiont1")==null?"0":rs.getString("meterDpiont1");
			String meterDpiont2 = rs.getString("meterDpiont2")==null?"0":rs.getString("meterDpiont2");
			String meterDpiont3 = rs.getString("meterDpiont3")==null?"0":rs.getString("meterDpiont3");
			String meterDpiont4 = rs.getString("meterDpiont4")==null?"0":rs.getString("meterDpiont4");
			String meterDpiont5 = rs.getString("meterDpiont5")==null?"0":rs.getString("meterDpiont5");
			String meterDpiont6 = rs.getString("meterDpiont6")==null?"0":rs.getString("meterDpiont6");

			byte[] imagePiont1 = rs.getBytes("imagePiont1")==null?null:rs.getBytes("imagePiont1");
			byte[] imagePiont2 = rs.getBytes("imagePiont2")==null?null:rs.getBytes("imagePiont2");
			byte[] imagePiont3 = rs.getBytes("imagePiont3")==null?null:rs.getBytes("imagePiont3");
			byte[] imagePiont4 = rs.getBytes("imagePiont4")==null?null:rs.getBytes("imagePiont4");
			byte[] imagePiont5 = rs.getBytes("imagePiont5")==null?null:rs.getBytes("imagePiont5");
			byte[] imagePiont6 = rs.getBytes("imagePiont6")==null?null:rs.getBytes("imagePiont6");
			byte[] imagePiont7 = rs.getBytes("imagePiont7")==null?null:rs.getBytes("imagePiont7");
			byte[] imagePiont8 = rs.getBytes("imagePiont8")==null?null:rs.getBytes("imagePiont8");
			byte[] imagePiont9 = rs.getBytes("imagePiont9")==null?null:rs.getBytes("imagePiont9");
			byte[] imagePiont10 = rs.getBytes("imagePiont10")==null?null:rs.getBytes("imagePiont10");
			byte[] imagePiont11 = rs.getBytes("imagePiont11")==null?null:rs.getBytes("imagePiont11");
			byte[] imagePiont12 = rs.getBytes("imagePiont12")==null?null:rs.getBytes("imagePiont12");

			if(meterUpiont1 != null){result.add(new String[]{meterUpiont1,meterDpiont1,imageByteToString(System.currentTimeMillis(),imagePiont1),imageByteToString(System.currentTimeMillis(),imagePiont7)});}
			if(meterUpiont2 != null){result.add(new String[]{meterUpiont2,meterDpiont2,imageByteToString(System.currentTimeMillis(),imagePiont2),imageByteToString(System.currentTimeMillis(),imagePiont8)});}
			if(meterUpiont3 != null){result.add(new String[]{meterUpiont3,meterDpiont3,imageByteToString(System.currentTimeMillis(),imagePiont3),imageByteToString(System.currentTimeMillis(),imagePiont9)});}
			if(meterUpiont4 != null){result.add(new String[]{meterUpiont4,meterDpiont4,imageByteToString(System.currentTimeMillis(),imagePiont4),imageByteToString(System.currentTimeMillis(),imagePiont10)});}
			if(meterUpiont5 != null){result.add(new String[]{meterUpiont5,meterDpiont5,imageByteToString(System.currentTimeMillis(),imagePiont5),imageByteToString(System.currentTimeMillis(),imagePiont11)});}
			if(meterUpiont6 != null){result.add(new String[]{meterUpiont6,meterDpiont6,imageByteToString(System.currentTimeMillis(),imagePiont6),imageByteToString(System.currentTimeMillis(),imagePiont12)});}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private String imageByteToString(Long nowTime,byte[] image) throws IOException, InterruptedException {

		String originName = String.valueOf(nowTime);

		if (image != null && image.length > 0){
			InputStream inputStream = new ByteArrayInputStream(image);
			MultipartFile multipartFile = new MultipartFileDto(originName, originName + ".jpg", "image/jpeg", inputStream);


			String datePath = LocalDate.now().format(datePathFormat);
			File folder = new File(String.format("%s%s", FrameworkConfig.dataPath, datePath));
			if (!folder.exists() && !folder.mkdirs()) {
				throw new BusinessException(1, "无法创建目录");
			} else {
				String oldName = multipartFile.getOriginalFilename();
				String newName = CentralizedUidGenerator.getId() + oldName.substring(oldName.lastIndexOf("."));
				String suffix = oldName.substring(oldName.lastIndexOf(".") + 1);

				try {
					if (
							suffix.equals("png") ||
									suffix.equals("jpg")) {
						multipartFile.transferTo(new File(folder, newName));
						return String.format("%s/%s", datePath, newName);
					}else {
						throw new BusinessException(1, "无法上传该类型文件");
					}
				} catch (IOException var7) {
					throw new BusinessException(1, "无法上传文件");
				} finally {
					Thread.sleep(10);
				}
			}
		}else {
			return "";
		}
	}






	/**
	 * @param record:
	 * @param meter:
	 * @return: void
	 * @description: 添加标准器信息到记录中
	 * @author: zhangyan
	 * @date: 2024/7/14 22:16
	 **/
	private void loadStandardMetetInfo(PgRecord record, StandardMeter meter){
		record.setSname(meter.getSname());
		record.setScode(meter.getScode());
		record.setSRangeL(meter.getSRangeL());
		record.setSRangeH(meter.getSRangeH());
		record.setSResolution(meter.getSResolution());
		record.setSEdate(meter.getSEdate());
		record.setSResolution(meter.getSResolution());
		record.setSFactory(meter.getSFactory());
		record.setSRegulateBcode(meter.getSRegulateBcode());
		record.setSBdate(meter.getSBdate());
	}

	/**
	 * @param :
	 * @return: void
	 * @description: 从标准器数据库读取同步
	 * @author: zhangyan
	 * @date: 2024/7/14 23:50
	**/
	@Transactional(rollbackFor = Exception.class)
	public void readStandardMeter(){
		List<StandardMeter> meters = standardMeterService.get();
		List<Integer> ids = new ArrayList<>();
		for (StandardMeter m:meters){
			ids.add(m.getSid());
		}
		try{
			Connection conn = sqlConnect.connect();
			String sqlCount = "select count(*) from Tbl_StandardMeter";
			PreparedStatement ps = conn.prepareStatement(sqlCount);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if (rs.next()){
				count = rs.getInt(1);
			}
			if (count == meters.size()){

			}else {
				// 获取所有标准器
				String sql = "select * from Tbl_StandardMeter";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();

				List<StandardMeter> standards = new ArrayList<>();
				while(rs.next()){
					StandardMeter standardMeter = new StandardMeter();
					standardMeter.setSid(rs.getInt("sid"));
					standardMeter.setSname(rs.getString("sname"));
					standardMeter.setScode(rs.getString("scode"));
					standardMeter.setSRegulateBcode(rs.getString("sResolution"));
					standardMeter.setSRangeL(rs.getString("sRangeL"));
					standardMeter.setSRangeH(rs.getString("sRangeH"));
					standardMeter.setSEdate(rs.getDate("sEdate"));
					standardMeter.setSRegulateCode(rs.getString("sRegulateCode"));
					standardMeter.setSFactory(rs.getString("sFactory"));
					standardMeter.setSRegulateBcode(rs.getString("sRegulateBcode"));
					standardMeter.setSBdate(rs.getDate("sBdate"));
					standardMeter.setSAccuracy(rs.getString("sAccuracy"));
					standardMeter.setSDivisionNo(rs.getString("sDivisionNo"));
					standardMeter.setSRangeUnit(rs.getString("sRangeUnit"));
					standardMeter.setSunit(rs.getString("sunit"));
					standardMeter.setSModule(rs.getInt("sModule"));
					standardMeter.setRtp(rs.getBigDecimal("rtp").toString());
					standardMeter.setRa(rs.getBigDecimal("ra").toString());
					standardMeter.setRb(rs.getBigDecimal("rb").toString());
					standardMeter.setRc(rs.getBigDecimal("rc").toString());
					standardMeter.setA4(rs.getBigDecimal("a4").toString());
					standardMeter.setB4(rs.getBigDecimal("b4").toString());
					standardMeter.setW0(rs.getBigDecimal("w0").toString());
					standardMeter.setW100(rs.getBigDecimal("w100").toString());
					standardMeter.setT800(rs.getBigDecimal("t800").toString());
					standardMeter.setT900(rs.getBigDecimal("t900").toString());
					standardMeter.setT1000(rs.getBigDecimal("t1000").toString());
					standardMeter.setT1100(rs.getBigDecimal("t1100").toString());

					standards.add(standardMeter);
				}
				for (int i = 0; i < standards.size(); i++) {
					StandardMeter standardMeter = standards.get(i);
					if (ids.indexOf(standardMeter.getSid()) > -1){
						continue;
					}else {
						standardMeterService.insert(standardMeter);
					}
				}
			}

			sqlConnect.release(conn,ps,rs);
		}catch (Exception e){
			PrintUtil.info("读取标准器数据库出错"
					+ sdf.format(new Date())
					+ e.toString());
		}
	}
}
