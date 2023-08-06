package com.flz.nettystudy.udp.client;

import com.flz.nettystudy.udp.dto.UdpMessage;
import com.flz.nettystudy.udp.dto.UdpResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UdpClientDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf content = msg.content();
        int length = content.readInt();
        ByteBuf byteBuf = content.readSlice(length).retain();
        UdpResponse udpResponse = UdpResponse.builder()
                .sender(msg.sender())
                .receiver(msg.recipient())
                .responseMessage(UdpMessage.builder()
                        .content(byteBuf)
                        .build())
                .build();
        log.info("decode DatagramPacket to UdpResponse");
        out.add(udpResponse);
    }
}
