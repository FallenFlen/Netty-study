package com.flz.nettystudy.protobuf;

import com.flz.nettystudy.protobuf.entity.Student;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class ProtobufServer {
    private int port;
    private NioEventLoopGroup boosGroup;
    private NioEventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;

    public ProtobufServer(int port) {
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
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            channel.pipeline().addLast(new ProtobufEncoder());
                            channel.pipeline().addLast(new ProtobufDecoder(Student.getDefaultInstance()));
                            channel.pipeline().addLast(new ProtobufServerHandler());
                        }
                    })
                    .bind(this.port)
                    .sync()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println("[ProtobufServer] Chat room server started");
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

    public static void main(String[] args) {
        new ProtobufServer(9966).start();
    }
}
