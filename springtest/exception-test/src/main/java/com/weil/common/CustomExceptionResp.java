package com.weil.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CustomExceptionResp
 * @Author weil
 * @Description //自定义异常信息响应
 * @Date 2022/2/18 16:21
 * @Version 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomExceptionResp {
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 错误类型
     */
    private String errorType;
}
