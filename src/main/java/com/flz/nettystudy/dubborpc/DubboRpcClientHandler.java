package com.flz.nettystudy.dubborpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;

@Setter
@Getter
public class DubboRpcClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {
    private ChannelHandlerContext channelHandlerContext;
    // 2.设置请求参数
    private String param; // rpc请求参数
    private String result; // 响应结果

    // 1.设置全局ChannelHandlerContext
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("1.设置全局ChannelHandlerContext");
        this.channelHandlerContext = ctx;
    }

    // 4.收到服务器响应
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("4.收到服务器响应");
        result = msg.toString();
        // 拿到响应结果后唤醒被挂起的线程
        notifyAll();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端发生异常!");
        cause.printStackTrace();
        ctx.close();
    }

    // 3.该方法被代理对象调用，携带参数执行真正的rpc请求
    @Override
    public synchronized String call() throws Exception {
        System.out.println("3.该方法被代理对象调用，携带参数执行真正的rpc请求");
        // 请求
        channelHandlerContext.channel().writeAndFlush(param);
        // 请求完之后挂起当前线程，等待结果到来后被唤醒
        wait();
        // 此时拿到结果了，返回
        return result;
    }
}
