package com.weil.openpdf;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

import java.io.FileOutputStream;
import java.io.IOException;

public class OpenPDFDemo {
    public static void main(String[] args) throws Exception {

//        createPdf();

        readPdf();
    }

    /**
     * 读取pdf
     * @return: void
     * @author: weil
     * @date: 2024/7/17 9:50
     **/
    public static void readPdf() throws Exception {
        String srcPdf = "C:\\Users\\weil\\Desktop\\多个文档处理工具对比.pdf"; // 源 PDF 文件路径
//        String destTxt = "path/to/destination.txt"; // 目标文本文件路径
        PdfReader reader = new PdfReader(srcPdf);
        PdfTextExtractor pdfTextExtractor = new PdfTextExtractor(reader);
        // 遍历 PDF 中的所有页面
//        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
//            // 获取页面的文本内容
//            String text = pdfTextExtractor.getTextFromPage(i);
//            // 将提取的文本写入到文本文件中
//            System.out.println(text);
//            // 可以选择写入到文件，如果需要
//            // writeToFile(destTxt, content);
//        }
        String text = pdfTextExtractor.getTextFromPage(1);

        // 将提取的文本写入到文本文件中
        System.out.println(text);

    }

    // 将文本写入文件的辅助方法
    private static void writeToFile(String destTxt, String content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(destTxt, true)) {
            fos.write((content + "\n").getBytes());
        }
    }

    /**
     * 创建pdf
     * @return: void
     * @author: weil
     * @date: 2024/7/16 16:30
     **/
    public static void createPdf() throws Exception {
        // 创建一个新的 Document 实例
        Document document = new Document();

        // 创建一个 PdfWriter 实例并关联到 Document，用于写入 PDF 文件
        PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));

        // 打开文档以供编辑
        document.open();

        // 注册并使用中文字体，这里使用“STSong-Light”作为示例，需要根据实际情况调整
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 12);

        // 添加文本到 PDF
        document.add(new Paragraph("Hello, OpenPDF! 你好", font));

        // 添加更多的文本或内容...

        // 关闭文档，完成编辑
        document.close();
    }

}