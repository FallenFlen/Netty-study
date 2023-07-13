package com.flz.nettystudy.unpackingAndSticking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class UnpackingAndStickingDecoder extends ReplayingDecoder<UnpackingAndStickingMessageProtocol> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("[UnpackingAndStickingDecoder] 启动!");
        int length = in.readInt();
        // 这里的buffer就被限制了长度，长度取决于发送方消息的长度，因此读取到的数据也被限制了长度，固能读出单次发送的完整消息
        byte[] buffer = new byte[length];
        in.readBytes(buffer);
        UnpackingAndStickingMessageProtocol messageProtocol = UnpackingAndStickingMessageProtocol.of(length, buffer);
        out.add(messageProtocol);
    }
}
