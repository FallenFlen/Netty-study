package com.flz.nettystudy.security;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;

public class SecurityServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    // 这玩意建议共享，不需要每个pipeline都加一个
    private RuleBasedIpFilter ruleBasedIpFilter = new RuleBasedIpFilter(
            // 127.0.0.1 网络为8位，拒绝
            new IpSubnetFilterRule("127.0.0.1", 8, IpFilterRuleType.REJECT));

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(ruleBasedIpFilter)
                .addLast(new SecurityServerHandler());
    }
}
