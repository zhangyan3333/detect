package com.bjj.detect;

import com.bjj.detect.dao.PgCertificateDao;
import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.PgInfo;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.query.PgRecordQuery;
import com.bjj.detect.service.PgRecordService;
import com.syzx.framework.query.QueryResult;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ExportResult {

	@Autowired
	private PgRecordService pgRecordService;

	@Autowired
	private PgCertificateDao certificateDao;

	@Resource
	private Environment environment;

	@Test
	public void consoleDetectRecord() throws IOException {
		//调用上面写的方法prepareFile把文件名传入
		InputStream template = new FileInputStream("G:\\mqs\\检定结果通知书.docx");
		//创建XWPFDocument对象，表示Word文档
		XWPFDocument document = new XWPFDocument(template);

		PgCertificate certificate = certificateDao.get().get(0);

		replaceResultParagraph(document,2,certificate);

		replaceResultExcel(document,2,certificate);

		OutputStream output = new FileOutputStream("G:\\mqs\\test.docx");
		// 将填充后的文档写入输出流
		document.write(output);
		// 关闭模板输入流和文档
		template.close();
		document.close();
	}

	public void replaceResultParagraph(XWPFDocument document, int index, PgCertificate pgCertificate) throws IOException {

		//获取所有段落
		List<XWPFParagraph> paragraphList = document.getParagraphs();
		//遍历段落
		for (int i = 0; i < paragraphList.size(); i++) {
			//获取每个段落
			XWPFParagraph paragraph = paragraphList.get(i);
			//获取每个段落中的内容
			List<XWPFRun> runs = paragraph.getRuns();
			Date date = pgCertificate.getDetectDate();

			Calendar end = Calendar.getInstance();
			end.setTime(date);
			end.add(Calendar.MONTH, Integer.valueOf(environment.getProperty("step")));
			Date endDate = end.getTime();
			//循环段落内容
			for (int i1 = 0; i1 < runs.size(); i1++) {
				String text = runs.get(i1).toString();
				//调用自定义方法replacePlaceholderV2替换内容
				if (runs.get(i1).toString().contains("detectCode")) { replaceParagraphText(runs.get(i1), "detectCode", pgCertificate.getCertCode()); }

				if (runs.get(i1).toString().contains("approver")) { replaceParagraphText(runs.get(i1), "approver", pgCertificate.getApprover()); }
				if (runs.get(i1).toString().contains("verifier")) { replaceParagraphText(runs.get(i1), "verifier", pgCertificate.getVerifier()); }
				if (runs.get(i1).toString().contains("inspector")) { replaceParagraphText(runs.get(i1), "inspector", pgCertificate.getInspector()); }
				if (runs.get(i1).toString().contains("yy")) { replaceParagraphText(runs.get(i1), "yy", String.format("%tY", date)); }
				if (runs.get(i1).toString().contains("mm")) { replaceParagraphText(runs.get(i1), "mm", String.format("%tm", date)); }
				if (runs.get(i1).toString().contains("dd")) { replaceParagraphText(runs.get(i1), "dd", String.format("%td", date)); }
				if (index == 1 && runs.get(i1).toString().contains("g")) { replaceParagraphText(runs.get(i1), "g", String.format("%tY", endDate)); }
				if (index == 1 && runs.get(i1).toString().contains("yb")) { replaceParagraphText(runs.get(i1), "yb", String.format("%tm", endDate)); }
				if (index == 1 && runs.get(i1).toString().contains("yc")) { replaceParagraphText(runs.get(i1), "yc", String.format("%td", endDate)); }
			}

		}
	}


	public void replaceResultExcel(XWPFDocument document, int index, PgCertificate pgCertificate) throws IOException {
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

					if (text.equals("meterCustomer")){ replaceCellValue(cell,"meterCustomer",pgCertificate.getMeterCustomer()); }
					if (text.equals("meterName")){ replaceCellValue(cell,"meterName",pgCertificate.getMeterName()); }
					if (text.equals("meterType")){ replaceCellValue(cell,"meterType",pgCertificate.getMeterType()); }
					if (text.equals("meterCode")){ replaceCellValue(cell,"meterCode",pgCertificate.getMeterCode()); }
					if (text.equals("meterFactory")){ replaceCellValue(cell,"meterFactory",pgCertificate.getMeterFactory()); }
					if (text.equals("sBasis")){ replaceCellValue(cell,"sBasis",pgCertificate.getSBasis().split("《")[0]); }
					if (text.equals("detectResult")){ replaceCellValue(cell,"detectResult",pgCertificate.getDetectResult()==0?"合格":"不合格"); }

					if (text.equals("sbasis")){ replaceCellValue(cell,"sbasis",pgCertificate.getSBasis()); }
					if (text.equals("standardLoc")){ replaceCellValue(cell,"standardLoc",pgCertificate.getStandardLoc()); }
					if (text.equals("stt")){ replaceCellValue(cell,"stt",String.valueOf(pgCertificate.getSTemperature())); }
					if (text.equals("shh")){ replaceCellValue(cell,"shh",String.valueOf(pgCertificate.getSHumidity())); }

					if (text.equals("an")){ replaceCellValue(cell,"an",String.valueOf(pgCertificate.getSname())); }
					if (text.equals("ar")){ replaceCellLnValue(cell,"ar",String.valueOf(pgCertificate.getSRange())); }
					if (text.equals("al")){ replaceCellValue(cell,"al",String.valueOf(pgCertificate.getSResolution()) + "级"); }
					if (text.equals("ac")){ replaceCellValue(cell,"ac",String.valueOf(pgCertificate.getSRegulateCode())); }
					if (text.equals("ad")){ replaceCellValue(cell,"ad",String.valueOf(pgCertificate.getSEdate())); }

					if (text.equals("bn")){ replaceCellValue(cell,"bn",String.valueOf(pgCertificate.getMname())); }
					if (text.equals("br")){ replaceCellValue(cell,"br",String.valueOf(pgCertificate.getMRange())); }
					if (text.equals("bl")){ replaceCellValue(cell,"bl",String.valueOf(pgCertificate.getMResolution()) + "级"); }
					if (text.equals("bc")){ replaceCellValue(cell,"bc",String.valueOf(pgCertificate.getSFactory() + " " + pgCertificate.getSRegulateBcode())); }
					if (text.equals("bd")){ replaceCellValue(cell,"bd",String.valueOf(pgCertificate.getSBdate())); }


					if (text.equals("sa")){ replaceCellValue(cell,"sa",String.valueOf(pgCertificate.getZeroErrorMax())); }  // 零位误差
					if (text.equals("sb")){ replaceCellValue(cell,"sb",String.valueOf(pgCertificate.getIndicationErrorMax())); }
					if (text.equals("sc")){ replaceCellValue(cell,"sc",String.valueOf(pgCertificate.getReturnErrorMax())); }
					if (text.equals("sd")){ replaceCellValue(cell,"sd",String.valueOf(pgCertificate.getPositionMax())); }

					if (index == 2 && text.equals("UnqualifiedItem")){ replaceCellValue(cell,"UnqualifiedItem",String.valueOf(pgCertificate.getUnqualifiedItem())); }

				}
			}
		}
	}







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

	public void replaceCellLnValue(XWPFTableCell cell, String placeholder, String replacement){
		if (cell.getText().equals(placeholder)) {
			cell.removeParagraph(0);
			XWPFParagraph xwpfParagraph1 = cell.addParagraph();
			String[] split = replacement.split(",");
			xwpfParagraph1.setAlignment(ParagraphAlignment.CENTER);
			for (String s : split) {
				XWPFRun run = xwpfParagraph1.createRun();//对某个段落设置格式
				run.setText(s.trim());
				run.addBreak();//换行
			}
		}
	}


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
