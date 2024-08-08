package com.weil.libreofiice;

import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.remote.RemoteConverter;
import org.jodconverter.remote.office.RemoteOfficeManager;
import org.jodconverter.remote.ssl.SslConfig;

import java.io.File;

/**
 * @Name: RemoteDemo
 * @Description:
 * @Author: weil
 * @Date: 2024-07-29 17:54
 * @Version: 1.0
 */
public class RemoteDemo {
    public static void main(String[] args) {
        SslConfig sslConfig = new SslConfig();
        sslConfig.setEnabled(true);
        sslConfig.setKeyAlias("root");
        sslConfig.setKeyPassword("123456");
        // 创建远程Office管理器
        String officeUrl = "http://192.168.6.131:9980/";
        OfficeManager officeManager = RemoteOfficeManager.builder()
                .urlConnection(officeUrl)
                .connectTimeout(10000l)
                .poolSize(2)
                .taskExecutionTimeout(20000l)
                .taskQueueTimeout(20000l)
                .sslConfig(sslConfig)
                .install()
                .build();

        try {
            System.err.println("开始连接 " + officeUrl);
            long t1 = System.currentTimeMillis();
            officeManager.start();
            long t2 = System.currentTimeMillis();
            System.out.println("start: " + (t2 - t1) + " ms");
//            InstalledOfficeManagerHolder.setInstance(officeManager);
            String sourceFile = "C:\\Users\\weil\\Desktop\\www.docx";
            String targetFile = "C:\\Users\\weil\\Desktop\\out\\123.pdf";
            // 进行一个简单的文档转换测试
            File file = new File(sourceFile); // 替换为实际文件路径
            File outputFile = new File(targetFile); // 输出文件路径
            long t3 = System.currentTimeMillis();
            RemoteConverter converter = RemoteConverter.make(officeManager);
            long t4 = System.currentTimeMillis();
            System.out.println("convert make: " + (t4 - t3) + " ms");
            converter.convert(file).as(DefaultDocumentFormatRegistry.DOCX).to(outputFile).as(DefaultDocumentFormatRegistry.PDF).execute();

            long t5 = System.currentTimeMillis();

            System.err.println("转换成功,耗时:"+(t5-t4));

            // 这里可以添加你的文档转换逻辑

        } catch (OfficeException e) {
            System.err.println("连接失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 确保最后关闭office管理器
            if (officeManager.isRunning()) {
                try {
                    officeManager.stop();
                } catch (OfficeException e) {
                    e.printStackTrace();
                }
                System.err.println("关闭office管理器");
            }
        }
    }
}
