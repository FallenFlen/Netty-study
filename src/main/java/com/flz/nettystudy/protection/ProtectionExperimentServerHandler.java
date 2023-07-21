package com.flz.nettystudy.protection;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ProtectionExperimentServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        Channel channel = ctx.channel();
        String address = channel.remoteAddress().toString();
        System.out.println(String.format("客户端%s的消息:%s", address, msg.toString(CharsetUtil.UTF_8)));
        // 高低水位线保护，当channel可写时，才能响应数据
        if (channel.isActive() && channel.isWritable()) {
            ByteBuf response = Unpooled.copiedBuffer(String.format("客户端%s的响应", address), CharsetUtil.UTF_8);
            channel.writeAndFlush(response);
        }
    }
}
