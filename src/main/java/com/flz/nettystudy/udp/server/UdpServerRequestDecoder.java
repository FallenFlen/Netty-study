package com.flz.nettystudy.udp.server;

import com.flz.nettystudy.udp.dto.UdpMessage;
import com.flz.nettystudy.udp.dto.UdpRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class UdpServerRequestDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        // 获取发送的消息长度
        int length = msg.content().readInt();
        // 根据该长度复制一份切片出来
        ByteBuf byteBuf = msg.content().readSlice(length).retain();
        UdpMessage udpMessage = UdpMessage.builder()
                .content(byteBuf)
                .build();
        UdpRequest udpRequest = UdpRequest.builder()
                .requestMessage(udpMessage)
                .sender(msg.sender())
                .build();
        out.add(udpRequest);
    }
}
