package com.flz.nettystudy.common.base.udp;

import com.flz.nettystudy.common.base.NamedEndpoint;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class AbstractCommonCustomUdpServer extends BaseUdpServer implements NamedEndpoint {
    private ChannelInitializer<SocketChannel> channelInitializer;

    protected AbstractCommonCustomUdpServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port);
        this.channelInitializer = Objects.requireNonNull(channelInitializer);
    }

    @Override
    protected void doStart() throws Throwable {
        ChannelFuture channelFuture = this.bootstrap.group(this.group)
                .channel(DatagramChannel.class)
                .handler(this.channelInitializer)
                .bind(this.port)
                .sync()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("[{}] udp server started!", getEndpointDescription());
                    }
                });
        channelFuture.channel().closeFuture().sync();
    }

    @Override
    public String getEndpointDescription() {
        return getClass().getName();
    }
}
