package com.weil.chat.session;

/**
 * @ClassName ChatSessionFactory
 * @Author weil
 * @Description //聊天会话工厂
 * @Date 2021/9/15 11:22
 * @Version 1.0.0
 **/
public abstract class ChatSessionFactory {
    private static ChatSession session = new ChatSessionImp();
    public static final ChatSession getSession(){
        return session;
    }
}
