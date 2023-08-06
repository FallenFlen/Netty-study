package com.flz.nettystudy.protection;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpOptionServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ProtectionExperimentServer extends AbstractCommonCustomTcpOptionServer {
    protected ProtectionExperimentServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port, channelInitializer);
    }

    @Override
    protected ServerBootstrap withOptions(ServerBootstrap serverBootstrap) {
        return serverBootstrap;
    }

    public static void main(String[] args) {
        new ProtectionExperimentServer(8080, new ProtectionExperimentServerChannelInitializer()).start();
    }
}
