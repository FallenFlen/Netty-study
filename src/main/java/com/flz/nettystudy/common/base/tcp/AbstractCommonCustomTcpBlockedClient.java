package com.flz.nettystudy.common.base.tcp;

import com.flz.nettystudy.common.base.NamedEndpoint;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 通用持续性客户端，发完消息不会断开连接
 */
public abstract class AbstractCommonCustomTcpBlockedClient extends BaseTcpClient implements NamedEndpoint {
    private ChannelInitializer<SocketChannel> channelInitializer;

    public AbstractCommonCustomTcpBlockedClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
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

        channelFuture.channel().closeFuture().sync();
    }

    @Override
    public String getEndpointDescription() {
        return getClass().getName();
    }
}
