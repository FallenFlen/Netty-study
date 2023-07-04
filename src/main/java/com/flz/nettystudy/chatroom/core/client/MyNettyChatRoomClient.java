package com.flz.nettystudy.chatroom.core.client;

import com.flz.nettystudy.chatroom.core.config.MyNettyChatRoomClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MyNettyChatRoomClient {
    private String host;
    private int port;
    private NioEventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;
    public static final int MAX_RECONNECT_TIME = 3;
    private volatile boolean connectSuccess;

    public MyNettyChatRoomClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean reconnect() throws InterruptedException {
        int reconnectCount = 0;
        while (reconnectCount < MAX_RECONNECT_TIME) {
            try {
                connect();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                TimeUnit.SECONDS.sleep(1L);
                reconnectCount++;
            }
        }
        return false;
    }

    public void connect() throws InterruptedException {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        try {
            ChannelFuture channelFuture = this.bootstrap.group(this.group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyNettyChatRoomClientInitializer(this))
                    .connect(this.host, this.port)
                    .sync()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            connectSuccess = true;
                        }
                    });
            this.channel = channelFuture.channel();
            startSending();
            channelFuture.channel().closeFuture().sync();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("客户端启动失败");
            connectSuccess = false;
            throw throwable;
        } finally {
            this.group.shutdownGracefully();
        }
    }

    private void startSending() {
        Scanner scanner = new Scanner(System.in);
        boolean isReady = connectSuccess;
        while (!isReady) {
            isReady = connectSuccess;
        }

        System.out.println("准备就绪，可以发送消息了");

        while (connectSuccess && scanner.hasNext() && channel.isActive()) {
            String msg = scanner.nextLine();
            if (channel.isActive()) {
                channel.writeAndFlush(msg + "\r\n");
            } else {
                break;
            }
        }

        System.out.println("Stop sending message");
    }
}
