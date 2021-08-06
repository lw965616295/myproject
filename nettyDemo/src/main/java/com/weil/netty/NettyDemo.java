package com.weil.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.EventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @ClassName NettyDemo
 * @Author weil
 * @Description //netty的相关组件
 * @Date 2021/8/6 16:10
 * @Version 1.0.0
 **/
@Slf4j
public class NettyDemo {
    @Test
    public void eventLoop(){
        NioEventLoopGroup loopGroup = new NioEventLoopGroup(2);
        // eventLoop在轮巡
        //16:30:08 [DEBUG] [main] c.w.n.NettyDemo - io.netty.channel.nio.NioEventLoop@33990a0c
        //16:30:08 [DEBUG] [main] c.w.n.NettyDemo - io.netty.channel.nio.NioEventLoop@4de4b452
        //16:30:08 [DEBUG] [main] c.w.n.NettyDemo - io.netty.channel.nio.NioEventLoop@33990a0c
        log.debug(loopGroup.next().toString());
        log.debug(loopGroup.next().toString());
        log.debug(loopGroup.next().toString());

        System.out.println();

        // 或者用遍历来拿出每个线程（该接口实现了iteration）
        //16:35:29 [DEBUG] [main] c.w.n.NettyDemo - io.netty.channel.nio.NioEventLoop@33990a0c
        //16:35:29 [DEBUG] [main] c.w.n.NettyDemo - io.netty.channel.nio.NioEventLoop@4de4b452
        for (EventExecutor eventExecutor : loopGroup) {
            log.debug(eventExecutor.toString());
        }
    }
}
