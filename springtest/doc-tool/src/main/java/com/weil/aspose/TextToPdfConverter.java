package com.weil.aspose;

import com.aspose.words.*;

import java.io.FileInputStream;
import java.nio.charset.Charset;

public class TextToPdfConverter {
    public static void main(String[] args) throws Exception {
        // 加载文本文件
        FileInputStream in = new FileInputStream("C:\\Users\\weil\\Desktop\\abc.txt");

        LoadOptions options = new LoadOptions();
        options.setLoadFormat(LoadFormat.TEXT);
        options.setEncoding(Charset.forName("utf-8"));
        Document doc = new Document(in, options);
        // 保存为PDF文件
        doc.save("output.pdf", SaveFormat.PDF);
    }
}