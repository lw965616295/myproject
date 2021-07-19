package com.weil.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName NioDemo
 * @Author weil
 * @Description //nio demo
 * @Date 2021/7/2 16:59
 * @Version 1.0.0
 **/
@Slf4j
public class NioDemo {
    /**
     * nio简单使用
     * channel的read方法将数据写到bytebuffer,然后通过bytebuffer的get方法将数据读到内存
     */
    @Test
    public void nioDemo() {
        // 类加载器获取文件路径
        String path = null;
        try {
            path = NioDemo.class.getClassLoader().getResource("demo.txt").getPath();
            log.debug("文件地址：{}", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (FileChannel channel = new FileInputStream(path).getChannel()) {
            //分配buffer空间
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            byte[] aByte = new byte[10];
            int count;
            while ((count = channel.read(byteBuffer))>0){//读数据时pos指针会从0处往后移动,当读不到数据时会返回-1
                log.debug("读取到的字节数 {}", count);
                //切换读模式（就是改变pos指针的位置到0处开始读数据，并且limit记录了pos的位置，避免读到空）
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()){//判断当前指针pos是不是小于limit指针（limit指针标记最大可读数量）
                    byte b = byteBuffer.get();//每次读一个字节
                    log.debug(String.valueOf((char)b));
                }
                //切换写模式（读完数据后，指针会到达limit处，所以要再次切换pos位置到0处）或者compact()
                byteBuffer.clear();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * byteBuffer一些用法
     */
    @Test
    public void byteBufferUsage1(){
        // 这里分配10个字节空间
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        // 通过put方法存值，使用putChar一次暂用2个字节
        byteBuffer.putChar('a');
        byteBuffer.putChar('b');
        byteBuffer.putChar('c');
        // 切换读模式
        byteBuffer.flip();
        // 如果这边使用byteBuffer.clear()会出现下面结果；
        // 解释：clear类似与重置byteBuffer里面的指针的位置（并不会清空数据），pos:0,limit:10,cap:10，这样再读取的时候，就会把剩余的4个字节也读了，
        // 而使用byteBuffer.flip()，只会把pos指针的位置变成0，limit依旧在6的位置
        //15:11:23 [DEBUG] [main] c.w.nio.NioDemo - a
        //15:11:23 [DEBUG] [main] c.w.nio.NioDemo - b
        //15:11:23 [DEBUG] [main] c.w.nio.NioDemo - c
        //15:11:23 [DEBUG] [main] c.w.nio.NioDemo -
        //15:11:23 [DEBUG] [main] c.w.nio.NioDemo -

        //正确结果：
        //15:23:31 [DEBUG] [main] c.w.nio.NioDemo - a
        //15:23:31 [DEBUG] [main] c.w.nio.NioDemo - b
        //15:23:31 [DEBUG] [main] c.w.nio.NioDemo - c
        while (byteBuffer.hasRemaining()){
            char aChar = byteBuffer.getChar();
            log.debug(String.valueOf(aChar));
        }
    }
    @Test
    public void byteBufferUsage2(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        // 通过put方法存值
        byteBuffer.putChar('a');
        byteBuffer.putChar('b');
        byteBuffer.putChar('c');
        // 切换读模式
        byteBuffer.flip();
        // 使用compact()来切换写模式，会把未读的数据提出来放到数组前面，然后再从他们后面开始写
        // 先读一个a再使用compact()，然后再写一个a，然后再读出所有的，那么打印结果就变成：b c a
        // 结果与预期一样：
        //15:27:42 [DEBUG] [main] c.w.nio.NioDemo - 读出一个a：a
        //15:27:42 [DEBUG] [main] c.w.nio.NioDemo - b
        //15:27:42 [DEBUG] [main] c.w.nio.NioDemo - c
        //15:27:42 [DEBUG] [main] c.w.nio.NioDemo - a
        char c = byteBuffer.getChar();
        log.debug("读出一个a：{}", c);
        byteBuffer.compact();
        byteBuffer.putChar('a');
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            char aChar = byteBuffer.getChar();
            log.debug(String.valueOf(aChar));
        }
    }
    @Test
    public void byteBufferUsage3(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        // 通过put方法存值
        byteBuffer.putChar('a');
        byteBuffer.putChar('b');
        byteBuffer.putChar('c');
        // 切换读模式
        byteBuffer.flip();
        // get(int i)方法不会让pos指针往下走，这样可以重复读那一个字节的数据
        // 执行多次读取a的操作
        //15:41:34 [DEBUG] [main] c.w.nio.NioDemo - 读出一个a：a
        //15:41:34 [DEBUG] [main] c.w.nio.NioDemo - 读出一个a：a
        //15:41:34 [DEBUG] [main] c.w.nio.NioDemo - 读出一个a：a
        byte a = byteBuffer.get(1);
        log.debug("读出一个a：{}", (char)a);
        byte b = byteBuffer.get(1);
        log.debug("读出一个a：{}", (char)b);
        byte c = byteBuffer.get(1);
        log.debug("读出一个a：{}", (char)c);
    }
    @Test
    public void byteBufferUsage4(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        // 通过put方法存值
        byteBuffer.putChar('a');
        byteBuffer.putChar('b');
        byteBuffer.putChar('c');
        // 切换读模式
        byteBuffer.flip();
        // byteBuffer.rewind()方法会让pos指针重置到0，那就可以多次读取；和flip()很像，但它不会将limit位置变成pos
        while (byteBuffer.hasRemaining()){
            char aChar = byteBuffer.getChar();
            log.debug(String.valueOf(aChar));
        }
        byteBuffer.rewind();
        log.debug("再来一次读取》》》");
        while (byteBuffer.hasRemaining()){
            char aChar = byteBuffer.getChar();
            log.debug(String.valueOf(aChar));
        }
        //15:50:43 [DEBUG] [main] c.w.nio.NioDemo - a
        //15:50:43 [DEBUG] [main] c.w.nio.NioDemo - b
        //15:50:43 [DEBUG] [main] c.w.nio.NioDemo - c
        //15:50:43 [DEBUG] [main] c.w.nio.NioDemo - 再来一次读取》》》
        //15:50:43 [DEBUG] [main] c.w.nio.NioDemo - a
        //15:50:43 [DEBUG] [main] c.w.nio.NioDemo - b
        //15:50:43 [DEBUG] [main] c.w.nio.NioDemo - c
    }
    @Test
    public void byteBufferUsage5(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        // 通过put方法存值
        byteBuffer.putChar('a');
        byteBuffer.putChar('b');
        byteBuffer.putChar('c');
        // 切换读模式
        byteBuffer.flip();
        // byteBuffer.mark()可以标记位置，再通过byteBuffer.reset()，会将pos指向mark标记的位置来实现标记重复读的操作
        // 注意：rewind 和 flip 都会清除 mark 位置

        char c = byteBuffer.getChar();
        log.debug("读了一个：{}", c);
        byteBuffer.mark();
        log.debug("加了一个标记mark，继续往下读>>>");
        while (byteBuffer.hasRemaining()){
            log.debug(String.valueOf(byteBuffer.getChar()));
        }
        log.debug("从标记位置再读一次>>>");
        byteBuffer.reset();
        while (byteBuffer.hasRemaining()){
            log.debug(String.valueOf(byteBuffer.getChar()));
        }
        //16:02:07 [DEBUG] [main] c.w.nio.NioDemo - 读了一个：a
        //16:02:07 [DEBUG] [main] c.w.nio.NioDemo - 加了一个标记mark，继续往下读>>>
        //16:02:07 [DEBUG] [main] c.w.nio.NioDemo - b
        //16:02:07 [DEBUG] [main] c.w.nio.NioDemo - c
        //16:02:07 [DEBUG] [main] c.w.nio.NioDemo - 从标记位置再读一次>>>
        //16:02:07 [DEBUG] [main] c.w.nio.NioDemo - b
        //16:02:07 [DEBUG] [main] c.w.nio.NioDemo - c
    }
    /**
     * byteBuffer和string相互转换
     */
    @Test
    public void byteBuffer_String(){
        ByteBuffer bb = Charset.defaultCharset().encode("你好");
        while (bb.hasRemaining()){
            log.debug("读出：{}",bb.get());
        }

        bb.flip();
        CharBuffer charBuffer = Charset.defaultCharset().decode(bb);
        log.debug("转换：{}", charBuffer.toString());
        //16:18:56 [DEBUG] [main] c.w.nio.NioDemo - 读出：-28
        //16:18:56 [DEBUG] [main] c.w.nio.NioDemo - 读出：-67
        //16:18:56 [DEBUG] [main] c.w.nio.NioDemo - 读出：-96
        //16:18:56 [DEBUG] [main] c.w.nio.NioDemo - 读出：-27
        //16:18:56 [DEBUG] [main] c.w.nio.NioDemo - 读出：-91
        //16:18:56 [DEBUG] [main] c.w.nio.NioDemo - 读出：-67
        //16:18:56 [DEBUG] [main] c.w.nio.NioDemo - 你好
    }

    /**
     * 分散读取
     */
    @Test
    public void readMethod() {
        RandomAccessFile raf = null;
        try {
            String path = NioDemo.class.getClassLoader().getResource("demo.txt").getPath();
            raf = new RandomAccessFile(path, "rw");
            FileChannel channel = raf.getChannel();
            ByteBuffer allocate1 = ByteBuffer.allocate(9);
            ByteBuffer allocate2 = ByteBuffer.allocate(3);
            channel.read(new ByteBuffer[]{allocate1, allocate2});
            allocate1.flip();
            allocate2.flip();
            log.debug("allocate1:{}", Charset.defaultCharset().decode(allocate1).toString());
            log.debug("allocate2:{}", Charset.defaultCharset().decode(allocate2).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(raf != null){
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //16:35:24 [DEBUG] [main] c.w.nio.NioDemo - allocate1:123456789
        //16:35:24 [DEBUG] [main] c.w.nio.NioDemo - allocate2:asd
    }

    /**
     * 统一写
     */
    @Test
    public void writeMethod(){
        String path = NioDemo.class.getClassLoader().getResource("demo.txt").getPath();
        try (RandomAccessFile rw = new RandomAccessFile(path, "rw")) {
            FileChannel channel = rw.getChannel();
            ByteBuffer b1 = ByteBuffer.allocate(4);
            ByteBuffer b2 = ByteBuffer.allocate(4);
            b1.put(new byte[]{'a','b','c'});
            b2.put(new byte[]{'1','2','3'});
            b1.flip();
            b2.flip();
            channel.write(new ByteBuffer[]{b1, b2});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
