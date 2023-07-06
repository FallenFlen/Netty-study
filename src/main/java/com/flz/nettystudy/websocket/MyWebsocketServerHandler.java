package com.flz.nettystudy.websocket;

import com.flz.nettystudy.common.utils.DateUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MyWebsocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Channel channel = channelHandlerContext.channel();
        System.out.println("[ws]收到来自客户端的消息:" + textWebSocketFrame.text());
        channel.writeAndFlush(new TextWebSocketFrame("当前服务器时间:" + DateUtils.getCurrentTimeStr()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // asLongText():每一个channel的唯一标识
        // asShortText():每一个channel的唯一标识asLongText()的后缀部分，可能重复
        System.out.println("客户端加入了:" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端离开了:" + ctx.channel().id().asLongText());
    }
}
