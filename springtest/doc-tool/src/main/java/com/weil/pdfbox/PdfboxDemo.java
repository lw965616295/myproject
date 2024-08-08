package com.weil.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Name: PdfboxDemo
 * @Description:
 * @Author: weil
 * @Date: 2024-07-17 09:47
 * @Version: 1.0
 */
public class PdfboxDemo {
    public static void main(String[] args) {
//        readPdf();
//        writePdf();
//        exportPic();
        readPdfSpec();
    }

    /**
     * 特殊读取pdf
     * @return: void
     * @author: weil
     * @date: 2024/7/17 11:11
     **/
    public static void readPdfSpec(){
        try (PDDocument document = PDDocument.load(new File("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.pdf"))) {
            PDFTextStripper stripper = new PDFTextStripper();
            // 开启后特殊符号后面的文本也能解析
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(document.getNumberOfPages());

            String text = stripper.getText(document);

            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出图片
     * @return: void
     * @author: weil
     * @date: 2024/7/17 10:12
     **/
    public static void exportPic() {
        try (PDDocument document = PDDocument.load(new File("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.pdf"))) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage image = renderer.renderImageWithDPI(page, 300); // 渲染PDF页面为图片，DPI为300
                ImageIO.write(image, "JPG", new File("output-" + (page + 1) + ".jpg")); // 保存图片
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建pdf
     * @return: void
     * @author: weil
     * @date: 2024/7/17 10:05
     **/
    public static void writePdf() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // 中文需要自定义字体
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Hello World!");
                contentStream.endText();
            }

            document.save("hello-world.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取pdf
     * @return: void
     * @author: weil
     * @date: 2024/7/17 9:50
     **/
    public static void readPdf(){
        // 读取 resources 目录下 input.pdf 文件
        try(PDDocument pdDocument = PDDocument.load(new File("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.pdf"))) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            // 读取所有的分页
            for (int i = 1; i <= pdDocument.getNumberOfPages(); i++) {
                // 设置起始-结束页  这里设置指定某页
                pdfTextStripper.setStartPage(i);
                pdfTextStripper.setEndPage(i);
                // 读取每一页
                String pageText = pdfTextStripper.getText(pdDocument);
                System.out.println(String.format("第%s页读取内容：", i));
                System.out.println(pageText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
