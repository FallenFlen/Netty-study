package com.flz.nettystudy.common.base;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public abstract class AbstractCommonCustomClient extends BaseClient implements NamedEndpoint {
    private ChannelInitializer<SocketChannel> channelInitializer;

    public AbstractCommonCustomClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(host, port);
        this.channelInitializer = channelInitializer;
    }

    @Override
    protected void doConnect() throws Throwable {
        ChannelFuture channelFuture = this.bootstrap.group(this.group)
                .channel(NioSocketChannel.class)
                .handler(this.channelInitializer)
                .connect(this.host, this.port)
                .sync()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.printf("[%s] Started!\n", getEndpointDescription());
                    }
                });

//        channelFuture.channel().closeFuture().sync();
    }

    @Override
    public String getEndpointDescription() {
        return getClass().getName();
    }
}
