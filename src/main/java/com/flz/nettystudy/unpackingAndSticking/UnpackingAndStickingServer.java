package com.flz.nettystudy.unpackingAndSticking;

import com.flz.nettystudy.common.base.AbstractCommonCustomServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class UnpackingAndStickingServer extends AbstractCommonCustomServer {

    protected UnpackingAndStickingServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port, channelInitializer);
    }

    @Override
    public String getEndpointDescription() {
        return "UnpackingAndStickingServer";
    }
}
