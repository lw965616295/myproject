package com.weil.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class WordToImageConverter {

    public static void main(String[] args) throws IOException {
        // 读取 Word 文档
        String docFilePath = "C:\\Users\\weil\\Desktop\\文档处理工具比较.docx";
        FileInputStream fis = new FileInputStream(new File(docFilePath));
        XWPFDocument document = new XWPFDocument(fis);

        // 渲染第一页为图片
        int pageNum = 0; // 注意：Apache POI 的页码从0开始
        BufferedImage image = renderPageToImage(document, pageNum);

        // 保存图片到文件
        String outputImagePath = "C:\\Users\\weil\\Desktop\\out\\output.png";
        File outputFile = new File(outputImagePath);
        ImageIO.write(image, "PNG", outputFile);

        System.out.println("转换完成，图片保存在：" + outputImagePath);
    }

    public static BufferedImage renderPageToImage(XWPFDocument document, int pageNum) throws IOException {
        // 创建一个 BufferedImage
        int width = 800; // 图片宽度
        int height = 1000; // 图片高度
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // 设置背景色为白色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // 获取指定页的段落列表
        java.util.List<XWPFParagraph> paragraphs = document.getParagraphs();
        int y = 50; // 初始绘制的 Y 坐标

        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            if(runs.size() > 0) {
                // 逐段绘制文本
                XWPFRun run = runs.get(0); // 获取段落的第一个 Run
                String text = run.getText(0);
                if (text != null && !text.isEmpty()) {
                    graphics.setColor(Color.BLACK);
                    graphics.drawString(text, 50, y);
                    y += 20; // 段落之间的间距
                }

                // 绘制段落中的图片
                java.util.List<XWPFPicture> pictures = run.getEmbeddedPictures();
                for (XWPFPicture pic : pictures) {
//                String fileName = pic.getPictureData().getFileName();
//                int imageType = pic.getPictureData().suggestFileExtension().equals("png") ?
//                        BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;

                    BufferedImage bufferedImage = ImageIO.read(pic.getPictureData().getPackagePart().getInputStream());
                    graphics.drawImage(bufferedImage, 50, y, null);
                    y += bufferedImage.getHeight() + 20; // 图片之间的间距
                }
            }

        }

        graphics.dispose();
        return image;
    }
}
