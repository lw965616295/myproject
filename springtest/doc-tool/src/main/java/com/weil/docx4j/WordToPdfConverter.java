package com.weil.docx4j;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class WordToPdfConverter {

    public static void main(String[] args) {
        try {
            // 加载Word文档
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File("C:\\Users\\weil\\Desktop\\bb.docx"));

            // 创建PDF设置
            FOSettings foSettings = Docx4J.createFOSettings();
            foSettings.setWmlPackage(wordMLPackage);

            // 输出PDF文件
            OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\weil\\Desktop\\out\\output.pdf"));
            Docx4J.toPDF(wordMLPackage, outputStream);
            outputStream.close();

            System.out.println("转换完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}