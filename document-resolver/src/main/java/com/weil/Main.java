package com.weil;

import com.weil.document.model.ResolveParam;
import com.weil.document.resolver.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {
//        test("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.docx", new DocxResolver());
        test("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.doc", new DocResolver());
//        test("C:\\Users\\weil\\Desktop\\xs.xls", new XlsResolver());
//        test("C:\\Users\\weil\\Desktop\\xs.xlsx", new XlsxResolver());
//        test("C:\\Users\\weil\\Desktop\\pp.ppt", new PptResolver());
//        test("C:\\Users\\weil\\Desktop\\pp.pptx", new PptxResolver());
//        test("C:\\Users\\weil\\Desktop\\test.pdf", new PdfResolver());
//        test("C:\\Users\\weil\\Desktop\\account.txt", new TxtResolver());
    }

    public static void test(String filePath, Resolver resolver){
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 转字节输入流实现重复读取
            byte[] buffer = new byte[1024];
            int len;
            while ((len =fis.read(buffer, 0, buffer.length) ) != -1) {
                bos.write(buffer, 0, len);
            }
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bos.toByteArray());

            resolver.resolve(ResolveParam.builder().inputStream(inputStream).build());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}