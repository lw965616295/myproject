package com.weil.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
        NioEventLoopGroup loopGroup1 = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        loopGroup1.scheduleAtFixedRate(()->{
            log.debug("执行了");
        },0,1000, TimeUnit.MICROSECONDS);
    }
    @Test
    public void futurePromise(){
//        多线程获取数据
        DefaultEventLoop eventLoop = new DefaultEventLoop();
        DefaultPromise<Integer> p = new DefaultPromise(eventLoop);
        eventLoop.execute(()->{
            p.setSuccess(123);
        });
        try {
            Object o = p.get();//同步阻塞方法
            log.debug("阻塞，{} ", o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 或者使用监听，即当前线程自己打印
        p.addListener(future -> {
            Object now = future.getNow();//非阻塞方法，但是此时是可以确定有值的
            log.debug("非阻塞，{}", now);
        });
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
        directBuf.writeInt(1);
        System.out.println(directBuf);
    }
    @Test
    public void byteBuf1(){
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(1);
        buffer.writeInt(2);
        buffer.writeInt(3);
        System.out.println(buffer.readInt());
        System.out.println(buffer);
        // 标记位置
        buffer.markReaderIndex();
        System.out.println(buffer.readInt());
        System.out.println(buffer);
        System.out.println(buffer.readInt());
        System.out.println(buffer);
        //
//        System.out.println(buffer.readInt());
//        System.out.println(buffer);
        // 重置位置
        System.out.println("重置位置");
        buffer.resetReaderIndex();
        System.out.println(buffer);
        System.out.println(buffer.readInt());
        System.out.println(buffer);
        System.out.println(buffer.readInt());
        System.out.println(buffer);
    }
    @Test
    public void byteBuf2(){
        // slice 切片
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{1,2,3,4});
        System.out.println(buffer);
        ByteBuf slice = buffer.slice();
//        slice.writeByte(5);  // 切片长度从创建的时候就固定了write指针到read指针之间，要是调用写会抛异常IndexOutOfBoundsException
        // slice占用的那块内存和buffer一样，所有修改操作会直接影响buffer
        slice.setByte(0, 5);
        System.out.println(buffer.readByte());
    }
    @Test
    public void byteBuf3(){
        // duplicate 与原内存相同,读写指针没有限制，可以添加；与slice不同
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{1,2,3,4});
        System.out.println(buffer);
        ByteBuf duplicate = buffer.duplicate();
        duplicate.writeByte(5);
        System.out.println(buffer);
        System.out.println(duplicate);
    }
    @Test
    public void byteBuf4(){
        // copy 这个是深拷贝，完全和原来的没有关系
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{1,2,3,4});
        System.out.println(buffer);
        ByteBuf copy = buffer.copy();
        copy.writeByte(5);
        System.out.println(buffer);
        System.out.println(copy);
    }
    @Test
    public void byteBuf5(){
        // composite 组合，避免内存复制
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{1,2,3,4});
        System.out.println(buffer);
        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.buffer(10);
        buffer1.writeBytes(new byte[]{6,7,8,9});
        System.out.println(buffer1);
        CompositeByteBuf bufs = ByteBufAllocator.DEFAULT.compositeBuffer();
        bufs.addComponents(buffer, buffer1);
        System.out.println(bufs);
    }
    @Test
    public void byteBuf6(){
        // 直接内存byteBuf 的手动释放
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{1,2,3,4});
        System.out.println(buffer);
        buffer.retain(); // 相当于引用计数加1
        buffer.release();// 相当于引用计数减1，当减为0就释放
        System.out.println(buffer);

    }
}
