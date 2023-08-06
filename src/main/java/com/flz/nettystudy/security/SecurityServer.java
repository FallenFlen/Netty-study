package com.flz.nettystudy.security;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpOptionServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class SecurityServer extends AbstractCommonCustomTcpOptionServer {
    protected SecurityServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port, channelInitializer);
    }

    @Override
    protected ServerBootstrap withOptions(ServerBootstrap serverBootstrap) {
        return serverBootstrap;
    }

    public static void main(String[] args) {
        new SecurityServer(8081, new SecurityServerChannelInitializer()).start();
    }
}
