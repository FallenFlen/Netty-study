package com.flz.nettystudy.unpackingAndSticking;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class UnpackingAndStickingClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new UnpackingAndStickingEncoder())
                .addLast(new UnpackingAndStickingDecoder())
                .addLast(new UnpackingAndStickingClientHandler());
    }
}
