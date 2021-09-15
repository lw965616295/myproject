package com.weil.chat.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ChatSessionImp
 * @Author weil
 * @Description //会话实现
 * @Date 2021/9/15 11:15
 * @Version 1.0.0
 **/
public class ChatSessionImp implements ChatSession {
    private static final Map<String, Channel> nameChannelMap = new ConcurrentHashMap<>();
    private static final Map<Channel, String> channelNameMap = new ConcurrentHashMap<>();
    @Override
    public void bind(String name, Channel channel) {
        nameChannelMap.put(name, channel);
        channelNameMap.put(channel, name);
    }

    @Override
    public void unbind(Channel channel) {
        nameChannelMap.remove(channelNameMap.get(channel));
        channelNameMap.remove(channel);
    }

    @Override
    public Channel getChannel(String name) {
        return nameChannelMap.get(name);
    }
}
