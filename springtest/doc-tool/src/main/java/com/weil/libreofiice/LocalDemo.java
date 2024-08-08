package com.weil.libreofiice;

import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.InstalledOfficeManagerHolder;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;

import java.io.File;

/**
 * @Name: LocalDemo
 * @Description:
 * @Author: weil
 * @Date: 2024-08-05 13:19
 * @Version: 1.0
 */
public class LocalDemo {
    public static void main(String[] args) throws Exception {
        LocalOfficeManager localOfficeManager = LocalOfficeManager.builder()
                .officeHome("D:\\LibreOffice")
                .portNumbers(8100)
                .processTimeout(10000l)
                .maxTasksPerProcess(2)
                .install()
                .build();
        long t1 = System.currentTimeMillis();
        localOfficeManager.start();
        InstalledOfficeManagerHolder.setInstance(localOfficeManager);
        long t2 = System.currentTimeMillis();
        System.out.println("office进程启动成功,"+(t2 -t1)+"ms");

        long t3 = System.currentTimeMillis();
//        final PagesSelectorFilter selectorFilter = new PagesSelectorFilter(1);
        LocalConverter converter = LocalConverter.builder()
                .officeManager(localOfficeManager)
//                .filterChain(selectorFilter)
                .build();
        System.out.println("创建DocumentConverter实例，"+(t3 -t2)+"ms");

        String sourceFile = "C:\\Users\\weil\\Desktop\\vvv.docx";
        String targetFile = "C:\\Users\\weil\\Desktop\\out\\aaa.pdf";

        converter.convert(new File(sourceFile)).as(DefaultDocumentFormatRegistry.DOCX)
                .to(new File(targetFile)).as(DefaultDocumentFormatRegistry.PDF)
                .execute();
        System.out.println("转换成功,"+(System.currentTimeMillis() -t3)+"ms");
    }
}
