package com.flz.nettystudy.udp.dto;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.InetSocketAddress;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UdpResponse {
    private UdpMessage responseMessage;
    private InetSocketAddress receiver;
    private InetSocketAddress sender;

    public String toDescription() {
        ByteBuf contentByteBuf = responseMessage.getContent();
        String content = contentByteBuf.readableBytes() > 0 ? contentByteBuf.toString(CharsetUtil.UTF_8) : "";
        return String.format("[%s]->[%s]:%s", sender.toString(), receiver.toString(), content);
    }
}
