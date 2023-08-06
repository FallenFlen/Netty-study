package com.flz.nettystudy.udp.dto;

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
}
