package com.flz.nettystudy.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;

import java.io.FileInputStream;

import static com.flz.nettystudy.common.Constants.CR;

public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    // msg:文件路径，客户端传递文件名，向服务器请求一个文件
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (StringUtil.isNullOrEmpty(msg)) {
            ctx.writeAndFlush("File path is empty" + CR);
            return;
        }

        try (FileInputStream fileInputStream = (FileInputStream) FileServerHandler.class.getResourceAsStream("/" + msg)) {
            if (fileInputStream == null) {
                ctx.writeAndFlush("File is not existed:" + msg + CR);
                return;
            }

            ctx.write("You fetched a file successfully");
            // 原生支持FileRegion，文件区域类型的数据传输文件
            ctx.writeAndFlush(new DefaultFileRegion(fileInputStream.getChannel(), 0, fileInputStream.available()));
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
