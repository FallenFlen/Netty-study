package com.flz.nettystudy.unpackingAndSticking;

import com.flz.nettystudy.common.utils.RandomUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

public class UnpackingAndStickingServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffer);
        String content = new String(buffer, StandardCharsets.UTF_8);
        String remoteAddress = channelHandlerContext.channel().remoteAddress().toString();
        System.out.printf("收到来自客户端[%s]的消息:%s\n", remoteAddress, content);

        ByteBuf response = Unpooled.copiedBuffer("已成功发送消息，id：" + RandomUtils.randomUUID(), CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(response);
    }
}
