package com.weil.poi;

import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ReadPPT {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = new FileInputStream("C:\\Users\\weil\\Desktop\\pp.ppt");
        POIFSFileSystem fs = new POIFSFileSystem(inputStream);
        HSLFSlideShow ppt = new HSLFSlideShow(fs);

        List<HSLFSlide> slides = ppt.getSlides();
        int slideCount = slides.size();
        System.out.println("Total Slides: " + slideCount);
        
        for (int i = 0; i < slideCount; i++) {
            HSLFSlide slide = slides.get(i);
            System.out.println("Slide " + (i + 1) + ":");
            
            // 遍历幻灯片中的所有形状
            List<HSLFShape> shapes = slide.getShapes();
            for (HSLFShape shape : shapes) {
                if (shape instanceof HSLFTextShape) {
                    HSLFTextShape textShape = (HSLFTextShape) shape;
                    // 打印文本形状的文本
                    System.out.println(textShape.getText());
                }
            }
        }
        
        inputStream.close();
    }
}