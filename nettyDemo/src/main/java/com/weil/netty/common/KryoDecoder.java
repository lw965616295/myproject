package com.weil.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @ClassName KryoDecoder
 * @Author weil
 * @Description //TODO
 * @Date 2021/7/1 13:52
 * @Version 1.0.0
 **/
public class KryoDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] b = new byte[36];
        byteBuf.readBytes(b);
        System.out.println(new String(b, Charset.defaultCharset()));

    }
}
