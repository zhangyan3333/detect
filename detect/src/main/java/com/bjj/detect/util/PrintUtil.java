package com.bjj.detect.util;

//import com.loongxi.consumer.bean.DownloadInvokePacket;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.Sides;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

/**
 * @projectName: eiss_serve
 * @package: com.loongxi.test.print
 * @className: PrintUtil
 * @author: shicheng Pang
 * @description: 打印工具类
 * @date: 2023/8/25 9:10
 * @version: 1.0
 */
public class PrintUtil {

	public static void PrintFile(File file,String printerNames) throws Exception {
		String suffix = file.getName().split("\\.")[1];
		String path = file.getPath();

		String[] printerArr= printerNames.split(",");
		String newPath = "";
		for (String printerName : printerArr) {
			switch (suffix){
				case "png":
					printImageToPdf(path,printerName,"png");
					break;
				case "jpeg":
					printImageToPdf(path,printerName,"jpeg");
					break;
				case "pdf":
					printPDF(path,printerName);
					break;
				case "docx":
					printWord(path,printerName);
					break;
				case "doc":
					printWord(path,printerName);
					break;
				default:
					throw new Exception("不支持的打印类型！");
			}
		}

	}

	/**
	 * @param txtFilePath:源文件路径
	 * @param printerName: 打印机名，不传入则调用默认打印机
	 * @return void
	 * @author pangshicheng
	 * @description 打印文本、json（暂未完成）
	 * @date 2023/8/25 13:31
	 */
	public static void printText(String txtFilePath,String printerName) throws Exception{

		//todo 暂未完成
		File file = new File(txtFilePath);
		if (file == null) {
			System.err.println("缺少打印文件");
		}
		InputStream fis = null;

		try {

			PrintService printService = getPrinter(printerName);


			//配置
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintRequestAttributeSet option = new HashPrintRequestAttributeSet();
			option.add(new Copies(1));  //份数
			option.add(Sides.DUPLEX);//单双面


			byte[] fileBytes;
			fis = new FileInputStream(txtFilePath);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int bytesRead;
			byte[] buffer = new byte[4096]; // Adjust buffer size as needed
			while ((bytesRead = fis.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			fileBytes = outputStream.toByteArray();


			Doc doc = new SimpleDoc(fileBytes, flavor, null);

			DocPrintJob job = printService.createPrintJob(); // 创建打印作业
			job.print(doc, option);


		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("打印文件出错：" + e.getMessage());
		} finally {
			// 关闭打印的文件流
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param wordFilePath:源文件路径
	 * @param printerName:打印机名，不传入则调用默认打印机
	 * @return void
	 * @author pangshicheng
	 * @description 打印word
	 * @date 2023/8/25 10:19
	 */
	public static void printWord(String wordFilePath,String printerName) throws Exception{
		try {
			String newPath = "G:\\a.pdf";
			//将word打印成pdf
			String pdfPath = changeWordToPDF(wordFilePath,newPath);
			//调用打印pdf方法
			printPDF(newPath,"test");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("打印文件出错：" + e.getMessage());
		}
	}

	/**
	 * @param filePath: 源文件路径
	 * @param printerName: 打印机名，不传入则调用默认打印机
	 * @return void
	 * @author pangshicheng
	 * @description 打印pdf文件
	 * @date 2023/8/25 10:11
	 */
	public static void printPDF(String filePath,String printerName) throws Exception {


		File file = new File(filePath);
		PDDocument document = null;
		try {
			document = PDDocument.load(file);
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setJobName("打印" + file.getName());

			// 查找并设置打印机
			PrintService printService = getPrinter(printerName);
			printJob.setPrintService(printService);


			Book book = PDFBookOption(document);
			printJob.setPageable(book);
			printJob.setCopies(1);//设置打印份数

			//添加打印属性
			PrintRequestAttributeSet option = getAttributeSetForPDF();
			printJob.print(option);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("打印文件出错：" + e.getMessage());
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * @param filePath:源文件路径
	 * @param printerName:打印机名，不传入则调用默认打印机
	 * @return void
	 * @author pangshicheng
	 * @description 打印图片文件（png jpeg）
	 * @date 2023/8/25 10:12
	 */
	public static void printImageToPdf(String filePath,String printerName,String suffix) throws Exception {
		File file = new File(filePath);
		if (file == null) {
			System.err.println("缺少打印文件");
		}
		InputStream fis = null;

		try {

			// 设置打印格式，如果未确定类型，可选择autosense
			DocFlavor flavor = getImageDocFlavor(suffix);
			// 设置打印参数
			PrintRequestAttributeSet aset = getAttributeSetForImage(suffix);
			// 定位打印服务
			PrintService printService = getPrinter(printerName);


			fis = new FileInputStream(file); // 构造待打印的文件流
			Doc doc = new SimpleDoc(fis, flavor, null);

			DocPrintJob job = printService.createPrintJob(); // 创建打印作业
			job.print(doc, aset);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("打印文件出错：" + e.getMessage());
		} finally {
			// 关闭打印的文件流
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * @param :
	 * @return ArrayList<String>
	 * @author pangshicheng
	 * @description 获取全部打印机名
	 * @date 2023/8/28 17:08
	 */
	public static ArrayList<String> getAllPrinter() throws Exception {
		// 定位打印服务
		PrintService printService = null;
		//创建一个过滤条件，只选择纯文本格式的打印机
		//DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
		//PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, null);


		//获得本台电脑连接的所有打印机
		PrintService[] printServices = PrinterJob.lookupPrintServices();
		ArrayList<String> names = new ArrayList<String>();
		for (PrintService printer : printServices){
			String name = printer.getName();
			names.add(name);
		}
		return names;
	}


	// 设置pdf打印参数
	private static PrintRequestAttributeSet getAttributeSetForPDF() throws URISyntaxException {
		// 设置打印参数
		PrintRequestAttributeSet option = new HashPrintRequestAttributeSet();
		option.add(Sides.DUPLEX);//单双面

		//设置虚拟打印机保存路径
		URI outputUri = getPDFPrinterRandomTmpFilePath("pdf");
//        option.add(new Destination(outputUri));

		return option;
	}

	//pdf的纸张相关参数
	private static Book PDFBookOption(PDDocument document) throws Exception {
		if(document == null){
			throw new Exception("文档不存在！");
		}
		//设置纸张及缩放
		PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);


		//设置多页打印
		Book book = new Book();
		PageFormat pageFormat = new PageFormat();


		//设置打印方向
		pageFormat.setOrientation(PageFormat.PORTRAIT);//纵向
		//pageFormat.setPaper();//设置纸张
		book.append(pdfPrintable, pageFormat, document.getNumberOfPages());

		return book;
	}

	// 设置image打印参数
	private static PrintRequestAttributeSet getAttributeSetForImage(String suffix) throws URISyntaxException {
		// 设置打印参数
		PrintRequestAttributeSet option = new HashPrintRequestAttributeSet();
		option.add(new Copies(1));  //份数
		//option.add(MediaSize.ISO.A4);  //纸张
		//option.add(Finishings.STAPLE);  //装订
		option.add(Sides.DUPLEX);//单双面

		//设置虚拟打印机保存路径
		URI outputUri = getPDFPrinterRandomTmpFilePath(suffix);
		option.add(new Destination(outputUri));

		return option;
	}


	//设置image打印结果格式
	private static DocFlavor getImageDocFlavor(String suffix) throws Exception {
		DocFlavor flavor = null;
		switch (suffix){
			case "png":
				flavor = DocFlavor.INPUT_STREAM.PNG;
				break;
			case "jpeg":
				flavor = DocFlavor.INPUT_STREAM.JPEG;
				break;
			default:
				//如果未确定类型，可选择autosense
				//flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
				throw new Exception("不支持的图片类型！");
		}
		return flavor;
	}

	//获取打印机
	private static PrintService getPrinter(String printerName) throws Exception {
		// 定位打印服务
		PrintService printService = null;
		if (printerName != null) {

//            // 创建一个过滤条件，只选择纯文本格式的打印机
//            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
//            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, null);

			//获得本台电脑连接的所有打印机
			PrintService[] printServices = PrinterJob.lookupPrintServices();
			if (printServices == null || printServices.length == 0) {
				throw new Exception("打印失败!未找到可用打印机");
			}
			//匹配指定打印机
			for (int i = 0; i < printServices.length; i++) {
				if (printServices[i].getName().contains(printerName)) {
					printService = printServices[i];

					System.out.println("找到打印机：" + printService.getName());
					System.out.println("打印机 描述: " + printService.toString());
					break;
				}
			}
			if (printService == null) {
				throw new Exception("打印失败!" + "未找到名称为" + printerName + "的打印机!");
				//System.out.print("未找到名称为" + printerName + "的打印机，正在调用默认打印机...");
				//PrintService defaultPrinter = PrintServiceLookup.lookupDefaultPrintService();
				//return defaultPrinter;
			}
		}else{
			throw new Exception("打印失败：" + "未选择打印机!");
			//System.out.print("未找到名称为" + printerName + "的打印机，正在调用默认打印机...");
			//printService = PrintServiceLookup.lookupDefaultPrintService();
		}
		return printService;
	}

//	//获取随机文件路径
//	private static String getRandomTmpFilePath(String suffix){
//		return DownloadInvokePacket.getRandomTmpFilePath(suffix);
//	}

	//获取pdf打印机的 缓存路径（适用于虚拟打印机的打印产出地址，理论上不影响物理打印机）
	public static URI getPDFPrinterRandomTmpFilePath(String suffix) throws URISyntaxException {
		String cacheFolderPath = System.getProperty("java.io.tmpdir");
		String outputFileName = + LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) +".pdf";

		String outputPath = "file:\\" + cacheFolderPath + outputFileName;

		URI outputUri = new URI(outputPath.replace("\\","/"));
		return outputUri;
	}

	//Aspose提供的工具来将Word转为PDF
	private static String changeWordToPDF(String filePath,String newPath) throws Exception{

//		String pdfPath = getRandomTmpFilePath("pdf");
//		AsposeUtil.wordToPdf(filePath,pdfPath,null);

		PdfUtil.docToPdf(filePath,newPath);

		return  newPath;
	}
}

