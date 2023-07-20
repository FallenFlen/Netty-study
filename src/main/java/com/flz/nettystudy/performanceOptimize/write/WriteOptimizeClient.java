package com.flz.nettystudy.performanceOptimize.write;

import com.flz.nettystudy.common.base.AbstractCommonCustomContinuousClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WriteOptimizeClient extends AbstractCommonCustomContinuousClient {
    public WriteOptimizeClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(host, port, channelInitializer);
    }

    public static void main(String[] args) throws Throwable {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    new WriteOptimizeClient("127.0.0.1", 8080, new WriteOptimizeClientChannelInitializer()).connect();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
