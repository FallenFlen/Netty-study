package com.flz.nettystudy.file;

import com.flz.nettystudy.common.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.FileRegion;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String) {
            log.info("Server info:{}", msg);
        }

        if (msg instanceof FileRegion) {
            log.info("Server has sent you a file");
            FileRegion fileRegion = (FileRegion) msg;
            readFileContent(fileRegion);
        }
    }

    private void readFileContent(FileRegion fileRegion) {
        File tmp = null;
        try {
            tmp = File.createTempFile("tmp", "txt");
            // 通过FileRegion写入临时文件
            try (FileOutputStream fileOutputStream = new FileOutputStream(tmp)) {
                FileChannel channel = fileOutputStream.getChannel();
                fileRegion.transferTo(channel, 0);
            }

            // 从临时文件读取内容
            try (FileInputStream fileInputStream = new FileInputStream(tmp)) {
                byte[] buffer = new byte[1024];
                int length = -1;
                StringBuilder sb = new StringBuilder();
                while ((length = fileInputStream.read(buffer, 0, length)) != -1) {
                    sb.append(new String(buffer, StandardCharsets.UTF_8)).append("\n");
                }
                log.info("full file content:{}", sb);
            }
        } catch (Exception e) {
            log.error("fetch file content failed:", e);
        } finally {
            if (tmp != null) {
                tmp.delete();
            }
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("test.txt" + Constants.CR);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
