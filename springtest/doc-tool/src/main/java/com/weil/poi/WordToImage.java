package com.weil.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WordToImage {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\weil\\Desktop\\多个文档处理工具对比.docx";
        String outputPath = "output.png";
        
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            StringBuilder textBuilder = new StringBuilder();

            for (XWPFParagraph paragraph : paragraphs) {
                textBuilder.append(paragraph.getText()).append("\n");
            }

            String text = textBuilder.toString();
            BufferedImage image = createImageFromText(text, 800, 1200);
            ImageIO.write(image, "png", new FileOutputStream(outputPath));
            
            System.out.println("Image created successfully: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage createImageFromText(String text, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(Color.BLACK);

        g2d.setFont(new Font("宋体", Font.BOLD, 20));
        
        int x = 10;
        int y = 25;
        for (String line : text.split("\n")) {
            g2d.drawString(line, x, y);
            y += g2d.getFontMetrics().getHeight();
        }
        
        g2d.dispose();
        return image;
    }
}
