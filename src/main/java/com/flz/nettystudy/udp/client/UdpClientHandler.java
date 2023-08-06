package com.flz.nettystudy.udp.client;

import com.flz.nettystudy.common.utils.JsonUtils;
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
public class UdpClientHandler extends SimpleChannelInboundHandler<UdpResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UdpResponse msg) throws Exception {
        // receive response
        log.info("receive full response from server:{}", JsonUtils.silentMarshal(msg));
        log.info("receive msg in full response from server:{}", msg.getResponseMessage().getContent().toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UdpRequest udpRequest = UdpRequest.builder()
                .requestMessage(UdpMessage.builder()
                        .content(Unpooled.copiedBuffer("I am Udp message:" + RandomUtils.randomUUID(), CharsetUtil.UTF_8))
                        .build())
                .sender((InetSocketAddress) ctx.channel().remoteAddress())
                .build();
        log.info("send udp message to server:{}", JsonUtils.silentMarshal(udpRequest));
        ctx.channel().writeAndFlush(udpRequest);
    }
}
