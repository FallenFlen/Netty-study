package com.flz.nettystudy.protection;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpBlockedClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ProtectionExperimentClient extends AbstractCommonCustomTcpBlockedClient {

    public ProtectionExperimentClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(host, port, channelInitializer);
    }

    public static void main(String[] args) throws Throwable {
        new ProtectionExperimentClient("127.0.0.1", 8080, new ProtectionExperimentClientChannelInitializer()).connect();
    }
}
