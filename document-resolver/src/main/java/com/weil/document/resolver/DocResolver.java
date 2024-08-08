package com.weil.document.resolver;

import com.weil.document.model.ResolveParam;
import com.weil.document.model.TextModel;
import com.weil.document.utils.Word2Pdf2PicUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Name: DocResolver
 * @Description: doc文件解析
 * @Author: weil
 * @Date: 2024-07-30 16:33
 * @Version: 1.0
 */
public class DocResolver extends DefaultResolver {
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
        try (HWPFDocument document = new HWPFDocument(param.getInputStream())) {
            Range range = document.getRange();
            for (int j = 0; j < range.numParagraphs(); j++) {
                Paragraph paragraph = range.getParagraph(j);
                // 全文文本
                sb.append(paragraph.text()).append(System.lineSeparator());

                // 章节文本
                // 标题类型，1、2、3、4...分别代表几级标题，0代表正文
                short styleIndex = paragraph.getStyleIndex();
                if(0 != styleIndex){
                    // 该条记录为标题
                    titleTmp = paragraph.text()+"#"+i++;
                    map.put(titleTmp,"");
                }else {
                    // 组装内容
                    String s = map.get(titleTmp);
                    s+=System.lineSeparator();
                    s+=paragraph.text();
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
