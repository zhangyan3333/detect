package com.bjj.detect.util;

import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.service.PgInfoService;
import com.bjj.detect.service.PgRecordService;
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

	private static final DateTimeFormatter datePathFormat = DateTimeFormatter.ofPattern("yyyy/MM");

	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	public void detectRecordSqlToMysql(){

		List<Integer> records = new ArrayList<>();
		for(PgRecord pg : pgRecordService.get()){
			records.add(pg.getMid());
		}
		readRecord(records,sqlConnect);
		System.out.println("");
	}

	@Transactional(rollbackFor = Exception.class)
	public List<PgRecord> readRecord(List<Integer> pgs,SqlConnect sqlConnect){
		List<PgRecord> records = new ArrayList<>();
		try{
			Connection conn = sqlConnect.connect();
			String sql = "select * from Tbl_DetectedMeter_copy1 where mid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,70);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				int mid = rs.getInt("mid");
				if (pgs.indexOf(mid) == -1){
					PgRecord record = new PgRecord();
					record.setMid(mid);
					record.setMeterFactory(rs.getString("meterFactory"));
					record.setMeterResolution(rs.getString("meterResolution"));
					record.setMeterCustomer(rs.getString("meterCustomer"));
					record.setMeterRangeL(rs.getString("meterRangeL"));
					record.setMeterRangeH(rs.getString("meterRangeH"));
					record.setMeterCode(rs.getString("meterCode"));
					record.setMeterDivisionNo(rs.getString("meterDivisionNo"));

					// 将图片从数据库读出并存储到本地
					imageToPath(record,rs);

					pgRecordService.insert(record);
					records.add(record);
				}
			}
			sqlConnect.release(conn,ps,rs);
		}catch (Exception e){
			PrintUtil.info("读取检测仪表数据库出错"
					+ sdf.format(new Date())
					+ e.toString());
		}
		return records;
	}

	@Transactional(rollbackFor = Exception.class)
	public void imageToPath(PgRecord record,ResultSet rs) throws Exception{

		List<String[]> infos = changeToPgInfo(rs);

		List<Float> pa = pressArray(record.getMeterRangeL(),record.getMeterRangeH());
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

			pgInfoService.insert(info);
		}
	}

	/**
	 * @param meterRangeL:
	 * @param meterRangeH:
	 * @return: float
	 * @description: 根据仪表测量范围，获取标准压力器值
	 * @author: zhangyan
	 * @date: 2024/7/14 1:56
	**/
	private List<Float> pressArray(	String meterRangeL, String meterRangeH){
		List<Float> pressArray = new ArrayList<Float>();
		if (meterRangeL.equals("0")&&meterRangeH.equals("1.6")){
			pressArray.add(0f);
			pressArray.add(0.4f);
			pressArray.add(0.8f);
			pressArray.add(1.2f);
			pressArray.add(1.6f);
		}
		return pressArray;
	}

	private List<String[]> changeToPgInfo(ResultSet rs){
		List<String[]> result = new ArrayList<>();
		try {
			String meterUpiont1 = rs.getString("meterUpiont1");
			String meterUpiont2 = rs.getString("meterUpiont2");
			String meterUpiont3 = rs.getString("meterUpiont3");
			String meterUpiont4 = rs.getString("meterUpiont4");
			String meterUpiont5 = rs.getString("meterUpiont5");
			String meterUpiont6 = rs.getString("meterUpiont6");

			String meterDpiont1 = rs.getString("meterDpiont1");
			String meterDpiont2 = rs.getString("meterDpiont2");
			String meterDpiont3 = rs.getString("meterDpiont3");
			String meterDpiont4 = rs.getString("meterDpiont4");
			String meterDpiont5 = rs.getString("meterDpiont5");
			String meterDpiont6 = rs.getString("meterDpiont6");

			byte[] imagePiont1 = rs.getBytes("imagePiont1");
			byte[] imagePiont2 = rs.getBytes("imagePiont2");
			byte[] imagePiont3 = rs.getBytes("imagePiont3");
			byte[] imagePiont4 = rs.getBytes("imagePiont4");
			byte[] imagePiont5 = rs.getBytes("imagePiont5");
			byte[] imagePiont6 = rs.getBytes("imagePiont6");
			byte[] imagePiont7 = rs.getBytes("imagePiont7");
			byte[] imagePiont8 = rs.getBytes("imagePiont8");
			byte[] imagePiont9 = rs.getBytes("imagePiont9");
			byte[] imagePiont10 = rs.getBytes("imagePiont10");
			byte[] imagePiont11 = rs.getBytes("imagePiont11");
			byte[] imagePiont12 = rs.getBytes("imagePiont12");

			result.add(new String[]{meterUpiont1,meterDpiont1,imageByteToString(System.currentTimeMillis(),imagePiont1),imageByteToString(System.currentTimeMillis(),imagePiont7)});
			result.add(new String[]{meterUpiont2,meterDpiont2,imageByteToString(System.currentTimeMillis(),imagePiont2),imageByteToString(System.currentTimeMillis(),imagePiont8)});
			result.add(new String[]{meterUpiont3,meterDpiont3,imageByteToString(System.currentTimeMillis(),imagePiont3),imageByteToString(System.currentTimeMillis(),imagePiont9)});
			result.add(new String[]{meterUpiont4,meterDpiont4,imageByteToString(System.currentTimeMillis(),imagePiont4),imageByteToString(System.currentTimeMillis(),imagePiont10)});
			result.add(new String[]{meterUpiont5,meterDpiont5,imageByteToString(System.currentTimeMillis(),imagePiont5),imageByteToString(System.currentTimeMillis(),imagePiont11)});
			result.add(new String[]{meterUpiont6,meterDpiont6,imageByteToString(System.currentTimeMillis(),imagePiont6),imageByteToString(System.currentTimeMillis(),imagePiont12)});
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
}
