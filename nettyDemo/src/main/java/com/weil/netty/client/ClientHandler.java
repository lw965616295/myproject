package com.weil.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.weil.netty.common.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;

/**
 * @ClassName ClientHandler
 * @Author weil
 * @Description //客户端处理
 * @Date 2021/6/30 14:14
 * @Version 1.0.0
 **/
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(s);
        Student student = new Student();
        student.setName("weil");
        student.setAge(12);
        student.setBooks(Arrays.asList("语文书","数学书","音乐书"));
        ctx.channel().writeAndFlush(JSONObject.toJSONString(student));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端打印···");
        ctx.channel().writeAndFlush("客户端来了一个连接信息！");
    }
}
