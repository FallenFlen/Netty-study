package com.flz.nettystudy.performanceOptimize.write;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class WriteOptimizeServer extends AbstractCommonCustomTcpServer {

    protected WriteOptimizeServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port, channelInitializer);
    }

    public static void main(String[] args) {
        new WriteOptimizeServer(8080, new WriteOptimizeServerChannelInitializer()).start();
    }
}
