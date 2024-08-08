package com.weil.document.resolver;

import com.weil.document.exception.ResolvingException;
import com.weil.document.model.ResolveParam;
import com.weil.document.model.TextModel;
import com.weil.document.utils.InputStreamConvert;

/**
 * @Name: DefaultResolver
 * @Description: 抽象类型解析器
 * @Author: weil
 * @Date: 2024-07-30 09:19
 * @Version: 1.0
 */
public abstract class DefaultResolver implements Resolver {
    @Override
    public void resolve(ResolveParam param) {
        // 校验参数
        checkParam(param);
        // 转换输入流
        convertInputStream(param);
        // 解析全文获取全文文本和段落文本数据
        TextModel textModel = textResolve(param);
        System.out.println("fulltext： " + textModel.getFullText());
        System.out.println("sectionMap: ");

        if(textModel.getSectionMap() != null){
            textModel.getSectionMap().forEach((k, v) -> {
                System.out.println("章节：" + k.split("#")[0]);
                System.out.println("内容：" + v);
            });
        }

        // 获取首页图片
        String path = homepageResolve(param);
        System.out.println("pic path: " + path);
    }

    private void convertInputStream(ResolveParam param) {
        param.setInputStream(InputStreamConvert.convert(param.getInputStream()));
    }

    /**
     * 校验参数
     */
    private void checkParam(ResolveParam param) {
        if (param == null || param.getInputStream() == null) {
            throw new ResolvingException("输入流不存在！");
        }
    }

    /**
     * 文本解析
     * @return: TextModel
     * @author: weil
     * @date: 2024/7/30 9:22
     **/
    protected abstract TextModel textResolve(ResolveParam param);

    /**
     * 首页图解析
     * @return: String 图片路径
     * @author: weil
     * @date: 2024/7/30 9:25
     **/
    protected abstract String homepageResolve(ResolveParam param);
}
