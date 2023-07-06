package com.flz.nettystudy.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebsocketServer {
    private int port;
    private NioEventLoopGroup boosGroup;
    private NioEventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;
    private String endpoint;

    public WebsocketServer(int port, String endpoint) {
        this.port = port;
        this.endpoint = endpoint;
        this.workerGroup = new NioEventLoopGroup();
        this.boosGroup = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
    }

    public void start() {
        try {
            this.serverBootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new WebsocketServerInitializer(this.endpoint))
                    .bind(this.port)
                    .sync()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println("Websocket server started!");
                        }
                    })
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Websocket server starts failed with exception");
        } finally {
            this.workerGroup.shutdownGracefully();
            this.boosGroup.shutdownGracefully();
        }
    }
}
