package com.flz.nettystudy.common.base.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

public abstract class BaseUdpClient {
    protected String host;
    protected int port;
    protected NioEventLoopGroup group;
    protected Bootstrap bootstrap;

    protected BaseUdpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws Throwable {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        try {
            doConnect();
        } catch (Throwable throwable) {
            System.out.println("[NettyUdpClient] connected failed");
            throwable.printStackTrace();
            throw throwable;
        } finally {
            this.group.shutdownGracefully();
        }
    }

    protected abstract void doConnect() throws Throwable;
}
