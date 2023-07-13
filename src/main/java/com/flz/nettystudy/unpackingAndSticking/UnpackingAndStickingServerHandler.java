package com.flz.nettystudy.unpackingAndSticking;

import com.flz.nettystudy.common.utils.RandomUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class UnpackingAndStickingServerHandler extends SimpleChannelInboundHandler<UnpackingAndStickingMessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UnpackingAndStickingMessageProtocol messageProtocol) throws Exception {
        String message = new String(messageProtocol.getContent(), StandardCharsets.UTF_8);
        String remoteAddress = channelHandlerContext.channel().remoteAddress().toString();
        System.out.printf("收到来自客户端[%s]的消息，长度：%s，内容：%s\n", remoteAddress, messageProtocol.getLength(), message);

        String responseMessage = "已成功发送消息，id：" + RandomUtils.randomUUID();
        byte[] responseMessageBytes = responseMessage.getBytes(StandardCharsets.UTF_8);
        UnpackingAndStickingMessageProtocol responseMessageProtocol = UnpackingAndStickingMessageProtocol
                .of(responseMessageBytes.length, responseMessageBytes);
        channelHandlerContext.writeAndFlush(responseMessageProtocol);
    }
}
