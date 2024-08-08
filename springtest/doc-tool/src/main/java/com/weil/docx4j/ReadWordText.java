package com.weil.docx4j;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.File;
import java.util.List;

public class ReadWordText {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\weil\\Desktop\\多个文档处理工具对比.docx";

        try {
            // Load the Word documentpackage com.weil.docx4j;
            //
            //import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
            //import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
            //import org.docx4j.wml.*;
            //
            //import java.io.File;
            //import java.util.List;
            //
            //public class ReadWordText {
            //    public static void main(String[] args) {
            //        String inputFilePath = "C:\\Users\\weil\\Desktop\\多个文档处理工具对比.docx";
            //
            //        try {
            //            // Load the Word document
            //            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(inputFilePath));
            //
            //            // Get the MainDocumentPart which contains the document content
            //            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
            //
            //            // Get the document body
            //            Body body = mainDocumentPart.getContents().getBody();
            //
            //            // Process the document content
            //            processDocumentContent(body);
            //        } catch (Exception e) {
            //            e.printStackTrace();
            //        }
            //    }
            //
            //    private static void processDocumentContent(Body body) {
            //        List<Object> content = body.getContent();
            //
            //        for (Object object : content) {
            //            if (object instanceof P) {
            //                // Handle paragraphs
            //                P paragraph = (P) object;
            //                System.out.println("Paragraph: " + getText(paragraph));
            //            } else if (object instanceof Tbl) {
            //                // Handle tables
            //                Tbl table = (Tbl) object;
            //                processTable(table);
            //            }
            //        }
            //    }
            //
            //    private static String getText(P paragraph) {
            //        StringBuilder text = new StringBuilder();
            //        List<Object> content = paragraph.getContent();
            //
            //        for (Object object : content) {
            //            if (object instanceof R) {
            //                R run = (R) object;
            ////                for (Object runContent : run.getContent()) {
            ////                    if (runContent instanceof Text) {
            ////                        Text txt = (Text) runContent;
            ////                        text.append(txt.getValue());
            ////                    }
            ////                }
            //                text.append(run.getParent());
            //            }
            //        }
            //        return text.toString();
            //    }
            //
            //    private static void processTable(Tbl table) {
            //        List<Object> rows = table.getContent();
            //
            //        for (Object rowObj : rows) {
            //            if (rowObj instanceof Tr) {
            //                Tr row = (Tr) rowObj;
            //                List<Object> cells = row.getContent();
            //
            //                for (Object cellObj : cells) {
            //                    if (cellObj instanceof Tc) {
            //                        Tc cell = (Tc) cellObj;
            //                        System.out.println("Table cell: " + getText(cell));
            //                    }
            //                }
            //            }
            //        }
            //    }
            //
            //    private static String getText(Tc tableCell) {
            //        StringBuilder text = new StringBuilder();
            //        List<Object> content = tableCell.getContent();
            //
            //        for (Object object : content) {
            //            if (object instanceof P) {
            //                P paragraph = (P) object;
            //                text.append(getText(paragraph)).append(" ");
            //            }
            //        }
            //        return text.toString().trim();
            //    }
            //}
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(inputFilePath));

            // Get the MainDocumentPart which contains the document content
            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();

            // Get the document body
            Body body = mainDocumentPart.getContents().getBody();

            // Process the document content
            processDocumentContent(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processDocumentContent(Body body) {
        List<Object> content = body.getContent();

        for (Object object : content) {
            if (object instanceof P) {
                // Handle paragraphs
                P paragraph = (P) object;
                System.out.println("Paragraph: " + getText(paragraph));
            } else if (object instanceof Tbl) {
                // Handle tables
                Tbl table = (Tbl) object;
                processTable(table);
            }
        }
    }

    private static String getText(P paragraph) {
        StringBuilder text = new StringBuilder();
        List<Object> content = paragraph.getContent();

        for (Object object : content) {
            if (object instanceof R) {
                R run = (R) object;
//                for (Object runContent : run.getContent()) {
//                    if (runContent instanceof Text) {
//                        Text txt = (Text) runContent;
//                        text.append(txt.getValue());
//                    }
//                }
                text.append(run.getParent());
            }
        }
        return text.toString();
    }

    private static void processTable(Tbl table) {
        List<Object> rows = table.getContent();

        for (Object rowObj : rows) {
            if (rowObj instanceof Tr) {
                Tr row = (Tr) rowObj;
                List<Object> cells = row.getContent();

                for (Object cellObj : cells) {
                    if (cellObj instanceof Tc) {
                        Tc cell = (Tc) cellObj;
                        System.out.println("Table cell: " + getText(cell));
                    }
                }
            }
        }
    }

    private static String getText(Tc tableCell) {
        StringBuilder text = new StringBuilder();
        List<Object> content = tableCell.getContent();

        for (Object object : content) {
            if (object instanceof P) {
                P paragraph = (P) object;
                text.append(getText(paragraph)).append(" ");
            }
        }
        return text.toString().trim();
    }
}
