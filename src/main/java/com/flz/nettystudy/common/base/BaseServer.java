package com.flz.nettystudy.common.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

public abstract class BaseServer {
    protected int port;
    protected NioEventLoopGroup boosGroup;
    protected NioEventLoopGroup workerGroup;
    protected ServerBootstrap serverBootstrap;

    protected BaseServer(int port) {
        this.port = port;
        this.boosGroup = new NioEventLoopGroup(new DefaultThreadFactory("boss"));
        this.workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("worker"));
        this.serverBootstrap = new ServerBootstrap();
    }

    public final void start() {
        try {
            doStart();
        } catch (Throwable throwable) {
            System.out.println("[NettyServer] error occurred when starting server");
            throwable.printStackTrace();
        } finally {
            this.workerGroup.shutdownGracefully();
            this.boosGroup.shutdownGracefully();
        }
    }

    protected abstract void doStart() throws Throwable;
}
