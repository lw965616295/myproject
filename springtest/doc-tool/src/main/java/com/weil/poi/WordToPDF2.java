package com.weil.poi;

import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
// 该方式丢失word格式
public class WordToPDF2 {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\weil\\Desktop\\多个文档处理工具对比.docx";
        String outputFilePath = "document.pdf";

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             XWPFDocument document = new XWPFDocument(fis);
             PDDocument pdfDocument = new PDDocument()) {

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            // 加载中文字体
            InputStream inputStream = WordToPDF2.class.getClassLoader().getResourceAsStream("SimSun.ttf");
            PDType0Font font = PDType0Font.load(pdfDocument, inputStream);

            PDPage page = new PDPage();
            pdfDocument.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

            contentStream.setFont(font, 12);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 750);

            for (XWPFParagraph paragraph : paragraphs) {
                String text = paragraph.getText();
                contentStream.showText(text);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            pdfDocument.save(outputFilePath);

            System.out.println("PDF created successfully: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
