package com.weil.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName RedisClient
 * @Author weil
 * @Description //按照redis协议操作redis
 * @Date 2021/9/3 15:37
 * @Version 1.0.0
 **/
@Slf4j
public class RedisClient {
    public static void main(String[] args) {
        // redis协议

        /*
            *<参数数量> CR LF
            $<参数 1 的字节数量> CR LF
            <参数 1 的数据> CR LF
            ...
            $<参数 N 的字节数量> CR LF
            <参数 N 的数据> CR LF
         */
        // CR LF 回车换行
        byte[] LINE = {13, 10};
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new LoggingHandler());
                            channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("建立连接！");
                                    set(ctx);
                                }

                                /**
                                 * set aa 123
                                 */
                                private void set(ChannelHandlerContext ctx){
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    buffer.writeBytes("*3".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("set".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$2".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("aa".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("123".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    ctx.channel().writeAndFlush(buffer);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf buf = (ByteBuf) msg;
                                    log.debug("读出：{}", buf.toString(StandardCharsets.UTF_8));
                                }
                            });
                        }
                    }).connect("127.0.0.1", 6379).sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            group.shutdownGracefully();
        }
    }
}
