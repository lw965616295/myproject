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
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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
        Scanner scanner = new Scanner(System.in);
        AtomicBoolean LOGIN = new AtomicBoolean(false);
        CountDownLatch wait = new CountDownLatch(1);
        try {
            ChannelFuture future = new Bootstrap()
                    .group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new LoggingHandler());
                            ch.pipeline().addLast(frameDecoder);
                            ch.pipeline().addLast(messageCodec);
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<Message>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
                                    log.debug(message.toString());
                                    String respMsg = message.getRespMsg();
                                    if("登录成功！".equals(respMsg)){
                                        LOGIN.set(true);
                                    }
                                    // 释放
                                    wait.countDown();
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("已连接服务器！{}", ctx.channel().remoteAddress());
                                    // 开启线程，处理各种聊天操作
                                    new Thread(()->{
                                        String name = null;
                                            System.out.println("请输入用户名：");
                                            name = scanner.nextLine();
                                            System.out.println("请输入密码：");
                                            String pwd = scanner.nextLine();
                                            Message message = new Message();
                                            message.setOperateType(OperateType.LOGIN);
                                            message.setName(name);
                                            message.setPwd(pwd);
                                            ctx.writeAndFlush(message);
                                            System.out.println("等待后续操作...");
                                            try {
                                                wait.await();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            if(!LOGIN.get()){
                                                // 登录不成功，结束
                                                ctx.channel().close();
                                                return;
                                            }
                                        while (true){
                                            System.out.println("==================================");
                                            System.out.println("send [username] [content]");
                                            System.out.println("gsend [group name] [content]");
                                            System.out.println("gcreate [group name] [m1,m2,m3...]");
                                            System.out.println("gmembers [group name]");
                                            System.out.println("gjoin [group name]");
                                            System.out.println("gquit [group name]");
                                            System.out.println("quit");
                                            System.out.println("==================================");
                                            String command = scanner.nextLine();
                                            String[] s = command.split(" ");
                                            switch (s[0]){
                                                case "send":
                                                    Message m1 = new Message();
                                                    m1.setOperateType(OperateType.SEND);
                                                    m1.setName(name);
                                                    m1.setTo(s[1]);
                                                    m1.setContent(s[2]);
                                                    ctx.writeAndFlush(m1);
                                                    break;
                                                case "gcreate":
                                                    Message m2 = new Message();
                                                    m2.setOperateType(OperateType.GCREATE);
                                                    m2.setGName(s[1]);
                                                    String[] split = s[2].split(",");
                                                    Set<String> members = Arrays.stream(split).collect(Collectors.toSet());
                                                    // 自己也加入
                                                    members.add(name);
                                                    m2.setMembers(members);
                                                    ctx.writeAndFlush(m2);
                                                    break;
                                                case "gsend":
                                                    Message m3 = new Message();
                                                    m3.setOperateType(OperateType.GSEND);
                                                    m3.setGName(s[1]);
                                                    m3.setContent(s[2]);
                                                    m3.setFrom(name);
                                                    ctx.writeAndFlush(m3);
                                                    break;
                                            }

                                        }

                                    }).start();
                                }
                                // 在连接断开时触发
                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("连接已经断开，按任意键退出..");
                                }

                                // 在出现异常时触发
                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    log.debug("连接已经断开，按任意键退出..{}", cause.getMessage());
                                }
                            });

                        }
                    })
                    .connect("127.0.0.1", 8080)
                    .sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        }finally {
            loopGroup.shutdownGracefully();
        }
    }
}
