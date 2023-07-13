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
        byte[] buffer = new byte[length];
        in.readBytes(buffer);
        UnpackingAndStickingMessageProtocol messageProtocol = UnpackingAndStickingMessageProtocol.of(length, buffer);
        out.add(messageProtocol);
    }
}
