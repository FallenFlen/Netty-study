package com.flz.nettystudy.dubborpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;

public class DubboRpcServerHandler extends ChannelInboundHandlerAdapter {
    private final HelloRpcService helloRpcService = new HelloRpcServiceImpl();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        if (StringUtil.isNullOrEmpty(message)) {
            System.out.println("客户端消息为空，wtf");
            return;
        }

        if (!message.startsWith(RpcConstant.MESSAGE_SEPARATOR)) {
            System.out.println("客户端消息格式不对");
            return;
        }

        String response = helloRpcService.hello(message.substring(RpcConstant.MESSAGE_SEPARATOR.length()));
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务器端发生异常!");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端" + ctx.channel().remoteAddress() + "加入了");
    }
}
