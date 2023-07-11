package com.flz.nettystudy.inboundAndOutbound;

import com.flz.nettystudy.common.utils.RandomUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class BoundServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("[BoundServer] received data:" + aLong);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        long randomLong = RandomUtils.nextLong();
        System.out.println("[BoundServerHandler] push data:" + randomLong);
        ctx.channel().writeAndFlush(randomLong);
    }
}
