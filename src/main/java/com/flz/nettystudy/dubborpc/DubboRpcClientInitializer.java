package com.flz.nettystudy.dubborpc;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DubboRpcClientInitializer extends ChannelInitializer<SocketChannel> {
    private DubboRpcClientHandler dubboRpcClientHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(this.dubboRpcClientHandler);
    }
}
