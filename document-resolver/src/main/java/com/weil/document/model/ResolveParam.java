package com.weil.document.model;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

/**
 * @Name: ResolveParam
 * @Description: 解析参数
 * @Author: weil
 * @Date: 2024-07-30 11:25
 * @Version: 1.0
 */
@Data
@Builder
public class ResolveParam {
    /**
     * 文件流
     **/
    private InputStream inputStream;
}
