package com.weil.thread;

/**
 * @ClassName DeadLockDemo
 * @Author weil
 * @Description //死锁模拟
 * @Date 2021/11/5 14:42
 * @Version 1.0.0
 **/
public class DeadLockDemo {
    public static void main(String[] args) {
        // 线程1拥有资源a;线程2拥有资源b;线程1等待b资源，线程2等待a资源；
        // 1和2谁都不想先放弃自己的资源，就这样僵持
        Object a = new Object();
        Object b = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (a){
                System.out.println(Thread.currentThread()+"get a");
                // 等待500ms，创造条件让线程2获取b资源
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 等待获取b资源
                System.out.println(Thread.currentThread()+"wait b");
                synchronized (b){
                    System.out.println(Thread.currentThread()+"get b");
                }
            }
        });
        t1.setName("线程1");
        t1.start();
        Thread t2 = new Thread(() -> {
            synchronized (b){
                System.out.println(Thread.currentThread()+"get b");
                // 等待500ms，创造条件让线程1获取a资源
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 等待获取b资源
                System.out.println(Thread.currentThread()+"wait a");
                synchronized (a){
                    System.out.println(Thread.currentThread()+"get a");
                }
            }
        });
        t2.setName("线程2");
        t2.start();

    }
    /**
     * 打破死锁的方式，或者使用超时等待wait(time)；这样就不需要其他线程进行唤醒操作；
     * 使用wait的原因使其能释放锁；相反sleep同样是让线程进入等待，但它不会释放锁
     */
    public void t1(){
        // 线程1拥有资源a;线程2拥有资源b;线程1等待b资源，线程2等待a资源；
        // 1和2谁都不想先放弃自己的资源，就这样僵持
        Object a = new Object();
        Object b = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (a){
                System.out.println(Thread.currentThread()+"get a");
                // 等待500ms，创造条件让线程2获取b资源
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 等待获取b资源
                System.out.println(Thread.currentThread()+"wait b");
                try {
                    a.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    System.out.println(Thread.currentThread()+"get b");
                }
            }
        });
        t1.setName("线程1");
        t1.start();
        Thread t2 = new Thread(() -> {
            synchronized (b){
                System.out.println(Thread.currentThread()+"get b");
                // 等待500ms，创造条件让线程1获取a资源
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 等待获取b资源
                System.out.println(Thread.currentThread()+"wait a");
                synchronized (a){
                    System.out.println(Thread.currentThread()+"get a");
                    a.notify();
                }
            }
        });
        t2.setName("线程2");
        t2.start();
    }
}
