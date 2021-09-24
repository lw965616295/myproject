package com.weil.chat.session;

/**
 * @ClassName GroupSessionFactory
 * @Author weil
 * @Description //聊天室会话工厂
 * @Date 2021/9/24 9:48
 * @Version 1.0.0
 **/
public abstract class GroupSessionFactory {
    private static GroupSession groupSession = new GroupSessionImp();
    public static final GroupSession getGroupSession(){
        return groupSession;
    }
}
