package com.flz.nettystudy.unpackingAndSticking;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class UnpackingAndStickingClient extends AbstractCommonCustomTcpClient {


    public UnpackingAndStickingClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(host, port, channelInitializer);
    }

    @Override
    public String getEndpointDescription() {
        return "UnpackingAndStickingClient";
    }

    public static void main(String[] args) throws Throwable {
        new UnpackingAndStickingClient("127.0.0.1", 9968, new UnpackingAndStickingClientChannelInitializer())
                .connect();
    }
}
