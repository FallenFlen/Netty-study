package com.flz.nettystudy.udp.server;

import com.flz.nettystudy.udp.dto.UdpResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UdpServerRequestEncoder extends MessageToMessageEncoder<UdpResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UdpResponse msg, List<Object> out) throws Exception {
        log.info("encode UdpResponse to DatagramPacket");
        // 编码为udp包
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeInt(msg.getResponseMessage().getContent().readableBytes());
        byteBuf.writeBytes(msg.getResponseMessage().getContent());
        // 此处的sender:客户端，receiver:服务器
        DatagramPacket datagramPacket = new DatagramPacket(byteBuf, msg.getSender(), msg.getReceiver());
        out.add(datagramPacket);
    }
}
