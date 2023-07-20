package com.flz.nettystudy.performanceOptimize.write;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class WriteOptimizeServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到数据:" + msg);
        ctx.writeAndFlush("已收到消息");
    }
}
