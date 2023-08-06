package com.flz.nettystudy.unpackingAndSticking;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class UnpackingAndStickingServer extends AbstractCommonCustomTcpServer {

    protected UnpackingAndStickingServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port, channelInitializer);
    }

    @Override
    public String getEndpointDescription() {
        return "UnpackingAndStickingServer";
    }

    public static void main(String[] args) {
        new UnpackingAndStickingServer(9968, new UnpackingAndStickingServerChannelInitializer()).start();
    }
}
