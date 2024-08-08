package com.weil.poi;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * @Name: TestPpt
 * @Description:
 * @Author: weil
 * @Date: 2024-07-25 13:15
 * @Version: 1.0
 */
public class TestPptDraw {
    public static void main(String[] args) throws Exception {
//        pptxDraw();
        pptDraw();
    }
    public static void pptxDraw() throws Exception {
        String pptFile = "C:\\Users\\weil\\Desktop\\pp.pptx";
        // 读取 PowerPoint 文件内容
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(pptFile));
        Integer width = 900;
        Integer height = 500;
        // 渲染 PowerPoint 内容为图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取第一张幻灯片
        XSLFSlide slide = ppt.getSlides().get(1);

        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fill(new Rectangle2D.Float(0,0,width,height));

        slide.draw(graphics2D);

        // 将图片写入文件
        ImageIO.write(image, "png", new File("C:\\Users\\weil\\Desktop\\out\\pptx.png"));

        // 释放资源
        graphics2D.dispose();
        ppt.close();

    }
    public static void pptDraw() throws Exception {
        String pptFile = "C:\\Users\\weil\\Desktop\\pp.ppt";
        // 读取 PowerPoint 文件内容
        HSLFSlideShow ppt = new HSLFSlideShow(new FileInputStream(pptFile));
        Integer width = 900;
        Integer height = 500;
        // 渲染 PowerPoint 内容为图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取第一张幻灯片
        HSLFSlide hslfShapes = ppt.getSlides().get(1);

        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fill(new Rectangle2D.Float(0,0,width,height));

        hslfShapes.draw(graphics2D);

        // 将图片写入文件
        ImageIO.write(image, "png", new File("C:\\Users\\weil\\Desktop\\out\\ppt.png"));

        // 释放资源
        graphics2D.dispose();
        ppt.close();

    }
}
