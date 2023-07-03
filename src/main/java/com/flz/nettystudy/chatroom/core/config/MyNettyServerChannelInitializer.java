package com.flz.nettystudy.chatroom.core.config;

import com.flz.nettystudy.chatroom.core.handler.MyNettyChatRoomServerHandler;
import com.flz.nettystudy.common.handler.MyIdleHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class MyNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                .addLast("encoder", new StringEncoder())
                .addLast("decoder", new StringDecoder())
                .addLast("myNettyChatRoomServerHandler", new MyNettyChatRoomServerHandler())
                // netty自带的空闲处理器，检测到空闲之后，会发布IdleStateEvent，并将event传递给下一个handler
                .addLast("idleStateHandler", new IdleStateHandler(3, 5, 8))
                .addLast("myIdleHandler", new MyIdleHandler());
    }
}
