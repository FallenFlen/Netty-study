package com.flz.nettystudy.dubborpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DubboRpcClient {
    private final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private DubboRpcClientHandler handler;
    private ChannelInitializer<SocketChannel> channelInitializer;
    private String host;
    private int port;
    private NioEventLoopGroup group;
    private Bootstrap bootstrap;
    private boolean connected;

    public DubboRpcClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer, DubboRpcClientHandler handler) {
        this.channelInitializer = channelInitializer;
        this.handler = handler;
        this.host = host;
        this.port = port;
    }

    // 通过jdk动态代理获取rpc调用接口实例
    @SuppressWarnings("unchecked")
    public <T> T getRpcRemoteClient(Class<T> serviceClass, String messageSeparator) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass},
                (proxy, method, args) -> {
                    if (!connected) {
                        connect();
                    }
                    // 拼接协议头
                    String param = messageSeparator + " " + args[0];
                    System.out.println("2.设置rpc请求参数:" + param);
                    this.handler.setParam(param);
                    return EXECUTOR.submit(this.handler).get();
                });
    }

    public void connect() throws InterruptedException {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();

        try {
            this.bootstrap.group(this.group)
                    .channel(NioSocketChannel.class)
                    .handler(this.channelInitializer)
                    .connect(this.host, this.port)
                    .sync()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println("Rpc客户端启动成功!");
                        }
                    });
            this.connected = true;
        } catch (Throwable throwable) {
            System.out.println("Rpc客户端启动异常!");
            throw throwable;
        }
    }

    public static void main(String[] args) {
        DubboRpcClientHandler dubboRpcClientHandler = new DubboRpcClientHandler();
        DubboRpcClientInitializer dubboRpcClientInitializer = new DubboRpcClientInitializer(dubboRpcClientHandler);
        DubboRpcClient dubboRpcClient = new DubboRpcClient("127.0.0.1", 9969, dubboRpcClientInitializer, dubboRpcClientHandler);
        HelloRpcService rpcRemoteClient = dubboRpcClient.getRpcRemoteClient(HelloRpcService.class, RpcConstant.MESSAGE_SEPARATOR);
        String response = rpcRemoteClient.hello("respect");
        System.out.println("Rpc响应：" + response);
    }
}
