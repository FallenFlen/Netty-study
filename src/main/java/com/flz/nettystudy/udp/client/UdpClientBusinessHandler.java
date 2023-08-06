package com.flz.nettystudy.udp.client;

import com.flz.nettystudy.common.utils.RandomUtils;
import com.flz.nettystudy.udp.dto.UdpMessage;
import com.flz.nettystudy.udp.dto.UdpRequest;
import com.flz.nettystudy.udp.dto.UdpResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class UdpClientBusinessHandler extends SimpleChannelInboundHandler<UdpResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UdpResponse msg) throws Exception {
        // receive response
        log.info("receive response from udp server:{}", msg.toDescription());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message = "I am Udp message:" + RandomUtils.randomUUID();
        UdpRequest udpRequest = UdpRequest.builder()
                .requestMessage(UdpMessage.builder()
                        .content(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8))
                        .build())
                .sender((InetSocketAddress) ctx.channel().remoteAddress())
                .build();
        log.info("send udp message to server:{}", message);
        ctx.channel().writeAndFlush(udpRequest);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
