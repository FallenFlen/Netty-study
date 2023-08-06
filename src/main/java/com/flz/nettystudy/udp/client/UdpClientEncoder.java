package com.flz.nettystudy.udp.client;

import com.flz.nettystudy.udp.dto.UdpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UdpClientEncoder extends MessageToMessageEncoder<UdpRequest> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UdpRequest msg, List<Object> out) throws Exception {
        log.info("encode UdpRequest to DatagramPacket");
        DatagramPacket datagramPacket = new DatagramPacket(msg.getRequestMessage().getContent(), msg.getSender());
        out.add(datagramPacket);
    }
}
