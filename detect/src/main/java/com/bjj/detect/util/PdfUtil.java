package com.bjj.detect.util;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.*;

import com.aspose.pdf.*;
import com.aspose.words.*;
import com.aspose.cells.Workbook;
import com.aspose.words.Border;
import com.aspose.words.Document;
import com.aspose.words.HeaderFooter;
import com.aspose.words.HorizontalAlignment;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.aspose.words.Shape;
import com.aspose.words.VerticalAlignment;

/**
 * @program: researchReport
 * @description
 * @author: boyer
 * @create: 2022-07-21 14:03
 */
public class PdfUtil
{

    /**
     * ��Classpath��jar�ļ��У��ж�ȡLicense,��Ȼ�������ˮӡ
     */
    private static boolean loadLicense() {
        boolean result = false;
        //���ض�ȡָ����Դ��������
        License license = new License();
        InputStream is = null;
        try {
            is = PdfUtil.class.getResourceAsStream("/license.xml");
            if(is==null)
                throw new RuntimeException("�Ҳ��������ļ�license.xml");
            license.setLicense(is);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            if(is!=null){
                try{ is.close(); }catch(IOException ex){ };
                is = null;
            }
        }
        return result;
    }

    /**
     * ��֤License ������֤��ת������pdf�ĵ�����ˮӡ����
     * @return
     */
    public static boolean getLicenseExcel() {
        boolean result = false;
        try {

            InputStream is =PdfUtil.class.getResourceAsStream("/license.xml");
            //ע��˴�Ϊ��Ӧaspose-cells��jar��
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();

            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ��֤License ������֤��ת������pdf�ĵ�����ˮӡ����
     * @return
     */
    public static boolean getLicensePpt(){
        boolean result = false;
        try {

            InputStream is =PdfUtil.class.getResourceAsStream("/license.xml");
            //ע��˴�Ϊ��Ӧaspose-slides��jar��
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * wordתpdf
     *
     * @param wordPath
     *            Դ�ļ�·��
     * @param pdfPath
     *            ת���ļ�·��
     */
    public static void docToPdf(String wordPath, String pdfPath)
    {
        // ��֤License
        if (!loadLicense()) {
            return ;
        }
        try
        {
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);
            // ȫ��֧��DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF �໥ת��
            doc.save(os, com.aspose.words.SaveFormat.PDF);
            os.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * excelתpdf
     *
     * @param excelPath
     *            Դ�ļ�·��
     * @param pdfPath
     *            ת���ļ�·��
     */
    public static void excelToPdf(String excelPath, String pdfPath)
    {
        // ��֤License
        if (!getLicenseExcel()) {
            return ;
        }
        try
        {
            Workbook wb = new Workbook(excelPath);
            FileOutputStream fileOS = new FileOutputStream(new File(pdfPath));
            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
            fileOS.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ppt תpdf ����
     * @param Address pptԭ·��
     * @param outPath pdfת��·��
     */
    public static void pptToPdf(String Address,String outPath) {
        // ��֤License
        if (!getLicensePpt()) {
            return ;
        }
        try {
            File file = new File(outPath);
            com.aspose.slides.Presentation pres = new com.aspose.slides.Presentation(Address);
            FileOutputStream fileOS = new FileOutputStream(file);
            pres.save(fileOS, com.aspose.slides.SaveFormat.Pdf);
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ϊword�ĵ����ˮӡ
     * @param doc word�ĵ�ģ��
     * @param watermarkText ��Ҫ��ӵ�ˮӡ�ֶ�
     * @throws Exception
     */
    public static void insertWatermarkText(Document doc, String watermarkText) throws Exception {
        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        //ˮӡ����
        watermark.getTextPath().setText(watermarkText);
        //ˮӡ����
        watermark.getTextPath().setFontFamily("����");
        //ˮӡ���
        watermark.setWidth(100);
        //ˮӡ�߶�
        watermark.setHeight(20);
        //��תˮӡ
        watermark.setRotation(-40);
        //ˮӡ��ɫ ǳ��ɫ
        watermark.getFill().setColor(Color.lightGray);
        watermark.setStrokeColor(Color.lightGray);
        //�������ˮƽλ��
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        //������Դ�ֱλ��
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        //���ð�װ����
        watermark.setWrapType(WrapType.NONE);
        //���ô�ֱ����
        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
        //�����ı�ˮƽ���뷽ʽ
        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // ����һ���¶��䲢�ڸö������ˮӡ��
        Paragraph watermarkPara = new Paragraph(doc);
        watermarkPara.appendChild(watermark);

        for (Section sect : doc.getSections())
        {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
        System.out.println("���ˮӡ�ɹ�");
    }

    /**
     *  ����ˮӡ
     * @param watermarkPara
     * @param sect
     * @param headerType
     * @throws Exception
     */
    private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect, int headerType) throws Exception {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
        if (header == null) {
            header = new HeaderFooter(sect.getDocument(), headerType);
            sect.getHeadersFooters().add(header);
        }
        // ��ͷ������һ��ˮӡ�Ŀ�¡
        header.appendChild(watermarkPara.deepClone(true));
    }


    /**
     * ��ҳü�в�������ˮӡ
     * @param document
     * @param watermarkText
     * @throws Exception
     */
    //ҳüҳ�� ��������
    private static void insertFooterAndHeader(Document document,String watermarkText) throws Exception{
        // �ĵ����������࣬�ɶԵ�ǰ�����ģ����б༭�������Ȳ��ֹ��ܡ�
        DocumentBuilder builder = new DocumentBuilder(document);
        //   1����ʼ����ҳü
        //   ������ƶ���ҳüλ��
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        //   ���ҳü��
        Border borderHeader = builder.getParagraphFormat().getBorders().getBottom();
        borderHeader.setShadow(true);
        borderHeader.setDistanceFromText(2);
        borderHeader.setLineStyle(LineStyle.SINGLE);
        //   ����ҳ�ű߾�
        builder.getPageSetup().setHeaderDistance(35.9);
        builder.getPageSetup().setFooterDistance(36);
        //����ҳü�����ݺ�����
        Paragraph header = builder.insertParagraph();
        Run runHeader = new Run(document,watermarkText);
        runHeader.getFont().setSize(7.5);
        runHeader.getFont().setName("����");
        header.appendChild(runHeader);

        //   2����ʼ����ҳ��
        //   ������ƶ���ҳ��λ��
        builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);
        //   ���ҳ����
        Border borderFooter = builder.getParagraphFormat().getBorders().getTop();
        borderFooter.setShadow( true );
        borderFooter.setDistanceFromText(2);
        borderFooter.setLineStyle(LineStyle.SINGLE);
        // ����ҳ�ű߾�
        builder.getPageSetup().setHeaderDistance(35.9);
        builder.getPageSetup().setFooterDistance(36);
        //����ҳ�ŵ����ݺ�����
        Paragraph paragraph = builder.insertParagraph();
        Run run = new Run(document,watermarkText);
        run.getFont().setSize(7.5);
        run.getFont().setName("����");
        paragraph.appendChild(run);
    }



    /**
     * ��������ˮӡ
     * @param mdoc
     * @param wmText
     * @throws Exception
     */
    public static void WaterMarkMore(Document mdoc, String wmText)throws Exception{
        Paragraph watermarkPara = new Paragraph(mdoc);
        for (int j = 0; j < 500; j = j + 100)
        {
            for (int i = 0; i < 700; i = i + 85)
            {
                Shape waterShape = ShapeMore(mdoc, wmText, j, i);
                watermarkPara.appendChild(waterShape);
            }
        }
        for (Section sect : mdoc.getSections())
        {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
    }

    /**
     * ����3��ˮӡ
     * @param mdoc
     * @param wmText
     * @throws Exception
     */
    public static void WaterMark3(Document mdoc, String wmText)throws Exception{
        Paragraph watermarkPara = new Paragraph(mdoc);
        Shape waterShape1 = ShapeMore(mdoc, wmText, 80, 160);
        Shape waterShape2 = ShapeMore(mdoc, wmText, 200, 350);
        Shape waterShape3 = ShapeMore(mdoc, wmText, 300, 600);
        watermarkPara.appendChild(waterShape1);
        watermarkPara.appendChild(waterShape2);
        watermarkPara.appendChild(waterShape3);

        for (Section sect : mdoc.getSections())
        {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
    }

    /**
     * ����ˮӡ����
     * @param doc
     * @param wmText
     * @param left
     * @param top
     * @return
     * @throws Exception
     */
    public static Shape ShapeMore(Document doc, String wmText, double left, double top)throws Exception{
        Shape waterShape = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        //ˮӡ����
        waterShape.getTextPath().setText(wmText);
        //ˮӡ����
        waterShape.getTextPath().setFontFamily("����");
        //ˮӡ���
        waterShape.setWidth(100);
        //ˮӡ�߶�
        waterShape.setHeight(20);
        //��תˮӡ
        waterShape.setRotation(-40);
        //ˮӡ��ɫ ǳ��ɫ
        /*waterShape.getFill().setColor(Color.lightGray);
        waterShape.setStrokeColor(Color.lightGray);*/
        waterShape.setStrokeColor(new Color(210,210,210));
        //��ˮӡ������ҳ������
        waterShape.setLeft(left);
        waterShape.setTop(top);
        //���ð�װ����
        waterShape.setWrapType(WrapType.NONE);
        return waterShape;
    }


    /**
     * ҳü��ҳ�� ����ͼƬ
     * @param document
     * @param picUrl
     * @throws Exception
     */
    private static void insertImageToHeader(Document document,String picUrl) throws Exception{
        // �ĵ����������࣬�ɶԵ�ǰ�����ģ����б༭�������Ȳ��ֹ��ܡ�
        DocumentBuilder builder = new DocumentBuilder(document);
        //   1����ʼ����ҳü
        //   ������ƶ���ҳüλ��
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        //   ���ҳü��
        Border borderHeader = builder.getParagraphFormat().getBorders().getBottom();
        borderHeader.setShadow(true);
        borderHeader.setDistanceFromText(22);
        borderHeader.setLineStyle(LineStyle.SINGLE);
        // ����ҳ�ű߾�
        builder.getPageSetup().setHeaderDistance(35.9);
        builder.getPageSetup().setFooterDistance(36);
        //����ҳü��ͼƬ
        builder.insertImage(picUrl, RelativeHorizontalPosition.MARGIN, 0, RelativeVerticalPosition.TOP_MARGIN, 44, 97, 27, WrapType.THROUGH);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);

        //   2����ʼ����ҳ��
        //   ������ƶ���ҳ��λ��
        builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);
        //   ���ҳ����
        Border borderFooter = builder.getParagraphFormat().getBorders().getTop();
        borderFooter.setShadow( true );
        borderFooter.setDistanceFromText(2);
        borderFooter.setLineStyle(LineStyle.SINGLE);
        // ����ҳ�ű߾�
        builder.getPageSetup().setHeaderDistance(35.9);
        builder.getPageSetup().setFooterDistance(36);
        //����ҳ�ŵ�ͼƬ
        builder.insertImage(picUrl, RelativeHorizontalPosition.MARGIN, 0, RelativeVerticalPosition.TOP_MARGIN, 44, 97, 27, WrapType.THROUGH);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);

    }

    /**
     * ��pdf�ļ����ˮӡ
     * @param filepath �ļ�·��
     * @param data  ˮӡ����
     * @throws Exception
     */
    public static void addWatermark(String filepath,String data) throws Exception {
        com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(filepath);
        TextStamp textStamp = new TextStamp(data);
        textStamp.setBackground(true);
        textStamp.setXIndent(100);
        textStamp.setYIndent(100);
        textStamp.setRotateAngle(50);

        textStamp.getTextState().setFont(FontRepository.findFont("SimSun"));//Arial
        textStamp.getTextState().setFontSize(34.0F);
        textStamp.getTextState().setFontStyle(FontStyles.Italic);
        textStamp.getTextState().setForegroundColor(com.aspose.pdf.Color.fromRgb(Color.lightGray));

        TextStamp textStamp1 = new TextStamp(data);
        textStamp1.setBackground(true);
        textStamp1.setXIndent(300);//����λ��
        textStamp1.setYIndent(300);
        textStamp1.setRotateAngle(50);//������ת�Ƕ�
        textStamp1.getTextState().setFont(FontRepository.findFont("SimSun"));
        textStamp1.getTextState().setFontSize(34.0F);//���������С
        textStamp1.getTextState().setFontStyle(FontStyles.Italic);
        textStamp1.getTextState().setForegroundColor(com.aspose.pdf.Color.fromRgb(Color.lightGray));//����������ɫ

        TextStamp textStamp2 = new TextStamp(data);
        textStamp2.setBackground(true);
        textStamp2.setXIndent(500);
        textStamp2.setYIndent(500);
        textStamp2.setRotateAngle(50);
        textStamp2.getTextState().setFont(FontRepository.findFont("SimSun"));
        textStamp2.getTextState().setFontSize(34.0F);
        textStamp2.getTextState().setFontStyle(FontStyles.Italic);
        textStamp2.getTextState().setForegroundColor(com.aspose.pdf.Color.fromRgb(Color.lightGray));

        PageCollection pages = pdfDocument.getPages();
        int size = pages.size();
        for (int i = 1; i <= size; i++) {
            pages.get_Item(i).addStamp(textStamp);
            pages.get_Item(i).addStamp(textStamp1);
            pages.get_Item(i).addStamp(textStamp2);
        }
        pdfDocument.save(filepath);
    }

    /**
     * ��pdf�ļ����ͼƬˮӡ
     * @param filepath �ļ�·��
     * @param imgUrl  ͼƬˮӡ·��
     * @throws Exception
     */
    public static void addWatermark2(String filepath,String imgUrl) throws Exception {
        com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(filepath);

        ImageStamp img = new ImageStamp(imgUrl);
        img.setBackground(true);
        img.setXIndent(100);
        img.setYIndent(100);
        img.setRotateAngle(50);

        ImageStamp img2 = new ImageStamp(imgUrl);
        img2.setBackground(true);
        img2.setXIndent(300);
        img2.setYIndent(300);
        img2.setRotateAngle(50);

        ImageStamp img3 = new ImageStamp(imgUrl);
        img3.setBackground(true);
        img3.setXIndent(500);
        img3.setYIndent(500);
        img3.setRotateAngle(50);

        PageCollection pages = pdfDocument.getPages();
        int size = pages.size();
        for (int i = 1; i <= size; i++) {
            pages.get_Item(i).addStamp(img);
            pages.get_Item(i).addStamp(img2);
            pages.get_Item(i).addStamp(img3);
        }
        pdfDocument.save(filepath);
    }

    public static void main(String[] args)
    {
        try {

                Document document = new Document("F:\\2.docx");
                //��word���һ��ˮӡ
                //insertWatermarkText(document,"�ҵ�ˮӡ1");
                //document.save("F:\\2-1.docx");
                //��word�������ˮӡ
                //WaterMarkMore(document,"�ҵ�ˮӡ2");
                //document.save("F:\\2-2.docx");
                //��word���3��ˮӡ
                //WaterMark3(document,"�ҵ�ˮӡ3");
                //ҳüҳ���������ˮӡ
                //insertFooterAndHeader(document,"��˾���ܲ���������й");
                //document.save("F:\\2-3.docx");

                //wordתpdf
                docToPdf("F://2-3.docx","F://2.pdf");

                //excelתpdf
                //excelToPdf("F://01.xls","F://01.pdf");

                //pptתpdf
                //pptToPdf("F://test.ppt","F://2.pdf");

                //pdf�������ˮӡ
                //addWatermark("F://2.pdf","�ҵ�ˮӡ");

                //pdf���ͼƬˮӡ
                addWatermark2("F://2.pdf","F:\\ace\\assets\\avatars\\user.jpg");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
