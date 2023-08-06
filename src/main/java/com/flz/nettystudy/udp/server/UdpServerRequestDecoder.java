package com.flz.nettystudy.udp.server;

import com.flz.nettystudy.common.utils.JsonUtils;
import com.flz.nettystudy.udp.dto.UdpMessage;
import com.flz.nettystudy.udp.dto.UdpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UdpServerRequestDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        UdpMessage udpMessage = UdpMessage.builder()
                .content(msg.content())
                .build();
        UdpRequest udpRequest = UdpRequest.builder()
                .requestMessage(udpMessage)
                .sender(msg.sender())
                .build();
        log.info("encode DatagramPacket to UdpRequest:{}", JsonUtils.silentMarshal(udpRequest));
        out.add(udpRequest);
    }
}
