package com.flz.nettystudy.udp.server;

import com.flz.nettystudy.common.base.udp.AbstractCommonCustomUdpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MyUdpServer extends AbstractCommonCustomUdpServer {
    public MyUdpServer(int port, ChannelInitializer<NioDatagramChannel> channelInitializer) {
        super(port, channelInitializer);
    }

    public static void main(String[] args) {
        new MyUdpServer(9000, new ChannelInitializer<>() {
            @Override
            protected void initChannel(NioDatagramChannel ch) throws Exception {
                ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
                        .addLast(new UdpServerRequestDecoder())
                        .addLast(new UdpServerRequestEncoder())
                        .addLast(new UdpServerBusinessHandler());
            }
        }).start();
    }
}
