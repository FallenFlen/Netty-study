package com.flz.nettystudy.protobuf;

import com.flz.nettystudy.protobuf.entity.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufClientHandler extends SimpleChannelInboundHandler<Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Student student) throws Exception {
        System.out.println("[ProtobufClientHandler] stu:" + student.getInfo());
    }
}
