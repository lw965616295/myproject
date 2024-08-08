package com.weil.document.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.apache.pdfbox.text.PDFTextStripper;

import java.util.*;

/**
 * @Name: PdfSectionUtil
 * @Description: pdf章节获取工具
 * @Author: weil
 * @Date: 2024-07-31 09:34
 * @Version: 1.0
 */
public class PdfSectionUtil {
    /**
     * 分隔符
     */
    public static final String SEPARATOR = "#";
    
    /**
     * pdf章节获取
     * @param document
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @author: weil
     * @date: 2024/7/31 9:36
     **/
    public static Map<String,String> getSectionMap(PDDocument document) throws Exception {
        // 获取PDF文件的文档目录对象
        PDDocumentOutline documentOutline = document.getDocumentCatalog().getDocumentOutline();

        // 存储标题和页码
        List<String> strs = new ArrayList<>();
        // 输出目录内容
        if (documentOutline != null) {

            printOutline(documentOutline, strs);
        }

        PDFTextStripper stripper = new PDFTextStripper();
        // 开启后特殊符号后面的文本也能解析
        stripper.setSortByPosition(true);

        // 定义返回结果
        Map<String,String> map = new LinkedHashMap<>();
        // 防止标题重复
        int duplication = 0;

        // 遍历标题
        for (int titleIdx=0; titleIdx < strs.size(); titleIdx++) {
            // 当前标题信息
            String currentStr = strs.get(titleIdx);
            // 下一个标题
            String nextStr = "";
            if(titleIdx+1<strs.size()) {
                nextStr = strs.get(titleIdx+1);
            }

            String[] curr = currentStr.split(SEPARATOR);
            String currentTitle = curr[0];
            Integer currentNum = Integer.parseInt(curr[1]);

            String nextTitle = "";
            Integer nextNum = 0;
            if(nextStr != null && nextStr.length()>0){
                String[] next = nextStr.split(SEPARATOR);
                nextTitle = next[0];
                nextNum = Integer.parseInt(next[1]);
            }
            stripper.setStartPage(currentNum);
            stripper.setEndPage(currentNum);
            String currentText = stripper.getText(document);
            if(currentNum == nextNum){
                // 同一页 定位两个标题所在位置，截取中间内容
                int currIdx = currentText.indexOf(currentTitle);
                int nextIdx = currentText.indexOf(nextTitle);
                String content = currentText.substring(currIdx+currentTitle.length(), nextIdx);
                map.put(currentTitle+SEPARATOR+duplication++, content);
            }else if(nextNum >currentNum){
                // 当前页
                int currIdx = currentText.indexOf(currentTitle);
                String content = currentText.substring(currIdx+currentTitle.length());
                // 存在中间页
                if(nextNum-currentNum > 1){
                    for(int i=currentNum+1;i<nextNum;i++){
                        stripper.setStartPage(i);
                        stripper.setEndPage(i);
                        content+=stripper.getText(document);
                    }
                }
                // 最后
                stripper.setStartPage(nextNum);
                stripper.setEndPage(nextNum);
                String nextContent = stripper.getText(document);
                int nextIdx = nextContent.indexOf(nextTitle);
                content+=nextContent.substring(0, nextIdx);
                map.put(currentTitle+SEPARATOR+duplication++, content);
            }

        }

        return map;
    }

    // 递归输出目录内容
    private static void printOutline(PDOutlineNode documentOutline, List<String> strs) throws Exception {
        PDOutlineItem item = documentOutline.getFirstChild();
        while (item != null){
            int pages = 0;
            if(item.getDestination() instanceof PDPageDestination){
                PDPageDestination pd = (PDPageDestination) item.getDestination();
                pages = pd.retrievePageNumber() + 1;
            }
            if (item.getAction()  instanceof PDActionGoTo) {
                PDActionGoTo gta = (PDActionGoTo) item.getAction();
                if (gta.getDestination() instanceof PDPageDestination) {
                    PDPageDestination pd = (PDPageDestination) gta.getDestination();
                    pages = pd.retrievePageNumber() + 1;
                }
            }
            strs.add(item.getTitle()+ SEPARATOR +pages);
            // 递归处理子项
            printOutline(item, strs);
            // 获取同级
            item= item.getNextSibling();
        }

    }
}
