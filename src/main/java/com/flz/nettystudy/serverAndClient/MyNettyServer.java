package com.flz.nettystudy.serverAndClient;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyNettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 服务器端使用的channel
                    // ChannelOption.SO_BACKLOG对应的是tcp/ip协议, listen函数 中的 backlog 参数，用来初始化服务端可连接队列
                    // backlog 用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // tcp参数，使用心跳机制保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 为worker group中，pipeline设置处理器handler
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new MyNettyServerHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync(); // 异步绑定端口并启动

            channelFuture.channel().closeFuture().sync(); // 异步关闭,对关闭通道进行监听（即有关闭事件时才会触发真正的关闭）
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            // 真关闭，优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
