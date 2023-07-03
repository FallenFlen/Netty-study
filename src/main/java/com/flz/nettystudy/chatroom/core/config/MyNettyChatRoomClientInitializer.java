package com.flz.nettystudy.chatroom.core.config;

import com.flz.nettystudy.chatroom.core.handler.MyNettyChatRoomClientHandler;
import com.flz.nettystudy.common.handler.MyIdleHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class MyNettyChatRoomClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("encoder", new StringEncoder())
                .addLast("decoder", new StringDecoder())
                .addLast("myNettyChatRoomClientHandler", new MyNettyChatRoomClientHandler())
                .addLast("idleStateHandler", new IdleStateHandler(3, 5, 8))
                .addLast("writeTimeoutHandler", new WriteTimeoutHandler(20))
                .addLast("myIdleHandler", new MyIdleHandler());
    }
}
