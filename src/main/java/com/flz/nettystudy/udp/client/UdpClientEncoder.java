package com.flz.nettystudy.udp.client;

import com.flz.nettystudy.udp.dto.UdpRequest;
import io.netty.buffer.ByteBuf;
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
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeInt(msg.getRequestMessage().getContent().readableBytes());
        byteBuf.writeBytes(msg.getRequestMessage().getContent());
        DatagramPacket datagramPacket = new DatagramPacket(byteBuf, msg.getSender());
        out.add(datagramPacket);
    }
}
