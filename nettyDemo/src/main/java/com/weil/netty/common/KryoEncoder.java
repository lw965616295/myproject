package com.weil.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * @ClassName KryoEncoder
 * @Author weil
 * @Description //TODO
 * @Date 2021/7/1 13:29
 * @Version 1.0.0
 **/
public class KryoEncoder extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        byteBuf.writeCharSequence(s, Charset.defaultCharset());
    }
}
