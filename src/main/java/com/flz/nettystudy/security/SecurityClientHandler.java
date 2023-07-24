package com.flz.nettystudy.security;

import com.flz.nettystudy.common.utils.DateUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class SecurityClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String responseMsg = msg.toString(StandardCharsets.UTF_8);
        System.out.println(DateUtils.getCurrentTimeStr() + " " + responseMsg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");
    }
}
