package com.flz.nettystudy.common.base.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

public abstract class BaseUdpServer {
    protected Bootstrap bootstrap;
    protected int port;
    protected NioEventLoopGroup group;

    protected BaseUdpServer(int port) {
        this.port = port;
        this.bootstrap = new Bootstrap();
        this.group = new NioEventLoopGroup(new DefaultThreadFactory("udp-server"));
    }

    public final void start() {
        try {
            doStart();
        } catch (Throwable throwable) {
            System.out.println("[NettyUdpServer] error occurred when starting udp server");
            throwable.printStackTrace();
        } finally {
            this.group.shutdownGracefully();
        }
    }

    protected abstract void doStart() throws Throwable;
}
