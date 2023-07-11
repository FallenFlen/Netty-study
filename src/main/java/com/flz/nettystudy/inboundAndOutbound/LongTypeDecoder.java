package com.flz.nettystudy.inboundAndOutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class LongTypeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 按8字节拆分读取的数据，如果一次性没读完，则会循环直到读完，循环会导致后续的inbound handler被调用多次，导致解码多次
        if (byteBuf.readableBytes() >= 8) {
            // list的数据会让下一个inbound handler接收
            list.add(byteBuf.readLong());
        }
    }
}
