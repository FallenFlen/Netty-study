package com.flz.nettystudy.common.base;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

public abstract class BaseClient {
    protected String host;
    protected int port;
    protected NioEventLoopGroup group;
    protected Bootstrap bootstrap;
    protected boolean connected;

    public BaseClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        try {
            doConnect();
            this.connected = true;
        } catch (Throwable throwable) {
            System.out.println("[NettyClient] connected failed");
            throwable.printStackTrace();
        } finally {
            this.group.shutdownGracefully();
        }
    }

    protected abstract void doConnect() throws Throwable;

    public boolean isConnected() {
        return connected;
    }
}
