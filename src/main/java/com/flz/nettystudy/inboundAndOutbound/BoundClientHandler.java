package com.flz.nettystudy.inboundAndOutbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class BoundClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("[BoundClientHandler] received from server:" + aLong);
        // 给客户端响应数据
        channelHandlerContext.channel().writeAndFlush(aLong * 2);
    }

}
