package com.weil.document.resolver;

import com.weil.document.model.ResolveParam;
import com.weil.document.model.TextModel;
import com.weil.document.utils.PdfSectionUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Name: PdfResolver
 * @Description: pdf文件解析
 * @Author: weil
 * @Date: 2024-07-31 09:20
 * @Version: 1.0
 */
public class PdfResolver extends DefaultResolver {
    @Override
    protected TextModel textResolve(ResolveParam param) {
        TextModel textModel = new TextModel();

        // 存放解析全文文本
        try (PDDocument document = PDDocument.load(param.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            // 开启后特殊符号后面的文本也能解析
            stripper.setSortByPosition(true);
            // 从第一开始
            stripper.setStartPage(1);
            stripper.setEndPage(document.getNumberOfPages());
            String text = stripper.getText(document);
            textModel.setFullText(text);

            // 处理章节内容
            Map<String, String> sectionMap = PdfSectionUtil.getSectionMap(document);
            textModel.setSectionMap(sectionMap);

            // 重置字节数组流
            param.getInputStream().reset();
        }catch (Exception e){
            e.printStackTrace();
        }
        return textModel;
    }

    @Override
    protected String homepageResolve(ResolveParam param) {
        String picPath = "C:\\Users\\weil\\Desktop\\out\\pdf.jpg";
        try (PDDocument document = PDDocument.load(param.getInputStream())) {
            PDFRenderer renderer = new PDFRenderer(document);
            if(document.getNumberOfPages()>0){
                // 渲染PDF页面为图片，DPI为300
                BufferedImage image = renderer.renderImageWithDPI(0, 300);
                // 保存图片
                ImageIO.write(image, "JPG", new File(picPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picPath;
    }
}
