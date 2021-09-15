package com.weil.chat.session;

import io.netty.channel.Channel;

/**
 * @InterfaceName ChatSession
 * @Author weil
 * @Description //聊天会话
 * @Date 2021/9/15 11:10
 * @Version 1.0.0
 **/
public interface ChatSession {
    /**
     * 绑定，用户名和频道
     */
    void bind(String name, Channel channel);
    /**
     * 解绑
     */
    void unbind(Channel channel);
    /**
     * 获取
     */
    Channel getChannel(String name);
}
