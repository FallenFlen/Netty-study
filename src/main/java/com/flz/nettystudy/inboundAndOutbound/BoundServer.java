package com.flz.nettystudy.inboundAndOutbound;

import com.flz.nettystudy.common.base.tcp.BaseTcpServer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class BoundServer extends BaseTcpServer {
    public BoundServer(int port) {
        super(port);
    }

    @Override
    protected void doStart() throws Throwable {
        this.serverBootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new BoundServerInitializer())
                .bind(this.port)
                .sync()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("[BoundServer] Started!");
                    }
                })
                .channel()
                .closeFuture()
                .sync();
    }

    public static void main(String[] args) {
        new BoundServer(9967).start();
    }
}
