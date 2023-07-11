package com.flz.nettystudy.inboundAndOutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class BoundServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new LongTypeEncoder()) // 编码器：出站时编码数据
                .addLast(new LongTypeDecoder()) // 解码器：入站时解码数据
                .addLast(new BoundServerHandler());
    }
}
