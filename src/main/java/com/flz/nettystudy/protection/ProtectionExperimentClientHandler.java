package com.flz.nettystudy.protection;

import com.flz.nettystudy.common.utils.DateUtils;
import com.flz.nettystudy.common.utils.RandomUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ProtectionExperimentClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String responseMsg = msg.toString(StandardCharsets.UTF_8);
        System.out.println(DateUtils.getCurrentTimeStr() + " " + responseMsg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello服务器" + RandomUtils.randomUUID(), CharsetUtil.UTF_8));
        ctx.channel().eventLoop().scheduleAtFixedRate(() -> {
            ByteBuf msg = Unpooled.copiedBuffer("hello服务器" + RandomUtils.randomUUID(), CharsetUtil.UTF_8);
            ctx.channel().writeAndFlush(msg);
        }, 0L, 250L, TimeUnit.MILLISECONDS);

    }
}
