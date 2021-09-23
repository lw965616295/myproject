package com.weil.chat.handler;

import com.weil.chat.common.OperateType;
import com.weil.chat.protocol.Message;
import com.weil.chat.service.UserLogin;
import com.weil.chat.service.UserLoginImp;
import com.weil.chat.session.ChatSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ChatHandler
 * @Author weil
 * @Description //聊天处理器
 * @Date 2021/9/23 11:39
 * @Version 1.0.0
 **/
@ChannelHandler.Sharable
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<Message> {
    UserLogin userLogin = new UserLoginImp();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("有客户端连接,{}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("失去连接，{}", ctx.channel().remoteAddress());
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        OperateType operateType = message.getOperateType();
        switch (operateType){
            case LOGIN:
                //登录
                boolean flag = userLogin.login(message.getName(), message.getPwd());
                if(flag){
                    ChatSessionFactory.getSession().bind(message.getName(), ctx.channel());
                    Message reMsg = new Message();
                    reMsg.setSuccess(true);
                    reMsg.setRespMsg("登录成功！");
                    ctx.writeAndFlush(reMsg);
                }else {
                    Message reMsg = new Message();
                    reMsg.setSuccess(false);
                    reMsg.setRespMsg("登录失败！");
                    ctx.writeAndFlush(reMsg);
                }
                break;
            case SEND:
                String to = message.getTo();
                Channel c1 = ChatSessionFactory.getSession().getChannel(to);
                if(c1 == null){
                    // 不在线
                    Message m1 = new Message();
                    m1.setSuccess(false);
                    m1.setRespMsg("对方不存在或者不在线！");
                    ctx.writeAndFlush(m1);
                }else {
                    // 在线
                    c1.writeAndFlush(message);
                }
                break;
        }
    }
}
