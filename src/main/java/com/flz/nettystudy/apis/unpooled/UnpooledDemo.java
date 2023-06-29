package com.flz.nettystudy.apis.unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UnpooledDemo {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println(buffer.readerIndex());
        System.out.println(buffer.writerIndex());
        System.out.println(buffer.capacity());
        System.out.println("-".repeat(10));
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
        System.out.println("-".repeat(10));
        System.out.println(buffer.readerIndex());
    }
}
