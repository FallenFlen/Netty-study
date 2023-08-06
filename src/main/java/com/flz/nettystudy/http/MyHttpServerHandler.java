package com.flz.nettystudy.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.StandardCharsets;

/**
 * SimpleChannelInboundHandler:简单ChannelInboundHandler的实现，实现了一些通用的方法逻辑，并提供了channelRead0模板方法做我们自定义业务
 * HttpObject:netty提供的封装http报文的接口
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        String ip = channelHandlerContext.channel().remoteAddress().toString();
        System.out.printf("client ip:%s,current http message type:%s%n", ip, httpObject.getClass());

        if (httpObject instanceof HttpRequest) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,I am http server".getBytes(StandardCharsets.UTF_8));
            // DefaultFullHttpResponse:netty提供的http响应报文封装
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
//            channelHandlerContext.writeAndFlush(response);
            channelHandlerContext.channel().writeAndFlush(response);
        }
    }
}
