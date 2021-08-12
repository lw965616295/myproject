package com.weil.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ThreadTest
 * @Author weil
 * @Description //线程操作
 * @Date 2021/8/11 14:31
 * @Version 1.0.0
 **/
@Slf4j
public class ThreadTest {
    public static void main(String[] args) {
        log.debug("主线线程开始！");
        AtomicInteger i = new AtomicInteger(1);
        Thread thread = new Thread(() -> {
            log.debug("子线程开始！");
            while (true) {
                log.debug(String.valueOf(i.getAndIncrement()));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "子线程");
        // 设置成为守护线程（最低级别，jvm中没有其他线程操作时，其就可以不必存在），这里会随着主线程结束而结束
        // 默认不设置，就算主线程结束，其也不会立刻结束
        // 这里存在一个奇怪的情况；在junit模式下，不管是不是设置了守护线程，线程都会结束
//        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(2000);
            System.out.println("s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试线程优先级
     */
    @Test
    public void testPriority(){
        Thread t1 = new Thread(new myRunner(), "A线程");
        Thread t2 = new Thread(new myRunner(), "B线程");
        Thread t3 = new Thread(new myRunner(), "C线程");
        t1.setPriority(Thread.NORM_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t3.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
        t3.start();

        //延时一下，看上面结果；junit结束，子线程也会结束，所以要延迟
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    class myRunner implements Runnable{
        @Override
        public void run() {
            while (true){
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
