package com.bjj.detect;

import org.apache.poi.xwpf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class OperateWordTest {

	@Test
	public void testTemplate() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		//调用上面写的方法prepareFile把文件名传入
		InputStream template = prepareFile("检定证书.docx");
	    //创建XWPFDocument对象，表示Word文档
		XWPFDocument document = new XWPFDocument(template);
		//获取所有段落
		List<XWPFParagraph> paragraphList = document.getParagraphs();
        //遍历段落
		for (int i = 0; i < paragraphList.size(); i++) {
			//获取每个段落
			XWPFParagraph paragraph = paragraphList.get(i);
			//获取每个段落中的内容
			List<XWPFRun> runs = paragraph.getRuns();
			//循环段落内容
			for (int i1 = 0; i1 < runs.size(); i1++) {
				//调用自定义方法replacePlaceholderV2替换内容
				if (runs.get(i1).toString().contains("name")) {
					replaceParagraphText(runs.get(i1), "name", "测试一下能不能成功");
				}

			}
		}

		replaceTable(document);


		OutputStream output = new FileOutputStream("G:\\mqs\\test.docx");
		// 将填充后的文档写入输出流
		document.write(output);
		// 关闭模板输入流和文档
		template.close();
		document.close();
	}

	/**
	 * 替换表格内容
	 * @param
	 * @Return void
	 * @Exception
	 */
	public void replaceTable(XWPFDocument document) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		int tableSize = document.getTables().size();
		for (int m = 0; m < tableSize; m++) {
			XWPFTable table = document.getTables().get(m);
			for (int i = 0; i < table.getRows().size(); i++) {
				XWPFTableRow newRow = table.getRow(i);
				List<XWPFTableCell> cells = newRow.getTableCells();

				for (int j = 0; j < cells.size(); j++) {
					XWPFTableCell cell = cells.get(j);

					if (cell.getText().equals("loc")){
						replaceCellValue(cell,"loc","测试地址");
					}
					if (cell.getText().equals("date")){
						replaceCellValue(cell,"date",sdf.format(new Date()));
					}
					if (cell.getText().equals("type")){
						replaceCellValue(cell,"type","测试类型");
					}

					//表格样式一致-->没有此段表格会默认左对齐
					//有此段会使表格格式一致
					CTTc cttc = cell.getCTTc();
					CTTcPr ctPr = cttc.addNewTcPr();
					ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
					cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
				}
			}
		}
	}


	//形参templateFileName是你模板名字
    //使用InputStream读取
	private InputStream prepareFile(String templateFileName) throws IOException {
		return new FileInputStream("G:\\mqs\\" + templateFileName);
	}

	/**
	 * @param run         文本内容
	 * @param placeholder 模板中自定义的占位符
	 * @param replacement 替换的内容
	 * @throws IOException
	 * @throws
	 */
	public void replaceParagraphText(XWPFRun run, String placeholder, String replacement) throws IOException {
		// 获取当前XWPFRun对象的文本内容
		String text = run.getText(0);
		// 判断文本内容是否包含占位符
		if (text != null && text.contains(placeholder)) {
			// 替换文本中的占位符为指定的替换内容，并更新XWPFRun的文本内容
			run.setText(text.replace(placeholder, replacement.replace("[", "").replace("]", "")), 0);
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


}
