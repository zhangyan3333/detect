package com.bjj.detect.serviceimpl;

import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.query.PgRecordQuery;
import com.bjj.detect.service.FilesService;
import com.bjj.detect.service.PgRecordService;
import com.syzx.framework.config.FrameworkConfig;
import com.syzx.framework.exceptions.BusinessException;
import com.syzx.framework.query.QueryResult;
import com.syzx.framework.uid.CentralizedUidGenerator;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FilesServiceImpl implements FilesService {

	private static final DateTimeFormatter datePathFormat = DateTimeFormatter.ofPattern("yyyy/MM");

	@Autowired
	private PgRecordService pgRecordService;

	@Override
	public String saveBasicFile(MultipartFile file) {
		String datePath = LocalDate.now().format(datePathFormat);
		File folder = new File(String.format("%s%s", FrameworkConfig.dataPath, datePath));
		if (!folder.exists() && !folder.mkdirs()) {
			throw new BusinessException(1, "无法创建目录");
		} else {
			String oldName = file.getOriginalFilename();
			String newName = CentralizedUidGenerator.getId() + oldName.substring(oldName.lastIndexOf("."));
			String suffix = oldName.substring(oldName.lastIndexOf(".") + 1);

			try {
				if (    suffix.equals("docx") ||
						suffix.equals("doc") ||
						suffix.equals("xls") ||
						suffix.equals("xlsx") ||
						suffix.equals("pdf") ||
						suffix.equals("png") ||
						suffix.equals("jpg")) {
					file.transferTo(new File(folder, newName));
					return String.format("%s/%s", datePath, newName);
				}else {
					throw new BusinessException(1, "无法上传该类型文件");
				}
			} catch (IOException var7) {
				throw new BusinessException(1, "无法上传文件");
			}
		}
	}

	@Override
	public String saveFaceFile(MultipartFile file) {
		String datePath = LocalDate.now().format(datePathFormat);
		File folder = new File(String.format("%s%s", FrameworkConfig.dataPath, datePath));
		if (!folder.exists() && !folder.mkdirs()) {
			throw new BusinessException(1, "无法创建目录");
		} else {
			String oldName = file.getOriginalFilename();
			String newName = CentralizedUidGenerator.getId() + oldName.substring(oldName.lastIndexOf("."));
			String suffix = oldName.substring(oldName.lastIndexOf(".") + 1);

			try {
				if (
						suffix.equals("png") ||
						suffix.equals("jpg")) {
					file.transferTo(new File(folder, newName));
					return String.format("%s/%s", datePath, newName);
				}else {
					throw new BusinessException(1, "无法上传该类型文件");
				}
			} catch (IOException var7) {
				throw new BusinessException(1, "无法上传文件");
			}
		}
	}


	public boolean deleteFile(String filePath) {
		File file = new File(String.format("%s%s", FrameworkConfig.dataPath, filePath));
		if (file.exists()) {
//			contractFileService.deleteByPath(filePath);
			return file.delete();
		} else {
			throw new BusinessException(1, "指定文件不存在");
		}
	}

	@Override
	public String exportDetectRecord(Object o,int index) {
		String filePath = "";
		if (index == 0 ){ filePath = FrameworkConfig.dataPath + "2024/template" + "/一般压力表原始记录.docx";}
		if (index == 1 ){ filePath = FrameworkConfig.dataPath + "2024/template" + "/检定证书.docx";}
		if (index == 2 ){ filePath = FrameworkConfig.dataPath + "2024/template" + "/检定结果通知书.docx";}
		String returnPath = "";
		try{
			//调用上面写的方法prepareFile把文件名传入
			InputStream template = new FileInputStream(filePath);
			//创建XWPFDocument对象，表示Word文档
			XWPFDocument document = new XWPFDocument(template);
			//获取所有段落
			List<XWPFParagraph> paragraphList = document.getParagraphs();
			PgRecord record = (PgRecord) o;

			replaceDetectRecordParagraph(document,record);

			replaceDetectRecordExcel(document,record);

			String temp = "/检定证书" + record.getMeterFactory() + String.valueOf(System.currentTimeMillis()) + ".docx";

			String newFilePath = FrameworkConfig.dataPath + LocalDate.now().format(datePathFormat) + temp;

			OutputStream output = new FileOutputStream(newFilePath);
			// 将填充后的文档写入输出流
			document.write(output);
			// 关闭模板输入流和文档
			template.close();
			document.close();
			output.flush();
			output.close();

			returnPath = LocalDate.now().format(datePathFormat) + temp;
			record.setRecordFile(returnPath);
			pgRecordService.update(record);
		}catch (Exception e){
			e.printStackTrace();
		}


		return returnPath;
	}
























	public void replaceDetectRecordParagraph(XWPFDocument document,PgRecord record) throws IOException {
		//获取所有段落
		List<XWPFParagraph> paragraphList = document.getParagraphs();
		//遍历段落
		for (int i = 0; i < paragraphList.size(); i++) {
			//获取每个段落
			XWPFParagraph paragraph = paragraphList.get(i);
			//获取每个段落中的内容
			List<XWPFRun> runs = paragraph.getRuns();
			Date date = record.getDetectTime();
			//循环段落内容
			for (int i1 = 0; i1 < runs.size(); i1++) {
				//调用自定义方法replacePlaceholderV2替换内容
				if (runs.get(i1).toString().contains("detectCode")) { replaceParagraphText(runs.get(i1), "detectCode", record.getDetectCode()); }
				if (runs.get(i1).toString().contains("ude")) { replaceParagraphText(runs.get(i1), "ude", record.getInspector()); }
				if (runs.get(i1).toString().contains("upe")) { replaceParagraphText(runs.get(i1), "upe", record.getVerifier()); }
				if (runs.get(i1).toString().contains("yy")) { replaceParagraphText(runs.get(i1), "yy", String.format("%tY", date)); }
				if (runs.get(i1).toString().contains("mm")) { replaceParagraphText(runs.get(i1), "mm", String.format("%tm", date)); }
				if (runs.get(i1).toString().contains("dd")) { replaceParagraphText(runs.get(i1), "dd", String.format("%td", date)); }

			}
		}
	}

	public void replaceDetectRecordCellParagraph1(XWPFTableCell cell, PgRecord record) throws IOException {
		//获取所有段落
		List<XWPFParagraph> paragraphList = cell.getParagraphs();
		//遍历段落
		for (int i = 0; i < paragraphList.size(); i++) {
			//获取每个段落
			XWPFParagraph paragraph = paragraphList.get(i);
//			// 设置段落对齐方式为居左
//			paragraph.setAlignment(ParagraphAlignment.LEFT);
			//获取每个段落中的内容
			List<XWPFRun> runs = paragraph.getRuns();
			//循环段落内容
			for (int i1 = 0; i1 < runs.size(); i1++) {
				//调用自定义方法replacePlaceholderV2替换内容
				if (runs.get(i1).toString().contains("a")) { replaceParagraphText(runs.get(i1), "a", record.getAppearance()); }
				if (runs.get(i1).toString().contains("b")) { replaceParagraphText(runs.get(i1), "b", record.getPointer()); }

				if (runs.get(i1).toString().contains("h")) { replaceParagraphText(runs.get(i1), "h", String.valueOf(record.getIndicationErrorMax())); }
				if (runs.get(i1).toString().contains("i")) { replaceParagraphText(runs.get(i1), "i", String.valueOf(record.getReturnErrorMax())); }
				if (runs.get(i1).toString().contains("j")) { replaceParagraphText(runs.get(i1), "j", String.valueOf(record.getPositionMax())); }
				if (runs.get(i1).toString().contains("k")) { replaceParagraphText(runs.get(i1), "k", String.valueOf(record.getIndicationErrorPermit())); }
				if (runs.get(i1).toString().contains("l")) { replaceParagraphText(runs.get(i1), "l", String.valueOf(record.getReturnErrorPermit())); }
				if (runs.get(i1).toString().contains("m")) { replaceParagraphText(runs.get(i1), "m", String.valueOf(record.getPositionPermit())); }

				if (runs.get(i1).toString().contains("o")) { replaceParagraphText(runs.get(i1), "o", String.valueOf(record.getIndicationErrorUpper())); }
				if (runs.get(i1).toString().contains("p")) { replaceParagraphText(runs.get(i1), "p", String.valueOf(record.getReturnErrorUpper())); }
				if (runs.get(i1).toString().contains("q")) { replaceParagraphText(runs.get(i1), "q", String.valueOf(record.getPositionUpper())); }
				if (runs.get(i1).toString().contains("r")) { replaceParagraphText(runs.get(i1), "r", String.valueOf(record.getIndicationErrorUpperPermit())); }
				if (runs.get(i1).toString().contains("s")) { replaceParagraphText(runs.get(i1), "s", String.valueOf(record.getReturnErrorUpperPermit())); }
				if (runs.get(i1).toString().contains("t")) { replaceParagraphText(runs.get(i1), "t", String.valueOf(record.getPositionUpperPermit())); }

				if (runs.get(i1).toString().contains("u")) { replaceParagraphText(runs.get(i1), "u", String.valueOf(record.getDetectLevel())); }

				if (runs.get(i1).toString().contains("x")) {
					if (record.getDetectResult() == 0){
						replaceParagraphText(runs.get(i1), "x", "☑");
					}else {
						replaceParagraphText(runs.get(i1), "x", "□");
					}
				}
				if (runs.get(i1).toString().contains("y")) {
					if (record.getDetectResult() == 1){
						replaceParagraphText(runs.get(i1), "y", "☑");
					}else {
						replaceParagraphText(runs.get(i1), "y", "□");
					}
				}
			}
		}
	}

	public void replaceDetectRecordCellParagraph2(XWPFTableCell cell,PgRecord record) throws IOException {
		//获取所有段落
		List<XWPFParagraph> paragraphList = cell.getParagraphs();
		//遍历段落
		for (int i = 0; i < paragraphList.size(); i++) {
			//获取每个段落
			XWPFParagraph paragraph = paragraphList.get(i);
			//获取每个段落中的内容
			List<XWPFRun> runs = paragraph.getRuns();
			Date date = record.getSEdate();
			//循环段落内容
			for (int i1 = 0; i1 < runs.size(); i1++) {
				if (runs.get(i1).toString().contains("yy")) { replaceParagraphText(runs.get(i1), "yy", String.format("%tY", date)); }
				if (runs.get(i1).toString().contains("mm")) { replaceParagraphText(runs.get(i1), "mm", String .format("%tm", date)); }
				if (runs.get(i1).toString().contains("dd")) { replaceParagraphText(runs.get(i1), "dd", String .format("%td", date)); }
			}
		}
	}

	public void replaceDetectRecordExcel(XWPFDocument document,PgRecord record) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		int tableSize = document.getTables().size();
		for (int m = 0; m < tableSize; m++) {
			XWPFTable table = document.getTables().get(m);
			for (int i = 0; i < table.getRows().size(); i++) {
				XWPFTableRow newRow = table.getRow(i);
				List<XWPFTableCell> cells = newRow.getTableCells();

				for (int j = 0; j < cells.size(); j++) {
					XWPFTableCell cell = cells.get(j);
					String text = cell.getText();
					if (text.equals("meterCustomer")){ replaceCellValue(cell,"meterCustomer",record.getMeterCustomer()); }
					if (text.equals("meterCode")){ replaceCellValue(cell,"meterCode",record.getMeterCode()); }
					if (text.equals("ladnh")){ replaceCellValue(cell,"ladnh",record.getMeterRangeL()+"～"+record.getMeterRangeH()); }
					if (text.equals("scode")){ replaceCellValue(cell,"scode",record.getScode()); }
					if (text.equals("mr")){ replaceCellValue(cell,"mr",record.getMeterResolution() + "级"); }
					if (text.equals("mdn")){ replaceCellValue(cell,"mdn",record.getMeterDivisionNo()); }
					if (text.equals("standardLoc")){ replaceCellValue(cell,"standardLoc",record.getStandardLoc()); }
					if (text.equals("meterFactory")){ replaceCellValue(cell,"meterFactory",record.getMeterFactory()); }
					if (text.equals("sBasis")){ replaceCellValue(cell,"sBasis",record.getSBasis()); }
					if (text.equals("stx")){ replaceCellValue(cell,"stx",String.valueOf(record.getSTemperature())+"℃"); }
					if (text.equals("shx")){ replaceCellValue(cell,"shx",String.valueOf(record.getSHumidity())+"％RH"); }
					if (text.equals("qte")){ replaceCellValue(cell,"qte",String.valueOf(record.getRemark())); }

					for (int k = 0; k < 6; k++) {
						if (k < record.getInfos().size()){
							PgInfo info = record.getInfos().get(k);
							if (text.equals("A"+(k+1))){ replaceCellValue(cell,"A"+(k+1),String.valueOf(info.getSPressure()));continue; }
							if (text.equals("B"+(k+1))){ replaceCellValue(cell,"B"+(k+1),String.valueOf(info.getStrikeUp()));continue; }
							if (text.equals("C"+(k+1))){ replaceCellValue(cell,"C"+(k+1),String.valueOf(info.getReturnError()));continue; }
							if (text.equals("D"+(k+1))){ replaceCellValue(cell,"D"+(k+1),String.valueOf(info.getPositionUp()));continue; }
							if (text.equals("E"+(k+1))){ replaceCellValue(cell,"E"+(k+1),String.valueOf(info.getPositionDown()));continue; }
							if (text.equals("F"+(k+1))){ replaceCellValue(cell,"F"+(k+1),String.valueOf(info.getIndicationError()));continue; }
							if (text.equals("G"+(k+1))){ replaceCellValue(cell,"G"+(k+1),String.valueOf(info.getReturnError()));continue; }
						}else {
							if (text.equals("A"+(k+1))){ replaceCellValue(cell,"A"+(k+1),"");continue; }
							if (text.equals("B"+(k+1))){ replaceCellValue(cell,"B"+(k+1),"");continue; }
							if (text.equals("C"+(k+1))){ replaceCellValue(cell,"C"+(k+1),"");continue; }
							if (text.equals("D"+(k+1))){ replaceCellValue(cell,"D"+(k+1),"");continue; }
							if (text.equals("E"+(k+1))){ replaceCellValue(cell,"E"+(k+1),"");continue; }
							if (text.equals("F"+(k+1))){ replaceCellValue(cell,"F"+(k+1),"");continue; }
							if (text.equals("G"+(k+1))){ replaceCellValue(cell,"G"+(k+1),"");continue; }
						}
					}

					if (text.indexOf("检定结论")>-1){
						replaceDetectRecordCellParagraph1(cell,record);
					}

					if (text.indexOf("yy 年")>-1){
						replaceDetectRecordCellParagraph2(cell,record);
					}
					if (text.equals("poi")){ replaceCellValue(cell,"app","asdasfasf"); }
					//表格样式一致-->没有此段表格会默认左对齐
					//有此段会使表格格式一致
//					CTTc cttc = cell.getCTTc();
//					CTTcPr ctPr = cttc.addNewTcPr();
//					ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
//					cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
				}
			}
		}
	}

	/**
	 * @param cell:
	 * @param placeholder:
	 * @param replacement:
	 * @return: void
	 * @description:  通用 读取 cell单元格内容
	 * @author: zhangyan
	 * @date: 2024/7/16 15:12
	**/
	public void replaceCellValue(XWPFTableCell cell, String placeholder, String replacement){
		if (cell.getText().equals(placeholder)) {
			List<XWPFParagraph> paragraphs = cell.getParagraphs();
			for (XWPFParagraph paragraph : paragraphs) {
				String parText = paragraph.getText();//段落文字
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					String tempText = run.getText(run.getTextPosition());
					if (tempText != null && tempText.equals(parText.substring(0,1))) {
						run.setText("", 0);
					}else {
						run.setText(replacement, 0);
					}
				}
			}
		}
	}

	/**
	 * @param run:
	 * @param placeholder:
	 * @param replacement:
	 * @return: void
	 * @description: 读取段落文本，不是表格中的文本
	 * @author: zhangyan
	 * @date: 2024/7/16 15:13
	**/
	public void replaceParagraphText(XWPFRun run, String placeholder, String replacement) throws IOException {
		// 获取当前XWPFRun对象的文本内容
		String text = run.getText(0);
		// 判断文本内容是否包含占位符
		if (text != null && text.contains(placeholder)) {
			// 替换文本中的占位符为指定的替换内容，并更新XWPFRun的文本内容
			run.setText(text.replace(placeholder, replacement.replace("[", "").replace("]", "")), 0);
		}
	}
}
