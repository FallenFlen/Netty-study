package com.flz.nettystudy.inboundAndOutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LongTypeEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        // 按8字节拆分读取的数据，如果一次性没读完，则会循环直到读完，循环会导致后续的outbound handler被调用多次，导致编码多次
        if (byteBuf.readableBytes() >= 8) {
            byteBuf.readLong();
        }
    }
}
