package com.flz.nettystudy.common.base.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

public abstract class BaseTcpClient {
    protected String host;
    protected int port;
    protected NioEventLoopGroup group;
    protected Bootstrap bootstrap;
    protected boolean connected;

    public BaseTcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws Throwable {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        try {
            doConnect();
            this.connected = true;
        } catch (Throwable throwable) {
            System.out.println("[NettyClient] connected failed");
            throwable.printStackTrace();
            throw throwable;
        } finally {
            this.group.shutdownGracefully();
        }
    }

    protected abstract void doConnect() throws Throwable;

    public boolean isConnected() {
        return connected;
    }
}
