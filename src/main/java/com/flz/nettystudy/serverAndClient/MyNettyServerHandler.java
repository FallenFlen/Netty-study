package com.flz.nettystudy.serverAndClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MyNettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 处理读取事件
     *
     * @param ctx 上下文对象，可以拿到很多有用的信息，例如ip，channel，pipeline
     * @param msg 收到的消息，是ByteBuf类型
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketAddress clientAddress = ctx.channel().remoteAddress();
        ByteBuf message = (ByteBuf) msg;
        log.info("receive message from client ip:{},content:{}", clientAddress, message.toString(CharsetUtil.UTF_8));
    }

    /**
     * 读取事件处理完毕，响应
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello,motherfucker".getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 异常处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常将通道关闭即可
        ctx.close();
    }
}
