package com.flz.nettystudy.inboundAndOutbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class BoundClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("[BoundClientHandler] received from server:" + aLong);
        // 给客户端响应数据
        long responseData = aLong * 2;
        System.out.println("[BoundClientHandler] response to server:" + responseData);
        channelHandlerContext.channel().writeAndFlush(responseData);
    }

}
