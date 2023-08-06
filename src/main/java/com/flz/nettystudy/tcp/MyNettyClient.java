package com.flz.nettystudy.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyNettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap(); // client的Bootstrap跟server的不一样

        try {
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new MyNettyClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect("localhost", 6668).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
