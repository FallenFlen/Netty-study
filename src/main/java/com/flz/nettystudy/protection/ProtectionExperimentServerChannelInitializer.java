package com.flz.nettystudy.protection;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;

public class ProtectionExperimentServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new GlobalTrafficShapingHandler(ch.eventLoop(), 0, 46));
        ch.pipeline().addLast(new ProtectionExperimentServerHandler());
    }
}
