package com.flz.nettystudy.chatroom.core.handler;

import com.flz.nettystudy.utils.DateUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MyNettyChatRoomServerHandler extends SimpleChannelInboundHandler<String> {
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    // 上线通知给其他客户端
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String currentTimeStr = DateUtils.getCurrentTimeStr();
        String serverMessage = String.format("%s 客户端 %s 加入了\n", currentTimeStr, channel.remoteAddress());
        // 先给其他客户端推送消息
        CHANNEL_GROUP.writeAndFlush(serverMessage);
        // 再把当前channel加入，防止上线消息推送给自己
        CHANNEL_GROUP.add(channel);
        // 推送特殊消息给当前channel，提示他已上线
        channel.writeAndFlush(String.format("%s 你已加入成功", currentTimeStr));
        // 服务器本地显示
        System.out.println(serverMessage);
        // 维护channel map
        CHANNEL_MAP.put(channel.remoteAddress().toString(), channel);
    }

    // 离线通知给其他客户端
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String serverMessage = String.format("%s 客户端 %s 离开了\n", DateUtils.getCurrentTimeStr(), channel.remoteAddress());
        CHANNEL_GROUP.remove(channel);
        CHANNEL_GROUP.writeAndFlush(serverMessage);
        // 服务器本地显示
        System.out.println(serverMessage);
        // 维护channel map
        CHANNEL_MAP.remove(channel.remoteAddress().toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) {
        Channel currentChannel = channelHandlerContext.channel();
        String currentTimeStr = DateUtils.getCurrentTimeStr();
        SocketAddress remoteAddress = currentChannel.remoteAddress();
        // 消息回显给客户端
        responseToSelfMsg(currentChannel, msg, currentTimeStr);
        // 消息转发
        transferChatMsg(currentChannel, currentTimeStr, remoteAddress, msg);
        // 服务器本地显示
        displayInServerLocally(msg, currentTimeStr, remoteAddress);
    }

    private void displayInServerLocally(String msg, String currentTimeStr, SocketAddress remoteAddress) {
        if (!isPrivateChat(msg)) {
            System.out.printf("%s 收到来自客户端 %s 的消息：%s", currentTimeStr, remoteAddress, msg);
            return;
        }

        String[] splitResult = splitMsg(msg);
        System.out.printf("%s 收到来自客户端%s-客户端%s的私聊消息：%s", currentTimeStr, remoteAddress, splitResult[0], msg);
    }

    private boolean isPrivateChat(String msg) {
        return msg.startsWith("#") && msg.lastIndexOf(" ") >= 0;
    }

    private void responseToSelfMsg(Channel currentChannel, String msg, String currentTimeStr) {
        if (!isPrivateChat(msg)) {
            currentChannel.writeAndFlush(String.format("%s 你发送了消息：%s", currentTimeStr, msg));
            return;
        }

        String[] splitResult = splitMsg(msg);
        currentChannel.writeAndFlush(String.format("%s 你给%s发送了私聊消息：%s", currentTimeStr, splitResult[0], splitResult[1]));
    }

    private String[] splitMsg(String msg) {
        int contentSplitIndex = msg.lastIndexOf(" ");
        String userKey = msg.substring(1, contentSplitIndex);
        String content = msg.substring(contentSplitIndex + 1);
        return new String[]{userKey, content};
    }

    private void transferChatMsg(Channel currentChannel, String currentTimeStr, SocketAddress remoteAddress, String msg) {
        if (!isPrivateChat(msg)) {
            // 消息转发
            CHANNEL_GROUP.forEach(channel -> {
                if (currentChannel != channel) {
                    channel.writeAndFlush(String.format("%s 客户端 %s 发送了消息：%s", currentTimeStr, remoteAddress, msg));
                }
            });
            return;
        }

        String[] splitResult = splitMsg(msg);
        Optional.ofNullable(CHANNEL_MAP.get(splitResult[0]))
                .orElseThrow(() -> new RuntimeException("Target channel not found with use key:" + splitResult[0]))
                .writeAndFlush(String.format("%s %s向你发送了消息：%s", currentTimeStr, remoteAddress, splitResult[1]));
    }
}
