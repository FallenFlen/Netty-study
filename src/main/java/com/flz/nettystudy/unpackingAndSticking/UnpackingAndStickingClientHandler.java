package com.flz.nettystudy.unpackingAndSticking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class UnpackingAndStickingClientHandler extends SimpleChannelInboundHandler<UnpackingAndStickingMessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UnpackingAndStickingMessageProtocol messageProtocol) throws Exception {
        String message = new String(messageProtocol.getContent(), StandardCharsets.UTF_8);
        System.out.printf("收到服务器消息，长度：%s，内容：%s\n", messageProtocol.getLength(), message);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String message = "随机消息" + i;
            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            UnpackingAndStickingMessageProtocol messageProtocol = UnpackingAndStickingMessageProtocol.of(bytes.length, bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }
}
