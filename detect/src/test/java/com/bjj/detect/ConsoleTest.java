//package com.bjj.detect;
//
////import com.aspose.words.Document;
//import com.bjj.detect.gptest.ListenerEvent;
////import com.bjj.detect.util.PdfUtil;
////import com.bjj.detect.util.PrintUtil;
//import com.bjj.detect.util.SqlConnect;
//import com.syzx.framework.utils.PrintUtil;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.printing.PDFPrintable;
//import org.apache.pdfbox.printing.Scaling;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationEventPublisher;
//
//import java.awt.*;
//import java.awt.print.Book;
//import java.awt.print.PageFormat;
//import java.awt.print.PrinterJob;
//import java.io.*;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.ArrayList;
//import java.util.List;
//import javax.print.Doc;
//import javax.print.DocFlavor;
//import javax.print.DocPrintJob;
//import javax.print.PrintException;
//import javax.print.PrintService;
//import javax.print.PrintServiceLookup;
//import javax.print.ServiceUI;
//import javax.print.SimpleDoc;
//import javax.print.attribute.DocAttributeSet;
//import javax.print.attribute.HashDocAttributeSet;
//import javax.print.attribute.HashPrintRequestAttributeSet;
//import javax.print.attribute.PrintRequestAttributeSet;
//import javax.print.attribute.standard.Sides;
//
//@SpringBootTest
//public class ConsoleTest {
//
//	@Autowired
//	private ApplicationEventPublisher publisher;
//
//	@Test
//	public void go() {
//		ListenerEvent listenerEvent = new ListenerEvent(this);
//		listenerEvent.setMessage("测试");
////		publisher.publishEvent(listenerEvent);
////		otherTest.setName("aaa");
//	}
//
//	@Autowired
//	private SqlConnect sqlConnect;
//
//	@Test
//	public void testAspect() {
//		List<Integer> ins = new ArrayList<>();
//		ins.add(1);
//		ins.add(2);
//		System.out.println(ins.indexOf(3));
//		System.out.println(ins.indexOf(2));
//		System.out.println(ins.indexOf(1));
//	}
//
//	@Test
//	public void testEnvironment(){
//		if (GraphicsEnvironment.isHeadless()) {
//			System.err.println("This environment does not support AWT components.");
//			// Handle the headless environment appropriately
//		} else {
//			// Create and use AWT components
//			// ...
//		}
//	}
//
//	/**
//	 * @param :
//	 * @return: void
//	 * @description: 解决思路是Tomcat运行时会把headless这个设置为true，然后java.awt包里函数调用时就会产生异常。
//	 *  如果打印方法不可用，修改Tomcat中的catalina.bat文件，编辑该文件并在最开头添加一行
//	 *  set JAVA_OPTS=-Djava.awt.headless=false
//	 * @author: zhangyan
//	 * @date: 2024/7/15 1:23
//	**/
//	@Test
//	public void printTest() {
//		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
//		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
//		PrintService service = ServiceUI.printDialog(null, 200, 200, pservices,
//				defaultService, flavor, aset);
//		if (service != null) {
//			try {
//				DocPrintJob pj = service.createPrintJob();
//				FileInputStream fis = new FileInputStream("G:" + File.separator + "检定证书.docx");//打印D盘zhidao.txt文档。
//				DocAttributeSet das = new HashDocAttributeSet();
//				Doc doc = new SimpleDoc(fis, flavor, das);
//				pj.print(doc, aset);
//			} catch (FileNotFoundException | PrintException fe) {
//				System.out.println("---------not find");
//			}
//		} else {
//			System.out.println("打印失败");
//		}
//	}
//
//	public void wordToPdf(String wordPath,String pdfPath) throws IOException {
//		FileOutputStream os = null;
//		try {
//			long old = System.currentTimeMillis();
//			File file = new File(pdfPath);
//			os = new FileOutputStream(file);
//			Document doc = new Document(wordPath);
//			doc.save(os, com.aspose.words.SaveFormat.PDF);
//			long now = System.currentTimeMillis();
//			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(os!=null) {
//				os.close();
//			}
//		}
//	}
//
//	@Test
//	public void testPrint2() throws Exception {
//		wordToPdf("G:\\检定证书.docx","G:\\检定证书.pdf");
//
//
//		File file = new File("G:\\检定证书.pdf");
//		PDDocument document = null;
//		try {
//			document = PDDocument.load(file);
//			PrinterJob printJob = PrinterJob.getPrinterJob();
//			printJob.setJobName("打印" + file.getName());
//
//			PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
//			// 查找并设置打印机
//			PrintService printService = getPrinter(defaultService.getName());
//			printJob.setPrintService(printService);
//
//
//			Book book = PDFBookOption(document);
//			printJob.setPageable(book);
//			printJob.setCopies(1);//设置打印份数
//
//			//添加打印属性
//			PrintRequestAttributeSet option = getAttributeSetForPDF();
//			printJob.print(option);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("打印文件出错：" + e.getMessage());
//		} finally {
//			if (document != null) {
//				try {
//					document.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	// 设置pdf打印参数
//	private static PrintRequestAttributeSet getAttributeSetForPDF() throws URISyntaxException {
//		// 设置打印参数
//		PrintRequestAttributeSet option = new HashPrintRequestAttributeSet();
//		option.add(Sides.DUPLEX);//单双面
//
//		//设置虚拟打印机保存路径
//		URI outputUri = getPDFPrinterRandomTmpFilePath("pdf");
////        option.add(new Destination(outputUri));
//
//		return option;
//	}
//
//	//获取pdf打印机的 缓存路径（适用于虚拟打印机的打印产出地址，理论上不影响物理打印机）
//	public static URI getPDFPrinterRandomTmpFilePath(String suffix) throws URISyntaxException {
//		String cacheFolderPath = System.getProperty("java.io.tmpdir");
//		String outputFileName = + LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) +".pdf";
//
//		String outputPath = "file:\\" + cacheFolderPath + outputFileName;
//
//		URI outputUri = new URI(outputPath.replace("\\","/"));
//		return outputUri;
//	}
//
//	//pdf的纸张相关参数
//	private static Book PDFBookOption(PDDocument document) throws Exception {
//		if(document == null){
//			throw new Exception("文档不存在！");
//		}
//		//设置纸张及缩放
//		PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
//
//
//		//设置多页打印
//		Book book = new Book();
//		PageFormat pageFormat = new PageFormat();
//
//
//		//设置打印方向
//		pageFormat.setOrientation(PageFormat.PORTRAIT);//纵向
//		//pageFormat.setPaper();//设置纸张
//		book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
//
//		return book;
//	}
//
//	//获取打印机
//	private static PrintService getPrinter(String printerName) throws Exception {
//		// 定位打印服务
//		PrintService printService = null;
//		if (printerName != null) {
//
////            // 创建一个过滤条件，只选择纯文本格式的打印机
////            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
////            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, null);
//
//			//获得本台电脑连接的所有打印机
//			PrintService[] printServices = PrinterJob.lookupPrintServices();
//			if (printServices == null || printServices.length == 0) {
//				throw new Exception("打印失败!未找到可用打印机");
//			}
//			//匹配指定打印机
//			for (int i = 0; i < printServices.length; i++) {
//				if (printServices[i].getName().contains(printerName)) {
//					printService = printServices[i];
//
//					System.out.println("找到打印机：" + printService.getName());
//					System.out.println("打印机 描述: " + printService.toString());
//					break;
//				}
//			}
//			if (printService == null) {
//				throw new Exception("打印失败!" + "未找到名称为" + printerName + "的打印机!");
//				//System.out.print("未找到名称为" + printerName + "的打印机，正在调用默认打印机...");
//				//PrintService defaultPrinter = PrintServiceLookup.lookupDefaultPrintService();
//				//return defaultPrinter;
//			}
//		}else{
//			throw new Exception("打印失败：" + "未选择打印机!");
//			//System.out.print("未找到名称为" + printerName + "的打印机，正在调用默认打印机...");
//			//printService = PrintServiceLookup.lookupDefaultPrintService();
//		}
//		return printService;
//	}
//}
