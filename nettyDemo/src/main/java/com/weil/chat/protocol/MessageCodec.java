package com.weil.chat.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @ClassName MessageCodec
 * @Author weil
 * @Description //消息编解码器
 * @Date 2021/9/14 10:04
 * @Version 1.0.0
 **/
@ChannelHandler.Sharable
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> list) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] bytes = JSONObject.toJSONString(msg).getBytes();
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
        list.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readInt();
        byte[] b = new byte[length];
        byteBuf.readBytes(b, 0, length);
        String s = new String(b);
        Message message = JSON.parseObject(s, Message.class);
        list.add(message);
    }
}
