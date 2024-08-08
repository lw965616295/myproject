package com.weil.document.utils;

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
import java.io.IOException;
import java.io.InputStream;

/**
 * @Name: PptDrawUtil
 * @Description: ppt生成首图片工具
 * @Author: weil
 * @Date: 2024-07-25 13:15
 * @Version: 1.0
 */
public class PptDrawUtil {

    /**
     * 固定宽
     */
    private static final Integer width = 900;
    /**
     * 固定高
     */
    private static final Integer height = 500;

    private static final String picFormat = "jpg";

    public static void pptxDraw(InputStream inputStream, String picPath) {
        // 读取 PowerPoint 文件内容
        try(XMLSlideShow ppt = new XMLSlideShow(inputStream)) {


            // 渲染 PowerPoint 内容为图片
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 获取第一张幻灯片
            XSLFSlide slide = ppt.getSlides().get(1);

            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setPaint(Color.WHITE);
            graphics2D.fill(new Rectangle2D.Float(0, 0, width, height));

            slide.draw(graphics2D);

            // 将图片写入文件
            ImageIO.write(image, picFormat, new File(picPath));

            // 释放资源
            graphics2D.dispose();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void pptDraw(InputStream inputStream, String picPath) {
        try (HSLFSlideShow ppt = new HSLFSlideShow(inputStream)){
            // 读取 PowerPoint 文件内容
            // 渲染 PowerPoint 内容为图片
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 获取第一张幻灯片
            HSLFSlide hslfShapes = ppt.getSlides().get(1);

            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setPaint(Color.WHITE);
            graphics2D.fill(new Rectangle2D.Float(0,0,width,height));

            hslfShapes.draw(graphics2D);

            // 将图片写入文件
            ImageIO.write(image, picFormat, new File(picPath));

            // 释放资源
            graphics2D.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        pptxDraw(new FileInputStream("C:\\Users\\weil\\Desktop\\pp.pptx"), "C:\\Users\\weil\\Desktop\\out\\pptx.jpg");
        pptDraw(new FileInputStream("C:\\Users\\weil\\Desktop\\pp.ppt"), "C:\\Users\\weil\\Desktop\\out\\ppt.jpg");
    }
}
