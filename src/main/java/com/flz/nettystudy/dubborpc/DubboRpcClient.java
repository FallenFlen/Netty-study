package com.flz.nettystudy.dubborpc;

import com.flz.nettystudy.common.base.AbstractCommonCustomClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DubboRpcClient extends AbstractCommonCustomClient {
    private final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private DubboRpcClientHandler handler;

    public DubboRpcClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer, DubboRpcClientHandler handler) {
        super(host, port, channelInitializer);
        this.handler = handler;
    }

    // 通过jdk动态代理获取rpc调用接口实例
    @SuppressWarnings("unchecked")
    public <T> T getRpcRemoteClient(Class<T> serviceClass, String messageSeparator) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass},
                (proxy, method, args) -> {
                    if (!isConnected()) {
                        connect();
                    }
                    // 拼接协议头
                    System.out.println("2.设置rpc请求参数");
                    this.handler.setParam(messageSeparator + " " + args[0]);
                    return EXECUTOR.submit(this.handler).get();
                });
    }

    @Override
    public String getEndpointDescription() {
        return "DubboRpcClient";
    }

    public static void main(String[] args) {
        DubboRpcClientHandler dubboRpcClientHandler = new DubboRpcClientHandler();
        DubboRpcClient dubboRpcClient = new DubboRpcClient("127.0.0.1", 9969,
                new DubboRpcClientInitializer(dubboRpcClientHandler), dubboRpcClientHandler);
        HelloRpcService rpcRemoteClient = dubboRpcClient.getRpcRemoteClient(HelloRpcService.class, RpcConstant.MESSAGE_SEPARATOR);
        String response = rpcRemoteClient.hello("respect");
        System.out.println("Rpc响应：" + response);
    }
}
