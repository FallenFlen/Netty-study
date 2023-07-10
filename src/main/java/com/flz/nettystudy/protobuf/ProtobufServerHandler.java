package com.flz.nettystudy.protobuf;

import com.flz.nettystudy.protobuf.entity.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtobufServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Student student = (Student) msg;
        System.out.println("[ProtobufServerHandler] stu:" + student.getInfo());
    }
}
