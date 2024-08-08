package com.weil.document.resolver;

import com.weil.document.model.ResolveParam;
import com.weil.document.model.TextModel;
import com.weil.document.utils.PptDrawUtil;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.util.List;

/**
 * @Name: PptResolver
 * @Description: ppt文件解析
 * @Author: weil
 * @Date: 2024-07-30 17:44
 * @Version: 1.0
 */
public class PptResolver extends DefaultResolver {
    @Override
    protected TextModel textResolve(ResolveParam param) {
        TextModel textModel = new TextModel();

        // 存放解析全文文本
        StringBuilder sb = new StringBuilder();
        try (POIFSFileSystem fs = new POIFSFileSystem(param.getInputStream());
             HSLFSlideShow ppt = new HSLFSlideShow(fs)) {
            // 获取幻灯片
            List<HSLFSlide> slides = ppt.getSlides();
            for (int i = 0; i < slides.size(); i++) {
                HSLFSlide slide = slides.get(i);
                // 遍历幻灯片中的所有形状
                List<HSLFShape> shapes = slide.getShapes();
                for (HSLFShape shape : shapes) {
                    if (shape instanceof HSLFTextShape) {
                        HSLFTextShape textShape = (HSLFTextShape) shape;
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
        String picPath = "C:\\Users\\weil\\Desktop\\out\\ppt.png";
        PptDrawUtil.pptDraw(param.getInputStream(), picPath);
        return picPath;
    }
}
