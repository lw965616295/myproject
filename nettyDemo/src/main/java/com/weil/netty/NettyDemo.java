package com.weil.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
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
    @Test
    public void byteBuf(){
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer);//PooledUnsafeDirectByteBuf(ridx: 0, widx: 0, cap: 256) 默认256字节
        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.buffer(10);
        System.out.println(buffer1);//PooledUnsafeDirectByteBuf(ridx: 0, widx: 0, cap: 10)
        //默认是直接内存，可以手动改成堆内存或者直接内存
        ByteBuf heapBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        System.out.println(heapBuf);//PooledUnsafeHeapByteBuf
        ByteBuf directBuf = ByteBufAllocator.DEFAULT.directBuffer();
        System.out.println(directBuf);//PooledUnsafeDirectByteBuf
    }
}
