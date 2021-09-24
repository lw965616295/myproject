package com.weil.chat;

import com.weil.chat.handler.ChatHandler;
import com.weil.chat.protocol.FrameDecoder;
import com.weil.chat.protocol.MessageCodec;
import com.weil.chat.service.UserLogin;
import com.weil.chat.service.UserLoginImp;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
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
        MessageCodec messageCodec = new MessageCodec();
        ChatHandler chatHandler = new ChatHandler();
        try {
            ChannelFuture future = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 编解码器
                            ch.pipeline().addLast(new FrameDecoder());
                            ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(messageCodec);
                            // 5秒没有收到信息，触发读空闲事件
                            ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
                            // 处理空闲事件
                            ch.pipeline().addLast(new ChannelDuplexHandler(){
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                                    if(idleStateEvent.state().equals(IdleState.READER_IDLE)){
                                        log.debug("5秒没读到数据，关闭连接");
                                        // 关闭连接
                                        ctx.channel().close();
                                    }
                                }
                            });
                            ch.pipeline().addLast(chatHandler);
                        }
                    }).bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
