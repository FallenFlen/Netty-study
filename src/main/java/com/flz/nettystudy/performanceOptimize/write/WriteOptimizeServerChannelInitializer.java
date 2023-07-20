package com.flz.nettystudy.performanceOptimize.write;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.flush.FlushConsolidationHandler;

public class WriteOptimizeServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new FlushConsolidationHandler(5))
                .addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new WriteOptimizeServerHandler());
    }
}
