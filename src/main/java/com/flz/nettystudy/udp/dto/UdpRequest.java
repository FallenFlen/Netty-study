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
public class UdpRequest {
    private UdpMessage requestMessage;
    private InetSocketAddress sender;
}
