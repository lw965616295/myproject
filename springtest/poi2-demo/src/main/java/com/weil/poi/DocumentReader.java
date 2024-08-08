package com.weil.poi;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;

import java.io.*;

public class DocumentReader {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.doc");
        HWPFDocument document = new HWPFDocument(fis);

        Range range = document.getRange();
        int pageCount = range.numParagraphs();

        for (int i = 0; i < pageCount; i++) {
            Paragraph paragraph = range.getParagraph(i);
            String content = paragraph.text();
            System.out.println(paragraph.getStyleIndex());
            System.out.println("Page " + (i + 1) + ": " + content);
        }

        fis.close();
    }
}