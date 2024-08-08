//package com.weil.libreofiice;
//
//import com.artofsolving.jodconverter.DocumentConverter;
//import com.artofsolving.jodconverter.DocumentFamily;
//import com.artofsolving.jodconverter.DocumentFormat;
//import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
//import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
//import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
//import com.weil.utils.CheckMemTools;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.ConnectException;
//
///**
// * @Name: Demo
// * @Description: 老版本
// * @Author: weil
// * @Date: 2024-07-29 17:39
// * @Version: 1.0
// */
//public class Demo {
//    public static void main(String[] args) {
//        String sourceFile = "C:\\Users\\weil\\Desktop\\kkk.docx";
//        String targetFile = "C:\\Users\\weil\\Desktop\\out\\1234.pdf";
//        office2PDF(sourceFile, targetFile);
//    }
//    public static boolean office2PDF(String sourceFile, String destFile) {
//        try {
//            File inputFile = new File(sourceFile);
//            File outputFile = new File(destFile);
//            long t1 = System.currentTimeMillis();
//            // connect to an OpenOffice.org instance running on port 8100
//            //OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.129.155", 8100);
//            //OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.129.156", 8100);
//            OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.6.128", 8100);
//            connection.connect();
//
//            long t2 = System.currentTimeMillis();
//            System.out.println("建立链接，耗时："+(t2 -t1)+"ms");
//            // convert
//            // 本地调用
//            // DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
//            // 远程调用
//            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
//
//
//            final DocumentFormat docx = new DocumentFormat("Microsoft Word 2007 XML", DocumentFamily.TEXT, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
//            converter.convert(inputFile, docx, outputFile, null);
//            //converter.convert(inputFile, outputFile);
//
//            CheckMemTools.getInstance().check();
//            long t3 = System.currentTimeMillis();
//            System.out.println("转换完成，耗时："+(t3 - t2)+"ms");
//            // close the connection
//            connection.disconnect();
//
//            return true;
//        } catch (ConnectException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//}
