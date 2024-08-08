package com.weil.document.resolver;

import com.weil.document.model.ResolveParam;
import com.weil.document.model.TextModel;
import com.weil.document.utils.Word2Pdf2PicUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name: DocxResolver
 * @Description: docx文件解析
 * @Author: weil
 * @Date: 2024-07-30 09:54
 * @Version: 1.0
 */
public class DocxResolver extends DefaultResolver {
    @Override
    protected TextModel textResolve(ResolveParam param) {
        TextModel textModel = new TextModel();

        // 1.存放解析全文文本
        StringBuilder sb = new StringBuilder();

        // 2.简单存放标题和内容
        Map<String,String> map = new LinkedHashMap<>();
        String titleTmp = "";
        // 防止标题重复
        int i = 0;
        try (XWPFDocument document = new XWPFDocument(param.getInputStream())) {
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                // 全文文本
                sb.append(paragraph.getText()).append(System.lineSeparator());

                // 章节文本
                // 标题类型，1、2、3、4...分别代表几级标题，null代表正文
                String style = paragraph.getStyle();
                if(null != style){
                    // 该条记录为标题
                    titleTmp = paragraph.getText()+"#"+i++;
                    map.put(titleTmp,"");
                }else {
                    // 组装内容
                    String s = map.get(titleTmp);
                    s+=System.lineSeparator();
                    s+=paragraph.getText();
                    map.put(titleTmp, s);
                }
            }
            textModel.setFullText(sb.toString());
            textModel.setSectionMap(map);
            // 重置字节数组流
            param.getInputStream().reset();
        }catch (Exception e){
            e.printStackTrace();
        }
        return textModel;
    }


    @Override
    protected String homepageResolve(ResolveParam param) {
        // 当前先使用aspose方式生成首页图片
        String picPath = "C:\\Users\\weil\\Desktop\\success.jpg";
        Word2Pdf2PicUtil.wordConvertPdfFile(param.getInputStream(), picPath);
        return picPath;
    }
}
