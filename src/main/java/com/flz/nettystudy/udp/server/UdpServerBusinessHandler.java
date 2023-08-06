package com.flz.nettystudy.udp.server;

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
public class UdpServerBusinessHandler extends SimpleChannelInboundHandler<UdpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UdpRequest msg) throws Exception {
        // do business
        log.info("receive client udp request:{}", msg.toDescription());
        // create UdpResponse and response it
        UdpMessage udpMessage = UdpMessage.builder()
                .content(Unpooled.copiedBuffer("Your udp message has been processed", CharsetUtil.UTF_8))
                .build();
        UdpResponse response = UdpResponse.builder()
                .receiver((InetSocketAddress) ctx.channel().localAddress())
                // (InetSocketAddress) ctx.channel().remoteAddress() 只有在连接建立后才能使用，因为udp面向非连接，首次连接之前无法使用
                .sender(msg.getSender())
                .responseMessage(udpMessage)
                .build();
        ctx.channel().writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
