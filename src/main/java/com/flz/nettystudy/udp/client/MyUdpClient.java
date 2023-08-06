package com.flz.nettystudy.udp.client;

import com.flz.nettystudy.common.base.udp.AbstractCommonCustomUdpBlockedClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MyUdpClient extends AbstractCommonCustomUdpBlockedClient {
    protected MyUdpClient(String host, int port, ChannelInitializer<NioDatagramChannel> channelInitializer) {
        super(host, port, channelInitializer);
    }

    public static void main(String[] args) throws Throwable {
        new MyUdpClient("127.0.0.1", 9000, new ChannelInitializer<>() {
            @Override
            protected void initChannel(NioDatagramChannel ch) throws Exception {
                ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
                        .addLast(new UdpClientDecoder())
                        .addLast(new UdpClientEncoder())
                        .addLast(new UdpClientBusinessHandler());
            }
        }).connect();
    }
}
