package com.weil.chat;

import com.alibaba.fastjson.JSONObject;
import com.weil.chat.common.OperateType;
import com.weil.chat.protocol.FrameDecoder;
import com.weil.chat.protocol.Message;
import com.weil.chat.protocol.MessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName ChatClient
 * @Author weil
 * @Description //聊天客户端
 * @Date 2021/9/13 15:08
 * @Version 1.0.0
 **/
@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        FrameDecoder frameDecoder = new FrameDecoder();
        MessageCodec messageCodec = new MessageCodec();
        try {
            ChannelFuture future = new Bootstrap()
                    .group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler());
                            ch.pipeline().addLast(frameDecoder);
                            ch.pipeline().addLast(messageCodec);
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<Message>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
                                    log.debug(message.toString());
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("已连接服务器！{}", ctx.channel().remoteAddress());
                                    Message message = new Message();
                                    message.setOperateType(OperateType.LOGIN);
                                    message.setName("lisi");
                                    message.setPwd("123");
                                    ctx.writeAndFlush(message);
                                }

                            });

                        }
                    })
                    .connect("127.0.0.1", 8080)
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            loopGroup.shutdownGracefully();
        }
    }
}
