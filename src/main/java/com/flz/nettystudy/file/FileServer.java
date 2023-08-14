package com.flz.nettystudy.file;

import com.flz.nettystudy.common.base.tcp.AbstractCommonCustomTcpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class FileServer extends AbstractCommonCustomTcpServer {
    protected FileServer(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        super(port, channelInitializer);
    }

    public static void main(String[] args) {
        new FileServer(9002, new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new LineBasedFrameDecoder(8192))
                        .addLast(new StringDecoder())
                        .addLast(new StringEncoder())
                        .addLast(new ChunkedWriteHandler()) // 支持文件分片传输
                        .addLast(new FileServerHandler());
            }
        }).start();
    }
}
