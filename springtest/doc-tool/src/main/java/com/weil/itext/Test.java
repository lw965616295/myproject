package com.weil.itext;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Name: Test2
 * @Description:
 * @Author: weil
 * @Date: 2024-07-25 15:31
 * @Version: 1.0
 */
public class Test {
    public static void main ( String [] args ) throws Exception {
        String filePath = "C:\\Users\\weil\\Desktop\\test.pdf";
        PdfReader reader = new PdfReader ( filePath) ;
        List list = SimpleBookmark.getBookmark ( reader ) ;
        for (Iterator i = list.iterator (); i.hasNext () ; ) {
            showBookmark ((Map) i.next ()) ;
        }
    }

    private static void showBookmark ( Map bookmark ) {
        System.out.println(bookmark.get("Title"));
        System.out.println(bookmark);
        ArrayList kids = (ArrayList) bookmark.get("Kids");
        if (kids == null){
            return;
        }
        for ( Iterator i = kids.iterator () ; i.hasNext () ; ) {
            showBookmark (( Map ) i.next ()) ;
        }
    }
}
