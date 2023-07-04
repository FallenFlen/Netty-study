package com.flz.nettystudy.common.handler;

import com.flz.nettystudy.common.exception.MyClientDisconnectedException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.WriteTimeoutException;

public class MyClientHeartBeatHandler extends ChannelInboundHandlerAdapter {
    public static final String HEART_BEAT = "client_heart_beat";

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                ping(ctx.channel());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    private void ping(Channel channel) {
        if (!channel.isActive()) {
            throw new MyClientDisconnectedException();
        }
        channel.writeAndFlush(HEART_BEAT);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("因长时间空闲，连接已断开");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof MyClientDisconnectedException) {
            System.out.println("客户端已断开连接，停止发送心跳包");
            ctx.channel().close();
        } else if (cause instanceof WriteTimeoutException) {
            System.out.println("客户端发生写超时，已自动断开连接");
//            ctx.channel().close(); 会自动触发channel关闭
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }
}
