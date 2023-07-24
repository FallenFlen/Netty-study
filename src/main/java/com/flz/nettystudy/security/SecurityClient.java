package com.flz.nettystudy.security;

import com.flz.nettystudy.common.base.AbstractCommonCustomContinuousClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class SecurityClient extends AbstractCommonCustomContinuousClient {

    public SecurityClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(host, port, channelInitializer);
    }

    public static void main(String[] args) throws Throwable {
        new SecurityClient("127.0.0.1", 8081, new SecurityClientChannelInitializer()).connect();
    }
}
