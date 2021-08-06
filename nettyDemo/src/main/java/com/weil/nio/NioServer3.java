package com.weil.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName NioServer3
 * @Author weil
 * @Description //多线程多路复用
 * @Date 2021/8/3 9:30
 * @Version 1.0.0
 **/
public class NioServer3 {
    public static void main(String[] args) throws IOException {
        new BossEventLoop().register();
    }
    /**
     * 一个boss线程专门处理accept事件
     * 线程包含独立的selector
     */
    @Slf4j
    static class BossEventLoop implements Runnable{
        // 存放selector给线程用
        private Selector boss;
        // 初始化工作线程数组
        private WorkEventLoop[] works;
        // 用作负载均衡算法
        private AtomicInteger i = new AtomicInteger();
        // 防止多次执行注册方法
        private volatile boolean flag = false;

        // 初始化服务
        public void register() throws IOException {
            if(!flag){
                ServerSocketChannel ssc = ServerSocketChannel.open();
                ssc.bind(new InetSocketAddress(8081));
                // 多路复用时关闭阻塞
                ssc.configureBlocking(false);
                // 绑定选择器
                boss = Selector.open();
//            ssc.register(selector, SelectionKey.OP_ACCEPT, null);
                // 也可以后添加连接事件
                SelectionKey sk = ssc.register(boss, 0, null);
                sk.interestOps(SelectionKey.OP_ACCEPT);
                // 初始化工作线程
                works = initWorker();
                // 循环处理事件
                new Thread(this, "boss").start();
                log.debug("boss start...");
                flag = true;
            }

        }

        /**
         * 初始化工作线程
         */
        public WorkEventLoop[] initWorker(){

//            WorkEventLoop[] workEventLoops = new WorkEventLoop[Runtime.getRuntime().availableProcessors()]; //系统核心数
            // 初始化两个
            WorkEventLoop[] workEventLoops = new WorkEventLoop[2];
            for (int j = 0; j < workEventLoops.length; j++) {
                workEventLoops[j] = new WorkEventLoop(j);
            }
            return workEventLoops;
        }

        @Override
        public void run() {
            while (true){
                try {
                    log.debug("boss 阻塞前");
                    boss.select();
                    log.debug("boss 阻塞后");
                    // 遍历事件
                    Set<SelectionKey> sks = boss.selectedKeys();
                    Iterator<SelectionKey> iterator = sks.iterator();
                    while (iterator.hasNext()){
                        SelectionKey sk = iterator.next();
                        iterator.remove();
                        if (sk.isAcceptable()) {
                            ServerSocketChannel ssc = (ServerSocketChannel) sk.channel();
                            // 获取客户端
                            SocketChannel sc = ssc.accept();
                            // 解除阻塞
                            sc.configureBlocking(false);
                            log.debug("连接{}", sc);
                            // 调用工作线程处理非accept事件
                            // 简单的负载均衡
                            works[i.getAndIncrement()%works.length].register(sc);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 工作线程，专门处理其他非accept事件
     * 独享一个selector
     */
    @Slf4j
    static class WorkEventLoop implements Runnable{
        private Selector worker;
        // 记录线程标识
        private Integer index;
        // 防止多次执行注册方法
        private volatile boolean flag = false;

        WorkEventLoop(Integer index){
            this.index = index;
        }

        // 这边接收一个由boss线程传入的SocketChannel
        public void register(SocketChannel sc) throws IOException {
            // 上面创建了两个work及两个work线程；所以当第三个客户端连上去的时候会再调第一个
            if(!flag){
                worker = Selector.open();
                // 开启线程执行
                new Thread(this, "worker-"+index).start();
                flag = true;
            }
            log.debug("绑定selector,{},{}",sc,worker);
            sc.register(worker, SelectionKey.OP_READ, null);
            // 唤起线程中的worker.select()，让上面的注册生效
            log.debug("唤醒：{}", worker);
            worker.wakeup();
        }

        @Override
        public void run() {
            while (true){
                try {
                    log.debug("work 阻塞前");
                    worker.select();
                    log.debug("work 阻塞后");
                    Set<SelectionKey> sks = worker.selectedKeys();
                    Iterator<SelectionKey> iterator = sks.iterator();
                    while (iterator.hasNext()){
                        SelectionKey sk = iterator.next();
                        if(sk.isReadable()){
                            SocketChannel sc = (SocketChannel) sk.channel();
                            ByteBuffer bb = ByteBuffer.allocate(6);
                            int read = sc.read(bb);
                            if(read == -1){
                                //读完了
                                sk.cancel();
                                sc.close();
                            }else {
                                log.debug("{}发来的信息", sc.getRemoteAddress());
                                bb.flip();
                                log.debug(Charset.defaultCharset().decode(bb).toString());
                                // 回复
                                sc.write(Charset.defaultCharset().encode("hhhh"));
                            }
                        }
                        iterator.remove();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
