package com.flz.nettystudy.common.base;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Objects;

public abstract class AbstractCommonCustomServer extends BaseServer implements NamedEndpoint {
    private ChannelInitializer<SocketChannel> channelInitializer;

    protected AbstractCommonCustomServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port);
        this.channelInitializer = Objects.requireNonNull(channelInitializer);
    }

    @Override
    protected void doStart() throws Throwable {
        this.serverBootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                    }
                })
                .childHandler(this.channelInitializer)
                .bind(this.port)
                .sync()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.printf("[%s] Started!\n", getEndpointDescription());
                    }
                })
                .channel()
                .closeFuture()
                .sync();
    }

    @Override
    public String getEndpointDescription() {
        return getClass().getName();
    }
}
