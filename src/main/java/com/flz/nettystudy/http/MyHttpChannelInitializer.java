package com.flz.nettystudy.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyHttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // netty提供的默认的http编解码器，解析http的请求和响应报文
        socketChannel.pipeline()
                .addLast(new HttpServerCodec())
                .addLast(new MyHttpServerHandler());
    }
}
