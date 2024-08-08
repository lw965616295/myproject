package com.weil.document.resolver;

import com.weil.document.exception.ResolvingException;
import com.weil.document.model.ResolveParam;

/**
 * @Name: Resolver
 * @Description: 文档解析器接口
 * @Author: weil
 * @Date: 2024-07-30 09:11
 * @Version: 1.0
 */
public interface Resolver {

    /**
     * 解析接口
     * @return: void
     * @author: weil
     * @date: 2024/7/30 9:20
     **/
    default void resolve(ResolveParam param){
        throw new ResolvingException("not implement");
    }
}
