package com.flz.nettystudy.chatroom.core.config;

import com.flz.nettystudy.chatroom.core.handler.MyNettyChatRoomServerHandler;
import com.flz.nettystudy.common.handler.MyServerIdleStateHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class MyNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                .addLast("encoder", new StringEncoder())
                .addLast("decoder", new StringDecoder())
                .addLast("myNettyChatRoomServerHandler", new MyNettyChatRoomServerHandler())
                // netty自带的空闲处理器，检测到空闲之后，会发布IdleStateEvent，并将event传递给下一个handler
                .addLast("idleStateHandler", new IdleStateHandler(5, 5, 10))
                .addLast("readTimeoutHandler", new ReadTimeoutHandler(60)) // 如果读空闲时间长达x秒，则触发ReadTimeoutException,
                // 该异常会委派给下一个handler处理
                .addLast("myIdleHandler", new MyServerIdleStateHandler());
    }
}
