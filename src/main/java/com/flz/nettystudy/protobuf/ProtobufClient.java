package com.flz.nettystudy.protobuf;

import com.flz.nettystudy.protobuf.entity.Student;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.util.Random;
import java.util.Scanner;

public class ProtobufClient {
    private String host;
    private int port;
    private NioEventLoopGroup group;
    private Bootstrap bootstrap;

    public ProtobufClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        try {
            ChannelFuture channelFuture = this.bootstrap.group(this.group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new ProtobufEncoder());
                            channel.pipeline().addLast(new ProtobufDecoder(Student.getDefaultInstance()));
                            channel.pipeline().addLast(new ProtobufClientHandler());
                        }
                    })
                    .connect(this.host, this.port)
                    .sync()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println("[ProtobufClient] Started");
                        }
                    });
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();
            while (scanner.hasNext()) {
                String name = scanner.nextLine();
                Student student = Student.newBuilder()
                        .setId(random.nextInt(10000))
                        .setName(name)
                        .build();
                channelFuture.channel().writeAndFlush(student);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("客户端启动失败");
            throw throwable;
        } finally {
            this.group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ProtobufClient("127.0.0.1", 9966).start();
    }
}
