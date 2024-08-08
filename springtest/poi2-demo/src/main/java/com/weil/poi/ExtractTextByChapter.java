package com.weil.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExtractTextByChapter {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\weil\\Desktop\\多个文档处理工具对比.docx";

        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            // 简单存放标题和内容
            Map<String,String> map = new LinkedHashMap<>();
            String titleTmp = "";
            // 防止标题重复
            int i = 0;
            for (XWPFParagraph paragraph : paragraphs) {
                // 标题类型，1、2、3、4...分别代表几级标题，null代表正文
                String style = paragraph.getStyle();
                String text = paragraph.getText();
                if(null != style){
                    // 该条记录为标题
                    titleTmp = paragraph.getText()+i++;
                    map.put(titleTmp,"");
                }else {
                    String s = map.get(titleTmp);
                    s+="\r\n";
                    s+=text;
                    map.put(titleTmp,s);
                }

            }

            // 回显
            map.forEach((k,v)->{
                System.out.println("标题："+k);
                System.out.println("正文: "+v);
                System.out.println("-------------");
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
