package com.flz.nettystudy.chatroom.core.client;

import com.flz.nettystudy.chatroom.core.config.MyNettyChatRoomClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class MyNettyChatRoomClient {
    private String host;
    private int port;
    private NioEventLoopGroup group;
    private Bootstrap bootstrap;

    public MyNettyChatRoomClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
    }

    public void start() throws InterruptedException {
        try {
            ChannelFuture channelFuture = this.bootstrap.group(this.group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyNettyChatRoomClientInitializer())
                    .connect(this.host, this.port)
                    .sync()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println("客户端启动成功");
                        }
                    });

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String msg = scanner.nextLine();
                channelFuture.channel().writeAndFlush(msg + "\r\n");
            }
        } catch (Throwable throwable) {
            System.out.println("客户端启动失败");
            throwable.printStackTrace();
        } finally {
            this.group.shutdownGracefully();
        }


    }
}
