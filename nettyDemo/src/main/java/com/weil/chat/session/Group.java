package com.weil.chat.session;

import lombok.Data;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName Group
 * @Author weil
 * @Description //聊天室
 * @Date 2021/9/23 16:24
 * @Version 1.0.0
 **/
@Data
public class Group {
    /**
     * 聊天室名
     */
    private String name;
    /**
     * 成员
     */
    private Set<String> members;
    public static final Group EMPTY_GROUP = new Group("empty", Collections.EMPTY_SET);

    public Group(String name, Set<String> members) {
        this.name = name;
        this.members = members;
    }
}
