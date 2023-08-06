package com.flz.nettystudy.udp.server;

import com.flz.nettystudy.udp.dto.UdpResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UdpServerRequestEncoder extends MessageToMessageDecoder<UdpResponse> {
    @Override
    protected void decode(ChannelHandlerContext ctx, UdpResponse msg, List<Object> out) throws Exception {
        log.info("decode UdpResponse to DatagramPacket");
        // 编码为udp包
        DatagramPacket datagramPacket = new DatagramPacket(msg.getResponseMessage().getContent(), msg.getReceiver(), msg.getSender());
        out.add(datagramPacket);
    }
}
