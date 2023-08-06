package com.flz.nettystudy.common.base.udp;

import com.flz.nettystudy.common.base.NamedEndpoint;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCommonCustomUdpBlockedClient extends BaseUdpClient implements NamedEndpoint {
    private ChannelInitializer<NioDatagramChannel> channelInitializer;

    protected AbstractCommonCustomUdpBlockedClient(String host, int port, ChannelInitializer<NioDatagramChannel> channelInitializer) {
        super(host, port);
        this.channelInitializer = channelInitializer;
    }

    @Override
    protected void doConnect() throws Throwable {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();

        ChannelFuture channelFuture = this.bootstrap.group(this.group)
                .channel(NioDatagramChannel.class)
                .handler(this.channelInitializer)
                .connect(this.host, this.port)
                .sync()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("[{}] udp client started!", getEndpointDescription());
                    }
                });

        channelFuture.channel().closeFuture().sync();
    }

    @Override
    public String getEndpointDescription() {
        return getClass().getName();
    }
}
