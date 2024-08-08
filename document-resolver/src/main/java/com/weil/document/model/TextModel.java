package com.weil.document.model;

import lombok.Data;

import java.util.Map;

/**
 * @Name: TextModel
 * @Description: 文本提取model
 * @Author: weil
 * @Date: 2024-07-30 13:22
 * @Version: 1.0
 */
@Data
public class TextModel {
    /**
     * 全文文本
     **/
    private String fullText;

    /**
     * 章节文本<章节，内容>
     **/
    private Map<String, String> sectionMap;
}
