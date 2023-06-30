package com.flz.nettystudy.chatroom.core.config;

import com.flz.nettystudy.chatroom.core.handler.MyNettyChatRoomClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MyNettyChatRoomClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("encoder", new StringEncoder())
                .addLast("decoder", new StringDecoder())
                .addLast("myNettyChatRoomClientHandler", new MyNettyChatRoomClientHandler());
    }
}
