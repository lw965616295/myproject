package com.weil.document.resolver;

import com.weil.document.model.ResolveParam;
import com.weil.document.model.TextModel;
import com.weil.document.utils.PptDrawUtil;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.util.List;

/**
 * @Name: PptxResolver
 * @Description: pptx文件解析
 * @Author: weil
 * @Date: 2024-07-31 09:11
 * @Version: 1.0
 */
public class PptxResolver extends DefaultResolver {
    @Override
    protected TextModel textResolve(ResolveParam param) {
        TextModel textModel = new TextModel();

        // 存放解析全文文本
        StringBuilder sb = new StringBuilder();
        try (XMLSlideShow pptx = new XMLSlideShow(param.getInputStream())) {
            // 获取幻灯片
            List<XSLFSlide> slides = pptx.getSlides();
            for (int i = 0; i < slides.size(); i++) {
                XSLFSlide slide = slides.get(i);
                // 获取幻灯片上的所有文本运行
                List<XSLFShape> shapes = slide.getShapes();
                for (XSLFShape shape : shapes) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape textShape = (XSLFTextShape) shape;
                        // 保存文本
                        sb.append(textShape.getText()).append(System.lineSeparator());
                    }
                }
            }
            textModel.setFullText(sb.toString());
            // 重置字节数组流
            param.getInputStream().reset();
        }catch (Exception e){
            e.printStackTrace();
        }
        return textModel;
    }


    @Override
    protected String homepageResolve(ResolveParam param) {
        String picPath = "C:\\Users\\weil\\Desktop\\out\\pptx.png";
        PptDrawUtil.pptxDraw(param.getInputStream(), picPath);
        return picPath;
    }
}
