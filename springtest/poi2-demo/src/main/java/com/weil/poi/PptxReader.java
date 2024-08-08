package com.weil.poi;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PptxReader {
    public static void main(String[] args) throws IOException {
        String filepath = "C:\\Users\\weil\\Desktop\\pp.pptx";
        // 打开PPT文件
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(filepath));
 
        // 读取幻灯片数量
        int slideCount = ppt.getSlides().size();
        System.out.println("Number of slides: " + slideCount);
 
        // 遍历幻灯片
        for (XSLFSlide slide : ppt.getSlides()) {
            System.out.println("Slide:");
 
            // 获取幻灯片上的所有文本运行
            List<XSLFShape> shapes = slide.getShapes();
            for (XSLFShape shape : shapes) {
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) shape;
                    // 打印文本形状的文本
                    System.out.println(textShape.getText());
                }
            }
        }
 
        ppt.close(); // 关闭PPT文件
    }
}