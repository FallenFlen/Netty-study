package com.flz.nettystudy.udp.server;

import com.flz.nettystudy.common.utils.JsonUtils;
import com.flz.nettystudy.udp.dto.UdpMessage;
import com.flz.nettystudy.udp.dto.UdpRequest;
import com.flz.nettystudy.udp.dto.UdpResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class UdpServerBusinessHandler extends SimpleChannelInboundHandler<UdpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UdpRequest msg) throws Exception {
        // do business
        log.info("receive client udp full request:{}", JsonUtils.silentMarshal(msg));
        log.info("receive client udp message in full request:{}", msg.getRequestMessage().getContent().toString(CharsetUtil.UTF_8));
        // create UdpResponse and response it
        UdpMessage udpMessage = UdpMessage.builder()
                .content(msg.getRequestMessage().getContent())
                .build();
        UdpResponse response = UdpResponse.builder()
                .receiver((InetSocketAddress) ctx.channel().remoteAddress())
                .sender(msg.getSender())
                .responseMessage(udpMessage)
                .build();
        ctx.channel().writeAndFlush(response);
    }
}
