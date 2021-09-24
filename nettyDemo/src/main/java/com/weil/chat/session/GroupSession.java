package com.weil.chat.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * @InterfaceName GroupSession
 * @Author weil
 * @Description //聊天室会话
 * @Date 2021/9/23 16:30
 * @Version 1.0.0
 **/
public interface GroupSession {
    /**
     * 创建聊天室
     */
    Group createGroup(String name, Set<String> members);
    /**
     * 加入成员
     */
    Group joinMember(String name, String member);
    /**
     * 移除成员
     */
    Group removeMember(String name, String member);
    /**
     * 获取聊天室所有成员
     */
    Set<String> getMembers(String name);
    /**
     * 获取聊天室所有成员对应的channel
     */
    List<Channel> getChannels(String name);
}
