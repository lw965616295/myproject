package com.weil.document.resolver;

import com.weil.document.model.ResolveParam;
import com.weil.document.model.TextModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Name: TxtResolver
 * @Description: txt文件解析
 * @Author: weil
 * @Date: 2024-07-31 10:40
 * @Version: 1.0
 */
public class TxtResolver extends DefaultResolver {
    @Override
    protected TextModel textResolve(ResolveParam param) {
        TextModel textModel = new TextModel();

        // 存放解析全文文本
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(param.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            textModel.setFullText(sb.toString());
            // 重置字节数组流
            param.getInputStream().reset();
        }catch (Exception e){
            e.printStackTrace();
        }
        return textModel;
    }


    @Override
    protected String homepageResolve(ResolveParam param) {
        return null;
    }
}
