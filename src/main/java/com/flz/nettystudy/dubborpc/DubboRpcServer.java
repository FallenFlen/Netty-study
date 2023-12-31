package com.flz.nettystudy.dubborpc;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class DubboRpcServer extends AbstractCommonCustomTcpServer {
    protected DubboRpcServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port, channelInitializer);
    }

    @Override
    public String getEndpointDescription() {
        return "DubboRpcServer";
    }

    public static void main(String[] args) {
        new DubboRpcServer(9969, new DubboRpcServerInitializer()).start();
    }
}
