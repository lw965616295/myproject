package com.weil.tika;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Name: TikaDemo
 * @Description:
 * @Author: weil
 * @Date: 2024-07-17 11:29
 * @Version: 1.0
 */
public class TikaDemo {
    public static void main(String[] args) {
//        extractText();
        extractPDF();
    }

    /**
     * 提取文本
     * @return: void
     * @author: weil
     * @date: 2024/7/17 11:31
     **/
    public static void extractText() {
        try {
            Tika tika = new Tika();
            String filePath = "C:\\Users\\weil\\Desktop\\多个文档处理工具对比.pdf";
            String fileType = tika.detect(filePath);
            System.out.println("Detected file type: " + fileType);
            String content = tika.parseToString(new File(filePath));
            System.out.println("Extracted Content: " + "\n" + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void extractPDF() {
        try {
            File pdfFile = new File("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.pdf");
            FileInputStream fis = new FileInputStream(pdfFile);

            // 创建用于接收解析文本的处理器
            BodyContentHandler handler = new BodyContentHandler();
            // 创建用于接收元数据的对象
            Metadata metadata = new Metadata();
            // 创建自动检测解析器
            AutoDetectParser parser = new AutoDetectParser();

            // 解析PDF文件
            parser.parse(fis, handler, metadata);

            // 关闭输入流
            fis.close();

            // 输出文本内容
            System.out.println("Extracted Text: " + handler.toString());

            // 输出元数据
            System.out.println("Metadata:");
            for (String name : metadata.names()) {
                System.out.println(name + ": " + metadata.get(name));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
