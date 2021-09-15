package com.weil.chat;

import com.weil.chat.common.OperateType;
import com.weil.chat.protocol.FrameDecoder;
import com.weil.chat.protocol.Message;
import com.weil.chat.protocol.MessageCodec;
import com.weil.chat.service.UserLogin;
import com.weil.chat.service.UserLoginImp;
import com.weil.chat.session.ChatSessionFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ChatServer
 * @Author weil
 * @Description //聊天服务器
 * @Date 2021/9/7 16:33
 * @Version 1.0.0
 **/
@Slf4j
public class ChatServer {
    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        FrameDecoder frameDecoder = new FrameDecoder();
        MessageCodec messageCodec = new MessageCodec();
        UserLogin userLogin = new UserLoginImp();
        try {
            ChannelFuture future = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 编解码器
                            ch.pipeline().addLast(frameDecoder);
                            ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(messageCodec);
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<Message>(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("有客户端连接,{}", ctx.channel().remoteAddress());
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
                                    OperateType operateType = message.getOperateType();
                                    switch (operateType){
                                        case LOGIN :
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
                                    }
                                }
                            });
                        }
                    }).bind(8080).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
