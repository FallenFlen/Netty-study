package com.flz.nettystudy.common.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;

public class MyServerIdleStateHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            String eventName = "";
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    eventName = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventName = "写空闲";
                    break;
                case ALL_IDLE:
                    eventName = "读写空闲";
                    break;
            }
            System.out.println("检测到空闲事件发生---" + eventName);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ReadTimeoutException) {
            System.out.println("读超时发生，已终止与客户端的连接");
        }

        if (cause instanceof WriteTimeoutException) {
            System.out.println("写超时发生，客户端已主动断开连接");
        }
    }
}
