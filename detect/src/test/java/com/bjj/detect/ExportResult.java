package com.bjj.detect;

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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ExportResult {

	@Autowired
	private PgRecordService pgRecordService;

	@Test
	public void consoleDetectRecord() throws IOException {
		//调用上面写的方法prepareFile把文件名传入
		InputStream template = new FileInputStream("G:\\mqs\\检定结果通知书.docx");
		//创建XWPFDocument对象，表示Word文档
		XWPFDocument document = new XWPFDocument(template);

		PgRecordQuery query = new PgRecordQuery();
		QueryResult<PgRecord> result = pgRecordService.pageByQuery(query);
		PgRecord record = result.getEntities().get(0);

		replaceResultParagraph(document,record);

		replaceResultExcel(document,record);

		OutputStream output = new FileOutputStream("G:\\mqs\\test.docx");
		// 将填充后的文档写入输出流
		document.write(output);
		// 关闭模板输入流和文档
		template.close();
		document.close();
	}

	public void replaceResultParagraph(XWPFDocument document,PgRecord record) throws IOException {
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

				if (runs.get(i1).toString().contains("approver")) { replaceParagraphText(runs.get(i1), "approver", record.getApprover()); }
				if (runs.get(i1).toString().contains("verifier")) { replaceParagraphText(runs.get(i1), "verifier", record.getVerifier()); }
				if (runs.get(i1).toString().contains("inspector")) { replaceParagraphText(runs.get(i1), "inspector", record.getInspector()); }
				if (runs.get(i1).toString().contains("yy")) { replaceParagraphText(runs.get(i1), "yy", String.format("%tY", date)); }
				if (runs.get(i1).toString().contains("mm")) { replaceParagraphText(runs.get(i1), "mm", String.format("%tm", date)); }
				if (runs.get(i1).toString().contains("dd")) { replaceParagraphText(runs.get(i1), "dd", String.format("%td", date)); }

			}

		}
	}



	public void replaceResultExcel(XWPFDocument document,PgRecord record) throws IOException {
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
					System.out.println(text);
					if (text.equals("meterCustomer")){ replaceCellValue(cell,"meterCustomer",record.getMeterCustomer()); }
					if (text.equals("meterName")){ replaceCellValue(cell,"meterName",record.getMeterName()); }
					if (text.equals("meterType")){ replaceCellValue(cell,"meterType",record.getMeterType()); }
					if (text.equals("meterCode")){ replaceCellValue(cell,"meterCode",record.getMeterCode()); }
					if (text.equals("meterFactory")){ replaceCellValue(cell,"meterFactory",record.getMeterFactory()); }
					if (text.equals("sBasis")){ replaceCellValue(cell,"sBasis",record.getSBasis().split("《")[0]); }
					if (text.equals("detectResult")){ replaceCellValue(cell,"detectResult",record.getDetectResult()==0?"合格":"不合格"); }

					if (text.equals("sbasis")){ replaceCellValue(cell,"sbasis",record.getSBasis()); }
					if (text.equals("standardLoc")){ replaceCellValue(cell,"standardLoc",record.getStandardLoc()); }
					if (text.equals("stt")){ replaceCellValue(cell,"stt",String.valueOf(record.getSTemperature())); }
					if (text.equals("shh")){ replaceCellValue(cell,"shh",String.valueOf(record.getSHumidity())); }
//
//					if (text.equals("aa")){ replaceCellValue(cell,"aa",String.valueOf(record.getSHumidity())); }  // 零位误差
					if (text.equals("bb")){ replaceCellValue(cell,"bb",String.valueOf(record.getIndicationErrorMax())); }
					if (text.equals("cc")){ replaceCellValue(cell,"cc",String.valueOf(record.getReturnErrorMax())); }
					if (text.equals("dd")){ replaceCellValue(cell,"dd",String.valueOf(record.getPositionMax())); }

//					if (text.equals("UnqualifiedItem")){ replaceCellValue(cell,"UnqualifiedItem",String.valueOf(record.get())); }

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
