package com.weil.pdfbox;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name: Test
 * @Description:
 * @Author: weil
 * @Date: 2024-07-25 15:15
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) throws Exception {
//        File file = new File("E:\\navicat.pdf");
        File file = new File("C:\\Users\\weil\\Desktop\\test.pdf");
        PDDocument document = PDDocument.load(file);


        // 获取PDF文件的结构树根对象
//        PDStructureTreeRoot structureTree = document.getDocumentCatalog().getStructureTreeRoot();

        // 获取PDF文件的文档目录对象
        PDDocumentOutline documentOutline = document.getDocumentCatalog().getDocumentOutline();

        // 存储标题和页码
        List<String> strs = new ArrayList<>();
        // 输出目录内容
        if (documentOutline != null) {

            printOutline(documentOutline, "",0, strs);
        }

//        strs.stream().forEach(System.out::println);

        // 创建PDFTextStripper对象，用于提取文本
//        PDFTextStripper pdfStripper = new PDFTextStripper();
//        // 提取文本
//        String text = pdfStripper.getText(document);
//        // 输出文本内容
//        System.out.println(text);
        PDFTextStripper stripper = new PDFTextStripper();
        // 开启后特殊符号后面的文本也能解析
        stripper.setSortByPosition(true);

        // 遍历标题
        for (int titleIdx=0; titleIdx < strs.size(); titleIdx++) {
            // 当前标题信息
            String currentStr = strs.get(titleIdx);
            // 下一个标题
            String nextStr = "";
            if(titleIdx+1<=strs.size()) {
                nextStr = strs.get(titleIdx+1);
            }

//            System.out.println(currentStr);
//            System.out.println(nextStr);
            String[] curr = currentStr.split("#");
            String currentTitle = curr[0];
            Integer currentNum = Integer.parseInt(curr[1]);

            String nextTitle = "";
            Integer nextNum = 0;
            if(StringUtils.isNotEmpty(nextStr)){
                String[] next = nextStr.split("#");
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
                System.out.println("Current title: " + currentTitle);
                System.out.println("content: " + content);
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
                System.out.println("Current title: " + currentTitle);
                System.out.println("content: " + content);
            }

        }

        // 关闭PDDocument对象
        document.close();

    }

    // 递归输出目录内容
    private static void printOutline(PDOutlineNode documentOutline, String indent, int i, List<String> strs) throws Exception {
        PDOutlineItem item = documentOutline.getFirstChild();
        i++;
        indent = indent + "    ";
        while (item != null){
            //        PDPageDestination destination = (PDPageDestination) item.getDestination();
//        int pageNumber = destination.retrievePageNumber();

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
//            System.out.println("------" +indent + item.getTitle() + "----"+pages+"   层级:"+i );
            strs.add(item.getTitle()+"#"+pages);
            // 递归处理子项
            printOutline(item, indent ,i, strs);
//        获取同级
            item= item.getNextSibling();
        }

    }

}