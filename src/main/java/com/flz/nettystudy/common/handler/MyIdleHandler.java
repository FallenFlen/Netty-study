package com.flz.nettystudy.common.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyIdleHandler extends ChannelInboundHandlerAdapter {
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
}
