package com.weil.tika;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class PageContentHandler extends ToXMLContentHandler {
    private String pageTag = "div";
    private String pageClass = "page";
    private int pageNumber = 0;

    private Map<Integer,StringBuilder> pageMap;

    public PageContentHandler(){
        super();
        pageMap = new HashMap<>();
    }

    private void startPage() {
        pageNumber++;
        pageMap.put(pageNumber,new StringBuilder());
    }

    private void endPage() {
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if(pageTag.equals(qName) && pageClass.equals(atts.getValue("class"))){
            startPage();
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(pageTag.equals(qName)){
            endPage();
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if(length > 0 && pageNumber > 0){
            if(ch.length == 1 && ch[0] == '\n'){
                return;
            }
            pageMap.get(pageNumber).append(ch);
//            pageMap.get(pageNumber).append('\n');
        }
    }

    public static void main(String[] args) throws Exception{
        PageContentHandler handler = new PageContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream("C:\\Users\\weil\\Desktop\\多个文档处理工具对比.pdf");

        ParseContext pcontext = new ParseContext();

        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(inputstream, handler, metadata,pcontext);

        //getting the content of the document by pages.
        for(Map.Entry<Integer,StringBuilder> entry:handler.pageMap.entrySet()){
            System.out.println("======Page " + entry.getKey() + "=======");
            System.out.println(entry.getValue().toString());
        }

        //getting metadata of the document
        System.out.println("Metadata of the PDF:");
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name+ " : " + metadata.get(name));
        }
    }
}

