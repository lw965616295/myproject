package com.weil.controller;

import lombok.extern.slf4j.Slf4j;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Slf4j
@RestController
@RequestMapping
public class ConvertController {

    /**
     * 注入文档转换接口，
     * springboot自动装配时会在容器中添加一个bean对象，
     * 也就是其实现类LocalConverter
     */
    @Autowired
    private DocumentConverter documentConverter;

    @GetMapping("/test")
    public void convert() {
        File source = new File("C:\\Users\\weil\\Desktop\\kkk.docx");
        File target = new File("C:\\Users\\weil\\Desktop\\out\\aa.pdf");
// convert方法指定源文件对象/输入流
// to方法指定目标文件对象/输出流(源文件可以不存在，如果不存在则会创建)
// 目标文件的后缀要和即将转换的文件类型一致，否则会造成文件损坏
// as方法接受的时DocumentFormat对象，我们可以使用默认注册完的DocumentFormat对象，
// execute方法是执行转换的方法，方法没有返回值，是同步执行
        try {
            documentConverter.convert(source).to(target).as(DefaultDocumentFormatRegistry.PDF).execute();
        } catch (OfficeException e) {
            log.error("文档转换异常:{}", e.getMessage());
        }

    }
}