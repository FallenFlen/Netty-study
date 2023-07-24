package com.flz.nettystudy.security;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class SecurityClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new SecurityClientHandler());
    }
}
