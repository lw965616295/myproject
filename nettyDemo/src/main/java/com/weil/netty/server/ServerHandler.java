package com.weil.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName ServerHandler
 * @Author weil
 * @Description //服务处理
 * @Date 2021/6/30 13:30
 * @Version 1.0.0
 **/
public class ServerHandler extends SimpleChannelInboundHandler<String> {
     String s="";
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(s);
        if(!this.s.equals(s))
        ctx.channel().writeAndFlush("123");
        this.s=s;
    }
}
