package com.flz.nettystudy.chatroom.core.handler;

import com.flz.nettystudy.utils.DateUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyNettyChatRoomServerHandler extends SimpleChannelInboundHandler<String> {
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String serverMessage = String.format("%s 客户端 %s 加入了\n", DateUtils.getCurrentTimeStr(), channel.remoteAddress());
        CHANNEL_GROUP.add(channel);
        CHANNEL_GROUP.writeAndFlush(serverMessage);
    }

    // 离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String serverMessage = String.format("%s 客户端 %s 离开了\n", DateUtils.getCurrentTimeStr(), channel.remoteAddress());
        CHANNEL_GROUP.remove(channel);
        CHANNEL_GROUP.writeAndFlush(serverMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        Channel currentChannel = channelHandlerContext.channel();
        // 消息转发&回显
        CHANNEL_GROUP.forEach(channel -> {
            String currentTimeStr = DateUtils.getCurrentTimeStr();
            if (currentChannel != channel) {
                channel.writeAndFlush(String.format("%s 客户端 %s 发送了消息：%s", currentTimeStr, channel.remoteAddress(), msg));
            } else {
                currentChannel.writeAndFlush(String.format("%s 你发送了消息：%s", currentTimeStr, msg));
            }
        });
    }
}
