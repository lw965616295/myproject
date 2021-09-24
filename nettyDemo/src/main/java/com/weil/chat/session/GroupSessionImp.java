package com.weil.chat.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName GroupSessionImp
 * @Author weil
 * @Description //聊天室会话实现
 * @Date 2021/9/23 16:50
 * @Version 1.0.0
 **/
public class GroupSessionImp implements GroupSession {
    // 保存聊天会话
    private final Map<String, Group> groupMap = new ConcurrentHashMap<>();
    @Override
    public Group createGroup(String name, Set<String> members) {
        Group group = new Group(name, members);
        // 不存在key，代表保存成功并返回null;否则返回原来的value,保存失败
        return groupMap.putIfAbsent(name, group);
    }

    @Override
    public Group joinMember(String name, String member) {
        // 如果存在key就存入并返回新值；否则返回null
        return groupMap.computeIfPresent(name, (k,v)->{
            v.getMembers().add(member);
            return v;
        });
    }

    @Override
    public Group removeMember(String name, String member) {
        return groupMap.computeIfPresent(name, (k,v)->{
            v.getMembers().remove(member);
            return v;
        });
    }

    @Override
    public Set<String> getMembers(String name) {
        return groupMap.getOrDefault(name, Group.EMPTY_GROUP).getMembers();
    }

    @Override
    public List<Channel> getChannels(String name) {
        return getMembers(name).stream().map(p->ChatSessionFactory.getSession().getChannel(p)).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
