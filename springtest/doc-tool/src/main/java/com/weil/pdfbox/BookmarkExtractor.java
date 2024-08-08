package com.weil.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

import java.io.File;
import java.io.IOException;

public class BookmarkExtractor {

    public static void main(String[] args) {
        try {
            // 加载PDF文件
            PDDocument document = PDDocument.load(new File("C:\\Users\\weil\\Desktop\\test.pdf"));

            // 获取PDF文件中的所有书签
            PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();
            if (outline != null) {
                processOutline(outline, "", document);
            }

            // 关闭文档
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processOutline(PDOutlineNode outline, String indent, PDDocument document) throws IOException {
        PDOutlineItem current = outline.getFirstChild();
        while (current != null) {
            System.out.println(indent + "Title: " + current.getTitle());
            System.out.println(indent + "Page: " + current.findDestinationPage(document));
//            System.out.println(indent + "Parent: " + getParentTitle(outline));
            System.out.println();

            if (current.hasChildren()) {
                processOutline(current, indent + "  ", document);
            }

            current = current.getNextSibling();
        }
    }

//    private static String getParentTitle(PDOutlineNode outline) {
//        PDOutlineItem parent = outline.getParent();
//        if (parent != null) {
//            return parent.getTitle();
//        }
//        return "";
//    }
}