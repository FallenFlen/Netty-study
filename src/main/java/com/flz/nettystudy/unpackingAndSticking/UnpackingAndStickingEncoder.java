package com.flz.nettystudy.unpackingAndSticking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class UnpackingAndStickingEncoder extends MessageToByteEncoder<UnpackingAndStickingMessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UnpackingAndStickingMessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("[UnpackingAndStickingEncoder] 启动!");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
