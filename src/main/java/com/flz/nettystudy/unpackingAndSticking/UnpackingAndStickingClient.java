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

    public static void main(String[] args) {
        new UnpackingAndStickingClient("127.0.0.1", 9968, new UnpackingAndStickingClientChannelInitializer())
                .connect();
    }
}
