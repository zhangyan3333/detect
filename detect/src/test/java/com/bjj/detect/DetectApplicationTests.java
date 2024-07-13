package com.bjj.detect;

import com.bjj.detect.sqldto.DetectedDetail;
import com.bjj.detect.sqldto.WordItem;
import com.bjj.detect.util.SqlConnect;
import io.netty.handler.codec.base64.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DetectApplicationTests {

	@Autowired
	private SqlConnect sqlConnect;

	@Test
	void readImage() throws Exception{
		List<DetectedDetail> details = new ArrayList<>();

		Connection conn = sqlConnect.connect();
		String sql = "select * from Tbl_DetectedDetail order by ddate desc";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			DetectedDetail detail = new DetectedDetail();
			detail.setDid(rs.getInt("did"));
			detail.setMid(rs.getInt("mid"));
			detail.setDdate(rs.getDate("ddate"));
//			detail.setBookWord(rs.getBytes("bookWord"));
//			detail.setBookJl(rs.getBytes("bookJl"));
			detail.setMeterResault(rs.getInt("meterResault"));
			detail.setMeterRlaCode(rs.getString("meterRlaCode"));
			detail.setMeterRlaType(rs.getString("meterRlaType"));
			detail.setCheckStep(rs.getInt("checkStep"));
			detail.setCheckResault(rs.getInt("checkResault"));
			detail.setIsPrint(rs.getInt("isPrint"));

//			new BufferedOutputStream(new FileOutputStream("d:\\bjjImages\\"+ detail.getDid()+".jpg"));
			//读出流用getBinaryStream()方法。
//			InputStream is = rs.getBinaryStream("bookWord");
//			File file = new File("d:\\bjjImages\\"+ detail.getDid()+".jpg");
//			try (OutputStream os = new FileOutputStream(file)) {
//				byte[] buffer = new byte[1024];
//				int length;
//				while ((length = is.read(buffer)) != -1) {
//					os.write(buffer, 0, length);
//				}
//			}


			details.add(detail);
		}

//		for (DetectedDetail detail : details){
//			System.out.println( detail.toString() );
//		}

//		System.out.println("---------------------------------------------");
//		for (Byte b : details.get(0).getBookWord()){
//			System.out.print(b + " ");
//		}
//		System.out.println("---------------------------------------------");
//		System.out.println(details.get(0).getBookWord().toString());

//		byte2image(details.get(0).getBookWord(),"d:\\bjjImages\\abc.jpg");


		sqlConnect.release(conn,ps,rs);

	}

	@Test
	public void imageTest() throws Exception{
		byte[] source = image2byte("D:\\001.jpg");
		for (Byte b : source){
			System.out.print(b + " ");
		}
		Connection conn = sqlConnect.connect();
		String sql = "update Tbl_DetectedMeter_copy1 set " +
				"meterUpiont1=?,meterUpiont2=?,meterUpiont3=?,meterUpiont4=?,meterUpiont5=?,meterUpiont6=?," +
				"meterDpiont1=?,meterDpiont2=?,meterDpiont3=?,meterDpiont4=?,meterDpiont5=?,meterDpiont6=?," +
				"imagePiont1=?,imagePiont2=?,imagePiont3=?,imagePiont4=?,imagePiont5=?,imagePiont6=?," +
				"imagePiont7=?,imagePiont8=?,imagePiont9=?,imagePiont10=?,imagePiont11=?,imagePiont12=?" +
				" where mid = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,"0.00"); ps.setString(7,"0.00");ps.setBytes(13,source);ps.setBytes(19,source);
		ps.setString(2,"0.40"); ps.setString(8,"0.41");ps.setBytes(14,source);ps.setBytes(20,source);
		ps.setString(3,"0.80"); ps.setString(9,"0.81");ps.setBytes(15,source);ps.setBytes(21,source);
		ps.setString(4,"1.20"); ps.setString(10,"1.21");ps.setBytes(16,source);ps.setBytes(22,source);
		ps.setString(5,"1.60"); ps.setString(11,"1.62");ps.setBytes(17,source);ps.setBytes(23,source);
		ps.setString(6,null); ps.setString(12,null);ps.setBytes(18,null);ps.setBytes(24,null);
		ps.setInt(25,70);
		int a = ps.executeUpdate();
		System.out.println(a);


//		DetectedDetail detail = new DetectedDetail();
//		detail.setDid(2);
//		detail.setBookWord(source);

//		Connection conn = sqlConnect.connect();
//		// 添加
//		String sql = "insert into Tbl_test(id,image) values(?,?) ";
//		PreparedStatement ps = conn.prepareStatement(sql);
//		ps.setInt(1,2);
//		ps.setBytes(2,source);
//		int result = ps.executeUpdate();
//		System.out.println(result == 1);

//		conn.close();
//		ps.close();

//		String sql = "select * from Tbl_test";
//		PreparedStatement ps = conn.prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next()){
//			InputStream is = rs.getBinaryStream("image");
//			File file = new File("d:\\bjjImages\\abc.jpg");
//			try (OutputStream os = new FileOutputStream(file)) {
//				byte[] buffer = new byte[1024];
//				int length;
//				while ((length = is.read(buffer)) != -1) {
//					os.write(buffer, 0, length);
//				}
//			}
//		}
//
//		sqlConnect.release(conn,ps,rs);

//		byte2image(source,"d:\\bjjImages\\abc.jpg");
	}

	//图片到byte数组
	public byte[] image2byte(String path){
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		}
		catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		}
		catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}


	//byte数组到图片
	public void byte2image(byte[] data,String path){
		if(data.length<3||path.equals("")) return;
		try{
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in " + path);
		} catch(Exception ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();
		}
	}
	//byte数组到16进制字符串
	public String byte2string(byte[] data){
		if(data==null||data.length<=1) return "0x";
		if(data.length>200000) return "0x";
		StringBuffer sb = new StringBuffer();
		int buf[] = new int[data.length];
		//byte数组转化成十进制
		for(int k=0;k<data.length;k++){
			buf[k] = data[k]<0?(data[k]+256):(data[k]);
		}
		//十进制转化成十六进制
		for(int k=0;k<buf.length;k++){
			if(buf[k]<16) sb.append("0"+Integer.toHexString(buf[k]));
			else sb.append(Integer.toHexString(buf[k]));
		}
		return "0x"+sb.toString().toUpperCase();
	}

	public static void saveToImgFile(String src,String output){
		if(src==null||src.length()==0){
			return;
		}
		try{
			FileOutputStream out = new FileOutputStream(new File(output));
			byte[] bytes = src.getBytes();
			for(int i=0;i<bytes.length;i+=2){
				out.write(charToInt(bytes[i])*16+charToInt(bytes[i+1]));
			}
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static int charToInt(byte ch){
		int val = 0;
		if(ch>=0x30&&ch<=0x39){
			val=ch-0x30;
		}else if(ch>=0x41&&ch<=0x46){
			val=ch-0x41+10;
		}
		return val;
	}
}
