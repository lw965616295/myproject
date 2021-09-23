package com.weil.chat.protocol;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @ClassName FrameDecoder
 * @Author weil
 * @Description //TODO
 * @Date 2021/9/14 9:29
 * @Version 1.0.0
 **/
public class FrameDecoder extends LengthFieldBasedFrameDecoder {
    public FrameDecoder(){
        this(1024, 0, 4, 0, 0);
    }
    public FrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip){
        // 最大长度，长度偏移，长度占用字节，长度调整，剥离字节数
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
