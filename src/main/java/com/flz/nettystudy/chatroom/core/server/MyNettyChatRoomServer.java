package com.flz.nettystudy.chatroom.core.server;

import com.flz.nettystudy.chatroom.core.config.MyNettyServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyNettyChatRoomServer {
    private int port;
    private NioEventLoopGroup boosGroup;
    private NioEventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;

    public MyNettyChatRoomServer(int port) {
        this.port = port;
        this.boosGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
    }

    public void start() {
        try {
            this.serverBootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new MyNettyServerChannelInitializer())
                    .bind(this.port)
                    .sync()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println("[MyNettyChatRoomServer] Chat room server started");
                        }
                    })
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("[MyNettyChatRoomServer] error occurred when starting chat room server");
        } finally {
            this.workerGroup.shutdownGracefully();
            this.boosGroup.shutdownGracefully();
        }
    }
}
