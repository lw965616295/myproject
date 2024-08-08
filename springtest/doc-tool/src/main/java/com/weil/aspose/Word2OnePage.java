package com.weil.aspose;

import com.aspose.words.Document;

/**
 * @Name: Word2OnePage
 * @Description: 获取第一页
 * @Author: weil
 * @Date: 2024-08-02 09:09
 * @Version: 1.0
 */
public class Word2OnePage {
    public static void main(String[] args) {
        try {
            Document doc = new Document("C:\\Users\\weil\\Desktop\\文档处理工具比较.docx");
            System.out.println(doc.getPageCount());

            doc.save("multipageWithImage.docx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
