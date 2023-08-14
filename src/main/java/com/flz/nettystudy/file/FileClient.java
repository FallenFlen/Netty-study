package com.flz.nettystudy.file;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpBlockedClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class FileClient extends AbstractCommonCustomTcpBlockedClient {
    public FileClient(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(host, port, channelInitializer);
    }

    public static void main(String[] args) throws Throwable {
        new FileClient("127.0.0.1", 9002, new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new LineBasedFrameDecoder(8192))
                        .addLast(new StringDecoder())
                        .addLast(new StringEncoder())
                        .addLast(new ChunkedWriteHandler()) // 支持文件分片传输
                        .addLast(new FileClientHandler());
            }
        }).connect();
    }
}
