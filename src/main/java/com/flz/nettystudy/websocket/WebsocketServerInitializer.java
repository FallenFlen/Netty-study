package com.flz.nettystudy.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {
    public WebsocketServerInitializer(String endpoint) {
        this.endpoint = endpoint;
    }

    private String endpoint;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(8192))
                .addLast(new ChunkedWriteHandler())
                // 服务器根路径
                .addLast(new WebSocketServerProtocolHandler(endpoint, null, true))
                .addLast("myWebsocketServerHandler", new MyWebsocketServerHandler());
    }
}
