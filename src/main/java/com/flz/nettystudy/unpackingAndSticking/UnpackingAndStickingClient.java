package com.flz.nettystudy.unpackingAndSticking;

import com.flz.nettystudy.common.base.AbstractCommonCustomClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class UnpackingAndStickingClient extends AbstractCommonCustomClient {


    public UnpackingAndStickingClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(host, port, channelInitializer);
    }

    @Override
    public String getEndpointDescription() {
        return "UnpackingAndStickingClient";
    }
}
