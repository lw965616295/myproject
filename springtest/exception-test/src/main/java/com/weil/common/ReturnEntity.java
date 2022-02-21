package com.weil.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName ReturnEntity
 * @Author weil
 * @Description //返回实体
 * @Date 2022/2/18 16:49
 * @Version 1.0.0
 **/
@Data
@Accessors(chain = true)
public class ReturnEntity<T> {
    private String code;
    private String msg;
    private T t;
}
