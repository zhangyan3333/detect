package com.bjj.detect;

import com.bjj.detect.gptest.ListenerEvent;
import com.bjj.detect.util.SqlConnect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

@SpringBootTest
public class ConsoleTest {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Test
	public void go() {
		ListenerEvent listenerEvent = new ListenerEvent(this);
		listenerEvent.setMessage("测试");
//		publisher.publishEvent(listenerEvent);
//		otherTest.setName("aaa");
	}

	@Autowired
	private SqlConnect sqlConnect;

	@Test
	public void testAspect() {
		List<Integer> ins = new ArrayList<>();
		ins.add(1);
		ins.add(2);
		System.out.println(ins.indexOf(3));
		System.out.println(ins.indexOf(2));
		System.out.println(ins.indexOf(1));
	}

	@Test
	public void testEnvironment(){
		if (GraphicsEnvironment.isHeadless()) {
			System.err.println("This environment does not support AWT components.");
			// Handle the headless environment appropriately
		} else {
			// Create and use AWT components
			// ...
		}
	}

	/**
	 * @param :
	 * @return: void
	 * @description: 解决思路是Tomcat运行时会把headless这个设置为true，然后java.awt包里函数调用时就会产生异常。
	 *  如果打印方法不可用，修改Tomcat中的catalina.bat文件，编辑该文件并在最开头添加一行
	 *  set JAVA_OPTS=-Djava.awt.headless=false
	 * @author: zhangyan
	 * @date: 2024/7/15 1:23
	**/
	@Test
	public void printTest() {
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
		PrintService service = ServiceUI.printDialog(null, 200, 200, pservices,
				defaultService, flavor, aset);
		if (service != null) {
			try {
				DocPrintJob pj = service.createPrintJob();
				FileInputStream fis = new FileInputStream("G:" + File.separator + "检定证书.docx");//打印D盘zhidao.txt文档。
				DocAttributeSet das = new HashDocAttributeSet();
				Doc doc = new SimpleDoc(fis, flavor, das);
				pj.print(doc, aset);
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (PrintException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("打印失败");
		}
	}


}
