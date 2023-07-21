package com.flz.nettystudy.protection;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ProtectionExperimentClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ProtectionExperimentClientHandler());
    }
}
