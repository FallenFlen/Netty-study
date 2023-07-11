package com.flz.nettystudy.common.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

public abstract class BaseServer {
    protected int port;
    protected NioEventLoopGroup boosGroup;
    protected NioEventLoopGroup workerGroup;
    protected ServerBootstrap serverBootstrap;

    protected BaseServer(int port) {
        this.port = port;
        this.boosGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
    }

    public final void start() {
        try {
            doStart();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("[NettyServer] error occurred when starting server");
        } finally {
            this.workerGroup.shutdownGracefully();
            this.boosGroup.shutdownGracefully();
        }
    }

    protected abstract void doStart() throws Throwable;
}
