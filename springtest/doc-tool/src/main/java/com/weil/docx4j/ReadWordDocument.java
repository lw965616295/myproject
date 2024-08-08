package com.weil.docx4j;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;

public class ReadWordDocument {
    public static void main(String[] args) {
        try {
            // 加载一个现有的 Word 文档
            WordprocessingMLPackage wordMLPackage = Docx4J.load(new File("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.docx"));

            // 获取文档中的文本
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            // 遍历文档中的所有段落
//            List<Object> bodyElements = documentPart.getJaxbElement().getBody().getContent();
//            for (Object bodyElement : bodyElements) {
//                if (bodyElement instanceof P) {
//                    P paragraph = (P) bodyElement;
//                    List<Object> paragraphElements = paragraph.getContent();
//                    for (Object paragraphElement : paragraphElements) {
//                        if (paragraphElement instanceof R) {
//                            R run = (R) paragraphElement;
//                            System.out.println(run.getParent());
//                        }
//                    }
//                }
//            }


            System.out.println("Word document read successfully.");
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }
}