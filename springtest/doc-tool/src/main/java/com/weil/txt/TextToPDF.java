package com.weil.txt;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class TextToPDF {
    public static void main(String[] args) {
        // 替换为你的文件路径
        String filePath = "C:\\Users\\weil\\Desktop\\abc.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            // 创建一个PDF文档
            PDDocument document = new PDDocument();
            // 创建一个页面
            PDPage page = new PDPage();
            document.addPage(page);
            // 创建一个用于在页面上写入内容的流
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            // 设置字体和字体大小
            // 加载中文字体
            InputStream inputStream = TextToPDF.class.getClassLoader().getResourceAsStream("SimSun.ttf");
            PDType0Font font = PDType0Font.load(document, inputStream);
            contentStream.setFont(font, 12);
            // 设置开始绘制文本的位置
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(20, 700);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 750);
            // 添加文本到内容流
            String line;
            while ((line = reader.readLine()) != null) {
                contentStream.showText(line);
                contentStream.newLine();
            }

            contentStream.endText();
            // 关闭内容流
            contentStream.close();
 
            // 保存PDF文档
            document.save("text.pdf");
            document.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}