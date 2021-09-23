package com.weil.chat.protocol;

import com.weil.chat.common.OperateType;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName Message
 * @Author weil
 * @Description //消息模型
 * @Date 2021/9/7 16:49
 * @Version 1.0.0
 **/
@Data
@ToString
public class Message {
    /**
     * 操作类型
     */
    private OperateType operateType;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 响应
     */
    private boolean success;
    /**
     * 响应内容
     */
    private String respMsg;
    /**
     * 内容
     */
    private String content;
    /**
     * 到达
     */
    private String to;
    /**
     * 来源
     */
    private String from;
}
