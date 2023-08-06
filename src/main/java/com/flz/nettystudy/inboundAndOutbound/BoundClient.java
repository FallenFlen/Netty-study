package com.flz.nettystudy.inboundAndOutbound;

import com.flz.nettystudy.common.base.tcp.BaseTcpClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;

public class BoundClient extends BaseTcpClient {
    public BoundClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void doConnect() throws Throwable {
        ChannelFuture channelFuture = this.bootstrap.group(this.group)
                .channel(NioSocketChannel.class)
                .handler(new BoundClientInitializer())
                .connect(this.host, this.port)
                .sync()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("[ProtobufClient] Started");
                    }
                });

        channelFuture.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws Throwable {
        new BoundClient("127.0.0.1", 9967).connect();
    }
}
