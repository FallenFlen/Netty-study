package com.flz.nettystudy.chatroom.core.config;

import com.flz.nettystudy.chatroom.core.handler.MyNettyChatRoomServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MyNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                .addLast("encoder", new StringEncoder())
                .addLast("decoder", new StringDecoder())
                .addLast("myNettyChatRoomServerHandler", new MyNettyChatRoomServerHandler());
    }
}
